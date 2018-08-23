package br.com.connector.aleson.android.connector.cryptography.domain;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.com.connector.aleson.android.connector.cryptography.Crypto;

/**
 * Created by Aleson on 6/19/2018.
 */

public class Safe {

    private String content;

    public String getArg0() {
        try {
            return Crypto.AESdecrypt(content);
        } catch (InvalidAlgorithmParameterException e) {
           return "";
        } catch (InvalidKeyException e) {
            return "";
        } catch (BadPaddingException e) {
            return "";
        } catch (IllegalBlockSizeException e) {
            return "";
        }
    }

    public void setContent(Object arg0) {
        //AES
        try {
            this.content = Crypto.AESencrypt(new Gson().toJson(arg0));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public void setContent(Object arg0, String publicKey){
        //RSA
        try {
            this.content = Crypto.RSAecnrypt(new Gson().toJson(arg0), publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
