package ru.unn.agile.ColorConverter.model;

import static ru.unn.agile.ColorConverter.model.ColorSpaces.*;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckParametersTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkNumberParametersWithoutParams() {
        CheckParameters.checkNumberParameters(new double[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNumberParameters4Params() {
        CheckParameters.checkNumberParameters(new double[]{1, 2, 3, 4});
    }

    @Test()
    public void testCheckNumberParameters() {
        assertTrue(CheckParameters.checkNumberParameters(new double[]{1, 2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectNumberParametersRGB() {
        CheckParameters.checkParameters(RGB, new double[]{-1, 100, 100, 100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersRGB1() {
        CheckParameters.checkParameters(RGB, new double[]{-1, 100, 100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersRGB2() {
        CheckParameters.checkParameters(RGB, new double[]{100, 100, 256});
    }

    @Test(expected = NumberFormatException.class)
    public void checkIncorrectParametersRGB3() {
        CheckParameters.checkParameters(RGB, new double[]{255, 255, 244.1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectNumberParametersLAB() {
        CheckParameters.checkParameters(LAB, new double[]{-1, 100, 100, 200});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersLAB1() {
        CheckParameters.checkParameters(LAB, new double[]{-1, 100, 100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersLAB2() {
        CheckParameters.checkParameters(LAB, new double[]{99, 100, -256});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersLAB3() {
        CheckParameters.checkParameters(LAB, new double[]{0, -128, 129});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersLAB4() {
        CheckParameters.checkParameters(LAB, new double[]{0, -129, 128});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectNumberParametersHSV() {
        CheckParameters.checkParameters(HSV, new double[]{-1, 100, 100, 100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersHSV1() {
        CheckParameters.checkParameters(HSV, new double[]{-1, 100, 100});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersHSV2() {
        CheckParameters.checkParameters(HSV, new double[]{100, 100, 361});
    }

    @Test(expected = NumberFormatException.class)
    public void checkIncorrectParametersHSV3() {
        CheckParameters.checkParameters(HSV, new double[]{1.2, 255, 1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIncorrectParametersHSV4() {
        CheckParameters.checkParameters(HSV, new double[]{10, 255, 1});
    }
}
