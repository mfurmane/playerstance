package pl.mfurmane.rest.utils;

import org.springframework.security.crypto.encrypt.Encryptors;

public class Decryptor {

    private static String salt = "AAAA";

    private Decryptor() {}

    public static String decrypt(String input, String secret) {
        return Encryptors.delux(secret, salt).decrypt(input);
//        return input;
    }

    public static String encrypt(String input, String secret) {
        return Encryptors.delux(secret, salt).encrypt(input);
//        return input;
    }
}
