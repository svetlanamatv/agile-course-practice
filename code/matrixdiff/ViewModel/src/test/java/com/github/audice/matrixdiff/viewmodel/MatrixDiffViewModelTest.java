package com.github.audice.matrixdiff.viewmodel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Denis on 23.12.2016.
 */
public class MatrixDiffViewModelTest {
    @Test
    public void byDefaultToFillButtonIsDisabled() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        assertFalse(viewModel.isToFillButtonEnabled());
    }

    @Test
    public void whenEnteredCorrectNumericInTextFieldToFillButtonIsEnabled() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        assertTrue(viewModel.isToFillButtonEnabled());
    }

    @Test
    public void whenClearTextFieldToFillButtonIsDisabled() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        viewModel.setSizeOfMatrix("");
        assertFalse(viewModel.isToFillButtonEnabled());
    }

    @Test
    public void whenEnterRightSizeGetImplementOfMatrix() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        assertNotEquals(viewModel.getEmptyMatrix(), "");
    }

    @Test
    public void whenEnterMatrixNotFullOrHaveMistakeIsDisabledCalculateButton() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,A}");
        assertFalse(viewModel.getIsCalculateButton());
    }
    @Test
    public void whenEnterMatrixNotFullOrHaveMistakeIsEnabledCalculateButton() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        assertTrue(viewModel.getIsCalculateButton());
    }
    @Test
    public void canCalculateNewArrayOfElements() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        assertTrue(viewModel.calculateMatrixDiff());
    }
    @Test
    public void canGetResultOfCalculateNewArrayOfElements() {
        MatrixDiffViewModel viewModel = new MatrixDiffViewModel();
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        viewModel.calculateMatrixDiff();
        assertEquals(viewModel.getResultOfCalculate(), 0, 0.0001);
    }
}
