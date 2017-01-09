package com.github.audice.matrixdiff.model;

import java.util.Arrays;

/**
 * Created by Denis on 23.12.2016.
 */
public class SquareMatrix {
    private int dimension;
    private float[] arrayOfMatrix;
    public SquareMatrix(final int dimension, final float[] arrayOfMatrix) {
        if (dimension > 0 && arrayOfMatrix.length == (dimension * dimension)) {
            this.dimension = dimension;
            this.arrayOfMatrix = Arrays.copyOf(arrayOfMatrix, arrayOfMatrix.length);
        }
    }

    public int getDimension() {
        return this.dimension;
    }

    public float[] getArrayOfMatrix() {
        return this.arrayOfMatrix;
    }

    public float getElementOfArrayOfMatrix(final int index) {
        return this.arrayOfMatrix[index];
    }

    @Override
    public String toString() {
        String arrayText = "";
        if (this.dimension > 0) {
            for (int elementNumber = 0; elementNumber < arrayOfMatrix.length; elementNumber++) {
                arrayText += Float.toString(arrayOfMatrix[elementNumber]) + ",";
            }
            return arrayText;
        }
        return null;
    }

    public float determinant() {
        return getRightWalk() - getLeftWalk();
    }

    public float getRightWalk() {
        float multyRightStep = 1;
        float rightSum = 0;
        for (int i = 0; i < dimension; i++) {
            int countElement = 0;
            for (int j = 0; j < dimension; j++) {
                multyRightStep *= arrayOfMatrix[i + countElement];
                if ((i + 1 + countElement) % dimension == 0) {
                    countElement++;
                } else {
                    countElement += dimension + 1;
                }
            }
            rightSum += multyRightStep;
            multyRightStep = 1;
        }
        return rightSum;
    }

    public float getLeftWalk() {
        float multyLeftWalk = 1;
        float leftSum = 0;
        for (int i = dimension - 1; i > -1; i--) {
            int countElement = 0;
            for (int j = 0; j < dimension; j++) {
                multyLeftWalk *= arrayOfMatrix[i + countElement];
                if ((i + countElement) % dimension == 0) {
                    countElement += 2 * dimension - 1;
                } else {
                    countElement += dimension - 1;
                }
            }
            leftSum += multyLeftWalk;
            multyLeftWalk = 1;
        }
        return leftSum;
    }
}
