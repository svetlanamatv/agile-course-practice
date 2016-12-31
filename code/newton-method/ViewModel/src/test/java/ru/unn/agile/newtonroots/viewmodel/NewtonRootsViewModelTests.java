package ru.unn.agile.newtonroots.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.newtonroots.model.StoppingCriterion;

import static org.junit.Assert.*;

public class NewtonRootsViewModelTests {
    private NewtonRootsViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new NewtonRootsViewModel(new TimestampingInMemoryLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    private void setValidViewModelInputState() {
        viewModel.editLeftPointTo("-1");
        viewModel.editRightPointTo("1");
        viewModel.editAccuracyTo("0.001");
        viewModel.editFuncitonExpressionTo("x");
        viewModel.editDerivativeStepTo("0.001");
        viewModel.editStartPointTo("0.1");
        viewModel.setStopCriterion(StoppingCriterion.FunctionModulus);
    }

    private void setInvalidModelInputState() {
        setValidViewModelInputState();
        viewModel.editDerivativeStepTo("z");
    }

    @Test
    public void checkDefaultState() {
        assertEquals("", viewModel.getLeftPoint());
        assertEquals("", viewModel.getRightPoint());
        assertEquals("", viewModel.getDerivativeStep());
        assertEquals("", viewModel.getAccuracy());
        assertEquals("", viewModel.getFunctionExpression());
        assertEquals(true, viewModel.getInputDataIsInvalid());
        assertEquals("", viewModel.getSolverReport());
        assertEquals("", viewModel.getStartPoint());
    }

    @Test
    public void setBadFormattedIntervalBounds() {
        setValidViewModelInputState();
        viewModel.editRightPointTo("-a");
        viewModel.editLeftPointTo("b");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedAccuracy() {
        setValidViewModelInputState();
        viewModel.editAccuracyTo("-a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedDerivativeStep() {
        setValidViewModelInputState();
        viewModel.editDerivativeStepTo("-a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedFunction() {
        setValidViewModelInputState();
        viewModel.editFuncitonExpressionTo("a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedStartPoint() {
        setValidViewModelInputState();
        viewModel.editStartPointTo("a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setNonMonotonicFiction() {
        setValidViewModelInputState();
        viewModel.editFuncitonExpressionTo("x^2");
        assertEquals(ApplicationStatus.NON_MONOTONIC_FUNCTION.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setBadInterval() {
        setValidViewModelInputState();
        viewModel.editLeftPointTo("-1");
        viewModel.editRightPointTo("-2");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setBadAccuracy() {
        setValidViewModelInputState();
        viewModel.editAccuracyTo("0");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setStartPointOutsideOfInterval() {
        setValidViewModelInputState();
        viewModel.editStartPointTo("1000");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setBadDerivativeStep() {
        setValidViewModelInputState();
        viewModel.editDerivativeStepTo("0");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void waitingStatusWhenOneFieldIsEmpty() {
        setValidViewModelInputState();
        viewModel.editAccuracyTo("");
        assertEquals(ApplicationStatus.WAITING.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void readyStatusWhenParametersAreOK() {
        setValidViewModelInputState();
        assertEquals(ApplicationStatus.READY.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void successStatusWhenRootFound() {
        setValidViewModelInputState();
        viewModel.findRoot();
        assertEquals(ApplicationStatus.SUCCESS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void reportNotEmptyWhenRootFound() {
        setValidViewModelInputState();
        viewModel.findRoot();
        assertFalse(viewModel.getSolverReport().isEmpty());
    }

    @Test
    public void failedStatusWhenRootNotFound() {
        setValidViewModelInputState();
        viewModel.editFuncitonExpressionTo("x+100");
        viewModel.findRoot();
        assertEquals(ApplicationStatus.FAILED.toString(),
                viewModel.getApplicationStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findRootThrowsIfInputIsInvalid() {
        setInvalidModelInputState();
        viewModel.findRoot();
    }

    @Test(expected = ExplicitlyEditableStringProperty.SetOusideOfEditException.class)
    public void explicitlyEditablePropertiesThrowUponSetIfEditWasNotStarted() {
        viewModel.functionExpressionProperty().set("e^x");
    }

    @Test
    public void finishAllEditsCausesUnfinishedEditToFinish() {
        viewModel.startPointProperty().startEdit();
        viewModel.startPointProperty().set("0.01");

        viewModel.finishAllEdits();

        assertFalse(viewModel.startPointProperty().isBeingEdited());
    }
}
