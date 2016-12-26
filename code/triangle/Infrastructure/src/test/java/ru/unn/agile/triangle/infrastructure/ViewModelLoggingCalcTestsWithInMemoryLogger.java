package ru.unn.agile.triangle.infrastructure;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.ViewModelLoggingCalcTestsBase;
import ru.unn.agile.triangle.viewmodel.mock.FakeLogger;

@RunWith(Parameterized.class)
public class ViewModelLoggingCalcTestsWithInMemoryLogger
        extends ViewModelLoggingCalcTestsBase {
    private static final int MAX_RECORDS_IN_MEMORY = 100;

    @Parameterized.Parameters
    public static Iterable loggerMessages() {
        return ViewModelLoggingCalcTestsBase.loggerMessages();
    }

    public ViewModelLoggingCalcTestsWithInMemoryLogger(final String expectedMessage) {
        super(expectedMessage);
    }

    @Override
    protected Logger constructLogger() throws Exception {
        return new InMemoryLogger(MAX_RECORDS_IN_MEMORY);
    }
}
