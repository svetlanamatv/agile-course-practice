package com.github.audice.matrixdiff.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Denis on 23.12.2016.
 */
public class MatrixDiffViewModelTest {
    private MatrixDiffViewModel viewModel;
    public void setCurViewModel(final MatrixDiffViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Before
    public void enteringDate() {
        FakeLogger logger = new FakeLogger();
        viewModel = new MatrixDiffViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void byDefaultToFillButtonIsDisabled() {
        assertFalse(viewModel.isToFillButtonEnabled());
    }

    @Test
    public void whenEnteredCorrectNumericInTextFieldToFillButtonIsEnabled() {
        viewModel.setSizeOfMatrix("3");
        assertTrue(viewModel.isToFillButtonEnabled());
    }

    @Test
    public void whenClearTextFieldToFillButtonIsDisabled() {
        viewModel.setSizeOfMatrix("3");
        viewModel.setSizeOfMatrix("");
        assertFalse(viewModel.isToFillButtonEnabled());
    }

    @Test
    public void whenEnterRightSizeGetImplementOfMatrix() {
        viewModel.setSizeOfMatrix("3");
        assertNotEquals(viewModel.getEmptyMatrix(), "");
    }

    @Test
    public void whenEnterMatrixNotFullOrHaveMistakeIsDisabledCalculateButton() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,A}");
        assertFalse(viewModel.getIsCalculateButton());
    }

    @Test
    public void whenEnterMatrixNotFullOrHaveMistakeIsEnabledCalculateButton() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        assertTrue(viewModel.getIsCalculateButton());
    }

    @Test
    public void canCalculateNewArrayOfElements() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        assertTrue(viewModel.calculateMatrixDiff());
    }

    @Test
    public void canGetResultOfCalculateNewArrayOfElements() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        viewModel.calculateMatrixDiff();
        assertEquals(viewModel.getResultOfCalculate(), 0, 0.0001);
    }

    @Test
    public void canCreateViewModelWLogger() {
        FakeLogger logger = new FakeLogger();
        MatrixDiffViewModel viewModelLogged = new MatrixDiffViewModel(logger);
        assertNotNull(viewModelLogged);
    }

    @Test
    public void viewModelConstructorWithNullLogger() {
        try {
            new MatrixDiffViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void isLogEmptyOnStart() {
        List<String> log = viewModel.getLog();
        assertEquals(log.isEmpty(), true);
    }

    @Test
    public void isLogNotEmptyAfterAction() {
        viewModel.setSizeOfMatrix("3");
        List<String> log = viewModel.getLog();
        assertEquals(log.isEmpty(), false);
    }

    @Test
    public void isLogContainsProperMessage() {
        viewModel.setSizeOfMatrix("3");
        String message = viewModel.getLog().get(0);
        assertNotEquals(
                message.indexOf(MatrixDiffViewModel.LogMessages.GOOD_CHANGE_SIZE_MATRIX), -1);
    }

    @Test
    public void isLogContainsNotRightMessage() {
        viewModel.setSizeOfMatrix("3h");
        String message = viewModel.getLog().get(0);
        assertNotEquals(message, MatrixDiffViewModel.LogMessages.GOOD_CHANGE_SIZE_MATRIX);
    }

    @Test
    public void isLogContainsInfoPressButtonOfGetMatrix() {
        viewModel.setSizeOfMatrix("3");
        viewModel.getEmptyMatrix();
        String message = viewModel.getLog().get(1);
        assertNotEquals(
                message.indexOf(
                        MatrixDiffViewModel.LogMessages.ENTERED_BUTTON_OF_PUT_EMPTY_MATRIX), -1);
    }

    @Test
    public void isLogContainsInfoAboutFillMatrix() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        String message = viewModel.getLog().get(9);
        assertNotEquals(message.indexOf(MatrixDiffViewModel.LogMessages.ENTERED_CORRECT_ELEMENT
                + " in element of matrix "
                + Integer.toString(9)), -1);
    }

    @Test
    public void isLogContainsInfoAboutBadFillMatrix() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,h,5,6,7,8,9}");
        String message = viewModel.getLog().get(4);
        assertNotEquals(message.indexOf(
                MatrixDiffViewModel.LogMessages.ENTERED_NOT_CORRECT_ELEMENT), -1);
    }

    @Test
    public void isLogContainsInfoAboutGoodCalculate() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
        viewModel.calculateMatrixDiff();
        String message = viewModel.getLog().get(10);
        assertNotEquals(message.indexOf(MatrixDiffViewModel.LogMessages.RESULT_IS_CALCULATE), -1);
    }

    @Test
    public void isLogContainsInfoAboutBadCalculate() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,h,5,6,7,8,9}");
        viewModel.calculateMatrixDiff();
        String message = viewModel.getLog().get(5);
        assertNotEquals(message.indexOf(
                MatrixDiffViewModel.LogMessages.RESULT_IS_NOT_CALCULATE), -1);
    }

    @Test
    public void canGetEmptiMatrixIfButtonIsDisabled() {
        assertEquals(viewModel.getEmptyMatrix(), "");
    }

    @Test
    public void whenCalculateButtonIsDisabled() {
        viewModel.setSizeOfMatrix("3");
        viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,A}");
        assertFalse(viewModel.calculateMatrixDiff());
    }

    @Test
    public void whenFillMatrixIsBad() {
        viewModel.setSizeOfMatrix("3");
        assertFalse(viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,,8,9}"));
    }

    @Test
    public void whenFillMatrixIsDoubleBad() {
        viewModel.setSizeOfMatrix("3");
        assertFalse(viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,,,8,9}"));
    }
}
