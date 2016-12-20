package ru.unn.agile.Credit.viewmodel;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    private void checkEmptyFields() {
        assertEquals("", viewModel.sumProperty().get());
        assertEquals("", viewModel.monthsProperty().get());
        assertEquals("", viewModel.percentProperty().get());
        assertEquals("", viewModel.paymentProperty().get());
        assertEquals("", viewModel.overpaymentProperty().get());
        assertEquals("", viewModel.totalSumProperty().get());
    }

    @Test
    public void setDefaultValues() {
        checkEmptyFields();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void clearWorks() {
        setDefaultInput();
        viewModel.calculate();
        viewModel.clear();
        checkEmptyFields();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWithFullFields() {
        setDefaultInput();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.sumProperty().set("sdfa");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughData() {
        viewModel.sumProperty().set("100");
        viewModel.percentProperty().set("10");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonDisabledInitially() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        setDefaultInput();
        viewModel.sumProperty().set("dad4");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.sumProperty().set("1");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setDefaultInput();
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculationHasCorrectResult() {
        setDefaultInput();
        viewModel.calculate();
        assertEquals("926.345058970803", viewModel.paymentProperty().get());
        assertEquals("1116.1407076496362", viewModel.overpaymentProperty().get());
        assertEquals("11116.140707649636", viewModel.totalSumProperty().get());
    }

    private void checkBadValues() {
        assertEquals("", viewModel.paymentProperty().get());
        assertEquals("", viewModel.overpaymentProperty().get());
        assertEquals("", viewModel.totalSumProperty().get());
        assertEquals(Status.BAD_VALUES.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusBadValuesWitshNegativeInput() {
        setOptionalInput(-1000, 12, 10);
        viewModel.calculate();
        checkBadValues();
    }

    @Test
    public void statusBadValuesWitshZeroInput() {
        setOptionalInput(1000, 0, 12);

        viewModel.calculate();
        checkBadValues();
    }

    @Test
    public void checkSuccessStatus() {
        setDefaultInput();
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void checkStatusBadFormat() {
        viewModel.sumProperty().set("34fgdfg");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void checkStatusIsReady() {
        setDefaultInput();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    private void setDefaultInput() {
        setOptionalInput(10000, 12, 20);
    }

    private void setOptionalInput(final double sum, final double months, final double percent) {
        viewModel.sumProperty().set(Double.toString(sum));
        viewModel.monthsProperty().set(Double.toString(months));
        viewModel.percentProperty().set(Double.toString(percent));
    }

    @Test
    public void checkCalculationDisabledProperty() {
        assertEquals(viewModel.getCalculationDisabled(),
                     viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void checkStatusProperty() {
        assertEquals(viewModel.getStatus(),
                viewModel.statusProperty().get());
    }
}
