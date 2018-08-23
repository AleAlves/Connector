package br.com.connector.aleson.android.connector.cryptography;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import br.com.connector.aleson.android.connector.cryptography.domain.Aes;

/**
 * Created by Aleson on 5/27/2018.
 */

public class Crypto {

    private Aes aes;
    private static final int iterationCount = 2048;
    private static final int keyStrength = 256;
    private static final String AES_KDF = "PBKDF2WithHmacSHA1";
    private static final String RSA_KDF = "RSA";
    private static final String RSA_PADDING_SCHEME = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
    private static final String AES_PADDING_SCHEME = "AES/CBC/PKCS5Padding";

    private static Cipher cipher;
    private static SecretKeySpec secretKeySpec;
    private static byte[] AESKey;

    public Aes getAes() {
        return aes;
    }

    private static IvParameterSpec ivParameterSpec;
    static {
        byte[] bytes = new byte[16];
        Arrays.fill(bytes, (byte)0);
        ivParameterSpec = new IvParameterSpec(bytes);
    }

    public Crypto(){
        try {
            aes = new Aes();
            this.aes.setSecret(getSaltString(16));
            this.aes.setSalt(getSaltString(8));
            SecretKeyFactory factory = SecretKeyFactory.getInstance(AES_KDF);
            KeySpec spec = new PBEKeySpec(aes.getSecret().toCharArray(), aes.getSalt().getBytes(), iterationCount, keyStrength);
            SecretKey secretKey = factory.generateSecret(spec);
            secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            cipher = Cipher.getInstance(AES_PADDING_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            ivParameterSpec = new IvParameterSpec(cipher.getIV());
            aes.setIv(cipher.getIV());
//            Session.getInstance().setAes(aes);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    private static String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        while (salt.length() < length) {
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String AESencrypt(String plainData) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        return new String(Base64.encode(cipher.doFinal(plainData.getBytes()), Base64.DEFAULT)).replace("\n", "");
    }

    public static String AESdecrypt(String cipheredData) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return new String(cipher.doFinal(Base64.decode(cipheredData.getBytes(), 0)));
    }

    public static String AESdecrypt(String cipheredData, byte[] iv) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return new String(cipher.doFinal(Base64.decode(cipheredData.getBytes(), 0)));
    }

    public static String RSAecnrypt(String plainData, String serverPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        plainData = plainData.replace("\n","");
        serverPublicKey = serverPublicKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "").replace("\n", "");
        byte[] keyBytes = Base64.decode(serverPublicKey, 0);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(RSA_KDF);
        PublicKey pk = kf.generatePublic(spec);
        byte[] cipherText;
        Cipher cipher = Cipher.getInstance(RSA_PADDING_SCHEME);
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        try {
            cipherText = cipher.doFinal(plainData.getBytes());
        } catch (Exception e) {
            Log.e("CRYPTO", e.toString());
            cipherText = null;
        }
        return Base64.encodeToString(cipherText, 0).replace("\n", "");
    }

    public static void testRsa() throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            String pubKey = "-----BEGIN PUBLIC KEY-----\n" +
                    "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKabqyWMDJzZx7R/Ou/hVmHOeNxFSSag\n" +
                    "W44okC/aqRT1qYAG5iejQ13AgzOrMwOfke2j4obLagG+hCH1z8IV99UCAwEAAQ==\n" +
                    "-----END PUBLIC KEY-----";
            pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
            byte[] keyBytes = Base64.decode(pubKey, 0);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pk = kf.generatePublic(spec);
            byte[] cipherText;
            Cipher cipher = Cipher.getInstance(RSA_PADDING_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            cipherText = cipher.doFinal("Hello World!".getBytes());
            String crp = Base64.encodeToString(cipherText, 0);
            Log.i("CRYPTO", crp);
        } catch (Exception e) {
            Log.e("CRYPTO", e.toString());
        }
    }


    public static void test() {
        try {
//            String Test = "wow";
//            String password = Session.getInstance().getAESModel().getSecret();
//            String salt = Session.getInstance().getAESModel().getSalt();
//            byte[] decrypted = null;
//            byte[] encrypted = null;
//            byte[] iv = null;
//            byte[] saltB = salt.getBytes("Utf8");
//            int iterationCount = 2048;
//            int keyStrength = 256;
//
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltB, iterationCount, keyStrength);
//            SecretKey tmp = factory.generateSecret(spec);
//            try {
//                Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                SecretKeySpec secret = new SecretKeySpec(tmp.getEncoded(), "AES");
//                c.init(Cipher.ENCRYPT_MODE, secret);
//                encrypted = c.doFinal(Test.getBytes());
//                iv = c.getIV();
//
//                Log.w("CRYPTO", "encryptString: " + new String(Base64.encode(encrypted, Base64.DEFAULT)));
//                Log.w("CRYPTO", "encryptString iv:" + new String(Base64.encode(iv, Base64.DEFAULT)));
//                Log.w("CRYPTO", "encryptString salt:" + salt);
//                Log.w("CRYPTO", "encryptString Key:" + password);
//                c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                c.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(c.getIV()));
//                decrypted = c.doFinal(encrypted);
//
//                Log.w("CRYPTO", "decryptString: " + new String(decrypted));
//
//            } catch (Exception e) {
//                Log.w("CRYPTO", "Error1");
//                Log.w("CRYPTO", e);
//            }
            String textEncrypted = Crypto.AESencrypt("wow");
            Log.w("CRYPTO", "encryptString: " + textEncrypted);
            Log.w("CRYPTO", "decryptString:" + Crypto.AESdecrypt(textEncrypted));
            textEncrypted = Crypto.AESencrypt("wow");
            Log.w("CRYPTO", "encryptString 2: " + textEncrypted);
            Log.w("CRYPTO", "decryptString 2:" + Crypto.AESdecrypt(textEncrypted));
//            Log.w("CRYPTO", "encryptString iv:" + Base64.encodeToString(Session.getInstance().getAESModel().getIv(), 0));
//            Log.w("CRYPTO", "encryptString salt:" + Session.getInstance().getAESModel().getSalt());
//            Log.w("CRYPTO", "encryptString Key:" + Session.getInstance().getAESModel().getSecret());
        } catch (Exception e) {
            Log.w("CRYPTO", "Error2");
            Log.w("CRYPTO", e);
        }
    }
}
