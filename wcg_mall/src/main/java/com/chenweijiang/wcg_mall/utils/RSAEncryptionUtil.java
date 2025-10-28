package com.chenweijiang.wcg_mall.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA秘钥工具类
 * @author 枳枳
 */
public class RSAEncryptionUtil {
    private static final String ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";


    // 生成RSA密钥对，并返回包含公钥和私钥的字符串数组
    public static Map<String,String> generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        Map<String,String> map = new HashMap<>();
        map.put(PUBLIC_KEY, publicKeyString);
        map.put(PRIVATE_KEY, privateKeyString);

        return map;
    }

    // 使用公钥加密数据
    public static String encryptData(String data, String publicKeyString) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 使用私钥解密数据
    public static String decryptData(String encryptedData, String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }


    // 示例用法
//    public static void main(String[] args) throws Exception {
//        // 生成密钥对
//        KeyPair keyPair = generateKeyPair();
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//
//        // 待加密的数据
//        String originalData = "Hello, this is a secret message.";
//
//        // 使用公钥加密数据
//        String encryptedData = encryptData(originalData, publicKey);
//        System.out.println("Encrypted data: " + encryptedData);
//
//        // 使用私钥解密数据
//        String decryptedData = decryptData(encryptedData, privateKey);
//        System.out.println("Decrypted data: " + decryptedData);
//    }
}