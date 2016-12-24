package ru.unn.agile.triangle.infrastructure;

import org.junit.Assert;
import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.ViewModelLoggingTestsBase;

import java.io.IOException;

public class ViewModelLoggingTestsWithPlainTextLogger extends ViewModelLoggingTestsBase {
    private static final int MAX_RECORDS_IN_MEMORY = 100;
    private static final String TEXT_LOGGER_FILE =
            "./test-plain-text-logger-output.txt";

    @Override
    protected Logger constructLogger() {
        try {
            return new PlainTextFileLogger(TEXT_LOGGER_FILE, MAX_RECORDS_IN_MEMORY);
        } catch (final IOException ioex) {
            ioex.printStackTrace();
        }
        Assert.fail();
        return null;
    }
}
