package ru.unn.agile.MassConverter.ViewModel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.MassConverter.Model.MassConverter.ConversionSystem;

import java.util.List;

public class ViewModel {

    public static final String FIRST_SYSTEM_WAS_CHANGED = "First system was changed to ";
    public static final String SECOND_SYSTEM_WAS_CHANGED = "Second system was changed to ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private ILogger logger;
    private static final int  MAX_LENGHT_OF_INPUT = 10;
    private final StringProperty input = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty lastLogMessage = new SimpleStringProperty();
    private final ListProperty<String> listMessagesProperty = new SimpleListProperty<>();
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

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;
    }

    public ViewModel(final ILogger logger) {
        initialize();
        setLogger(logger);
    }

    public ViewModel() {
        initialize();
    }

    private void initialize() {
        input.set("");
        result.set("");
        lastLogMessage.set("");
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

    private void updateListMessages() {
        List<String> listMessages = logger.getLog();
        listMessagesProperty.set(FXCollections.observableArrayList(listMessages));
        lastLogMessage.set(logger.getLastMessage());
    }

    private void logChangeSecondSystem() {
        if (logger == null) {
            return;
        }

        logger.log(changeLogMessage(NumberOfSistem.SecondSystem));
        updateListMessages();
    }

    private void logChangeFirstSystem() {
        if (logger == null) {
            return;
        }

        logger.log(changeLogMessage(NumberOfSistem.FirstSystem));
        updateListMessages();
    }

    private void logInputValue() {
        if (logger == null) {
            return;
        }

        logger.log(editingFinishedLogMessage());
        updateListMessages();
    }

    private String editingFinishedLogMessage() {
        String messageLog = EDITING_FINISHED
                + "Input value are: "
                + input.get();

        return messageLog;
    }

    private String changeLogMessage(final NumberOfSistem numberOfSystem) {
        String messageLog;

        if (numberOfSystem == NumberOfSistem.FirstSystem) {
            messageLog = FIRST_SYSTEM_WAS_CHANGED + systemFromConvert.get();
        } else {
            messageLog = SECOND_SYSTEM_WAS_CHANGED + systemToConvert.get();
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

                checkSizeInput();

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

    private void checkSizeInput() {
        String inputValue = input.get();
        String[] splittingInput = new String[2];
        if (inputValue.contains(".")) {
            splittingInput = inputValue.split("\\.");
        } else {
            splittingInput[0] = inputValue;
            splittingInput[1] = "";
        }

        int integerSize = splittingInput[0].length();
        int fractSize = splittingInput[1].length();
        int sumInputSize = integerSize + fractSize;
        if (integerSize > MAX_LENGHT_OF_INPUT
           || fractSize > MAX_LENGHT_OF_INPUT
           || sumInputSize > MAX_LENGHT_OF_INPUT) {
            throw new NumberFormatException();
        }
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

    public StringProperty lastLogMessageProperty() {
        return lastLogMessage;
    }

    public ListProperty<String> logMessagesProperty() {
        return listMessagesProperty;
    }

    public ObjectProperty<ConversionSystem> systemToConvertProperty() {
        return systemToConvert;
    }

    public ObjectProperty<ConversionSystem> systemFromConvertProperty() {
        return systemFromConvert;
    }

    public void setSystemFromConvertProperty(final ConversionSystem system) {
        systemFromConvert.set(system);
    }

    public void setSystemToConvertProperty(final ConversionSystem system) {
        systemToConvert.set(system);
    }

    public void setInput(final String inputStr) {
        input.set(inputStr);
    }

    public final String getResult() {
        return result.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public final String getLastLogMessage() {
        return lastLogMessage.get();
    }

    public final ObservableList<ConversionSystem> getConversionSystems() {
        return conversionSystems.get();
    }

}

