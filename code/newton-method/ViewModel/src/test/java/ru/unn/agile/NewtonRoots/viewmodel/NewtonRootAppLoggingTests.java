package ru.unn.agile.NewtonRoots.viewmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ru.unn.agile.NewtonRoots.viewmodel.NewtonRootAppViewModel.LogMessages;

public class NewtonRootAppLoggingTests {
    private NewtonRootAppViewModel viewModel;

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void canSetLoggerOnAlreadyCreatedViewModel() {
        NewtonRootAppViewModel viewModelWithoutLogger = new NewtonRootAppViewModel();

        viewModelWithoutLogger.setLogger(new FakeLogger());

        assertNotNull(viewModelWithoutLogger.getLogger());
    }

    @Before
    public void setUp() {
        viewModel = new NewtonRootAppViewModel(new FakeLogger());
    }

    @Test
    public void changeOfLeftSegmentPointIsLogged() {
        viewModel.setLeftPoint("-1.0");

        String lastMessage = viewModel.getLog().get(0);
        assertEquals(lastMessage, LogMessages.LEFT_END_TEXT + viewModel.getLeftPoint());
    }

    @Test
    public void changeOfRightSegmentPointIsLogged() {
        viewModel.setRightPoint("1.0");

        String lastMessage = viewModel.getLog().get(0);
        assertEquals(lastMessage, LogMessages.RIGHT_END_TEXT + viewModel.getRightPoint());
    }

    @Test
    public void changeOfDerivativeStepIsLogged() {
        viewModel.setDerivativeStep("1e-4");

        String lastMessage = viewModel.getLog().get(0);
        assertEquals(lastMessage, LogMessages.DERIVATIVE_STEP_TEXT + viewModel.getDerivativeStep());
    }

    @Test
    public void changeOfAccuracyIsLogged() {
        viewModel.setAccuracy("1e-8");

        String lastMessage = viewModel.getLog().get(0);
        assertEquals(lastMessage, LogMessages.ACCURACY_TEXT + viewModel.getAccuracy());
    }

    @Test
    public void changeOfFunctionExpressionIsLogged() {
        viewModel.setFunction("e^(x^2) - 0.5");

        String lastMessage = viewModel.getLog().get(0);
        assertEquals(lastMessage, LogMessages.FUNCTION_EXPR_TEXT + viewModel.getFunction());
    }

    @Test
    public void changeOfStartPointIsLogged() {
        viewModel.setStartPoint("0.1");

        String lastMessage = viewModel.getLog().get(0);
        assertEquals(lastMessage, LogMessages.START_POINT_TEXT + viewModel.getStartPoint());
    }
}
