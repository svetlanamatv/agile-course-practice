package ru.unn.agile.triangle.viewmodel.utils;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class LoggerWrapper implements Logger {
    private final Logger logger;

    public LoggerWrapper(final Logger logger) {
        Objects.requireNonNull(logger);
        this.logger = logger;
    }

    @Override
    public void print(final String message) {
        logger.print(message);
    }

    @Override
    public void print(final String pattern, final Object... args) {
        logger.print(pattern, args);
    }

    @Override
    public int getRecordsNumber() {
        return logger.getRecordsNumber();
    }

    @Override
    public List<LoggerRecord> getLastRecords(final int recordsNumber) {
        return logger.getLastRecords(recordsNumber);
    }

    @Override
    public void addListenerForNewRecord(final Consumer<LoggerRecord> listener) {
        logger.addListenerForNewRecord(listener);
    }

    public boolean hasRecordWithMessage(final String message) {
        List<LoggerRecord> records = logger.getLastRecords(getRecordsNumber());
        for (LoggerRecord record : records) {
            String currentMessage = record.getMessage();
            if (currentMessage.contains(message)) {
                return true;
            }
        }
        return false;
    }
}
