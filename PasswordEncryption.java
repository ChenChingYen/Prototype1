package com.example.prototype;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {

    String encryptedpassword = null;

    public String encryption(String passwordInput){

        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(passwordInput.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedpassword = s.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println(e);
        }
        return encryptedpassword;

    }
}
