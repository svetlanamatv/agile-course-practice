package ru.unn.agile.newtonroots.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.newtonroots.model.NewtonMethod;

import static org.junit.Assert.*;

public class NewtonRootAppViewModelTests {
    private NewtonRootAppViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new NewtonRootAppViewModel(new TimestampingInMemoryLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    private void setValidViewModelInputState() {
        viewModel.setLeftPoint("-1");
        viewModel.setRightPoint("1");
        viewModel.setAccuracy("0.001");
        viewModel.setFunction("x");
        viewModel.setDerivativeStep("0.001");
        viewModel.setStartPoint("0.1");
        viewModel.setStopCriterion(NewtonMethod.StoppingCriterion.FunctionModule);
    }

    @Test
    public void checkDefaultState() {
        assertEquals("", viewModel.getLeftPoint());
        assertEquals("", viewModel.getRightPoint());
        assertEquals("", viewModel.getDerivativeStep());
        assertEquals("", viewModel.getAccuracy());
        assertEquals("", viewModel.getFunction());
        assertEquals(true, viewModel.getFindRootButtonDisable());
        assertEquals("", viewModel.getSolverReport());
        assertEquals("", viewModel.getStartPoint());
    }

    @Test
    public void setBadFormattedIntervalBounds() {
        setValidViewModelInputState();
        viewModel.setRightPoint("-a");
        viewModel.setLeftPoint("b");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedAccuracy() {
        setValidViewModelInputState();
        viewModel.setAccuracy("-a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedDerivativeStep() {
        setValidViewModelInputState();
        viewModel.setDerivativeStep("-a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedFunction() {
        setValidViewModelInputState();
        viewModel.setFunction("a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setBadFormattedStartPoint() {
        setValidViewModelInputState();
        viewModel.setStartPoint("a");
        assertEquals(ApplicationStatus.BAD_FORMAT.toString(), viewModel.getApplicationStatus());
    }

    @Test
    public void setNonMonotonicFiction() {
        setValidViewModelInputState();
        viewModel.setFunction("x^2");
        assertEquals(ApplicationStatus.NON_MONOTONIC_FUNCTION.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setBadInterval() {
        setValidViewModelInputState();
        viewModel.setLeftPoint("-1");
        viewModel.setRightPoint("-2");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setBadAccuracy() {
        setValidViewModelInputState();
        viewModel.setAccuracy("0");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setStartPointOutsideOfInterval() {
        setValidViewModelInputState();
        viewModel.setStartPoint("1000");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void setBadDerivativeStep() {
        setValidViewModelInputState();
        viewModel.setDerivativeStep("0");
        assertEquals(ApplicationStatus.BAD_PARAMETERS.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void waitingStatusWhenOneFieldIsEmpty() {
        setValidViewModelInputState();
        viewModel.setAccuracy("");
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
        viewModel.setFunction("x+100");
        viewModel.findRoot();
        assertEquals(ApplicationStatus.FAILED.toString(),
                viewModel.getApplicationStatus());
    }

    @Test
    public void findRootDoesNothingIfFindButtonIsDisabled()  {
      setValidViewModelInputState();
      viewModel.setDerivativeStep("z"); //set illegal value to disable findRoot button
      viewModel.findRoot();
      assertTrue(viewModel.getSolverReport().isEmpty());
    }
}
