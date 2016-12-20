package ru.unn.agile.RomanArabicConverter.Model;

import static ru.unn.agile.RomanArabicConverter.Model.MapData.*;

public class ArabicToRomanNumberConverter {
    private void validateArabicNumber(final int arabicNumber) {
        if (arabicNumber < 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        if (arabicNumber > MAX_NUMBER) {
            throw new IllegalArgumentException("Number must be less than 4000");
        }
    }

    public String convert(final int arabicNumber) {
        validateArabicNumber(arabicNumber);
        String romanNumber = "";
        int number = arabicNumber;
        while (number > 0) {
            romanNumber += ARABIC_TO_ROMAN_MAP.get(ARABIC_TO_ROMAN_MAP.floorKey(number));
            number -= ARABIC_TO_ROMAN_MAP.floorKey(number);
        }
        return romanNumber;
    }
}
