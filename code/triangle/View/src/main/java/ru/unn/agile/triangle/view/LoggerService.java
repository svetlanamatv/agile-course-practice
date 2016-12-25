package ru.unn.agile.triangle.view;

import ru.unn.agile.triangle.infrastructure.PlainTextFileLogger;
import ru.unn.agile.triangle.logging.Logger;

import java.io.IOException;

final class LoggerService {
    private static final String LOGGER_FILE_NAME = "./triangle-app.log";
    private static final int MAX_RECORDS_IN_MEMORY = 100;

    static Logger getLogger() {
        try {
            return new PlainTextFileLogger(
                    LOGGER_FILE_NAME,
                    MAX_RECORDS_IN_MEMORY);
        } catch (final IOException ioex) {
            System.out.format("Can't create text file logger with output file %s,"
                    + "application will start but without logging capability",
                    LOGGER_FILE_NAME);
            return new StubLogger();
        }
    }

    private LoggerService() {
    }
}
