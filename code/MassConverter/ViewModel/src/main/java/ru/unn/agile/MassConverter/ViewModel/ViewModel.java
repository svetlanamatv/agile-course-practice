package ru.unn.agile.MassConverter.ViewModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.MassConverter.Model.MassConverter.ConversionSystem;

import java.util.List;

public class ViewModel {

    private final ILogger logger;
    private final StringProperty input = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<ConversionSystem> systemToConvert = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<ConversionSystem>> conversionSystems
            = new SimpleObjectProperty<>(FXCollections
            .observableArrayList(ConversionSystem.values()));
    private final ObjectProperty<ConversionSystem> systemFromConvert = new SimpleObjectProperty<>();

    enum Status {
        WAITING("Waiting for data input"),
        WRONG_INPUT("Wrong input"),
        SUCCESS("Success");

        private String message;

        Status(final String message) {
            this.message = message;
        }

        public String toString() {
            return message;
        }
    }

    enum NumberOfSistem {
        FirstSystem,
        SecondSystem
    }

    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;
        input.set("");
        result.set("");
        status.set(Status.WAITING.toString());
        systemToConvert.set(ConversionSystem.GRAM);
        systemFromConvert.set(ConversionSystem.KILOGRAM);

        input.addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                final String oldValue, final String newValue) {
                changedValue();
                logInputValue();
            }
        });

        systemToConvert.addListener(new ChangeListener<ConversionSystem>() {
            @Override
            public void changed(final ObservableValue<? extends ConversionSystem> observable,
                                final ConversionSystem oldValue,
                                final ConversionSystem newValue) {
                changedValue();
                logChangeSecondSystem();
            }
        });

        systemFromConvert.addListener(new ChangeListener<ConversionSystem>() {
            @Override
            public void changed(final ObservableValue<? extends ConversionSystem> observable,
                                final ConversionSystem oldValue,
                                final ConversionSystem newValue) {
                changedValue();
                logChangeFirstSystem();
            }
        });
    }

    private void logChangeSecondSystem() {
        logger.log(changeLogMessage(NumberOfSistem.SecondSystem));
    }

    private void logChangeFirstSystem() {
        logger.log(changeLogMessage(NumberOfSistem.FirstSystem));
    }

    private void logInputValue() {
        logger.log(editingFinishedLogMessage());
    }

    private String editingFinishedLogMessage() {
        String messageLog = LogMessages.EDITING_FINISHED
                + "Input value are: "
                + input.get();

        return messageLog;
    }

    private String changeLogMessage(final NumberOfSistem numberOfSystem) {
        String messageLog;

        if (numberOfSystem == NumberOfSistem.FirstSystem) {
            messageLog = LogMessages.FIRST_SYSTEM_WAS_CHANGED + systemFromConvert.get();
        } else {
            messageLog = LogMessages.SECOND_SYSTEM_WAS_CHANGED + systemToConvert.get();
        }

        return messageLog;
    }

    private void changedValue() {
        status.set(Status.SUCCESS.toString());
        if (input.get().isEmpty()) {
            status.set(Status.WAITING.toString());
            result.set("");
        } else {
            try {
                result.set(String.valueOf(systemToConvert.get()
                        .convertTo(systemFromConvert.get()
                                .convertFrom(Double.parseDouble(input.get())))));
            } catch (NumberFormatException exception) {
                status.set(Status.WRONG_INPUT.toString());
                result.set("");
            } catch (IllegalArgumentException esception) {
                status.set(Status.WRONG_INPUT.toString());
                result.set("");
            }
        }
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty inputProperty() {
        return input;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public ObjectProperty<ConversionSystem> systemToConvertProperty() {
        return systemToConvert;
    }

    public ObjectProperty<ConversionSystem> systemFromConvertProperty() {
        return systemFromConvert;
    }

    public final String getResult() {
        return result.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public final ObservableList<ConversionSystem> getConversionSystems() {
        return conversionSystems.get();
    }

    public final class LogMessages {
        public static final String FIRST_SYSTEM_WAS_CHANGED = "First system was changed to ";
        public static final String SECOND_SYSTEM_WAS_CHANGED = "Second system was changed to ";
        public static final String EDITING_FINISHED = "Updated input. ";

        private LogMessages() { }
    }
}
