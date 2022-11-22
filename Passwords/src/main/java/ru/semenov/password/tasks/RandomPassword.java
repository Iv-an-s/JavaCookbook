package ru.semenov.password.tasks;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Генератор паролей
 * Требования к паролю:
 * 1) 8 символов.
 * 2) только цифры и латинские буквы разного регистра.
 * 3) обязательно должны присутствовать цифры, и буквы разного регистра.
 * Все сгенерированные пароли должны быть уникальными
 */

public class RandomPassword {
    public static final int SYMBOL_COUNT = 8;
    public static Map<Integer, String> passwordHashes = new HashMap<>();

    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
        System.out.println(passwordHashes.get(passwordHashes.size() - 1));
    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Basket[] baskets = new Basket[3];
        baskets[0] = new Basket('0', '9');
        baskets[1] = new Basket('a', 'z');
        baskets[2] = new Basket('A', 'Z');

        while (true) {
            baos.reset();
            int i = 0;
            for (; i < SYMBOL_COUNT-3; i++) {
                int index = (int) (Math.random() * 3);
                generateChar(baos, baskets, index);
            }
            for (int j = 0; j < baskets.length; j++) {
                generateChar(baos, baskets, j);
                i++;
            }
            while (i < SYMBOL_COUNT) {
                int index = (int) (Math.random() * 3);
                generateChar(baos, baskets, index);
                i++;
            }
            if (!passwordHashes.containsValue(getPasswordHash(baos.toString()))) {
                passwordHashes.put(passwordHashes.size(), getPasswordHash(baos.toString()));
                break;
            }
        }
        return baos;
    }

    private static void generateChar(ByteArrayOutputStream baos, Basket[] baskets, int index) {
        Basket basket = baskets[index];
        baos.write((char) basket.getRandom());
    }

    private static String getPasswordHash(String password) {
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = sha1.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(String.format("%02X", aByte)); // переводим в шестнадцатиричный формат
        }
        return stringBuilder.toString();
    }

    public static class Basket {
        int begin;
        int quantity;

        public Basket(char begin, char end) {
            this.begin = begin;
            this.quantity = end - begin + 1;
        }

        public int getRandom() {
            return (int) (Math.random() * quantity) + begin;
        }
    }
}


