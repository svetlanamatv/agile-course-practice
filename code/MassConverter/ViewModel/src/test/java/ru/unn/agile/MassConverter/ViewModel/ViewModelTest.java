package ru.unn.agile.MassConverter.ViewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ru.unn.agile.MassConverter.Model.MassConverter.*;
import static ru.unn.agile.MassConverter.ViewModel.ViewModel.Status.*;

public class ViewModelTest {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void finish() {
        viewModel = null;
    }

    @Test
    public void viewModelConstructorThrowsExceptionWhenLoggerIsNull() {
        try {
            FakeLogger logger = null;
            viewModel = new ViewModel(logger);
        } catch (IllegalArgumentException ex) {
                assertEquals("Logger parameter can't be null", ex.getMessage());
        }
    }

    @Test
    public void canCreateViewModelWithoutLogger() {
        try {
            ViewModel viewModel = new ViewModel();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
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
    public void canNotConvertNumberMoreThanTeenSigns() {
        viewModel.inputProperty().set("467586788986789761");

        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canNotConvertInputValueWhenIntegerPartMoreThanTeenSigns() {
        viewModel.inputProperty().set("467586788986789.761");

        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canNotConvertInputValueWhenFractionalPartMoreThanTeenSigns() {
        viewModel.inputProperty().set("4675.86788986789761");

        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canNotConvertIncorrectInputValue() {
        viewModel.inputProperty().set("4675.86788986.789761");

        assertEquals(WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void logMustBeEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void logMustContainsSomethingAfterSetInputValue() {
        viewModel.inputProperty().set("23");
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSetInputValue() {
        viewModel.inputProperty().set("23");
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.EDITING_FINISHED));
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
    public void logMustContainsSomethingAfterSetFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSetFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.FIRST_SYSTEM_WAS_CHANGED));
    }

    @Test
    public void logMustContainsFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ConversionSystem.CENTNER.toString()));
    }

    @Test
    public void logMustContainsSomethingAfterSetSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.POUND);
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSetSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.MILLIGRAM);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.SECOND_SYSTEM_WAS_CHANGED));
    }

    @Test
    public void logMustContainsSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.POUND);
        List<String> log = viewModel.getLog();
        String message = log.get(0);

        assertTrue(message.contains(ConversionSystem.POUND.toString()));
    }

    @Test
    public void lastLogMessageMustBeEmptyInTheBeginning() {
        String actualMessage = viewModel.lasLogMessageProperty().get();

        assertEquals("", actualMessage);
    }

    @Test
    public void lastLogMessageMustContainsFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);

        String actualMessage = viewModel.lasLogMessageProperty().get();

        assertTrue(actualMessage.contains(ConversionSystem.CENTNER.toString()));
    }

    @Test
    public void lastLogMessageMustContainsCorectlyValueOfInput() {
        String inputValue = "51";

        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        viewModel.systemToConvertProperty().set(ConversionSystem.POUND);
        viewModel.inputProperty().set(inputValue);

        String actualMessage = viewModel.lasLogMessageProperty().get();

        assertTrue(actualMessage.contains(inputValue));
    }

    @Test
    public void setInputValueMustSuccesedWhenViewModelWithoutLogger() {
        try {
            ViewModel empVieModel = new ViewModel();

            empVieModel.inputProperty().set("23");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setFirstSystemMustSuccesedWhenViewModelWithoutLogger() {
        try {
            ViewModel empVieModel = new ViewModel();

            empVieModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void viewModelWithoutLoggerMustSuccesedSetSecondSystem() {
        try {
            ViewModel empVieModel = new ViewModel();

            empVieModel.systemToConvertProperty().set(ConversionSystem.KILOGRAM);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void listMessagesPropertyMustContainsInputValue() {
        String inputValue = "23";
        viewModel.inputProperty().set(inputValue);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(inputValue));
    }

    @Test
    public void listMessagesPropertyMustContainsSomethingAfterInputFirstSystem() {
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(1, log.size());
    }

    @Test
    public void listMessagesPropertyMustContainsSomethingAfterInputSecondSystem() {
        viewModel.systemToConvertProperty().set(ConversionSystem.CENTNER);
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(1, log.size());
    }

    @Test
    public void listMessagesPropertyMustBeEmptyInTheBeginning() {
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(0, log.size());
    }
}
