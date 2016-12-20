package ru.unn.agile.ComplexNumberCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ViewModelTests {
    private ComplexNumberCalculatorViewModel viewModel;

    public void setExternalViewModel(final ComplexNumberCalculatorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private void setInputData(final String re1, final String im1,
                              final String re2, final String im2) {
        viewModel.firstArgRealProperty().set(re1);
        viewModel.firstArgImProperty().set(im1);
        viewModel.secondArgRealProperty().set(re2);
        viewModel.secondArgImProperty().set(im2);
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ComplexNumberCalculatorViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.firstArgRealProperty().get());
        assertEquals("", viewModel.firstArgImProperty().get());
        assertEquals("", viewModel.secondArgRealProperty().get());
        assertEquals("", viewModel.secondArgImProperty().get());
        assertEquals(Operations.ADDITION, viewModel.selectedOperationProperty().get());
        assertEquals("", viewModel.resultProperty().get());
    }


    @Test
    public void canSetAddOperation() {
        viewModel.selectedOperationProperty().set(Operations.ABS);
        assertEquals(Operations.ABS, viewModel.selectedOperationProperty().get());
    }


    @Test
    public void operationAddHasCorrectResult() {
        setInputData("1", "4", "-2", "-2.5");

        viewModel.calculate();

        assertEquals("-1.0 + 1.5i", viewModel.resultProperty().get());
    }

    @Test
    public void canSetBadFormatMessage() {
        setInputData("one", "4", "-2", "-2.5");

        viewModel.calculate();

        assertEquals("Incorrect input for the first argument", viewModel.resultProperty().get());
    }


    @Test
    public void operationMulHasCorrectResult() {
        setInputData("2", "3", "1", "2");
        viewModel.selectedOperationProperty().set(Operations.MULTIPLICATION);

        viewModel.calculate();

        assertEquals("-4.0 + 7.0i", viewModel.resultProperty().get());
    }

    @Test
    public void operationAddWithNegativeNumbersHasCorrectResult() {
        setInputData("1.2", "2.3", "-10.4", "-20.5");
        viewModel.selectedOperationProperty().set(Operations.ADDITION);

        viewModel.calculate();

        assertEquals("-9.2 - 18.2i", viewModel.resultProperty().get());
    }

    // Logger Tests

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ComplexNumberCalculatorViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void wrongDataFirstArgLogErrorMessage() {
        setInputData("", "2.3", "-10.4", "-20.5");
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Error : Incorrect input for the first argument"));
    }

    @Test
    public void wrongDataFirstArgLogErrorMessage2() {
        setInputData("b", "2.3", "", "");
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Error : Incorrect input for the first argument"));
    }

    @Test
    public void wrongDataSecondArgLogErrorMessage() {
        setInputData("1", "2.3", "a", "-20.5");
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Error : Incorrect input for the second argument"));
    }

    @Test
    public void unaryOperationCalculateLogMessage() {
        setInputData("1", "2.3", "a", "-20.5");
        viewModel.selectedOperationProperty().set(Operations.ABS);
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Arguments : Re1 = 1 Im1 = 2.3; Operation : ABS"));
    }

    @Test
    public void unaryOperationCalculateLogMessage2() {
        setInputData("5", "5", "a", "-20.5");
        viewModel.selectedOperationProperty().set(Operations.ARGUMENT);
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Arguments : Re1 = 5 Im1 = 5; Operation : ARGUMENT"));
    }

    @Test
    public void binaryOperationCalculateLogMessage() {
        setInputData("5", "5", "3", "7");
        viewModel.selectedOperationProperty().set(Operations.ADDITION);
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Arguments : Re1 = 5 Im1 = 5 Re2 = 3 Im2 = 7; Operation : ADDITION"));
    }

    @Test
    public void binaryOperationCalculateLogMessage2() {
        setInputData("5", "5", "3", "7");
        viewModel.selectedOperationProperty().set(Operations.DIVISION);
        viewModel.calculate();

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + "Calculate. Arguments : Re1 = 5 Im1 = 5 Re2 = 3 Im2 = 7; Operation : DIVISION"));
    }

    @Test
    public void canSeeOperationChangeInLog() {
        viewModel.onOperationChanged(Operations.ADDITION, Operations.ABS);

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + "Operation was changed to ABS"));
    }

    @Test
    public void argumentsAreCorrectlyLogged() {
        setInputData("1", "", "2", "");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + "Updated input. Input arguments are: \\[1; ; 2; \\]"));
    }
}
