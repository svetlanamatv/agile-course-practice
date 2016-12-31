package ru.unn.agile.newtonroots.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.newtonroots.model.StoppingCriterion;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootsViewModel.LogMessages;

import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.agile.newtonroots.viewmodel.testutils.RegexMatcher.matchesPattern;
import static ru.unn.agile.newtonroots.viewmodel.testutils.StringSuffixMatcher.endsWith;

public class NewtonRootsLoggingTests {
    private NewtonRootsViewModel viewModel;

    protected void setViewModel(final NewtonRootsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new NewtonRootsViewModel(new TimestampingInMemoryLogger());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void changeWithoutFinishingOfTrackingIsNotLogged() {
        int logSizeBefore = viewModel.getLogSize();

        viewModel.setAccuracy("1e-5");

        int logSizeAfter = viewModel.getLogSize();
        assertEquals(logSizeBefore, logSizeAfter);
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
        viewModel.setFunctionExpression("e^(x^2) - 0.5");
        viewModel.finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.FUNCTION_EXPR_TEXT + viewModel.getFunctionExpression();
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
        viewModel.setStopCriterion(StoppingCriterion.DifferenceBetweenApproximations);
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
        String expectedPattern = getRootSearchResultMessagePrefix()
                + "Root was found. Results: x=\\d+\\.\\d+  accuracy=\\d+\\.\\d+  iterations=\\d+";
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void runningFailedRootSearchIsLogged() {
        setupParametersForSuccessfulRootSearch();
        viewModel.setFunctionExpression("x+100");
        viewModel.finishEdit();

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = getRootSearchResultMessagePrefix() + "Root wasn't found";
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void afterInstantiationLogLinesIsEmpty() {
        assertTrue(viewModel.getLogLines().isEmpty());
    }

    @Test
    public void logLinesContainsLogInReverseOrder() {
        viewModel.setLeftPoint("-1.0");
        viewModel.finishEdit();
        viewModel.setStartPoint("-0.2");
        viewModel.finishEdit();

        List<String> messages = viewModel.getLogMessages();
        String expectedLogLines = messages.get(1) + "\n" + messages.get(0);
        String actualLogLines = viewModel.getLogLines();
        assertEquals(expectedLogLines, actualLogLines);
    }

    @Test
    public void oldestLogLinesWhichAreOutOfLimitAreNotShown() {
        final int logSizeLimit = NewtonRootsViewModel.getMaxLogLinesMessageCount();
        final String thrownOutOfLogValue = "sin(x-0.1)";
        final String retainedInLogValue = "1e-5";

        viewModel.setFunctionExpression(thrownOutOfLogValue);
        viewModel.finishEdit();
        viewModel.setAccuracy(retainedInLogValue);
        viewModel.finishEdit();
        int messagesLeftToAdd = logSizeLimit - 1;
        fillLogWithMessages(messagesLeftToAdd);

        String logLines = viewModel.getLogLines();
        String expectedLogEnd = LogMessages.getAccuracyChangeMessage(retainedInLogValue)
                + "\n" + LogMessages.getLogSizeLimitExceededMessage();
        assertThat(logLines, endsWith(expectedLogEnd));
    }

    private void fillLogWithMessages(final int messagesToAdd) {
        int messagesLeftToAdd = messagesToAdd;

        while (messagesLeftToAdd > 1) {
            viewModel.setLeftPoint("-1.0");
            viewModel.finishEdit();
            viewModel.setLeftPoint("1.0");
            viewModel.finishEdit();
            messagesLeftToAdd -= 2;
        }

        if (messagesLeftToAdd == 1) {
            viewModel.setLeftPoint("2.0");
            viewModel.finishEdit();
        }
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
        viewModel.setFunctionExpression("sin(x-0.1)");
        viewModel.finishEdit();
        viewModel.setStartPoint("-0.2");
        viewModel.finishEdit();
    }

    private String getRootSearchResultMessagePrefix() {
        final String rootSearchPattern = "Root search finished. Parameters: "
                + "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  "
                + "function: \\Q\"%s\"\\E  startPoint: %s  stopCriterion: %s. ";

        return String.format(".*" + rootSearchPattern,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunctionExpression(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion());
    }
}
