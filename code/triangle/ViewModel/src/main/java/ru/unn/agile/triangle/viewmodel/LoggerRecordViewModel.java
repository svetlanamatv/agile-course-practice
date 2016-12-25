package ru.unn.agile.triangle.viewmodel;

import ru.unn.agile.triangle.logging.LoggerRecord;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoggerRecordViewModel {
    private final LoggerRecord loggerRecord;

    public LoggerRecordViewModel(final LoggerRecord record) {
        Objects.requireNonNull(record);
        loggerRecord = record;
    }

    public final String getMessage() {
        return loggerRecord.getMessage();
    }

    public final LocalDateTime getTimestamp() {
        return loggerRecord.getTimestamp();
    }
}
