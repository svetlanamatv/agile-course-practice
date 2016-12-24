package ru.unn.agile.triangle.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;
import ru.unn.agile.triangle.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewModelProvider {
    private final ObjectProperty<ViewModel> viewModelProperty
            = new SimpleObjectProperty<>(new ViewModel(new StubLogger()));

    public final ReadOnlyObjectProperty<ViewModel> viewModelProperty() {
        return viewModelProperty;
    }

    public final ViewModel getViewModel() {
        return viewModelProperty.get();
    }

    class StubLogger implements Logger {
        @Override
        public void print(final String message) {
            // Stub
        }

        @Override
        public void print(final String pattern, final Object... args) {
            // Stub
        }

        @Override
        public int getRecordsNumber() {
            // Stub
            return 0;
        }

        @Override
        public List<LoggerRecord> getLastRecords(final int recordsNumber) {
            // Stub
            return new ArrayList<>();
        }
    }
}
