package ru.unn.agile.treesort.viewmodel;

import javafx.beans.property.BooleanProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTest {
    private ViewModel viewModel;
    private String validSourceString = "5,4, 3 ,2 , 1";
    private String invalidSourceString = "5,4, 3 ,2 , 1abc";
    private String resultString = "1, 2, 3, 4, 5";

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

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

    @Test
    public void canCreateViewModelWithLoggerParam() {
        viewModel = new ViewModel(new FakeLogger());

        assertNotNull(viewModel);
    }

    @Test
    public void canSetLogger() {
        viewModel.setLogger(new FakeLogger());
    }

    @Test
    public void canGetLogger() {
        ILogger logger = viewModel.getLogger();

        assertNotNull(logger);
    }

    public void canGetSourceTextFocused() {
        boolean focused = viewModel.isSourceTextFocused();

        assertNotNull(focused);
    }

    @Test
    public void isSourceTextFocusedOnStart() {
        viewModel = new ViewModel();

        assertTrue(viewModel.isSourceTextFocused());
    }

    @Test
    public void canSetSourceTextFocused() {
        viewModel.setSourceTextFocused(false);

        assertFalse(viewModel.isSourceTextFocused());
    }

    @Test
    public void canGetSourceTextFocusedProperty() {
        BooleanProperty focused = viewModel.sourceTextFocusedProperty();

        assertNotNull(focused);
    }

    @Test
    public void canGetLog() {
        List<String> logs = viewModel.getLog();

        assertNotNull(logs);
    }

    @Test
    public void canLogSortStarting() {
        viewModel.sort();
        String text = viewModel.getLog().get(0);

        assertTrue(text.endsWith(Messages.SORT_BUTTON_CLICKED));
    }

    @Test
    public void canLogSourceTextChanging() {
        viewModel.setSourceText("new text");
        viewModel.setSourceTextFocused(false);
        String text = viewModel.getLog().get(0);

        assertTrue(text.endsWith(Messages.SOURCE_CHANGED + " to \"new text\""));
    }

    @Test
    public void canLogSourceTextMultipleChanging() {
        viewModel.setSourceText("first text");
        viewModel.setSourceText("second text");
        viewModel.setSourceTextFocused(false);
        viewModel.setSourceTextFocused(true);
        viewModel.setSourceTextFocused(false);
        viewModel.setSourceTextFocused(true);
        viewModel.setSourceText("third text");
        viewModel.setSourceTextFocused(false);
        List<String> log = viewModel.getLog();

        assertTrue(log.get(0).endsWith(Messages.SOURCE_CHANGED + " to \"second text\""));
        assertTrue(log.get(1).endsWith(Messages.SOURCE_CHANGED + " to \"third text\""));
    }
}
