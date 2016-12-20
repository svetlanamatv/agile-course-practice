package ru.unn.agile.NewtonRoots.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.NewtonRoots.Model.NewtonMethod;
import ru.unn.agile.NewtonRoots.Model.MathFunction;
import ru.unn.agile.NewtonRoots.Model.NewtonMethod.StoppingCriterion;

public class NewtonRootAppViewModel  {
    private final StringProperty leftPoint;
    private final StringProperty rightPoint;
    private final StringProperty derivativeStep;
    private final StringProperty accuracy;
    private final StringProperty function;
    private final StringProperty solverReport;
    private final BooleanProperty findRootButtonDisable;
    private final StringProperty applicationStatus;
    private final StringProperty startPoint;

    private final ObjectProperty<ObservableList<StoppingCriterion>> stopCriterions =
            new SimpleObjectProperty<>(
                    FXCollections.observableArrayList(StoppingCriterion.values()));
    private final ObjectProperty<StoppingCriterion> stopCriterion = new SimpleObjectProperty<>();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    public NewtonRootAppViewModel()  {
        leftPoint = new SimpleStringProperty("");
        rightPoint = new SimpleStringProperty("");
        derivativeStep = new SimpleStringProperty("");
        accuracy = new SimpleStringProperty("");
        function = new SimpleStringProperty("");
        findRootButtonDisable = new SimpleBooleanProperty(true);
        solverReport = new SimpleStringProperty("");
        startPoint = new SimpleStringProperty("");
        applicationStatus = new SimpleStringProperty(ApplicationStatus.WAITING.toString());
        stopCriterion.set(StoppingCriterion.FunctionModule);

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(leftPoint);
            add(rightPoint);
            add(derivativeStep);
            add(accuracy);
            add(function);
            add(startPoint);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
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


    public ObjectProperty<ObservableList<StoppingCriterion>> stopCriterionsProperty() {
        return stopCriterions;
    }
    public final ObservableList<StoppingCriterion> getStopCriterions() {
        return stopCriterions.get();
    }
    public ObjectProperty<StoppingCriterion> stopCriterionProperty() {
        return stopCriterion;
    }
    public void setStopCriterion(final StoppingCriterion value) {
        stopCriterion.set(value);
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
        MathFunction testFunction = null;
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

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            applicationStatus.set(checkInput().toString());
            ApplicationStatus status = checkInput();
            applicationStatus.set(status.toString());
            findRootButtonDisable.set(status != ApplicationStatus.READY);
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
            }
        } catch (Exception e)  {
            applicationStatus.set(ApplicationStatus.FAILED.toString());
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
