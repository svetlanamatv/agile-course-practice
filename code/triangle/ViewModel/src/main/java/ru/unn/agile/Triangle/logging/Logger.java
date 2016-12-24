package ru.unn.agile.triangle.logging;

import java.util.List;

public interface Logger {
    void print(String message);
    void print(String pattern, Object... args);

    int getRecordsNumber();
    List<LoggerRecord> getLastRecords(int recordsNumber);
}
