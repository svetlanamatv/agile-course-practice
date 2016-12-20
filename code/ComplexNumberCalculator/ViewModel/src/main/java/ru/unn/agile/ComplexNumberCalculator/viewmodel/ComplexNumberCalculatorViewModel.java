package ru.unn.agile.ComplexNumberCalculator.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import ru.unn.agile.ComplexNumberCalculator.Model.ComplexNum;
import ru.unn.agile.ComplexNumberCalculator.viewmodel.impl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander on 23.11.2016.
 */
public class ComplexNumberCalculatorViewModel {
    private HashMap<Operations,
            CalculatorInterface.BinaryOperation<ComplexNum, ComplexNum, ComplexNum>>
            binaryOperationsMap;
    private HashMap<Operations,
            CalculatorInterface.UnaryOperation<? extends Object, ComplexNum>> unaryOperationsMap;

    private final StringProperty firstArgReal = new SimpleStringProperty();
    private final StringProperty firstArgIm = new SimpleStringProperty();
    private final StringProperty secondArgReal = new SimpleStringProperty();
    private final StringProperty secondArgIm = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();

    private final StringProperty logs = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<Operations>> supportedOperations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operations.values()));
    private final ObjectProperty<Operations> selectedOperation = new SimpleObjectProperty<>();

    private final ComplexNumberCalculatorWrapper calculator = new ComplexNumberCalculatorWrapper();

    private ILogger logger;
    private List<ValueCachingChangeListener> valueChangedListeners;

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    // FXML needs default c-tor for binding
    public ComplexNumberCalculatorViewModel() {
        init();
    }

    public ComplexNumberCalculatorViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        firstArgReal.set("");
        firstArgIm.set("");
        secondArgReal.set("");
        secondArgIm.set("");
        result.set("");
        selectedOperation.set(Operations.ADDITION);

        binaryOperationsMap = new HashMap<>();
        binaryOperationsMap.put(Operations.ADDITION, new Addition(calculator));
        binaryOperationsMap.put(Operations.SUBTRACTION, new Subtraction(calculator));
        binaryOperationsMap.put(Operations.DIVISION, new Division(calculator));
        binaryOperationsMap.put(Operations.MULTIPLICATION, new Multiplication(calculator));

        unaryOperationsMap = new HashMap<>();
        unaryOperationsMap.put(Operations.ABS, new Abs(calculator));
        unaryOperationsMap.put(Operations.ARGUMENT, new Argument(calculator));

        final List<StringProperty> vals = new ArrayList<StringProperty>() { {
            add(firstArgReal);
            add(firstArgIm);
            add(secondArgReal);
            add(secondArgIm);
        } };

        valueChangedListeners = new ArrayList<>();
        for (StringProperty val : vals) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            val.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        result.set(performOperation());
    }

    private String performOperation() {
        ComplexNum firstArg;

        StringBuilder messageLog = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);

        try {
            firstArg = new ComplexNum(Float.parseFloat(firstArgReal.getValue()),
                    Float.parseFloat(firstArgIm.getValue()));
        } catch (Exception e) {
            String errorMessage = "Incorrect input for the first argument";

            messageLog.append("Error : ").append(errorMessage);
            logger.log(messageLog.toString());
            updateLogs();

            return errorMessage;
        }

        if (binaryOperationsMap.get(selectedOperation.getValue()) == null) {
            unaryOperationsMap.get(selectedOperation.getValue()).setArgument(firstArg);

            String calcResult =
                    unaryOperationsMap.get(selectedOperation.getValue()).perform().toString();

            messageLog = createCalculateArgLogMessage(messageLog, false);
            logger.log(messageLog.toString());
            updateLogs();

            return calcResult;
        } else {
            try {
                ComplexNum secondArg = new ComplexNum(Float.parseFloat(secondArgReal.getValue()),
                        Float.parseFloat(secondArgIm.getValue()));

                binaryOperationsMap.get(selectedOperation.getValue()).setFirstArgument(firstArg);
                binaryOperationsMap.get(selectedOperation.getValue()).setSecondArgument(secondArg);

                String calcResult =
                        binaryOperationsMap.get(selectedOperation.getValue()).perform().toString();

                messageLog = createCalculateArgLogMessage(messageLog, true);
                logger.log(messageLog.toString());
                updateLogs();

                return calcResult;
            } catch (Exception e) {
                String errorMessage = "Incorrect input for the second argument";

                messageLog.append("Error : ").append(errorMessage);
                logger.log(messageLog.toString());
                updateLogs();

                return errorMessage;
            }
        }
    }

    private StringBuilder createCalculateArgLogMessage(final StringBuilder message,
                                                       final boolean isSecondArg) {
        message.append("Arguments :")
                .append(" Re1 = ").append(firstArgReal.getValue())
                .append(" Im1 = ").append(firstArgIm.getValue());

        if (isSecondArg) {
            message.append(" Re2 = ").append(secondArgReal.getValue())
                    .append(" Im2 = ").append(secondArgIm.getValue());
        }

        message.append("; Operation : ").append(selectedOperation.getValue().toString());

        return message;
    }

    public void onOperationChanged(final Operations oldValue, final Operations newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.OPERATION_WAS_CHANGED);
        message.append(newValue.toString());

        logger.log(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);

                message.append("Input arguments are: [")
                        .append(firstArgReal.get()).append("; ")
                        .append(firstArgIm.get()).append("; ")
                        .append(secondArgReal.get()).append("; ")
                        .append(secondArgIm.get()).append("]");

                logger.log(message.toString());
                updateLogs();

                listener.cache();
                break;
            }
        }
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String prevValue = new String();
        private String curValue = new String();

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }

            curValue = newValue;
        }

        public boolean isChanged() {
            return !prevValue.equals(curValue);
        }

        public void cache() {
            prevValue = curValue;
        }
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    public StringProperty firstArgRealProperty() {
        return firstArgReal;
    }

    public StringProperty firstArgImProperty() {
        return firstArgIm;
    }

    public StringProperty secondArgRealProperty() {
        return secondArgReal;
    }

    public StringProperty secondArgImProperty() {
        return secondArgIm;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public StringProperty resultProperty() {
        return result;
    }


    public final String getResult() {
        return result.get();
    }

    public ObjectProperty<Operations> selectedOperationProperty() {
        return selectedOperation;
    }

    public ObjectProperty<ObservableList<Operations>> supportedOperationsProperty() {
        return supportedOperations;
    }

    public final ObservableList<Operations> getSupportedOperations() {
        return supportedOperations.get();
    }

    public final String getLogs() {
        return logs.get();
    }
}

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
    public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private LogMessages() { }
}
