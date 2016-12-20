package ru.unn.agile.RomanArabicConverter.Model;

import static ru.unn.agile.RomanArabicConverter.Model.MapData.*;

public class RomanToArabicNumberConverter {
    private void validateRomanNumber(final String romanNumber) {
        RomanNumberValidator validator = new RomanNumberValidator();
        validator.validate(romanNumber);
    }

    public int convert(final String romanNumber) {
        validateRomanNumber(romanNumber);
        int arabicNumber = 0;
        int i = 0;
        while (i < romanNumber.length()) {
            if (i < romanNumber.length() - 1
                    && ROMAN_TO_ARABIC_MAP.containsKey(romanNumber.substring(i, i + 2))) {
                arabicNumber += ROMAN_TO_ARABIC_MAP.get(romanNumber.substring(i, i + 2));
                i++;
            } else {
                arabicNumber += ROMAN_TO_ARABIC_MAP.get(String.valueOf(romanNumber.charAt(i)));
            }
            i++;
        }
        return arabicNumber;
    }
}
