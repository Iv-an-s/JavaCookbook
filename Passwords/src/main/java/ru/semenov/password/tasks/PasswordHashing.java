package ru.semenov.password.tasks;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "password";

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

        byte[] md5Bytes = md5.digest(password.getBytes());
        byte[] sha1Bytes = sha1.digest(password.getBytes());

        StringBuilder stringBuilder1 = new StringBuilder();
        for (byte b : md5Bytes) {
            stringBuilder1.append(String.format("%02X", b));
        }
        System.out.println("хеш пароля по алгоритму md5: " + stringBuilder1.toString());

        StringBuilder stringBuilder2 = new StringBuilder();
        for (byte b : sha1Bytes) {
            stringBuilder2.append(String.format("%02x", b));
        }
        System.out.println("хеш пароля по алгоритму sha-1: " + stringBuilder2.toString());
    }
}
