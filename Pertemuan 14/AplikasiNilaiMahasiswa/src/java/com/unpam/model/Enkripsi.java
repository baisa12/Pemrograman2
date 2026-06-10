package com.unpam.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Enkripsi {

    public String hashMD5(String value) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes(StandardCharsets.UTF_8));

        byte[] byteData = md.digest();
        StringBuilder hexString = new StringBuilder();

        for (byte b : byteData) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}