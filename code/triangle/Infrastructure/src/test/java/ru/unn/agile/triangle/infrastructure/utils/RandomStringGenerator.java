package ru.unn.agile.triangle.infrastructure.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public final class RandomStringGenerator {
    private static final String CHARACTERS_SET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz _()+=!";

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MIN_STRING_LENGTH = 5;
    private static final int MAX_STRING_LENGTH = 80;

    public static String randomString(final int length) {
        final String cs = CHARACTERS_SET;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int charIndex = RANDOM.nextInt(cs.length());
            sb.append(cs.charAt(charIndex));
        }
        return sb.toString();
    }

    public static String randomString() {
        int stringLength = MIN_STRING_LENGTH + RANDOM.nextInt(
                MAX_STRING_LENGTH - MIN_STRING_LENGTH);
        return randomString(stringLength);
    }

    public static List<String> randomStrings(final int number) {
        ArrayList<String> resultList = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            resultList.add(RandomStringGenerator.randomString());
        }
        return resultList;
    }

    private RandomStringGenerator() {
    }
}
