package ru.unn.agile.NewtonRoots.viewmodel;

import org.junit.Before;
import org.junit.Test;

import ru.unn.agile.NewtonRoots.Model.NewtonMethod.StoppingCriterion;
import ru.unn.agile.NewtonRoots.viewmodel.NewtonRootAppViewModel.LogMessages;

import static org.junit.Assert.*;

public class NewtonRootAppLoggingTests {
    private NewtonRootAppViewModel viewModel;
    private static String ROOT_SEARCH_PATTERN = "Root search finished. Parameters: " +
            "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  " +
            "function: \\Q\"%s\"\\E  startPoint: %s  stopCriterion: %s. ";

    @Before
    public void setUp() {
        viewModel = new NewtonRootAppViewModel(new FakeLogger());
    }

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

    @Test
    public void changeOfLeftSegmentPointIsLogged() {
        viewModel.setLeftPoint("-1.0");

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.LEFT_END_TEXT + viewModel.getLeftPoint(), lastMessage);
    }

    @Test
    public void changeOfRightSegmentPointIsLogged() {
        viewModel.setRightPoint("1.0");

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.RIGHT_END_TEXT + viewModel.getRightPoint(), lastMessage);
    }

    @Test
    public void changeOfDerivativeStepIsLogged() {
        viewModel.setDerivativeStep("1e-4");

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.DERIVATIVE_STEP_TEXT + viewModel.getDerivativeStep(), lastMessage);
    }

    @Test
    public void changeOfAccuracyIsLogged() {
        viewModel.setAccuracy("1e-8");

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.ACCURACY_TEXT + viewModel.getAccuracy(), lastMessage);
    }

    @Test
    public void changeOfFunctionExpressionIsLogged() {
        viewModel.setFunction("e^(x^2) - 0.5");

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.FUNCTION_EXPR_TEXT + viewModel.getFunction(), lastMessage);
    }

    @Test
    public void changeOfStartPointIsLogged() {
        viewModel.setStartPoint("0.1");

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.START_POINT_TEXT + viewModel.getStartPoint(), lastMessage);
    }

    @Test
    public void changeOfStopCriterionIsLogged() {
        viewModel.setStopCriterion(StoppingCriterion.DifferenceBetweenApproximates);

        String lastMessage = viewModel.getLastLogMessage();
        assertEquals(LogMessages.STOP_CRITERION_TEXT + viewModel.getStopCriterion(), lastMessage);
    }

    @Test
    public void runningSuccessfulRootSearchIsLogged() {
        viewModel.setLeftPoint("-1.0");
        viewModel.setRightPoint("1.0");
        viewModel.setDerivativeStep("1e-5");
        viewModel.setAccuracy("1e-8");
        viewModel.setFunction("sin(x-0.1)");
        viewModel.setStartPoint("-0.2");

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format(ROOT_SEARCH_PATTERN,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunction(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion()) +
                "Root was found. Results: x=\\d+\\.\\d+  accuracy=\\d+\\.\\d+  iterations=\\d+";
        assertTrue(lastMessage.matches(expectedPattern));
    }

    @Test
    public void runningFailedRootSearchIsLogged() {
        viewModel.setLeftPoint("-1.0");
        viewModel.setRightPoint("1.0");
        viewModel.setDerivativeStep("1e-5");
        viewModel.setAccuracy("1e-8");
        viewModel.setFunction("x+100");
        viewModel.setStartPoint("0.1");

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format(ROOT_SEARCH_PATTERN,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunction(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion()) + "Root wasn't found";
        assertTrue(lastMessage.matches(expectedPattern));
    }
}
