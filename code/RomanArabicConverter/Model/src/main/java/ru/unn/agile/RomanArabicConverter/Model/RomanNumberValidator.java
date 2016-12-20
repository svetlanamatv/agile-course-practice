package ru.unn.agile.RomanArabicConverter.Model;

import static ru.unn.agile.RomanArabicConverter.Model.MapData.*;

public class RomanNumberValidator {
    public void validate(final String romanNumber) {
        checkInvalidSymbols(romanNumber);
        checkInvalidSequenceOfSymbols(romanNumber);
        checkPairsOfSymbolsFromLeftToRight(romanNumber);
        checkPairsOfSymbolsFromRightToLeft(romanNumber);
        checkThereAreNoSeveralBigSymbolsOnTheRight(romanNumber);
        checkThereAreNoSeveralLowSymbolsOnTheLeft(romanNumber);
    }

    private void checkInvalidSymbols(final String romanNumber) {
        if (!romanNumber.matches("[MDCLXVI]*")) {
            throw new IllegalArgumentException("Number contains invalid symbols");
        }
    }

    private void checkInvalidSequenceOfSymbols(final String romanNumber) {
        if (romanNumber.contains("IIII") || romanNumber.contains("VV")
                || romanNumber.contains("XXXX") || romanNumber.contains("LL")
                || romanNumber.contains("CCCC") || romanNumber.contains("DD")
                || romanNumber.contains("MMMM")) {
            throw new IllegalArgumentException("Number contains incorrect sequence of symbols");
        }
    }

    private void checkPairsOfSymbolsFromLeftToRight(final String romanNumber) {
        int currentNumber;
        int i = 0;
        while (i < romanNumber.length() - 1) {
            if (ROMAN_TO_ARABIC_MAP.containsKey(romanNumber.substring(i, i + 2))) {
                currentNumber = ROMAN_TO_ARABIC_MAP.get(romanNumber.substring(i, i + 2));
                i++;
            } else {
                currentNumber
                        = ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i)));
            }

            if (i < romanNumber.length() - 1
                    && ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i + 1)))
                    > currentNumber) {
                throw new IllegalArgumentException("Incorrect sequence of symbols");
            }
            i++;
        }
    }

    private void checkPairsOfSymbolsFromRightToLeft(final String romanNumber) {
        int currentNumber;
        int i = romanNumber.length() - 1;
        while (i > 0) {
            if (ROMAN_TO_ARABIC_MAP.containsKey(romanNumber.substring(i - 1, i + 1))) {
                currentNumber
                        = ROMAN_TO_ARABIC_MAP.get(romanNumber.substring(i - 1, i + 1));
                i--;
            } else {
                currentNumber
                        = ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i)));
            }

            if (i > 0
                    && ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i - 1)))
                    < currentNumber) {
                throw new IllegalArgumentException("Incorrect sequence of symbols");
            }
            i--;
        }
    }

    private void checkThereAreNoSeveralBigSymbolsOnTheRight(final String romanNumber) {
        int maxNumberOnTheRight;
        int i = 0;
        while (i < romanNumber.length() - 2) {
            if (ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i)))
                    > ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i + 1)))) {
                maxNumberOnTheRight
                        = ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i + 1)));
                i++;
                for (int j = i + 2; j < romanNumber.length(); j++) {
                    if (ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(j)))
                            > maxNumberOnTheRight) {
                        throw new IllegalArgumentException("Invalid");
                    }
                }
            }
            i++;
        }
    }

    private void checkThereAreNoSeveralLowSymbolsOnTheLeft(final String romanNumber) {
        int minNumberOnTheLeft;
        int i = romanNumber.length() - 1;
        while (i > 2) {
            if (ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i)))
                    < ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i - 1)))) {
                minNumberOnTheLeft
                        = ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i - 1)));
                i--;
                for (int j = i - 2; j > 0; j--) {
                    if (ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(j)))
                            < minNumberOnTheLeft) {
                        throw new IllegalArgumentException("Invalid");
                    }
                }
            }
            i--;
        }
    }
}
