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
        viewModel.setInput("a");
        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void emptyInput() {
        viewModel.setInput("");
        assertEquals(WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void validInput() {
        viewModel.setInput("1");
        assertEquals(SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void convert1kgTo1000Gram() {
        viewModel.setInput("1");
        assertEquals("1000.0", viewModel.getResult());
    }

    @Test
    public void convert1kgToTonne() {
        viewModel.setSystemToConvertProperty(ConversionSystem.TONNE);
        viewModel.setInput("1");
        assertEquals("0.001", viewModel.getResult());
    }

    @Test
    public void convert1kgToTonneAfterChangeSystem() {
        viewModel.setInput("1");
        viewModel.setSystemToConvertProperty(ConversionSystem.TONNE);
        assertEquals("0.001", viewModel.getResult());
    }

    @Test
    public void convertEmptyInputAfterChangeSystem() {
        viewModel.setSystemToConvertProperty(ConversionSystem.TONNE);
        assertEquals(WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void convertInvalidInputAfterChangeSystem() {
        viewModel.setInput("a");
        viewModel.setSystemToConvertProperty(ConversionSystem.TONNE);
        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void testGetStringResult() {
        viewModel.setInput("1");
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
        viewModel.setInput("1");
        assertEquals("1000.0", viewModel.getResult());
        viewModel.setInput("a");
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void emptyResultWhenEmptyInput() {
        viewModel.setInput("1");
        assertEquals("1000.0", viewModel.getResult());
        viewModel.setInput("");
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void convert1CentnerTo100000Gram() {
        viewModel.setInput("1");
        viewModel.systemFromConvertProperty().set(ConversionSystem.CENTNER);
        assertEquals("100000.0", viewModel.getResult());
    }

    @Test
    public void convertNegative() {
        viewModel.setInput("-1");
        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void canNotConvertNumberMoreThanTeenSigns() {
        viewModel.setInput("467586788986789761");

        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void canNotConvertInputValueWhenIntegerPartMoreThanTeenSigns() {
        viewModel.setInput("467586788986789.761");

        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void canNotConvertInputValueWhenFractionalPartMoreThanTeenSigns() {
        viewModel.setInput("4675.86788986789761");

        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void canNotConvertIncorrectInputValue() {
        viewModel.setInput("4675.86788986.789761");

        assertEquals(WRONG_INPUT.toString(), viewModel.getStatus());
    }

    @Test
    public void logMustBeEmptyInTheBeginning() {
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(0, log.size());
    }

    @Test
    public void logMustContainsSomethingAfterSetInputValue() {
        viewModel.setInput("23");
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSetInputValue() {
        viewModel.setInput("23");
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.EDITING_FINISHED));
    }

    @Test
    public void logMustContainsInputValue() {
        String inputValue = "23";
        viewModel.setInput(inputValue);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(inputValue));
    }

    @Test
    public void logMustContainsSomethingAfterSetFirstSystem() {
        viewModel.setSystemFromConvertProperty(ConversionSystem.CENTNER);
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSetFirstSystem() {
        viewModel.setSystemFromConvertProperty(ConversionSystem.CENTNER);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.FIRST_SYSTEM_WAS_CHANGED));
    }

    @Test
    public void logMustContainsFirstSystem() {
        viewModel.setSystemFromConvertProperty(ConversionSystem.CENTNER);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(ConversionSystem.CENTNER.toString()));
    }

    @Test
    public void logMustContainsSomethingAfterSetSecondSystem() {
        viewModel.setSystemToConvertProperty(ConversionSystem.POUND);
        List<String> log = viewModel.logMessagesProperty();

        assertEquals(1, log.size());
    }

    @Test
    public void logMustContainsPropertyOfLogMessagesAfterSetSecondSystem() {
        viewModel.setSystemToConvertProperty(ConversionSystem.MILLIGRAM);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(ViewModel.SECOND_SYSTEM_WAS_CHANGED));
    }

    @Test
    public void logMustContainsSecondSystem() {
        viewModel.setSystemToConvertProperty(ConversionSystem.POUND);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(ConversionSystem.POUND.toString()));
    }

    @Test
    public void lastLogMessageMustBeEmptyInTheBeginning() {
        String actualMessage = viewModel.lastLogMessageProperty().get();

        assertEquals("", actualMessage);
    }

    @Test
    public void lastLogMessageMustContainsFirstSystem() {
        viewModel.setSystemFromConvertProperty(ConversionSystem.CENTNER);

        String actualMessage = viewModel.getLastLogMessage();

        assertTrue(actualMessage.contains(ConversionSystem.CENTNER.toString()));
    }

    @Test
    public void lastLogMessageMustContainsCorectlyValueOfInput() {
        String inputValue = "51";

        viewModel.setSystemFromConvertProperty(ConversionSystem.CENTNER);
        viewModel.setSystemToConvertProperty(ConversionSystem.POUND);
        viewModel.setInput(inputValue);

        String actualMessage = viewModel.getLastLogMessage();

        assertTrue(actualMessage.contains(inputValue));
    }

    @Test
    public void setInputValueMustSuccesedWhenViewModelWithoutLogger() {
        try {
            ViewModel empVieModel = new ViewModel();

            empVieModel.setInput("23");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setFirstSystemMustSuccesedWhenViewModelWithoutLogger() {
        try {
            ViewModel empVieModel = new ViewModel();

            empVieModel.setSystemFromConvertProperty(ConversionSystem.CENTNER);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void viewModelWithoutLoggerMustSuccesedSetSecondSystem() {
        try {
            ViewModel empVieModel = new ViewModel();

            empVieModel.setSystemToConvertProperty(ConversionSystem.KILOGRAM);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void listMessagesPropertyMustContainsInputValue() {
        String inputValue = "23";
        viewModel.setInput(inputValue);
        List<String> log = viewModel.logMessagesProperty();
        String message = log.get(0);

        assertTrue(message.contains(inputValue));
    }
}
