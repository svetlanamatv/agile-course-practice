package ru.unn.agile.triangle.viewmodel;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.mock.FakeLogger;

public class ViewModelLoggingTestsWithFakeLogger extends ViewModelLoggingTestsBase {
    @Override
    protected Logger constructLogger() {
        return new FakeLogger();
    }
}
