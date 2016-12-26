package ru.unn.agile.triangle.viewmodel;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.mock.FakeLogger;

@RunWith(Parameterized.class)
public class ViewModelLoggingCalcTestsWithFakeLogger
        extends ViewModelLoggingCalcTestsBase {

    @Parameterized.Parameters
    public static Iterable loggerMessages() {
        return ViewModelLoggingCalcTestsBase.loggerMessages();
    }

    public ViewModelLoggingCalcTestsWithFakeLogger(final String expectedMessage) {
        super(expectedMessage);
    }

    @Override
    protected Logger constructLogger() throws Exception {
        return new FakeLogger();
    }
}
