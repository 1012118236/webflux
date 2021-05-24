package com.example.webflux.utils;

import java.security.*;
import java.util.concurrent.ThreadLocalRandom;

public class SecretUtil {


    public static void hehe(){
        // 创建 KeyPairGenerator对象，指定加密算法
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 可以初始化一些配置：密钥长度，随机数。
        // 配置随机串，加强加密安全性
        // 声明随机串，可以指定种子 SecureRandom(byte[] seed)
        SecureRandom random = new SecureRandom();
        keyPairGenerator.initialize(512, random);
        // 生成密钥
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公私密钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("publicKey");
        System.out.println(publicKey);
        System.out.println("privateKey");
        System.out.println(privateKey);
    }

    public static void main(String[] args) {

        System.out.println(1);

    }
}
