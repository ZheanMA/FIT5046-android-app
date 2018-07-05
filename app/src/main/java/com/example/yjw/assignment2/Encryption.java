package com.example.yjw.assignment2;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yjw on 2018/4/21.
 */

public class Encryption {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    //key to generate HASH code
    private static final String key = "smartER";

    public static String HmacSHA1Encrypt (String encryptText, String encryptKey) throws Exception {
        byte [] data=encryptKey.getBytes(ENCODING);
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        byte[] str = mac.doFinal(text);

        //Create Hex String
        StringBuffer hexString = new StringBuffer();
        for(int i = 0; i < str.length; i++) {
            String shaHex = Integer.toHexString(str[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }

    public static String convertSHA1 (String pw){
        try{
            return HmacSHA1Encrypt(pw, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
