package pl.mfurmane.rest.utils;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class Decryptor {

    private String password = "tmp";
    private String salt = "AAAA";
    private AesBytesEncryptor encryptor;

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
        System.out.println("\n"+"#: "+new String(encode)+"\n"+new String(encrypt)+"\n"+new String(encode1)+"\n");
        return new String(encode1);
    }

    public String deHex(String encryptedText) {

        byte[] decode = Hex.decode(encryptedText);
        byte[] decrypt = encryptor.decrypt(decode);
//        String decode1 = Utf8.decode(decrypt);
        byte[] decode1 = Base64.getDecoder().decode(decrypt);
        System.out.println("\n"+"@: "+new String(decode)+"\n"+new String(decrypt)+"\n"+decode1+"\n");
        return new String(decode1);
    }

    public static void main(String[] args) {
        Decryptor d = new Decryptor();
        String encrypt = d.encrypt("mfurmane9@gmail.com");
        System.out.println(encrypt);
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
