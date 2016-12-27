package ru.unn.agile.NewtonRoots.infrastructure;

import ru.unn.agile.NewtonRoots.viewmodel.NewtonRootAppLoggingTests;
import ru.unn.agile.NewtonRoots.viewmodel.NewtonRootAppViewModel;

import java.io.IOException;

public class NewtonRootAppLoggingTestsWithPlainTextLogger extends NewtonRootAppLoggingTests {
    @Override
    public void setUp() {
        try {
            PlainTextLogger logger = new PlainTextLogger("newton-method.log");
            viewModel = new NewtonRootAppViewModel(logger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
