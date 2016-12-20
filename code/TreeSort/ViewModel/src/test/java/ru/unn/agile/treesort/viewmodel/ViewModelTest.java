package ru.unn.agile.treesort.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTest {
    private ViewModel viewModel;
    private String validSourceString = "5,4, 3 ,2 , 1";
    private String invalidSourceString = "5,4, 3 ,2 , 1abc";
    private String resultString = "1, 2, 3, 4, 5";

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canAccessSourceTextProperty() {
        assertNotNull(viewModel.sourceTextProperty());
    }

    @Test
    public void canAccessStatusTextProperty() {
        assertNotNull(viewModel.statusTextProperty());
    }

    @Test
    public void canAccessButtonDisabledProperty() {
        assertNotNull(viewModel.buttonDisabledProperty());
    }

    @Test
    public void canAccessResultTextProperty() {
        assertNotNull(viewModel.resultTextProperty());
    }

    @Test
    public void canGetStatus() {
        assertEquals(ViewModel.Status.WAITING.toString(), viewModel.getStatusText());
    }

    @Test
    public void isValidationCorrectForValidText() {
        assertTrue(viewModel.validate(validSourceString));
    }

    @Test
    public void isValidationCorrectForInvalidText() {
        assertFalse(viewModel.validate(invalidSourceString));
    }

    @Test
    public void isButtonDisabledOnStart() {
        assertTrue(viewModel.isButtonDisabled());
    }

    @Test
    public void canGetSourceText() {
        String text = viewModel.getSourceText();
        assertNotNull(text);
    }

    @Test
    public void canSetSourceText() {
        viewModel.setSourceText(validSourceString);
        assertEquals(validSourceString, viewModel.getSourceText());
    }

    @Test
    public void shouldChangeStatusOnBad() {
        viewModel.setSourceText(invalidSourceString);
        assertEquals(ViewModel.Status.BAD.toString(), viewModel.getStatusText());
    }

    @Test
    public void shouldChangeStatusOnReady() {
        viewModel.setSourceText(validSourceString);
        assertEquals(ViewModel.Status.READY.toString(), viewModel.getStatusText());
    }

    @Test
    public void buttonShouldBeDisabled() {
        shouldChangeStatusOnBad();
        assertEquals(true, viewModel.isButtonDisabled());
    }

    @Test
    public void buttonShouldBeEnabled() {
        shouldChangeStatusOnReady();
        assertEquals(false, viewModel.isButtonDisabled());
    }

    @Test
    public void shouldDoSort() {
        shouldChangeStatusOnReady();
        viewModel.sort();
        assertEquals(resultString, viewModel.resultTextProperty().get());
    }
}
