package com.github.audice.matrixdiff.viewmodel;

import com.github.audice.matrixdiff.model.SquareMatrix;

/**
 * Created by Denis on 23.12.2016.
 */
public class MatrixDiffViewModel {
    private boolean toFillButtonEnabled = false;
    private int sizeOfMatrixInInteger = 0;
    private float[] arrayOfElement;
    private boolean isCalculateButton = false;
    private float result = 0;
    private SquareMatrix squareMatrix;

    public boolean isToFillButtonEnabled() {
        return toFillButtonEnabled;
    }


    public void setSizeOfMatrix(final String sizeOfMatrix) {
        if ("".equals(sizeOfMatrix) || (!sizeOfMatrix.matches("[0-9]*"))) {
            toFillButtonEnabled = false;
            sizeOfMatrixInInteger = 0;
            isCalculateButton = false;
            return;
        }
        sizeOfMatrixInInteger = Integer.parseInt(sizeOfMatrix);
        toFillButtonEnabled = true;
    }

    public boolean fillStringImgMatrixConvertToArray(final String strImgMatrix) {
        arrayOfElement = new float[sizeOfMatrixInInteger * sizeOfMatrixInInteger];
        int indexOfArraysElements = 0;
        strImgMatrix.replaceAll(" ", "");
        char[] chArray = strImgMatrix.toCharArray();
        String timeValueForConvert = "";
        String lenglyStringValue = "";
        for (int index = 1; index < chArray.length; index++) {
            if (String.valueOf(chArray[index]).equals(",")
                    && String.valueOf(chArray[index - 1]).equals(",")) {
                isCalculateButton = false;
                result = 0;
                return false;
            } else {
                timeValueForConvert = String.valueOf(chArray[index]);
                if (timeValueForConvert.matches("[0-9]*") || ".".equals(timeValueForConvert)) {
                    lenglyStringValue += timeValueForConvert;
                } else {
                    if (",".equals(timeValueForConvert) || "}".equals(timeValueForConvert)) {
                        arrayOfElement[indexOfArraysElements] = Float.parseFloat(lenglyStringValue);
                        lenglyStringValue = "";
                        indexOfArraysElements++;
                    } else {
                        isCalculateButton = false;
                        result = 0;
                        return false;
                    }
                }
            }
        }
        isCalculateButton = true;
        return true;
    }

    public String getEmptyMatrix() {
        if (toFillButtonEnabled) {
            String simpleMatrix = "{";
            for (int index = 0; index < sizeOfMatrixInInteger * sizeOfMatrixInInteger - 1;
                 index++) {
                    simpleMatrix += "0,";
            }
            simpleMatrix += "0}";
            return simpleMatrix;
        }
        return "";
    }

    public boolean getIsCalculateButton() {
        return isCalculateButton;
    }

    public boolean calculateMatrixDiff() {
        if (isCalculateButton) {
            squareMatrix = new SquareMatrix(sizeOfMatrixInInteger, arrayOfElement);
            result = squareMatrix.determinant();
            return true;
        }
        result = 0;
        return false;
    }

    public float getResultOfCalculate() {
        return result;
    }
}
