package ru.unn.agile.newtonroots.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.newtonroots.model.MathFunction;
import ru.unn.agile.newtonroots.model.NewtonMethod;
import ru.unn.agile.newtonroots.model.NewtonMethod.StoppingCriterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class NewtonRootAppViewModel  {
    private final StringProperty leftPoint = new SimpleStringProperty("");
    private final StringProperty rightPoint = new SimpleStringProperty("");
    private final StringProperty derivativeStep = new SimpleStringProperty("");
    private final StringProperty accuracy = new SimpleStringProperty("");
    private final StringProperty function = new SimpleStringProperty("");
    private final StringProperty solverReport = new SimpleStringProperty("");
    private final BooleanProperty findRootButtonDisable = new SimpleBooleanProperty(true);
    private final StringProperty applicationStatus =
            new SimpleStringProperty(ApplicationStatus.WAITING.toString());
    private final StringProperty startPoint = new SimpleStringProperty("");

    private final ObjectProperty<ObservableList<StoppingCriterion>> stopCriteria =
            new SimpleObjectProperty<>(
                    FXCollections.observableArrayList(StoppingCriterion.values()));
    private final ObjectProperty<StoppingCriterion> stopCriterion =
            new SimpleObjectProperty<>(StoppingCriterion.FunctionModule);

    private final StringProperty logLines = new SimpleStringProperty("");

    private final Logger logger;
    private final EditTracker<String> editTracker;

    public NewtonRootAppViewModel(Logger logger) {
        leftPoint.addListener(
                new StringValueChangeListener(LogMessages::getLeftPointChangeMessage));
        rightPoint.addListener(
                new StringValueChangeListener(LogMessages::getRightPointChangeMessage));
        derivativeStep.addListener(
                new StringValueChangeListener(LogMessages::getDerivativeStepChangeMessage));
        accuracy.addListener(
                new StringValueChangeListener(LogMessages::getAccuracyChangeMessage));
        function.addListener(
                new StringValueChangeListener(LogMessages::getFunctionExpressionChangeMessage));
        startPoint.addListener(
                new StringValueChangeListener(LogMessages::getStartPointChangeMessage));

        stopCriterion.addListener((observable, oldValue, newValue) ->  {
                if (!oldValue.equals(newValue)) {
                    logMessage(LogMessages.getStopCriterionChangeMessage(newValue));
                }
        });

        this.logger = logger;
        editTracker = new EditTracker<>();
    }

    public StringProperty leftPointProperty()  {
        return leftPoint;
    }
    public String getLeftPoint() {
        return leftPoint.get();
    }
    public void setLeftPoint(final String value) {
        leftPoint.set(value);
    }

    public StringProperty rightPointProperty()  {
        return rightPoint;
    }
    public String getRightPoint() {
        return rightPoint.get();
    }
    public void setRightPoint(final String value) {
        rightPoint.set(value);
    }

    public StringProperty derivativeStepProperty()  {
        return derivativeStep;
    }
    public String getDerivativeStep() {
        return derivativeStep.get();
    }
    public void setDerivativeStep(final String value) {
        derivativeStep.set(value);
    }

    public StringProperty accuracyProperty()  {
        return accuracy;
    }
    public String getAccuracy() {
        return accuracy.get();
    }
    public void setAccuracy(final String value) {
        accuracy.set(value);
    }

    public StringProperty functionProperty()  {
        return function;
    }
    public String getFunction() {
        return function.get();
    }
    public void setFunction(final String value) {
        function.set(value);
    }

    public BooleanProperty findRootButtonDisableProperty()  {
        return findRootButtonDisable;
    }
    public boolean getFindRootButtonDisable() {
        return findRootButtonDisable.get();
    }
    public void setFindRootButtonDisable(final boolean value) {
        findRootButtonDisable.set(value);
    }

    public StringProperty solverReportProperty()  {
        return solverReport;
    }
    public String getSolverReport() {
        return solverReport.get();
    }
    public void setSolverReport(final String value) {
        solverReport.set(value);
    }

    public StringProperty applicationStatusProperty()  {
        return applicationStatus;
    }
    public String getApplicationStatus() {
        return applicationStatus.get();
    }
    public void setApplicationStatus(final String value) {
        applicationStatus.set(value);
    }

    public StringProperty startPointProperty()  {
        return startPoint;
    }
    public String getStartPoint() {
        return startPoint.get();
    }
    public void setStartPoint(final String value) {
        startPoint.set(value);
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


    public void finishEdit() {
        editTracker.finishTracking();
    }

    private boolean checkInputFormat()  {
        try {
            Double.parseDouble(leftPoint.get());
            Double.parseDouble(rightPoint.get());
            Double.parseDouble(accuracy.get());
            Double.parseDouble(derivativeStep.get());
            Double.parseDouble(startPoint.get());
            MathFunction testFunction = new MathFunction(function.get());
        } catch (NumberFormatException nfe) {
            return false;
        } catch (Exception e)  {
            return false;
        }

        return true;
    }

    private boolean checkMonotonic()  {
        MathFunction testFunction;
        try {
             testFunction = new MathFunction(function.get());
        } catch (Exception e)  {
            return false;
        }

        return NewtonMethod.isMonotonicFunctionOnInterval(testFunction,
                Double.parseDouble(leftPoint.get()),
                Double.parseDouble(rightPoint.get()));
    }

    private boolean checkMethodParameters()  {
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

    private ApplicationStatus checkInput()  {
        if (leftPoint.get().isEmpty() || rightPoint.get().isEmpty()
                || accuracy.get().isEmpty() || derivativeStep.get().isEmpty()
                || function.get().isEmpty() || startPoint.get().isEmpty()
                || stopCriterion.get() == null) {
            return ApplicationStatus.WAITING;
        }

        if (!checkInputFormat()) {
            return ApplicationStatus.BAD_FORMAT;
        }

        if (!checkMonotonic())  {
            return ApplicationStatus.NON_MONOTONIC_FUNCTION;
        }

        if (!checkMethodParameters()) {
            return ApplicationStatus.BAD_PARAMETERS;
        }

        return ApplicationStatus.READY;
    }

    private class StringValueChangeListener implements ChangeListener<String> {
        private final Function<String, String> logMessageProducer;

        StringValueChangeListener(final Function<String, String> logMessageProducer) {
            this.logMessageProducer = logMessageProducer;
        }

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            ApplicationStatus status = checkInput();
            applicationStatus.set(status.toString());
            findRootButtonDisable.set(status != ApplicationStatus.READY);

            if (!editTracker.isTracking()) {
                editTracker.startTracking(oldValue,
                        value -> logMessage(logMessageProducer.apply(value)));
            }
            editTracker.updateValue(newValue);
        }
    }

    public void findRoot() {
        if (findRootButtonDisable.get()) {
            return;
        }

        try {
            NewtonMethod method = new NewtonMethod(Double.parseDouble(accuracy.get()),
                    Double.parseDouble(derivativeStep.get()));
            method.setStoppingCriterion(stopCriterion.get());
            MathFunction functionObj = new MathFunction(function.get());
            double left = Double.parseDouble(leftPoint.get());
            double right = Double.parseDouble(rightPoint.get());
            double root = method.findRoot(functionObj,
                    Double.parseDouble(startPoint.get()), left, right);
            if (Double.isNaN(root))  {
                throw new Exception();
            } else  {
                setSolverReport("Root: " + Double.toString(root)
                + "\nIterations performed: " + Integer.toString(method.getIterationsCounter())
                + "\nReached accuracy: " + Double.toString(method.getFinalAccuracy()));
                applicationStatus.set(ApplicationStatus.SUCCESS.toString());

                logMessage(LogMessages.getSuccessfulRunMessage(
                        getLeftPoint(),
                        getRightPoint(),
                        getDerivativeStep(),
                        getAccuracy(),
                        getFunction(),
                        getStartPoint(),
                        getStopCriterion(),
                        root,
                        method.getFinalAccuracy(),
                        method.getIterationsCounter()
                ));
            }
        } catch (Exception e)  {
            applicationStatus.set(ApplicationStatus.FAILED.toString());

            logMessage(LogMessages.getFailedRunMessage(
                    getLeftPoint(),
                    getRightPoint(),
                    getDerivativeStep(),
                    getAccuracy(),
                    getFunction(),
                    getStartPoint(),
                    getStopCriterion()
            ));
        }
    }

    private void logMessage(String message) {
        logger.appendMessage(message);
        ArrayList<String> reverseMessageList = new ArrayList<>(logger.getMessageList());
        Collections.reverse(reverseMessageList);
        logLines.set(String.join("\n", reverseMessageList));
    }

    static final class LogMessages {
        static final String LEFT_END_TEXT = "Left segment end changed to ";
        static final String RIGHT_END_TEXT = "Right segment end changed to ";
        static final String DERIVATIVE_STEP_TEXT = "Derivative step changed to ";
        static final String ACCURACY_TEXT = "Accuracy changed to ";
        static final String FUNCTION_EXPR_TEXT = "Function expression changed to ";
        static final String START_POINT_TEXT = "Start point changed to ";
        static final String STOP_CRITERION_TEXT = "Stop criterion changed to ";
        static final String ROOT_SEARCH_TEXT = "Root search finished. Parameters: " +
                "leftEnd: %s  rightEnd: %s  derivativeStep: %s  accuracy: %s  " +
                "function: \"%s\"  startPoint: %s  stopCriterion: %s. ";

        private LogMessages() {
        }

        static String getLeftPointChangeMessage(String value) {
            return LEFT_END_TEXT + value;
        }

        static String getRightPointChangeMessage(String value) {
            return RIGHT_END_TEXT + value;
        }

        static String getDerivativeStepChangeMessage(String value) {
            return DERIVATIVE_STEP_TEXT + value;
        }

        static String getAccuracyChangeMessage(String value) {
            return ACCURACY_TEXT + value;
        }

        static String getFunctionExpressionChangeMessage(String value) {
            return FUNCTION_EXPR_TEXT + value;
        }

        static String getStartPointChangeMessage(String value) {
            return START_POINT_TEXT + value;
        }

        static String getStopCriterionChangeMessage(StoppingCriterion value) {
            return STOP_CRITERION_TEXT + value;
        }

        static String getSuccessfulRunMessage(
                String leftPoint,
                String rightPoint,
                String derivativeStep,
                String accuracy,
                String function,
                String startPoint,
                StoppingCriterion stopCriterion,
                double root,
                double finalAccuracy,
                int iterationsCounter) {

            return String.format(ROOT_SEARCH_TEXT,
                    leftPoint, rightPoint, derivativeStep, accuracy,
                    function, startPoint, stopCriterion) +
                    String.format("Root was found. Results: x=%f  accuracy=%f  iterations=%d",
                            root, finalAccuracy, iterationsCounter);
        }

        static String getFailedRunMessage(
                String leftPoint,
                String rightPoint,
                String derivativeStep,
                String accuracy,
                String function,
                String startPoint,
                StoppingCriterion stopCriterion) {
            return String.format(ROOT_SEARCH_TEXT,
                    leftPoint, rightPoint, derivativeStep, accuracy,
                    function, startPoint, stopCriterion) +
                    "Root wasn't found";
        }
    }
}

enum ApplicationStatus {
    WAITING("Please provide input data"),
    READY("Press 'Find root'"),
    NON_MONOTONIC_FUNCTION("The function is not monotonic"),
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
