//package com.project.sangil_be.service;
//
//import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
//import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class JasyptTest {
//
//    @Test
//    public void jasyt_test() {
//        String plaintText = "https://72a36c58319744a0ad0eae1529653c38@o1260406.ingest.sentry.io/6440990";
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword("sangilbb");
////        config.setPassword(System.getenv("JASYPT_PASSWORD"));
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
//        config.setStringOutputType("base64");
//        encryptor.setConfig(config);
//
//        String encryptText = encryptor.encrypt(plaintText);
//        System.out.println(encryptText);
//        String decryptText = encryptor.decrypt(encryptText);
//        System.out.println(decryptText);
//        assertThat(plaintText).isEqualTo(decryptText);
//
//    }
//
//}