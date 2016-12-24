package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InMemoryLogger implements Logger {
    private final LinkedList<LoggerRecord> records = new LinkedList<>();
    private final int maxRecords;

    public InMemoryLogger(final int maxRecords) {
        this.maxRecords = maxRecords;
    }

    @Override
    public void print(final String message) {
        compactRecordsList();
        records.addLast(new LoggerRecord(message, LocalDateTime.now()));
    }

    @Override
    public void print(final String pattern, final Object... args) {
        String message = MessageFormat.format(pattern, args);
        print(message);
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
