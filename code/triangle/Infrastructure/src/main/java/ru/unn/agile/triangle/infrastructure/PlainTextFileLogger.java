package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.logging.LoggerRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlainTextFileLogger implements Logger {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm:ss.SSS");

    private final Logger inmemoryLogger;
    private final String outputFile;
    private FileWriter logWriter;

    public PlainTextFileLogger(final String pathToLogFile,
                               final int maxRecordsInMemory) throws IOException {
        outputFile = pathToLogFile;
        inmemoryLogger = new InMemoryLogger(maxRecordsInMemory);
        openLogFile(pathToLogFile);
    }

    @Override
    public void print(final String message) {
        inmemoryLogger.print(message);

        String timestampFormatted = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String logLine = MessageFormat.format("[{0}]: {1}",
                timestampFormatted, message);

        try {
            logWriter.write(logLine + "\n");
            logWriter.flush();
        } catch (final IOException ioex) {
            System.out.print(PlainTextFileLogger.class.getName()
                    + " can't write log line to file " + outputFile);
            ioex.printStackTrace();
        }
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

    public void close() throws IOException {
        logWriter.close();
    }

    private void openLogFile(final String pathToLogFile) throws IOException {
        File logFile = new File(pathToLogFile);
        if (!logFile.exists()) {
            logFile.createNewFile();
        }

        logWriter = new FileWriter(logFile, true);
    }
}
