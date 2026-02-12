package com.tummosoft;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
        
        
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class AesHelper {
     private static final String defaultKey = "1234567891011123";

    public static String encryptBase64(String content,String key){
        try{
            byte[] byteKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(),
                    key.getBytes()).getEncoded();
            SymmetricCrypto aes = SecureUtil.aes(byteKey);
            
            return aes.encryptBase64(content);
        }catch (Exception e){
          
        }
        return null;
    }

    public static String decryptStr(String encryptString,String key){
        try{
            byte[] byteKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(),
                    key.getBytes()).getEncoded();
            SymmetricCrypto aes = SecureUtil.aes(byteKey);
            
            return aes.decryptStr(encryptString);
        }catch (Exception e){
           
        }
        return null;
    }

}
