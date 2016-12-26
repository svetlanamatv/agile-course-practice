package ru.unn.agile.NewtonRoots.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.NewtonRoots.Model.NewtonMethod.StoppingCriterion;
import ru.unn.agile.NewtonRoots.viewmodel.NewtonRootAppViewModel.LogMessages;

import static org.junit.Assert.*;
import static ru.unn.agile.NewtonRoots.viewmodel.testutil.RegexMatcher.matchesPattern;

public class NewtonRootAppLoggingTests {
    private static String ROOT_SEARCH_PATTERN = "Root search finished. Parameters: " +
            "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  " +
            "function: \\Q\"%s\"\\E  startPoint: %s  stopCriterion: %s. ";
    private static String TIMESTAMP_PATTERN = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private NewtonRootAppViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new NewtonRootAppViewModel(new FakeLogger());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void changeOfLeftSegmentPointIsLogged() {
        viewModel.setLeftPoint("-1.0");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s%s", TIMESTAMP_PATTERN,
                LogMessages.LEFT_END_TEXT, viewModel.getLeftPoint());
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void changeOfRightSegmentPointIsLogged() {
        viewModel.setRightPoint("1.0");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s%s", TIMESTAMP_PATTERN,
                LogMessages.RIGHT_END_TEXT, viewModel.getRightPoint());
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void changeOfDerivativeStepIsLogged() {
        viewModel.setDerivativeStep("1e-4");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s%s", TIMESTAMP_PATTERN,
                LogMessages.DERIVATIVE_STEP_TEXT, viewModel.getDerivativeStep());
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void changeOfAccuracyIsLogged() {
        viewModel.setAccuracy("1e-8");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s%s", TIMESTAMP_PATTERN,
                LogMessages.ACCURACY_TEXT, viewModel.getAccuracy());
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void changeOfFunctionExpressionIsLogged() {
        viewModel.setFunction("e^(x^2) - 0.5");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s\\Q%s\\E", TIMESTAMP_PATTERN,
                LogMessages.FUNCTION_EXPR_TEXT, viewModel.getFunction());
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void changeOfStartPointIsLogged() {
        viewModel.setStartPoint("0.1");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s%s", TIMESTAMP_PATTERN,
                LogMessages.START_POINT_TEXT, viewModel.getStartPoint());
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void changeOfStopCriterionIsLogged() {
        viewModel.setStopCriterion(StoppingCriterion.DifferenceBetweenApproximates);

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format("%s %s%s", TIMESTAMP_PATTERN,
                LogMessages.STOP_CRITERION_TEXT, viewModel.getStopCriterion());
        assertThat(lastMessage, matchesPattern(expectedPattern));
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
        String expectedPattern = String.format(
                TIMESTAMP_PATTERN + " " + ROOT_SEARCH_PATTERN,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunction(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion()) +
                "Root was found. Results: x=\\d+\\.\\d+  accuracy=\\d+\\.\\d+  iterations=\\d+";
        assertThat(lastMessage, matchesPattern(expectedPattern));
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
        String expectedPattern = String.format(
                TIMESTAMP_PATTERN + " " + ROOT_SEARCH_PATTERN,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunction(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion()) + "Root wasn't found";
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }
}
