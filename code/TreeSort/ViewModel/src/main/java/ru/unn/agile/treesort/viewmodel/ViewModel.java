package ru.unn.agile.treesort.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.treesort.model.Tree;

public final class ViewModel {
    private final StringProperty sourceText = new SimpleStringProperty();
    private final BooleanProperty sourceTextChanged = new SimpleBooleanProperty(false);
    private final BooleanProperty sourceTextFocused = new SimpleBooleanProperty(true);

    private final StringProperty resultText = new SimpleStringProperty();
    private final StringProperty statusText = new SimpleStringProperty();
    private final BooleanProperty buttonDisabled = new SimpleBooleanProperty();

    private final ObjectProperty<ObservableList<String>> log = new SimpleObjectProperty<>(
            FXCollections.observableArrayList());

    private ILogger logger;

    private boolean initialized = false;

    public enum Status {
        WAITING("Please insert array of integers"),
        BAD("The data isn't correct"),
        READY("Everything is fine, ready to serve");

        private final String message;

        Status(final String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public ViewModel(final ILogger logger) {
        this.logger = logger;

        sourceText.addListener((observable, oldValue, newValue) -> {
            statusText.set(getStatus().toString());
            buttonDisabled.set(!canCalculate());
            if (initialized) {
                sourceTextChanged.set(true);
            }
        });

        sourceTextFocused.addListener((observable, oldValue, newValue) ->
                logSourceChange(newValue));

        sourceText.set("");

        initialized = true;
    }

    public ViewModel() {
        this(new FakeLogger());
    }

    public ILogger getLogger() {
        return logger;
    }

    public void setLogger(final ILogger logger) {
        this.logger = logger;
    }

    public ObservableList<String> getLog() {
        return log.get();
    }

    public ObjectProperty<ObservableList<String>> logProperty() {
        return log;
    }

    public boolean isSourceTextFocused() {
        return sourceTextFocused.get();
    }

    public void setSourceTextFocused(final boolean focused) {
        sourceTextFocused.set(focused);
    }

    public BooleanProperty sourceTextFocusedProperty() {
        return sourceTextFocused;
    }

    public StringProperty sourceTextProperty() {
        return sourceText;
    }

    public StringProperty statusTextProperty() {
        return statusText;
    }

    public StringProperty resultTextProperty() {
        return resultText;
    }

    public BooleanProperty buttonDisabledProperty() {
        return buttonDisabled;
    }

    public boolean isButtonDisabled() {
        return buttonDisabled.get();
    }

    public String getSourceText() {
        return sourceText.get();
    }

    public void setSourceText(final String text) {
        sourceText.set(text);
    }

    public String getStatusText() {
        return statusText.get();
    }

    private boolean canCalculate() {
        return validate(getSourceText());
    }

    boolean validate(final String s) {
        return s.matches("(-?\\d+ *, *)*-?\\d+");
    }

    private Status getStatus() {
        Status status;
        if (getSourceText().isEmpty()) {
            status = Status.WAITING;
        } else if (validate(getSourceText())) {
            status = Status.READY;
        } else {
            status = Status.BAD;
        }
        return status;
    }

    public void sort() {
        logSourceChange(false);
        log(Messages.SORT_BUTTON_CLICKED);

        if (canCalculate()) {
            String[] parts = getSourceText().split(" *, *");
            Tree tree = new Tree();
            for (String val : parts) {
                tree.insert(Integer.parseInt(val));
            }
            String result = tree.extractValues().toString();
            result = result.substring(1, result.length() - 1);
            resultText.set(result);
        }
    }

    private void log(final String text) {
        logger.log(text);
        log.get().setAll(logger.getLog());
    }

    private void logSourceChange(final boolean focused) {
        if (!focused && sourceTextChanged.get()) {
            log(Messages.SOURCE_CHANGED + " to \"" + sourceText.get() + "\"");
            sourceTextChanged.set(false);
        }
    }
}

final class Messages {
    static final String SOURCE_CHANGED = "Source text changed";
    static final String SORT_BUTTON_CLICKED = "Sort button clicked";

    private Messages() { };
}
