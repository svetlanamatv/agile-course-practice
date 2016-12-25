package ru.unn.agile.triangle.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.triangle.logging.Logger;

import java.util.LinkedList;
import java.util.Objects;

public class LoggerViewModel {
    private static final int MAX_LOGGER_RECORDS = 50;

    private final ListProperty<LoggerRecordViewModel> recordsProperty =
            new SimpleListProperty<>();

    public LoggerViewModel(final Logger logger) {
        Objects.requireNonNull(logger);

        ObservableList<LoggerRecordViewModel> observableLoggerRecords =
                FXCollections.observableList(new LinkedList<>());
        recordsProperty.set(observableLoggerRecords);

        logger.addListenerForNewRecord((record) -> {
            observableLoggerRecords.add(new LoggerRecordViewModel(record));
            removeOutOfBoundsLoggerRecords(observableLoggerRecords);
        });
    }

    public final ReadOnlyListProperty<LoggerRecordViewModel> recordsProperty() {
        return recordsProperty;
    }

    public final ObservableList<LoggerRecordViewModel> getRecords() {
        return FXCollections.unmodifiableObservableList(recordsProperty.get());
    }

    public final int getMaxNumberOfRecords() {
        return MAX_LOGGER_RECORDS;
    }

    private void removeOutOfBoundsLoggerRecords(
            final ObservableList<LoggerRecordViewModel> records) {
        if (records.size() > MAX_LOGGER_RECORDS) {
            records.remove(0);
        }
    }


}
