package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.util.List;

public class PlainTextFileLogger implements Logger {
    public PlainTextFileLogger(final String pathToLogFile) {

    }

    @Override
    public void print(final String message) {

    }

    @Override
    public void print(final String pattern, final Object... args) {

    }

    @Override
    public int getRecordsNumber() {
        return 0;
    }

    @Override
    public List<LoggerRecord> getLastRecords(final int recordsNumber) {
        return null;
    }
}
