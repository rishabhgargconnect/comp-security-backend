package com.example.ComputerSecurity.utils;

import java.util.Random;

public class RandomStringGenerator {

    final static int leftLimit = 48; // numeral '0'
    final static int rightLimit = 122; // letter 'z'
    final static int targetStringLength = 10;
    final static Random random = new Random();

    public static String getRandomString() {
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

}
