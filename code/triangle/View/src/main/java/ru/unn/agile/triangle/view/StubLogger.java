package ru.unn.agile.triangle.view;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
        return 0;
    }

    @Override
    public List<LoggerRecord> getLastRecords(final int recordsNumber) {
        return new ArrayList<>();
    }

    @Override
    public void addListener(Consumer<LoggerRecord> listener) {
        // Stub
    }
}
