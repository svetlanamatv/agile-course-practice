package ru.unn.agile.triangle.viewmodel.fake;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

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
        String message = String.format(pattern, args);
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

        int lastIndex = records.size() - 1;
        List<LoggerRecord> lastRecords =
                records.subList(lastIndex - recordsNumber, lastIndex);
        return Collections.unmodifiableList(lastRecords);
    }
}
