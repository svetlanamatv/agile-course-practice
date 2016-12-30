package ru.unn.agile.newtonroots.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.newtonroots.model.NewtonMethod.StoppingCriterion;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootAppViewModel.LogMessages;

import static org.junit.Assert.*;
import static ru.unn.agile.newtonroots.viewmodel.testutils.RegexMatcher.matchesPattern;
import static ru.unn.agile.newtonroots.viewmodel.testutils.StringSuffixMatcher.endsWith;

public class NewtonRootAppLoggingTests {
    private static String ROOT_SEARCH_PATTERN = "Root search finished. Parameters: " +
            "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  " +
            "function: \\Q\"%s\"\\E  startPoint: %s  stopCriterion: %s. ";
    protected NewtonRootAppViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new NewtonRootAppViewModel(new TimestampingInMemoryLogger());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void changeWithoutFinishingOfTrackingIsNotLogged() {
        viewModel.setAccuracy("1e-5");

        int logSize = viewModel.getLogger().getMessageList().size();
        assertEquals(0, logSize);
    }

    @Test
    public void changeOfLeftSegmentPointIsLogged() {
        viewModel.setLeftPoint("-1.0");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.LEFT_END_TEXT + viewModel.getLeftPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfLeftSegmentThroughPropertyIsLogged() {
        viewModel.leftPointProperty().set("-2.0");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.LEFT_END_TEXT + viewModel.getLeftPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfRightSegmentPointIsLogged() {
        viewModel.setRightPoint("1.0");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.RIGHT_END_TEXT + viewModel.getRightPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfDerivativeStepIsLogged() {
        viewModel.setDerivativeStep("1e-4");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.DERIVATIVE_STEP_TEXT + viewModel.getDerivativeStep();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfAccuracyIsLogged() {
        viewModel.setAccuracy("1e-8");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.ACCURACY_TEXT + viewModel.getAccuracy();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfFunctionExpressionIsLogged() {
        viewModel.setFunction("e^(x^2) - 0.5");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.FUNCTION_EXPR_TEXT + viewModel.getFunction();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfStartPointIsLogged() {
        viewModel.setStartPoint("0.1");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.START_POINT_TEXT + viewModel.getStartPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfStopCriterionIsLogged() {
        viewModel.setStopCriterion(StoppingCriterion.DifferenceBetweenApproximates);
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.STOP_CRITERION_TEXT + viewModel.getStopCriterion();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void runningSuccessfulRootSearchIsLogged() {
        setupParametersForSuccessfulRootSearch();

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format(".*" + ROOT_SEARCH_PATTERN,
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
        setupParametersForSuccessfulRootSearch();
        viewModel.setFunction("x+100");
        viewModel.finishEdit();

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = String.format(".*" + ROOT_SEARCH_PATTERN,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunction(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion()) + "Root wasn't found";
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    private void setupParametersForSuccessfulRootSearch() {
        viewModel.setLeftPoint("-1.0");
        viewModel.finishEdit();
        viewModel.setRightPoint("1.0");
        viewModel.finishEdit();
        viewModel.setDerivativeStep("1e-5");
        viewModel.finishEdit();
        viewModel.setAccuracy("1e-8");
        viewModel.finishEdit();
        viewModel.setFunction("sin(x-0.1)");
        viewModel.finishEdit();
        viewModel.setStartPoint("-0.2");
        viewModel.finishEdit();
    }
}
