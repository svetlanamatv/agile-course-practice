package ru.unn.agile.triangle.infrastructure;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.ViewModelLoggingCalcTestsBase;

import java.io.File;

@RunWith(Parameterized.class)
public class ViewModelLoggingCalcTestsWithPlainTextLogger
        extends ViewModelLoggingCalcTestsBase {
    private static final int MAX_RECORDS_IN_MEMORY = 100;
    private static final String TEXT_LOGGER_FILE =
            "./test-view-model-plain-text-logger-output.txt";

    private PlainTextFileLogger logger;

    @After
    public void tearDown() throws Exception {
        logger.close();
        new File(TEXT_LOGGER_FILE).delete();
    }

    @Parameterized.Parameters
    public static Iterable loggerMessages() {
        return ViewModelLoggingCalcTestsBase.loggerMessages();
    }

    public ViewModelLoggingCalcTestsWithPlainTextLogger(final String expectedMessage) {
        super(expectedMessage);
    }

    @Override
    protected Logger constructLogger() throws Exception {
        logger = new PlainTextFileLogger(TEXT_LOGGER_FILE, MAX_RECORDS_IN_MEMORY);
        return logger;
    }
}
