package ru.unn.agile.Statistics.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class LoggerTests {
    private ViewModel viewModel;

    @Before
    public void before() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void isLogEmptyInBeginning() {
        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void hasLogSomethingWhenOperationIsChanged() {
        viewModel.setOperation(Operation.EV);
        List<String> log = viewModel.getLog();
        assertNotEquals(0, log.size());
    }
}
