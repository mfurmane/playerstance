package pl.mfurmane.rest.utils;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class Decryptor {

    private String password = "tmp";
    private String salt = "AAAA";
    private AesBytesEncryptor encryptor;
    private boolean test = false;

    public Decryptor() {
        initialize();
    }

    public Decryptor(String password, String salt) {
        this.password = password;
        this.salt = salt;
        initialize();
    }

    private void initialize() {
        encryptor = new AesBytesEncryptor(password.toString(), salt, KeyGenerators.secureRandom(16), AesBytesEncryptor.CipherAlgorithm.CBC);
//        this(CipherUtils.newSecretKey("PBKDF2WithHmacSHA1",
//                new PBEKeySpec(password.toCharArray(), Hex.decode(salt), 1024, 256)), ivGenerator, alg);
    }

    public String enHex(String text) {

        byte[] encode = Utf8.encode(text);
        byte[] encrypt = encryptor.encrypt(encode);
//        char[] encode1 = Hex.encode(encrypt);
        byte[] encode1 = Base64.getEncoder().encode(encrypt);
        if (test) {
            System.out.println("\n" + "#: " + new String(encode) + "\n" + new String(encrypt) + "\n" + new String(encode1) + "\n");
        }
        return new String(encode1);
    }

    public String deHex(String encryptedText) {
        byte[] decode = Base64.getDecoder().decode(encryptedText);
        byte[] decrypt = encryptor.decrypt(decode);
//        String decode1 = Utf8.decode(decrypt);
        String decode1 = Utf8.decode(decrypt);
        if (test) {
            System.out.println("\n" + "@: " + new String(decode) + "\n" + new String(decrypt) + "\n" + decode1 + "\n");
        }
        return decode1;
    }

    public static void main(String[] args) {
        Decryptor d = new Decryptor();
        d.test = true;
        String encrypt = d.encrypt("mfurmane9@gmail.com");
        String encrypt2 = d.encrypt("Aa@123456");
        String encrypt3 = d.encrypt("CE00580D6B3D1E0D7B7B8EC5EAB0BC89");
        System.out.println("login: " + encrypt);
        System.out.println("password: " + encrypt2);
        System.out.println("password2: " + encrypt3);
        String decrypt = d.decrypt(encrypt);
        System.out.println(decrypt);
    }

    public String decrypt(String input) {

//        return Encryptors.delux(secret, salt).decrypt(input);
        return deHex(input);
//        return input;
    }

    public String encrypt(String input) {
//        return Encryptors.delux(secret, salt).encrypt(input);
        return enHex(input);
//        return input;
    }
}
