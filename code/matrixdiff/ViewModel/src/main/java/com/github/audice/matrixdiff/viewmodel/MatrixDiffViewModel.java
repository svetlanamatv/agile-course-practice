package com.github.audice.matrixdiff.viewmodel;

import com.github.audice.matrixdiff.model.SquareMatrix;

import java.util.List;

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
    private final ILogger logger;
    public static final String CHANGE_SIZE_OF_MATRIX_WITH_MATRIX = "Delete current "
            + "matrix in response change size of matrix";
    public static final String ENTERED_BUTTON_OF_PUT_EMPTY_MATRIX = "Got an empty matrix";
    public static final String BAD_CHANGE_SIZE_MATRIX = "Entered bad size of matrix";
    public static final String GOOD_CHANGE_SIZE_MATRIX = "Entered viable size of matrix";
    public static final String ENTERED_NOT_CORRECT_ELEMENT = "Entered not correct"
            + " element of matrix";
    public static final String ENTERED_CORRECT_ELEMENT = "Entered correct element of matrix";
    public static final String RESULT_IS_CALCULATE = "Result is calculate";
    public static final String RESULT_IS_NOT_CALCULATE = "Result isn't calculate";

    public boolean isToFillButtonEnabled() {
        return toFillButtonEnabled;
    }

    public MatrixDiffViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public void setSizeOfMatrix(final String sizeOfMatrix) {
        if ("".equals(sizeOfMatrix) || (!sizeOfMatrix.matches("[0-9]*"))) {
            toFillButtonEnabled = false;
            sizeOfMatrixInInteger = 0;
            isCalculateButton = false;
            logger.log(BAD_CHANGE_SIZE_MATRIX);
            return;
        }
        sizeOfMatrixInInteger = Integer.parseInt(sizeOfMatrix);
        toFillButtonEnabled = true;
        logger.log(GOOD_CHANGE_SIZE_MATRIX);
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
                    logger.log(ENTERED_CORRECT_ELEMENT
                            + " in element of matrix "
                            + Integer.toString(indexOfArraysElements + 1));
                } else {
                    if (",".equals(timeValueForConvert) || "}".equals(timeValueForConvert)) {
                        arrayOfElement[indexOfArraysElements] = Float.parseFloat(lenglyStringValue);
                        lenglyStringValue = "";
                        indexOfArraysElements++;
                    } else {
                        isCalculateButton = false;
                        result = 0;
                        logger.log(ENTERED_NOT_CORRECT_ELEMENT);
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
            logger.log(ENTERED_BUTTON_OF_PUT_EMPTY_MATRIX);
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
            logger.log(RESULT_IS_CALCULATE);
            return true;
        }
        result = 0;
        logger.log(RESULT_IS_NOT_CALCULATE);
        return false;
    }

    public float getResultOfCalculate() {
        return result;
    }

}
