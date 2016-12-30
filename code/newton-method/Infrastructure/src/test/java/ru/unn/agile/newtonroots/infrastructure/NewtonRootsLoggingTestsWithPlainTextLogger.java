package ru.unn.agile.newtonroots.infrastructure;

import ru.unn.agile.newtonroots.viewmodel.NewtonRootsLoggingTests;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootsViewModel;

import java.io.IOException;

public class NewtonRootsLoggingTestsWithPlainTextLogger extends NewtonRootsLoggingTests {
    @Override
    public void setUp() {
        try {
            PlainTextLogger logger = new PlainTextLogger("newton-method.log");
            setViewModel(new NewtonRootsViewModel(logger));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
