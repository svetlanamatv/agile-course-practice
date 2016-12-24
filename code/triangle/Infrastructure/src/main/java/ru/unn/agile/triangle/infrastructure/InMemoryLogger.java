package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.LoggerRecord;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InMemoryLogger extends ObservableLogger {
    private final LinkedList<LoggerRecord> records = new LinkedList<>();
    private final int maxRecords;

    public InMemoryLogger(final int maxRecords) {
        this.maxRecords = maxRecords;
    }

    @Override
    protected void addRecord(final LoggerRecord record) {
        compactRecordsList();
        records.addLast(record);
    }

    @Override
    public int getRecordsNumber() {
        return Math.min(maxRecords, records.size());
    }

    @Override
    public List<LoggerRecord> getLastRecords(final int recordsNumber) {
        int lastRecordsSize = Math.min(recordsNumber, getRecordsNumber());
        List<LoggerRecord> lastRecords = records.subList(
                records.size() - lastRecordsSize, lastRecordsSize);
        return Collections.unmodifiableList(lastRecords);
    }

    private void compactRecordsList() {
        if (records.size() >= maxRecords) {
            records.removeFirst();
        }
    }
}
