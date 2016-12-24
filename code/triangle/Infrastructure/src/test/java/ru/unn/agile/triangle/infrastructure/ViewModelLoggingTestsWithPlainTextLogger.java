package ru.unn.agile.triangle.infrastructure;

import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.ViewModelLoggingTestsBase;

public class ViewModelLoggingTestsWithPlainTextLogger extends ViewModelLoggingTestsBase {
    private static final String TEXT_LOGGER_FILE =
            "./view-model-logging-test-with-plain-text-logger-output.txt";

    @Override
    protected Logger constructLogger() {
        return new PlainTextFileLogger(TEXT_LOGGER_FILE);
    }
}
