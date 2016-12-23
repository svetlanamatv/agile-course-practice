package ru.unn.agile.PositionalNotation.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

/**
 * Created by evdo0116 on 22.11.2016.
 */
public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void cleanUp() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.numberProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(Notation.DECIMAL, viewModel.fromNotationProperty().get());
        assertEquals(Notation.DECIMAL, viewModel.toNotationProperty().get());
        assertEquals(Status.WAIT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledIfNumberIsNull() {
        viewModel.numberProperty().set("");
        assertTrue(viewModel.converterDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithIncorrectInput() {
        viewModel.numberProperty().setValue("0");

        assertTrue(viewModel.converterDisabledProperty().get());
    }

    @Test
    public void convert10BinaryToDecimalWhenInputAgain() {
        viewModel.numberProperty().setValue("10");
        viewModel.numberProperty().setValue("10");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);

        viewModel.convert();

        assertEquals("2", viewModel.resultProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        viewModel.numberProperty().setValue("10");

        assertFalse(viewModel.converterDisabledProperty().get());
    }

     @Test
    public void canConvert1111BinaryToDecimal() {
         viewModel.numberProperty().setValue("1111");
         viewModel.fromNotationProperty().setValue(Notation.BINARY);
         viewModel.toNotationProperty().setValue(Notation.DECIMAL);
         viewModel.convert();
         assertEquals("15", viewModel.resultProperty().get());
     }

     @Test
     public void canConvert1bHexToOctal() {
         viewModel.numberProperty().setValue("1b");
         viewModel.fromNotationProperty().setValue(Notation.HEX);
         viewModel.toNotationProperty().setValue(Notation.OCTAL);

         viewModel.convert();

         assertEquals("33", viewModel.resultProperty().get());
     }

    @Test
    public void statusIsWaitingWhenConvertWithEmptyNumber() {
        viewModel.convert();

        assertEquals(Status.WAIT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsErrorWhenIncorrectBinaryNumber() {
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.numberProperty().setValue("20");

        assertEquals(Status.ERROR.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsErrorWhenIncorrectDecimalNumber() {
        viewModel.fromNotationProperty().setValue(Notation.DECIMAL);
        viewModel.numberProperty().setValue("0");

        assertEquals(Status.ERROR.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsErrorWhenIncorrectOctalNumber() {
        viewModel.fromNotationProperty().setValue(Notation.OCTAL);
        viewModel.numberProperty().setValue("8");
        assertEquals(Status.ERROR.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void statusIsErrorWhenIncorrectHexNumber() {
        viewModel.fromNotationProperty().setValue(Notation.HEX);
        viewModel.numberProperty().setValue("g");

        assertEquals(Status.ERROR.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenCorrectNumber() {
        viewModel.fromNotationProperty().setValue(Notation.HEX);
        viewModel.numberProperty().setValue("1b");

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsSuccessWhenConverted() {
        viewModel.fromNotationProperty().setValue(Notation.HEX);
        viewModel.toNotationProperty().setValue(Notation.OCTAL);
        viewModel.numberProperty().setValue("1b");
        viewModel.convert();

        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullPositionalNotationLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException exep) {
            assertEquals("Logger parameter can't be null", exep.getMessage());
        } catch (Exception exep) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void iInTheBeginningLogIsEmpty() {
        List<String> logs = viewModel.getLog();

        assertTrue(logs.isEmpty());
}

    @Test
    public void logIncludeProperMessageAfterConvertionWhenConvert11111FromBinaryToOctal() {
        viewModel.numberProperty().setValue("11111");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.OCTAL);
        viewModel.convert();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*Calculate. .*"));
    }

    @Test
    public void logIncludeInputArgumentsAfterConvertionWhenConvert1000011FromBinaryToHex() {
        viewModel.numberProperty().setValue("1000011");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.HEX);
        viewModel.convert();

        String messageLog = viewModel.getLog().get(0);
        assertTrue(messageLog.matches(".*" + viewModel.numberProperty().get()
                + ".*" + viewModel.fromNotationProperty().get().name()
                + ".*" + viewModel.toNotationProperty().get().name()));
    }

    @Test
    public void argumentsInfoIsProperlyFormattedWhenConvert110FromBinaryToDecimal() {
        viewModel.numberProperty().setValue("110");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);
        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Arguments"
                + ": Number = " + viewModel.numberProperty().get()
                + "; From notaition = " + viewModel.fromNotationProperty().get().name()
                + "; To notaition = " + viewModel.toNotationProperty().get().name() + ".*"));
    }

    @Test
    public void conversionBinaryToHexIsMentionedInLog() {
        viewModel.numberProperty().setValue("10");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.HEX);
        viewModel.convert();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*"
                + "; From notaition = " + viewModel.fromNotationProperty().get().name()
                + "; To notaition = " + viewModel.toNotationProperty().get().name() + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        viewModel.numberProperty().setValue("10");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);
        viewModel.convert();
        viewModel.convert();
        viewModel.convert();
        viewModel.convert();
        viewModel.convert();

        assertEquals(5, viewModel.getLog().size());
    }

    @Test
    public void canSeeFromNotationChangeInLog() {
        viewModel.numberProperty().setValue("10");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);

        viewModel.fromNotationChanged(Notation.DECIMAL, Notation.BINARY);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*From notation was changed to binary.*"));
    }

    @Test
    public void canSeeToNotationChangeInLog() {
        viewModel.numberProperty().setValue("10");
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);

        viewModel.toNotationChanged(Notation.BINARY, Notation.DECIMAL);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*To notation was changed to decimal.*"));
    }

    @Test
    public void fromNotationIsNotLoggedIfNotChanged() {
        viewModel.fromNotationChanged(Notation.DECIMAL, Notation.BINARY);
        viewModel.fromNotationChanged(Notation.BINARY, Notation.BINARY);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void toNotationIsNotLoggedIfNotChanged() {
        viewModel.toNotationChanged(Notation.BINARY, Notation.DECIMAL);
        viewModel.toNotationChanged(Notation.DECIMAL, Notation.DECIMAL);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLogged() {
        viewModel.numberProperty().setValue("10");
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Updated input. "
                + "Input argument are: \\["
                + viewModel.numberProperty().get() + "\\]"));
    }

    @Test
    public void calculateIsNotCalledWhenButtonIsDisabled() {
        viewModel.convert();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.numberProperty().setValue("10");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.numberProperty().setValue("10");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Test
    public void doLogSameParametersWithPartialInput() {
        viewModel.numberProperty().setValue("11");
        viewModel.onFocusChanged(Boolean.FALSE, Boolean.TRUE);

        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void logIsNotEmptyAfterInputParameters() {
        viewModel.numberProperty().setValue("11");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.fromNotationProperty().setValue(Notation.BINARY);
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);
        viewModel.convert();

        String value = viewModel.logsProperty().getValue();

        assertNotNull(value);
    }

    @Test
    public void canConvertDecimalToDecimal() {
        viewModel.numberProperty().setValue("10");
        viewModel.numberProperty().setValue("10");
        viewModel.fromNotationProperty().setValue(Notation.DECIMAL);
        viewModel.toNotationProperty().setValue(Notation.DECIMAL);

        assertTrue(viewModel.resultProperty().get().isEmpty());
    }
}
