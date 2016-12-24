package ru.unn.agile.triangle.viewmodel.mock;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FakeLogger implements Logger {
    private final ArrayList<LoggerRecord> records = new ArrayList<>();

    @Override
    public void print(final String message) {
        records.add(new LoggerRecord(message, LocalDateTime.now()));
    }

    @Override
    public void print(final String pattern, final Object... args) {
        String message = MessageFormat.format(pattern, args);
        print(message);
    }

    @Override
    public int getRecordsNumber() {
        return records.size();
    }

    @Override
    public List<LoggerRecord> getLastRecords(final int recordsNumber) {
        if (recordsNumber > records.size()) {
            return Collections.unmodifiableList(records);
        }

        int recordsSize = records.size();
        List<LoggerRecord> lastRecords =
                records.subList(recordsSize - recordsNumber, recordsSize);
        return Collections.unmodifiableList(lastRecords);
    }
}
