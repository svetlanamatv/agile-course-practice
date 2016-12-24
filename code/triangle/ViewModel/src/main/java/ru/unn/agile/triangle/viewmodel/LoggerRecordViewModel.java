package ru.unn.agile.triangle.viewmodel;

import javafx.beans.property.*;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoggerRecordViewModel {
    private final StringProperty messageProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> timestampProperty
            = new SimpleObjectProperty<>();

    public LoggerRecordViewModel(final LoggerRecord record) {
        Objects.requireNonNull(record);
        messageProperty.set(record.getMessage());
        timestampProperty.set(record.getTimestamp());
    }

    public final ReadOnlyStringProperty messageProperty() {
        return messageProperty;
    }

    public final String getMessage() {
        return messageProperty.get();
    }

    public final ReadOnlyObjectProperty<LocalDateTime> timestampProperty() {
        return timestampProperty;
    }

    public final LocalDateTime getTimestamp() {
        return timestampProperty.get();
    }
}
