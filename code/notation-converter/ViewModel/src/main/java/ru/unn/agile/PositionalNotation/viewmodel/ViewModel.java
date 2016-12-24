package ru.unn.agile.PositionalNotation.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.PositionalNotation.model.Converter;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final ObjectProperty<ObservableList<Notation>> notations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Notation.values()));

    private final ObjectProperty<Notation> toNotation = new SimpleObjectProperty<>();
    private final ObjectProperty<Notation> fromNotation = new SimpleObjectProperty<>();
    private final BooleanProperty converterDisabled = new SimpleBooleanProperty();

    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final List<ValueCachingChangeListener> valueChangedListeners = new ArrayList<>();
    private ILogger positionalNotationLogger;

    private final StringProperty logs = new SimpleStringProperty();

    public ViewModel(final ILogger positionalNotationLogger) {
        setPositionalNotationLogger(positionalNotationLogger);
        init();
    }

    public final void setPositionalNotationLogger(final ILogger positionalNotationLogger) {
        if (positionalNotationLogger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.positionalNotationLogger = positionalNotationLogger;
    }

    private void init() {
        number.set("");
        result.set("");
        fromNotation.set(Notation.DECIMAL);
        toNotation.set(Notation.DECIMAL);
        status.set(Status.WAIT.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(number, result);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        converterDisabled.bind(couldCalculate.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(number);
            add(result);
        } };

        for (StringProperty field : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void convert() {
        if (converterDisabled.get()) {
            return;
        }
        result.set(Converter.convert(number.get(),
                fromNotation.get().name(), toNotation.get().name()));
        status.set(Status.SUCCESS.toString());
        StringBuilder message = new StringBuilder("Calculate. ");
        message.append("Arguments")
                .append(": Number = ").append(number.get())
                .append("; From notaition = ").append(fromNotation.get().name())
                .append("; To notaition = ").append(toNotation.get().name());

        positionalNotationLogger.writeLog(message.toString());
        updatePositionalNotationLogs();
    }

    public BooleanProperty converterDisabledProperty() {
        return converterDisabled;
    }

    public StringProperty numberProperty() {
        return number;
    }
    public StringProperty resultProperty() {
        return result;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final ObservableList<Notation> getNotations() {
        return notations.get();
    }

    public ObjectProperty<Notation> toNotationProperty() {
        return toNotation;
    }

    public ObjectProperty<Notation> fromNotationProperty() {
        return fromNotation;
    }
    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        String n = number.get();
        if (number.get().isEmpty()) {
            inputStatus = Status.WAIT;
        }
        if (!number.get().isEmpty() && Notation.DECIMAL.equals(fromNotation.get())
                && !n.matches("[1-9]+[0-9]*")) {
            inputStatus = Status.ERROR;
        }
        if (!number.get().isEmpty() && Notation.BINARY.equals(fromNotation.get())
                && !n.matches("1+[01]*")) {
            inputStatus = Status.ERROR;
        }
        if (!number.get().isEmpty() && Notation.OCTAL.equals(fromNotation.get())
                && !n.matches("[1-7]+[0-7]*")) {
            inputStatus = Status.ERROR;
        }
        if (!number.get().isEmpty() && Notation.HEX.equals(fromNotation.get())
                && !n.matches("[1-9a-fA-F]+[0-9a-fA-F]*")) {
            inputStatus = Status.ERROR;
        }
        return inputStatus;
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String previusValue = "";
        private String currentValue = "";
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String valueOld, final String valueNew) {
            if (valueOld.equals(valueNew)) {
                return;
            }
            status.set(getInputStatus().toString());
            currentValue = valueNew;
        }
        public boolean isChanged() {
            return !previusValue.equals(currentValue);
        }
        public void cache() {
            previusValue = currentValue;
        }
    }

    public final List<String> getLog() {
        return positionalNotationLogger.readLogs();
    }

    public void fromNotationChanged(final Notation oldValue, final Notation newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder("From notation was changed to ");
        message.append(newValue.toString());
        positionalNotationLogger.writeLog(message.toString());
        updatePositionalNotationLogs();
    }

    public void toNotationChanged(final Notation oldValue, final Notation newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder("To notation was changed to ");
        message.append(newValue.toString());
        positionalNotationLogger.writeLog(message.toString());
        updatePositionalNotationLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder messageLog = new StringBuilder("Updated input. ");
                messageLog.append("Input argument are: [")
                        .append(number.get()).append("]");
                positionalNotationLogger.writeLog(messageLog.toString());
                updatePositionalNotationLogs();

                listener.cache();
                break;
            }
        }
    }

    private void updatePositionalNotationLogs() {
        List<String> fullLog = positionalNotationLogger.readLogs();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }
}

enum Status {
    READY("Enter the button!"),
    WAIT("Wait a number!"),
    ERROR("This is bad format!"),
    SUCCESS("Success!");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
