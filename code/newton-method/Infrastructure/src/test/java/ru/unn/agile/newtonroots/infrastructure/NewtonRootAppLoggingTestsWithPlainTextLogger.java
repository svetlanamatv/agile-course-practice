package ru.unn.agile.newtonroots.infrastructure;

import ru.unn.agile.newtonroots.viewmodel.NewtonRootAppLoggingTests;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootAppViewModel;

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
