package ru.unn.agile.triangle.infrastructure;

import org.junit.After;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.ViewModelLoggingTestsBase;

import java.io.*;

public class ViewModelLoggingTestsWithPlainTextLogger extends ViewModelLoggingTestsBase {
    private static final int MAX_RECORDS_IN_MEMORY = 100;
    private static final String TEXT_LOGGER_FILE =
            "./test-view-model-plain-text-logger-output.txt";

    private PlainTextFileLogger logger;

    @After
    public void tearDown() throws Exception {
        logger.close();
        new File(TEXT_LOGGER_FILE).delete();
    }

    @Override
    protected Logger constructLogger() throws Exception {
        logger = new PlainTextFileLogger(TEXT_LOGGER_FILE, MAX_RECORDS_IN_MEMORY);
        return logger;
    }
}
