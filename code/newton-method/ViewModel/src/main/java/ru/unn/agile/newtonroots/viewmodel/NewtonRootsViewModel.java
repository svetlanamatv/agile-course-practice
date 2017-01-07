package ru.unn.agile.newtonroots.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.newtonroots.model.AnalyticallyDefinedScalarFunction;
import ru.unn.agile.newtonroots.model.NewtonMethod;
import ru.unn.agile.newtonroots.model.StoppingCriterion;

import java.util.List;

enum ApplicationStatus {
    WAITING("Please provide input data"),
    READY("Press 'Find root'"),
    NON_MONOTONIC_FUNCTION("The functionExpression is not monotonic"),
    BAD_FORMAT("Bad format"),
    BAD_PARAMETERS("Wrong method parameters"),
    SUCCESS("Root found"),
    FAILED("Root not found");

    private final String name;

    ApplicationStatus(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

public class NewtonRootsViewModel {
    private static final int MAX_LOGLINES_MESSAGE_COUNT = 500;
    private final ExplicitlyEditableStringProperty leftPoint;
    private final ExplicitlyEditableStringProperty rightPoint;
    private final ExplicitlyEditableStringProperty derivativeStep;
    private final ExplicitlyEditableStringProperty accuracy;
    private final ExplicitlyEditableStringProperty functionExpression;
    private final ExplicitlyEditableStringProperty startPoint;
    private final StringProperty solverReport = new SimpleStringProperty("");
    private final BooleanProperty inputDataIsInvalid = new SimpleBooleanProperty(true);
    private final StringProperty applicationStatus =
            new SimpleStringProperty(ApplicationStatus.WAITING.toString());

    private final ObjectProperty<ObservableList<StoppingCriterion>> stopCriteria =
            new SimpleObjectProperty<>(
                    FXCollections.observableArrayList(StoppingCriterion.values()));
    private final ObjectProperty<StoppingCriterion> stopCriterion =
            new SimpleObjectProperty<>(StoppingCriterion.FunctionModulus);

    private final StringProperty logLines = new SimpleStringProperty("");

    private final Logger logger;

    public static int getMaxLogLinesMessageCount() {
        return MAX_LOGLINES_MESSAGE_COUNT;
    }

    public NewtonRootsViewModel(final Logger logger) {
        leftPoint = new ExplicitlyEditableStringProperty("",
                value -> logMessage(LogMessages.getLeftPointChangeMessage(value)));
        rightPoint = new ExplicitlyEditableStringProperty("",
                value -> logMessage(LogMessages.getRightPointChangeMessage(value)));
        derivativeStep = new ExplicitlyEditableStringProperty("",
                value -> logMessage(LogMessages.getDerivativeStepChangeMessage(value)));
        accuracy = new ExplicitlyEditableStringProperty("",
                value -> logMessage(LogMessages.getAccuracyChangeMessage(value)));
        functionExpression = new ExplicitlyEditableStringProperty("",
                value -> logMessage(LogMessages.getFunctionExpressionChangeMessage(value)));
        startPoint = new ExplicitlyEditableStringProperty("",
                value -> logMessage(LogMessages.getStartPointChangeMessage(value)));

        leftPoint.addListener(new StringValueChangeListener());
        rightPoint.addListener(new StringValueChangeListener());
        derivativeStep.addListener(new StringValueChangeListener());
        accuracy.addListener(new StringValueChangeListener());
        functionExpression.addListener(new StringValueChangeListener());
        startPoint.addListener(new StringValueChangeListener());

        stopCriterion.addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                logMessage(LogMessages.getStopCriterionChangeMessage(newValue));
            }
        });

        this.logger = logger;
    }


    public ExplicitlyEditableStringProperty leftPointProperty() {
        return leftPoint;
    }

    public String getLeftPoint() {
        return leftPoint.get();
    }

    void editLeftPointTo(final String value) {
        leftPoint.startEdit();
        leftPoint.set(value);
        leftPoint.finishEdit();
    }


    public ExplicitlyEditableStringProperty rightPointProperty() {
        return rightPoint;
    }

    public String getRightPoint() {
        return rightPoint.get();
    }

    void editRightPointTo(final String value) {
        rightPoint.startEdit();
        rightPoint.set(value);
        rightPoint.finishEdit();
    }


    public ExplicitlyEditableStringProperty derivativeStepProperty() {
        return derivativeStep;
    }

    public String getDerivativeStep() {
        return derivativeStep.get();
    }

    void editDerivativeStepTo(final String value) {
        derivativeStep.startEdit();
        derivativeStep.set(value);
        derivativeStep.finishEdit();
    }


    public ExplicitlyEditableStringProperty accuracyProperty() {
        return accuracy;
    }

    public String getAccuracy() {
        return accuracy.get();
    }

    void editAccuracyTo(final String value) {
        accuracy.startEdit();
        accuracy.set(value);
        accuracy.finishEdit();
    }


    public ExplicitlyEditableStringProperty functionExpressionProperty() {
        return functionExpression;
    }

    public String getFunctionExpression() {
        return functionExpression.get();
    }

    void editFuncitonExpressionTo(final String value) {
        functionExpression.startEdit();
        functionExpression.set(value);
        functionExpression.finishEdit();
    }


    public ExplicitlyEditableStringProperty startPointProperty() {
        return startPoint;
    }

    public String getStartPoint() {
        return startPoint.get();
    }

    void editStartPointTo(final String value) {
        startPoint.startEdit();
        startPoint.set(value);
        startPoint.finishEdit();
    }


    public BooleanProperty inputDataIsInvalidProperty() {
        return inputDataIsInvalid;
    }

    public boolean getInputDataIsInvalid() {
        return inputDataIsInvalid.get();
    }


    public StringProperty solverReportProperty() {
        return solverReport;
    }

    public String getSolverReport() {
        return solverReport.get();
    }

    public void setSolverReport(final String value) {
        solverReport.set(value);
    }


    public StringProperty applicationStatusProperty() {
        return applicationStatus;
    }

    public String getApplicationStatus() {
        return applicationStatus.get();
    }


    public ObjectProperty<ObservableList<StoppingCriterion>> stopCriteriaProperty() {
        return stopCriteria;
    }

    public final ObservableList<StoppingCriterion> getStopCriteria() {
        return stopCriteria.get();
    }


    public ObjectProperty<StoppingCriterion> stopCriterionProperty() {
        return stopCriterion;
    }

    public StoppingCriterion getStopCriterion() {
        return stopCriterion.get();
    }

    public void setStopCriterion(final StoppingCriterion value) {
        stopCriterion.set(value);
    }


    public StringProperty logLinesProperty() {
        return logLines;
    }

    public String getLogLines() {
        return logLines.get();
    }


    public Logger getLogger() {
        return logger;
    }

    public String getLastLogMessage() {
        return logger.getLastMessage();
    }

    public List<String> getLogMessages() {
        return logger.getMessageList();
    }

    public int getLogSize() {
        return logger.getMessageCount();
    }


    public void finishAllEdits() {
        leftPoint.finishEdit();
        rightPoint.finishEdit();
        derivativeStep.finishEdit();
        accuracy.finishEdit();
        functionExpression.finishEdit();
        startPoint.finishEdit();
    }


    public void findRoot() {
        if (inputDataIsInvalid.get()) {
            logMessage(LogMessages.getInvalidInputMessage(this));
            throw new IllegalArgumentException();
        }

        double leftPointValue = Double.parseDouble(leftPoint.get());
        double rightPointValue = Double.parseDouble(rightPoint.get());
        double startPointValue = Double.parseDouble(startPoint.get());
        double accuracyValue = Double.parseDouble(accuracy.get());
        double derivativeStepValue = Double.parseDouble(derivativeStep.get());
        NewtonMethod method = new NewtonMethod(accuracyValue, derivativeStepValue);

        method.setStoppingCriterion(stopCriterion.get());

        AnalyticallyDefinedScalarFunction function;
        try {
            function = new AnalyticallyDefinedScalarFunction(functionExpression.get());
        } catch (AnalyticallyDefinedScalarFunction.InvalidFunctionExpressionException e) {
            e.printStackTrace();
            return;
        }

        double root = method.findRoot(function, startPointValue, leftPointValue, rightPointValue);
        NewtonMethod.ResultStatus result = method.getResultStatus();

        RuntimeException unexpectedInvalidInputException =
                new RuntimeException("Invalid input was supplied and not caught.");
        switch (result) {
            case RootSuccessfullyFound:
                int iterationsCount = method.getIterationsCount();
                double finalAccuracy = method.getFinalAccuracy();
                setSolverReport("Root: " + root
                        + "\nIterations performed: " + iterationsCount
                        + "\nReached accuracy: " + finalAccuracy);
                applicationStatus.set(ApplicationStatus.SUCCESS.toString());

                logMessage(LogMessages.getSuccessfulRunMessage(this,
                        root, finalAccuracy, iterationsCount));
                break;


            case NoRootInInterval:
                applicationStatus.set(ApplicationStatus.FAILED.toString());
                logMessage(LogMessages.getNoRootInIntervalMessage(this));
                break;


            case IncorrectIntervalBoundaries:
                logMessage(LogMessages.getUnexpectedInvalidInputMessage(
                        "Right point is on the left of the left point."));
                throw unexpectedInvalidInputException;


            case InitialPointOutsideInterval:
                logMessage(LogMessages.getUnexpectedInvalidInputMessage(
                        "Start point outside of the interval."));
                throw unexpectedInvalidInputException;


            case NonmonotonicFunctionOnInterval:
                logMessage(LogMessages.getUnexpectedInvalidInputMessage(
                        "Supplied function isn't monotonic on the interval."));
                throw unexpectedInvalidInputException;

            default:
                logMessage(LogMessages.UNKNOWN_ERROR_TEXT);
                throw new RuntimeException("Unknown error has occurred.");
        }
    }

    private boolean checkInputFormat() {
        try {
            Double.parseDouble(leftPoint.get());
            Double.parseDouble(rightPoint.get());
            Double.parseDouble(accuracy.get());
            Double.parseDouble(derivativeStep.get());
            Double.parseDouble(startPoint.get());
            AnalyticallyDefinedScalarFunction testFunction =
                    new AnalyticallyDefinedScalarFunction(functionExpression.get());
        } catch (NumberFormatException nfe) {
            return false;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean checkMonotonic() {
        AnalyticallyDefinedScalarFunction testFunction;
        try {
            testFunction = new AnalyticallyDefinedScalarFunction(functionExpression.get());
        } catch (Exception e) {
            return false;
        }

        return NewtonMethod.isFunctionMonotonicOnInterval(testFunction,
                Double.parseDouble(leftPoint.get()),
                Double.parseDouble(rightPoint.get()));
    }

    private boolean checkMethodParameters() {
        double leftPointValue = Double.parseDouble(leftPoint.get());
        double rightPointValue = Double.parseDouble(rightPoint.get());
        double startPointValue = Double.parseDouble(startPoint.get());

        if (leftPointValue >= rightPointValue || startPointValue < leftPointValue
                || startPointValue > rightPointValue) {
            return false;
        }

        double accuracyValue = Double.parseDouble(accuracy.get());
        double derivativeStepValue = Double.parseDouble(derivativeStep.get());
        double intervalLen = rightPointValue - leftPointValue;
        boolean isAccuracyAndStepIncorrect = accuracyValue <= 0 || accuracyValue > intervalLen
                || derivativeStepValue <= 0 || derivativeStepValue > intervalLen;

        return !isAccuracyAndStepIncorrect;
    }

    private ApplicationStatus checkInput() {
        if (leftPoint.get().isEmpty() || rightPoint.get().isEmpty()
                || accuracy.get().isEmpty() || derivativeStep.get().isEmpty()
                || functionExpression.get().isEmpty() || startPoint.get().isEmpty()
                || stopCriterion.get() == null) {
            return ApplicationStatus.WAITING;
        }

        if (!checkInputFormat()) {
            return ApplicationStatus.BAD_FORMAT;
        }

        if (!checkMonotonic()) {
            return ApplicationStatus.NON_MONOTONIC_FUNCTION;
        }

        if (!checkMethodParameters()) {
            return ApplicationStatus.BAD_PARAMETERS;
        }

        return ApplicationStatus.READY;
    }

    private void logMessage(final String message) {
        logger.appendMessage(message);

        updateLogLines();
    }

    private void updateLogLines() {
        String logLinesContents = logLines.get();

        if (logLinesContents.isEmpty()) {
            logLinesContents = logger.getLastMessage();
        } else {
            logLinesContents = logger.getLastMessage() + '\n' + logLinesContents;
        }

        if (logger.getMessageCount() > MAX_LOGLINES_MESSAGE_COUNT) {
            int lastNewlineIndex = logLinesContents.length()
                    - LogMessages.getLogSizeLimitExceededMessage().length() - 1;
            int secondToLastNewlineIndex = logLinesContents.lastIndexOf('\n', lastNewlineIndex);
            logLinesContents = logLinesContents.substring(0, secondToLastNewlineIndex) + '\n'
                    + LogMessages.getLogSizeLimitExceededMessage();
        }

        logLines.set(logLinesContents);
    }
    static final class LogMessages {
        static final String LEFT_END_TEXT = "Left segment end changed to ";
        static final String RIGHT_END_TEXT = "Right segment end changed to ";
        static final String DERIVATIVE_STEP_TEXT = "Derivative step changed to ";
        static final String ACCURACY_TEXT = "Accuracy changed to ";
        static final String FUNCTION_EXPR_TEXT = "Function expression changed to ";
        static final String START_POINT_TEXT = "Start point changed to ";
        static final String STOP_CRITERION_TEXT = "Stop criterion changed to ";
        static final String ROOT_SEARCH_FINISHED_TEXT = "Root search finished. ";
        static final String ROOT_SEARCH_PARAMETERS_TEXT = "Parameters: "
                + "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  "
                + "function: \"%s\"  startPoint: %s  stopCriterion: %s. ";
        static final String INVALID_INPUT_TEXT =
                "Root search didn't start because of invalid input. ";
        public static final String UNKNOWN_ERROR_TEXT = "Root search failed with unknown error.";

        private LogMessages() {
        }

        static String getLeftPointChangeMessage(final String value) {
            return LEFT_END_TEXT + value;
        }

        static String getRightPointChangeMessage(final String value) {
            return RIGHT_END_TEXT + value;
        }

        static String getDerivativeStepChangeMessage(final String value) {
            return DERIVATIVE_STEP_TEXT + value;
        }

        static String getAccuracyChangeMessage(final String value) {
            return ACCURACY_TEXT + value;
        }

        static String getFunctionExpressionChangeMessage(final String value) {
            return FUNCTION_EXPR_TEXT + value;
        }

        static String getStartPointChangeMessage(final String value) {
            return START_POINT_TEXT + value;
        }

        static String getStopCriterionChangeMessage(final StoppingCriterion value) {
            return STOP_CRITERION_TEXT + value;
        }

        static String getSuccessfulRunMessage(final NewtonRootsViewModel viewModel,
                final double root,
                final double finalAccuracy,
                final int iterationsCounter) {

            return ROOT_SEARCH_FINISHED_TEXT + getParametersMessageBlock(viewModel)
                    + String.format("Root was found. "
                            + "Results: x=%.7g  accuracy=%.7g  "
                            + "iterations=%d",
                            root, finalAccuracy, iterationsCounter);
        }

        static String getNoRootInIntervalMessage(final NewtonRootsViewModel viewModel) {
             return ROOT_SEARCH_FINISHED_TEXT + getParametersMessageBlock(viewModel)
                     + "No root in interval.";
        }

        static String getLogSizeLimitExceededMessage() {
            return "... <rest of the log is omitted> ...";
        }

        static String getInvalidInputMessage(final NewtonRootsViewModel viewModel) {
            return INVALID_INPUT_TEXT + getParametersMessageBlock(viewModel);
        }

        static String getUnexpectedInvalidInputMessage(final String explanation) {
            return "Root search failed because of invalid input, which wasn't caught. "
                    + explanation;
        }

        private static String getParametersMessageBlock(final NewtonRootsViewModel viewModel) {
            return String.format(ROOT_SEARCH_PARAMETERS_TEXT,
                    viewModel.getLeftPoint(),
                    viewModel.getRightPoint(),
                    viewModel.getDerivativeStep(),
                    viewModel.getAccuracy(),
                    viewModel.getFunctionExpression(),
                    viewModel.getStartPoint(),
                    viewModel.getStopCriterion());
        }
    }

    private class StringValueChangeListener implements ChangeListener<String> {

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            ApplicationStatus status = checkInput();
            applicationStatus.set(status.toString());
            inputDataIsInvalid.set(status != ApplicationStatus.READY);
        }
    }
}
