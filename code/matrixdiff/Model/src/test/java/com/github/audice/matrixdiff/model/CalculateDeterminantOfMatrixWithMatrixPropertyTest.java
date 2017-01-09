package com.github.audice.matrixdiff.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculateDeterminantOfMatrixWithMatrixPropertyTest {
    private int dimension = 3;
    private float[] testArray = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    private float delta = 0.001f;

    @Test
    public void canCreateSquareMatrix() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertNotNull(squareMatrix);
    }

    @Test
    public void canGetDimensionSquareMatrix() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertEquals(dimension, squareMatrix.getDimension());
    }

    @Test
    public void cantGetDimensionSquareMatrix() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertNotEquals(5, squareMatrix.getDimension());
    }

    @Test
    public void canGetArrayOfMatrix() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertArrayEquals(testArray, squareMatrix.getArrayOfMatrix(), delta);
    }

    @Test
    public void canGetElementOfArrayOfMatrix() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertEquals(testArray[2], squareMatrix.getElementOfArrayOfMatrix(2), delta);
    }

    @Test
    public void cantGetEqualsElementOfArrayOfMatrix() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertNotEquals(5f, squareMatrix.getElementOfArrayOfMatrix(2), delta);
    }

    @Test
    public void canGetRightWalkResult() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertEquals(225f, squareMatrix.getRightWalk(), delta);
    }

    @Test
    public void canGetLeftWalkResult() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertEquals(225f, squareMatrix.getLeftWalk(), delta);
    }

    @Test
    public void canGetDeterminant() {
        SquareMatrix squareMatrix;
        squareMatrix = new SquareMatrix(dimension, testArray);
        assertEquals(0f, squareMatrix.determinant(), delta);
    }
}
