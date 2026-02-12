package com.tummosoft;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

@Version(1.01f)
@ShortName("EncryptionHelper")
public class EncryptionHelper {

    private BA ba;
    private KeyPair keyPair;
    private RSA rsa = null;
    private AsymmetricCrypto ecies = null;

    public void InitRSA(final BA ba, String privateKeyBase64, String publicKeyBase64) {
        this.ba = ba;
        keyPair = SecureUtil.generateKeyPair("RSA");
        createKeyRSA(privateKeyBase64, publicKeyBase64);
    }

    public void InitECIES(final BA ba, String privateKeyBase64, String publicKeyBase64) {
        this.ba = ba;
        keyPair = SecureUtil.generateKeyPair("EC");
        createKeyECIES(privateKeyBase64, publicKeyBase64);
    }

    private void createKeyECIES(String privateKeyBase64, String publicKeyBase64) {

        if ((privateKeyBase64 != null) && (publicKeyBase64 != null)) {
            ecies = new AsymmetricCrypto(
                    "ECIES", SecureUtil.generatePrivateKey("EC", java.util.Base64.getDecoder().decode(privateKeyBase64)),
                    SecureUtil.generatePublicKey("EC", java.util.Base64.getDecoder().decode(publicKeyBase64)));
        } else if ((privateKeyBase64 == null) && (publicKeyBase64 == null)) {
            ecies = new AsymmetricCrypto("ECIES", keyPair.getPrivate(), keyPair.getPublic());
        }
    }

    private void createKeyRSA(String privateKeyBase64, String publicKeyBase64) {
        if ((privateKeyBase64 != null) && (publicKeyBase64 != null)) {
            rsa = new RSA(privateKeyBase64, publicKeyBase64);
        } else if ((privateKeyBase64 == null) && (publicKeyBase64 != null)) {
            rsa = new RSA(null, publicKeyBase64);
        } else if ((privateKeyBase64 != null) && (publicKeyBase64 == null)) {
            rsa = new RSA(privateKeyBase64, null);
        } else if ((privateKeyBase64 == null) && (publicKeyBase64 == null)) {
            rsa = new RSA(keyPair.getPrivate(), keyPair.getPublic());
        }
    }

    public String GetPublicKeyBase64() {
        if (rsa != null) {
            return rsa.getPublicKeyBase64();
        } else if (ecies != null) {
            return ecies.getPublicKeyBase64();
        } else {
            return null;
        }        
    }

    public String GetPrivateKeyBase64() {
         if (rsa != null) {
            return rsa.getPrivateKeyBase64();
        } else if (ecies != null) {
            return ecies.getPrivateKeyBase64();
        } else {
            return null;
        }        
    }

    public String Encrypt(String originalText) {
        byte[] encrypted = null;
         if (rsa != null) {
            encrypted = rsa.encrypt(originalText, KeyType.PublicKey);
        } else if (ecies != null) {
            encrypted = ecies.encrypt(originalText, KeyType.PublicKey);        
        }        
        
        String cipherBase64 = Base64.getEncoder().encodeToString(encrypted);
        return cipherBase64;
    }

    public String Decrypt(String encrypted) {
        byte[] decrypted = null;
        
        if (rsa != null) {
            decrypted = rsa.decrypt(encrypted, KeyType.PrivateKey);
        } else if (ecies != null) {
            decrypted = ecies.decrypt(encrypted, KeyType.PrivateKey);
        }

        return new String(decrypted);
    }

}
