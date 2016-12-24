package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

public class PlainTextFileLogger implements Logger {
    private final Logger inmemoryLogger;
    private FileWriter logWriter;

    public PlainTextFileLogger(final String pathToLogFile,
                               final int maxRecordsInMemory) throws IOException {
        inmemoryLogger = new InMemoryLogger(maxRecordsInMemory);
        openLogFile(pathToLogFile);
    }

    @Override
    public void print(final String message) {
        inmemoryLogger.print(message);
    }

    @Override
    public void print(final String pattern, final Object... args) {
        inmemoryLogger.print(pattern, args);
        print(MessageFormat.format(pattern, args));
    }

    @Override
    public int getRecordsNumber() {
        return inmemoryLogger.getRecordsNumber();
    }

    @Override
    public List<LoggerRecord> getLastRecords(int recordsNumber) {
        return inmemoryLogger.getLastRecords(recordsNumber);
    }

    private void openLogFile(final String pathToLogFile) throws IOException {
        File logFile = new File(pathToLogFile);
        if (!logFile.exists()) {
            logFile.createNewFile();
        }

        logWriter = new FileWriter(logFile);
    }
}
