package ru.unn.agile.RomanArabicConverter.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RomanToArabicNumberConverterTest {

    @Test
    public void convertITo1() {
        assertEquals(1, convertRomanToArabic("I"));
    }

    @Test
    public void convertVTo5() {
        assertEquals(5, convertRomanToArabic("V"));
    }

    @Test
    public void convertVITo6() {
        assertEquals(6, convertRomanToArabic("VI"));
    }

    @Test
    public void convertIVTo4() {
        assertEquals(4, convertRomanToArabic("IV"));
    }

    @Test
    public void convertMDCLXVITo1666() {
        assertEquals(1666, convertRomanToArabic("MDCLXVI"));
    }

    @Test
    public void convertMCDXLIVTo1444() {
        assertEquals(1444, convertRomanToArabic("MCDXLIV"));
    }

    @Test
    public void convertEmptyTo0() {
        assertEquals(0, convertRomanToArabic(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertX70V() {
        convertRomanToArabic("X70V");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertIIII() {
        convertRomanToArabic("IIII");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertXLC() {
        convertRomanToArabic("XLC");
    }

    @Test
    public void convertCXC() {
        assertEquals(190, convertRomanToArabic("CXC"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertCXCL() {
        convertRomanToArabic("CXCL");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertXD() {
        convertRomanToArabic("XD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertIIX() {
        convertRomanToArabic("IIX");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertCXXC() {
        convertRomanToArabic("CXXC");
    }

    @Test
    public void convertMMMCMXCIXTo3999() {
        assertEquals(3999, convertRomanToArabic("MMMCMXCIX"));
    }

    private int convertRomanToArabic(final String romanNumber) {
        RomanToArabicNumberConverter converter = new RomanToArabicNumberConverter();
        return converter.convert(romanNumber);
    }
}
