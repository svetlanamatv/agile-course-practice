package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.LoggerRecord;

import java.util.List;

public class TestObservableLogger extends ObservableLogger {
    private LoggerRecord lastRecord;

    @Override
    public int getRecordsNumber() {
        return 0;
    }

    @Override
    public List<LoggerRecord> getLastRecords(final int recordsNumber) {
        return null;
    }

    @Override
    protected void addRecord(final LoggerRecord record) {
        lastRecord = record;
    }

    LoggerRecord getLastAddedRecord() {
        return lastRecord;
    }
}
