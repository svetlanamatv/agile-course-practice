package ru.unn.agile.RomanArabicConverter.Model;

import java.util.TreeMap;

public final class MapData {
    public static final int MAX_NUMBER = 3999;

    private static final int ONE = 1;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final int FORTY = 40;
    private static final int FIFTY = 50;
    private static final int NINETY = 90;
    private static final int HUNDRED = 100;
    private static final int FOUR_HUNDRED = 400;
    private static final int FIVE_HUNDRED = 500;
    private static final int NINE_HUNDRED = 900;
    private static final int THOUSAND = 1000;

    public static final TreeMap<Integer, String> ARABIC_TO_ROMAN_MAP = new TreeMap<>();
    static {
        ARABIC_TO_ROMAN_MAP.put(ONE, "I");
        ARABIC_TO_ROMAN_MAP.put(FOUR, "IV");
        ARABIC_TO_ROMAN_MAP.put(FIVE, "V");
        ARABIC_TO_ROMAN_MAP.put(NINE, "IX");
        ARABIC_TO_ROMAN_MAP.put(TEN, "X");
        ARABIC_TO_ROMAN_MAP.put(FORTY, "XL");
        ARABIC_TO_ROMAN_MAP.put(FIFTY, "L");
        ARABIC_TO_ROMAN_MAP.put(NINETY, "XC");
        ARABIC_TO_ROMAN_MAP.put(HUNDRED, "C");
        ARABIC_TO_ROMAN_MAP.put(FOUR_HUNDRED, "CD");
        ARABIC_TO_ROMAN_MAP.put(FIVE_HUNDRED, "D");
        ARABIC_TO_ROMAN_MAP.put(NINE_HUNDRED, "CM");
        ARABIC_TO_ROMAN_MAP.put(THOUSAND, "M");
    }

    public static final TreeMap<String, Integer> ROMAN_TO_ARABIC_MAP = new TreeMap<>();
    static {
        ROMAN_TO_ARABIC_MAP.put("I", ONE);
        ROMAN_TO_ARABIC_MAP.put("IV", FOUR);
        ROMAN_TO_ARABIC_MAP.put("V", FIVE);
        ROMAN_TO_ARABIC_MAP.put("IX", NINE);
        ROMAN_TO_ARABIC_MAP.put("X", TEN);
        ROMAN_TO_ARABIC_MAP.put("XL", FORTY);
        ROMAN_TO_ARABIC_MAP.put("L", FIFTY);
        ROMAN_TO_ARABIC_MAP.put("XC", NINETY);
        ROMAN_TO_ARABIC_MAP.put("C", HUNDRED);
        ROMAN_TO_ARABIC_MAP.put("CD", FOUR_HUNDRED);
        ROMAN_TO_ARABIC_MAP.put("D", FIVE_HUNDRED);
        ROMAN_TO_ARABIC_MAP.put("CM", NINE_HUNDRED);
        ROMAN_TO_ARABIC_MAP.put("M", THOUSAND);
    }

    private MapData() {
    }
}
