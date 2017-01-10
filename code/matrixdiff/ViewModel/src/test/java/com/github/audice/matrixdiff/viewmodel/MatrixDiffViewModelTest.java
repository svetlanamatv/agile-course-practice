package com.github.audice.matrixdiff.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
* Created by Denis on 23.12.2016.
*/
public class MatrixDiffViewModelTest {
private MatrixDiffViewModel viewModel;

@Before
public void enteringDate() {
viewModel = new MatrixDiffViewModel();
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
public void canGetResultOfCalculateNewArrayOfElementsTest() {
viewModel.setSizeOfMatrix("3");
viewModel.fillStringImgMatrixConvertToArray("{1,2,3,4,5,6,7,8,9}");
viewModel.calculateMatrixDiff();
assertEquals(viewModel.getResultOfCalculate(), 0, 0.0001);
}
}
