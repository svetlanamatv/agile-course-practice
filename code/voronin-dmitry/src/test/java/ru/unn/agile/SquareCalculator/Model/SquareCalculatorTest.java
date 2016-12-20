package ru.unn.agile.SquareCalculator.Model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class SquareCalculatorTest {

    private static final double DELTA = 0.0000001;

    private SquareCalculator calculator;

    @Before
    public void setUp() {
        calculator = new SquareCalculator();
    }

    @Test
    public void calculSquareSphereWhenRad0() {

        double result = calculator.getSphereSquare(0);

        assertEquals(0, result, DELTA);

    }

    @Test
    public void calculSquareSphereWhenRad5Dot1() {

        double result = calculator.getSphereSquare(5.1);

        assertEquals(326.851299679482, result, DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareSphereWhenRadiusNegative() {

        calculator.getSphereSquare(-5.1);

    }

    @Test
    public void calculSquareConeWhenRadius0AndRulOfCone5() {

        double result = calculator.getConeSquare(0, 5);

        assertEquals(0, result, DELTA);

    }

    @Test
    public void calculSquareConeWhenRad5AndRulOfCone0() {

        double result = calculator.getConeSquare(5, 0);

        assertEquals(78.53981633974483, result, DELTA);

    }

    @Test
    public void calculSquareConeWhenRad0AndRulOfCone0() {

        double result = calculator.getConeSquare(0, 0);

        assertEquals(0, result, DELTA);

    }

    @Test
    public void calculSquareConeWhenRad1Dot1AndRulOfCone10Dot1() {

        double result = calculator.getConeSquare(1.1, 10.1);

        assertEquals(38.70442149222625, result, DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareConeWhenRadNegativeAndRulOfCone5() {

        calculator.getConeSquare(-1, 5);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareConeWhenRad5AndRulOfConeNegative() {

        calculator.getConeSquare(5, -1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareConeWhenRaAndRulOfConeNegative() {

        calculator.getConeSquare(-1, -1);

    }

    @Test
    public void calculSquareCubeWhenLenght0() {

        double result = calculator.getCubeSquare(0);

        assertEquals(0, result, DELTA);

    }

    @Test
    public void calculSquareCubeWhenLenght5() {

        double result = calculator.getCubeSquare(5);

        assertEquals(150, result, DELTA);

    }

    @Test (expected = IllegalArgumentException.class)
    public  void calculSquareCubeWhenLenghtNegative() {

        calculator.getCubeSquare(-5);

    }

    @Test
    public void calculSquareCylinderWhenRad0AndHeght0() {

        double result = calculator.getCylinderSquare(0, 0);

        assertEquals(0, result, DELTA);

    }

    @Test
    public void calculSquareCylinderWhenRad0AndHeight5() {

        double result = calculator.getCylinderSquare(0, 5);

        assertEquals(0, result, DELTA);

    }

    @Test
    public  void calculSquareCylinderWhenRad5AndHeight0() {

        double result = calculator.getCylinderSquare(5, 0);

        assertEquals(157.07963267948966, result, DELTA);

    }

    @Test
    public  void calculSquareCylinderWhenRad5AndHeight5() {

        double result = calculator.getCylinderSquare(5, 5);

        assertEquals(314.1592653589793, result, DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public  void calculSquareCylinderWhenRadNegativeAndHeight1() {

        calculator.getCylinderSquare(-1, 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public  void calculSquareCylinderWhenRad1AndHeightNegative() {

        calculator.getCylinderSquare(1, -1);

    }

    @Test(expected = IllegalArgumentException.class)
    public  void calculSquareCylinderWhenRadAndHeightNegative() {

        calculator.getCylinderSquare(-1, -1);

    }

    @Test
    public void calculSquareParallelepipedWhenHeight0Lenght0Width0() {

        double result = calculator.getParallelSquare(0, 0, 0);

        assertEquals(0, result, DELTA);

    }

    @Test
    public void calculSquareParallelepipedWhenHeight0Lenght2Width2() {

        double result = calculator.getParallelSquare(0, 2, 2);

        assertEquals(8, result, DELTA);

    }

    @Test
    public void calculSquareParallelepipedWhenHeight2Lenght0Width2() {

        double result = calculator.getParallelSquare(2, 0, 2);

        assertEquals(8, result, DELTA);

    }

    @Test
    public void calculSquareParallelepipedWhenHeight2Lenght2Width0() {

        double result = calculator.getParallelSquare(2, 2, 0);

        assertEquals(8, result, DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeightNegativeLenght2Width2() {

        calculator.getParallelSquare(-2, 2, 2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeight2eLenghtNegativeWidth2() {

        calculator.getParallelSquare(2, -2, 2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeight2Lenght2WidthNegative() {

        calculator.getParallelSquare(2, 2, -2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeighAndLenghtNegativeWidth2() {

        calculator.getParallelSquare(-2, -2, 2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeight2LenghtAndWidthNegative() {

        calculator.getParallelSquare(2, -2, -2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeightAndWidthNegativeLenght2() {

        calculator.getParallelSquare(-2, 2, -2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculSquareParallelepipedWhenHeightLenghtWidthNegative() {

        calculator.getParallelSquare(-2, -2, -2);

    }
}

