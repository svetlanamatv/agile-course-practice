package ru.unn.agile.RomanArabicConverter.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArabicToRomanNumberConverterTest {

    @Test
    public void convert1ToI() {
        assertEquals("I", convertArabicToRoman(1));
    }

    @Test
    public void convert5ToV() {
        assertEquals("V", convertArabicToRoman(5));
    }

    @Test
    public void convert2ToII() {
        assertEquals("II", convertArabicToRoman(2));
    }

    @Test
    public void convert1666ToMDCLXVI() {
        assertEquals("MDCLXVI", convertArabicToRoman(1666));
    }

    @Test
    public void convert4ToIV() {
        assertEquals("IV", convertArabicToRoman(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertNegative() {
        convertArabicToRoman(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convert4000ToRoman() {
        convertArabicToRoman(4000);
    }

    @Test
    public void convertZero() {
        assertEquals("", convertArabicToRoman(0));
    }

    @Test
    public void convert3999ToMMMCMXCIX() {
        assertEquals("MMMCMXCIX", convertArabicToRoman(3999));
    }

    private String convertArabicToRoman(final int arabicNumber) {
        ArabicToRomanNumberConverter converter = new ArabicToRomanNumberConverter();
        return converter.convert(arabicNumber);
    }
}
