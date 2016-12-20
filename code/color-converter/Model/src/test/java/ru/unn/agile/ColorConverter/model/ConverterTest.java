package ru.unn.agile.ColorConverter.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import static ru.unn.agile.ColorConverter.model.ColorSpaces.*;

public class ConverterTest {

    @Test
    public void convert1FromHSVToLAB() {
        double[] hsv = Converter.convert(HSV, LAB, new double[]{0, 0, 0});
        assertEquals(true, Arrays.equals(new double[]{0, 0, 0}, hsv));
    }

    @Test
    public void convert2FromHSVToLAB() {
        double[] hsv = Converter.convert(HSV, LAB, new double[]{360, 100, 100});
        assertEquals(true, Arrays.equals(new double[]{53.233, 80.109, 67.22}, hsv));
    }

    @Test
    public void convert3FromHSVToLAB() {
        double[] hsv = Converter.convert(HSV, LAB, new double[]{157, 14.8, 0.5});
        assertEquals(true, Arrays.equals(new double[]{0.271, 0.02, -0.005}, hsv));
    }

    @Test
    public void convert1FromLABToHSV() {
        double[] hsv = Converter.convert(LAB, HSV, new double[]{0, -128, -128});
        assertEquals(true, Arrays.equals(new double[]{220, 100, 76.078}, hsv));
    }

    @Test
    public void convert2FromLABToHSV() {
        double[] hsv = Converter.convert(LAB, HSV, new double[]{100, 128, 128});
        assertEquals(true, Arrays.equals(new double[]{15, 100, 100}, hsv));
    }

    @Test
    public void convert3FromLABToHSV() {
        double[] hsv = Converter.convert(LAB, HSV, new double[]{17, 0, -22});
        assertEquals(true, Arrays.equals(new double[]{206, 90.411, 28.627}, hsv));
    }

    @Test
    public void convert1FromHSVToRGB() {
        double[] hsv = Converter.convert(HSV, RGB, new double[]{355, 1.9, 100});
        assertEquals(true, Arrays.equals(new double[]{255, 250, 251}, hsv));
    }

    @Test
    public void convert2FromHSVToRGB() {
        double[] hsv = Converter.convert(HSV, RGB, new double[]{360, 100, 100});
        assertEquals(true, Arrays.equals(new double[]{255, 0, 0}, hsv));
    }

    @Test
    public void convert3FromHSVToRGB() {
        double[] hsv = Converter.convert(HSV, RGB, new double[]{0, 0, 0});
        assertEquals(true, Arrays.equals(new double[]{0, 0, 0}, hsv));
    }

    @Test
    public void convert1FromLABToRGB() {
        double[] hsv = Converter.convert(LAB, RGB, new double[]{42, 123, 33});
        assertEquals(true, Arrays.equals(new double[]{255, 0, 56}, hsv));
    }

    @Test
    public void convert2FromLABToRGB() {
        double[] hsv = Converter.convert(LAB, RGB, new double[]{100, 128, -128});
        assertEquals(true, Arrays.equals(new double[]{255, 137, 255}, hsv));
    }

    @Test
    public void convert3FromLABToRGB() {
        double[] hsv = Converter.convert(LAB, RGB, new double[]{100, 12, 47});
        assertEquals(true, Arrays.equals(new double[]{255, 244, 163}, hsv));
    }

    @Test
    public void convert1FromRGBToHSV() {
        double[] hsv = Converter.convert(RGB, HSV, new double[]{1, 255, 150});
        assertEquals(true, Arrays.equals(new double[]{155, 99.608, 100}, hsv));
    }

    @Test
    public void convert2FromRGBToHSV() {
        double[] hsv = Converter.convert(RGB, HSV, new double[]{0, 255, 0});
        assertEquals(true, Arrays.equals(new double[]{120, 100, 100}, hsv));
    }

    @Test
    public void convert3FromRGBToHSV() {
        double[] hsv = Converter.convert(RGB, HSV, new double[]{200, 1, 26});
        assertEquals(true, Arrays.equals(new double[]{352, 99.5, 78.431}, hsv));
    }
    @Test
    public void convert4FromRGBToHSV() {
        double[] hsv = Converter.convert(RGB, HSV, new double[]{0, 0, 0});
        assertEquals(true, Arrays.equals(new double[]{0, 0, 0}, hsv));
    }

    @Test
    public void convert5FromRGBToHSV() {
        double[] hsv = Converter.convert(RGB, HSV, new double[]{255, 255, 255});
        assertEquals(true, Arrays.equals(new double[]{0, 0, 100}, hsv));
    }

    @Test
    public void convert1FromRGBToLAB() {
        double[] hsv = Converter.convert(RGB, LAB, new double[]{100, 100, 100});
        assertEquals(true, Arrays.equals(new double[]{42.375, 0.001, -0.005}, hsv));
    }

    @Test
    public void convert2FromRGBToLAB() {
        double[] hsv = Converter.convert(RGB, LAB, new double[]{255, 0, 200});
        assertEquals(true, Arrays.equals(new double[]{57.492, 91.283, -34.317}, hsv));
    }
    @Test
    public void convert3FromRGBToLAB() {
        double[] hsv = Converter.convert(RGB, LAB, new double[]{0, 0, 0});
        assertEquals(true, Arrays.equals(new double[]{0, 0, 0}, hsv));
    }
    @Test
    public void convert4FromRGBToLAB() {
        double[] hsv = Converter.convert(RGB, LAB, new double[]{255, 255, 255});
        assertEquals(true, Arrays.equals(new double[]{100, 0.005, -0.01}, hsv));
    }
}
