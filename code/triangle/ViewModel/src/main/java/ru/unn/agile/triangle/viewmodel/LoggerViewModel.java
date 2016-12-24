package ru.unn.agile.triangle.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.triangle.logging.Logger;

import java.util.ArrayList;
import java.util.Objects;

public class LoggerViewModel {
    private final Logger logger;
    private final ListProperty<LoggerRecordViewModel> recordsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    public LoggerViewModel(final Logger logger) {
        Objects.requireNonNull(logger);
        this.logger = logger;
    }

    public final ReadOnlyListProperty<LoggerRecordViewModel> recordsProperty() {
        return recordsProperty;
    }

    public final ObservableList<LoggerRecordViewModel> getRecords() {
        return recordsProperty.get();
    }
}
