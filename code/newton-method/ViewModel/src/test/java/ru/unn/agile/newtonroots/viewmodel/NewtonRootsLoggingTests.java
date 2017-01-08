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
    public void editOfLeftSegmentPointIsLogged() {
        viewModel.editLeftPointTo("-1.0");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.LEFT_END_TEXT + viewModel.getLeftPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void settingLeftSegmentThroughPropertyWhileInEditModeIsLogged() {
        viewModel.leftPointProperty().startEdit();
        viewModel.leftPointProperty().set("-2.0");
        viewModel.leftPointProperty().finishEdit();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.LEFT_END_TEXT + viewModel.getLeftPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void propertySettingWithoutFinishingEditIsNotLogged() {
        int logSizeBefore = viewModel.getLogSize();

        viewModel.accuracyProperty().startEdit();
        viewModel.accuracyProperty().set("1e-5");

        int logSizeAfter = viewModel.getLogSize();
        assertEquals(logSizeBefore, logSizeAfter);
    }

    @Test
    public void editOfRightSegmentPointIsLogged() {
        viewModel.editRightPointTo("1.0");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.RIGHT_END_TEXT + viewModel.getRightPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void editOfDerivativeStepIsLogged() {
        viewModel.editDerivativeStepTo("1e-4");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.DERIVATIVE_STEP_TEXT + viewModel.getDerivativeStep();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void editOfAccuracyIsLogged() {
        viewModel.editAccuracyTo("1e-8");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.ACCURACY_TEXT + viewModel.getAccuracy();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void editOfFunctionExpressionIsLogged() {
        viewModel.editFuncitonExpressionTo("e^(x^2) - 0.5");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.FUNCTION_EXPR_TEXT + viewModel.getFunctionExpression();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void editOfStartPointIsLogged() {
        viewModel.editStartPointTo("0.1");

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.START_POINT_TEXT + viewModel.getStartPoint();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void changeOfStopCriterionIsLogged() {
        viewModel.setStopCriterion(StoppingCriterion.DifferenceBetweenApproximations);

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.STOP_CRITERION_TEXT + viewModel.getStopCriterion();
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void findRootLogsFailedRootSearchAttemptIfInputIsInvalid() {
        setupParametersForSuccessfulRootSearch();
        viewModel.editFuncitonExpressionTo("z");

        try {
            viewModel.findRoot();
        } catch (Exception e) {
            String lastLogMessage = viewModel.getLastLogMessage();
            String expectedPattern = ".*" + LogMessages.INVALID_INPUT_TEXT
                    + getRootSearchResultMessagePrefix();
            assertThat(lastLogMessage, matchesPattern(expectedPattern));
        }
    }

    @Test
    public void runningSuccessfulRootSearchIsLogged() {
        setupParametersForSuccessfulRootSearch();

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = ".*" + LogMessages.ROOT_SEARCH_FINISHED_TEXT
                + getRootSearchResultMessagePrefix()
                + "Root was found. Results: x=\\d+[\\.\\,]\\d+  "
                + "accuracy=\\d+[\\.\\,]\\d+  iterations=\\d+";
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void runningRootSearchWhenThereIsNoRootInIntervalIsLogged() {
        setupParametersForSuccessfulRootSearch();
        viewModel.editFuncitonExpressionTo("x+100");

        viewModel.findRoot();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedPattern = ".*" + LogMessages.ROOT_SEARCH_FINISHED_TEXT
                + getRootSearchResultMessagePrefix() + "No root in interval.";
        assertThat(lastMessage, matchesPattern(expectedPattern));
    }

    @Test
    public void afterInstantiationLogLinesIsEmpty() {
        assertTrue(viewModel.getLogLines().isEmpty());
    }

    @Test
    public void logLinesContainsLogInReverseOrder() {
        viewModel.editLeftPointTo("-1.0");
        viewModel.editStartPointTo("-0.2");

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

        viewModel.editFuncitonExpressionTo(thrownOutOfLogValue);
        viewModel.editAccuracyTo(retainedInLogValue);
        int messagesLeftToAdd = logSizeLimit - 1;
        fillLogWithMessages(messagesLeftToAdd);

        String logLines = viewModel.getLogLines();
        String expectedLogEnd = LogMessages.getAccuracyChangeMessage(retainedInLogValue)
                + "\n" + LogMessages.getLogSizeLimitExceededMessage();
        assertThat(logLines, endsWith(expectedLogEnd));
    }

    @Test
    public void finishingAllEditsWritesThePendingEditToLog() {
        viewModel.accuracyProperty().startEdit();
        viewModel.accuracyProperty().set("1e-10");

        viewModel.finishAllEdits();

        String lastMessage = viewModel.getLastLogMessage();
        String expectedMessage = LogMessages.getAccuracyChangeMessage(viewModel.getAccuracy());
        assertThat(lastMessage, endsWith(expectedMessage));
    }

    @Test
    public void finishingAllEditsDoesNotWritePendingEditWithoutChangesToLog() {
        viewModel.functionExpressionProperty().startEdit();
        viewModel.functionExpressionProperty().set("x^x");
        viewModel.functionExpressionProperty().set("");

        viewModel.finishAllEdits();

        assertEquals(0, viewModel.getLogSize());
    }

    private void fillLogWithMessages(final int messagesToAdd) {
        int messagesLeftToAdd = messagesToAdd;

        while (messagesLeftToAdd > 1) {
            viewModel.editLeftPointTo("-1.0");
            viewModel.editLeftPointTo("1.0");
            messagesLeftToAdd -= 2;
        }

        if (messagesLeftToAdd == 1) {
            viewModel.editLeftPointTo("2.0");
        }
    }

    private void setupParametersForSuccessfulRootSearch() {
        viewModel.editLeftPointTo("-1.0");
        viewModel.editRightPointTo("1.0");
        viewModel.editDerivativeStepTo("1e-5");
        viewModel.editAccuracyTo("1e-8");
        viewModel.editFuncitonExpressionTo("sin(x-0.1)");
        viewModel.editStartPointTo("-0.2");
    }

    private String getRootSearchResultMessagePrefix() {
        final String rootSearchParametersPattern = "Parameters: "
                + "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  "
                + "function: \\Q\"%s\"\\E  startPoint: %s  stopCriterion: %s. ";

        return String.format(rootSearchParametersPattern,
                viewModel.getLeftPoint(),
                viewModel.getRightPoint(),
                viewModel.getDerivativeStep(),
                viewModel.getAccuracy(),
                viewModel.getFunctionExpression(),
                viewModel.getStartPoint(),
                viewModel.getStopCriterion());
    }
}
