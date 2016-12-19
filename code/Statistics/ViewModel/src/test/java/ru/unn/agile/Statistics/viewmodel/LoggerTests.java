package ru.unn.agile.Statistics.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LoggerTests {
    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }
}
