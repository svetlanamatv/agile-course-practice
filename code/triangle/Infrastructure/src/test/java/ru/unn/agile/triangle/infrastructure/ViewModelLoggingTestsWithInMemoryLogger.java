package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.ViewModelLoggingTestsBase;

public class ViewModelLoggingTestsWithInMemoryLogger extends ViewModelLoggingTestsBase {
    private static final int MAX_RECORDS_IN_MEMORY = 100;

    @Override
    protected Logger constructLogger() throws Exception {
        return new InMemoryLogger(MAX_RECORDS_IN_MEMORY);
    }
}
