package br.com.connector.aleson.android.connector.cryptography.domain;

/**
 * Created by Aleson on 6/16/2018.
 */

public class Aes {
    
    private String salt = null;
    private byte[] iv = null;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    private String secret = null;

}
