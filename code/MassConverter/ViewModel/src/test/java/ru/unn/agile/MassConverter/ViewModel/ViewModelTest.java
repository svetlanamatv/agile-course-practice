package ru.unn.agile.MassConverter.ViewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.unn.agile.MassConverter.Model.MassConverter.*;
import static ru.unn.agile.MassConverter.ViewModel.ViewModel.Status.*;

public class ViewModelTest {
    private ViewModel viewModel;

    @Before
    public void start() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void finish() {
        viewModel = null;
    }

    @Test
    public void setDefaultValues() {
        assertEquals("", viewModel.inputProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(WAITING.toString(), viewModel.statusProperty().get());
        assertEquals(ConversionSystem.GRAM.toString(),
                viewModel.systemToConvertProperty().get().toString());
    }

    @Test
    public void invalidInput() {
        viewModel.inputProperty().set("a");
        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void emptyInput() {
        viewModel.inputProperty().set("");
        assertEquals(WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void validInput() {
        viewModel.inputProperty().set("1");
        assertEquals(SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convert1kgTo1000Gram() {
        viewModel.inputProperty().set("1");
        assertEquals("1000.0", viewModel.resultProperty().get());
    }

    @Test
    public void convert1kgToTonne() {
        viewModel.systemToConvertProperty().set(ConversionSystem.TONNE);
        viewModel.inputProperty().set("1");
        assertEquals("0.001", viewModel.resultProperty().get());
    }

    @Test
    public void convert1kgToTonneAfterChangeSystem() {
        viewModel.inputProperty().set("1");
        viewModel.systemToConvertProperty().set(ConversionSystem.TONNE);
        assertEquals("0.001", viewModel.resultProperty().get());
    }

    @Test
    public void convertEmptyInputAfterChangeSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.TONNE);
        assertEquals(WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertInvalidInputAfterChangeSystem() {
        viewModel.inputProperty().set("a");
        viewModel.systemToConvertProperty().set(ConversionSystem.TONNE);
        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testGetStringResult() {
        viewModel.inputProperty().set("1");
        assertEquals("1000.0", viewModel.getResult());
    }

    @Test
    public void testGetStringStatus() {
        assertEquals(WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void testGetConversionSystems() {
        ObservableList<ConversionSystem> observableList
                = FXCollections.observableArrayList(ConversionSystem.values());
        assertEquals(observableList, viewModel.getConversionSystems());
    }

    @Test
    public void emptyResultWhenInvalidInput() {
        viewModel.inputProperty().set("1");
        assertEquals("1000.0", viewModel.resultProperty().get());
        viewModel.inputProperty().set("a");
        assertEquals("", viewModel.resultProperty().get());
    }

    @Test
    public void emptyResultWhenEmptyInput() {
        viewModel.inputProperty().set("1");
        assertEquals("1000.0", viewModel.resultProperty().get());
        viewModel.inputProperty().set("");
        assertEquals("", viewModel.resultProperty().get());
    }

    @Test
    public void convert1CentnerTo100000Gram() {
        viewModel.inputProperty().set("1");
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        assertEquals("100000.0", viewModel.resultProperty().get());
    }

    @Test
    public void convertNegative() {
        viewModel.inputProperty().set("-1");
        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void logMustBeEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void logMustContainsSomethingAfterInputValue() {
        viewModel.inputProperty().set("23");
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterInputValue() {
        viewModel.inputProperty().set("23");
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.LogMessages.EDITING_FINISHED));
    }

    @Test
    public void logMustContainsInputValue() {
        String inputValue = "23";
        viewModel.inputProperty().set(inputValue);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(inputValue));
    }

    @Test
    public void logMustContainsSomethingAfterInputFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.LogMessages.FIRST_SYSTEM_WAS_CHANGED));
    }

    @Test
    public void logMustContainsFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ConversionSystem.CENTNER.toString()));
    }

    @Test
    public void logMustContainsSomethingAfterInputSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.POUND);
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.MILLIGRAM);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.LogMessages.SECOND_SYSTEM_WAS_CHANGED));
    }

    @Test
    public void logMustContainsSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.POUND);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ConversionSystem.POUND.toString()));
    }
}
