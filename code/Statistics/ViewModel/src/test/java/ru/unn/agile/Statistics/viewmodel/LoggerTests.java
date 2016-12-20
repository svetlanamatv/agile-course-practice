package ru.unn.agile.Statistics.viewmodel;

import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
    public void isLogContainingSomethingWhenOperationIsChanged() {
        viewModel.setOperation(Operation.EV);
        List<String> log = viewModel.getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenOperationIsChanged() {
        viewModel.setOperation(Operation.EV);
        List<String> log = viewModel.getLog();
        assertEquals(log.get(0), "Operation was changed to Expected value");

        viewModel.setOperation(Operation.VAR);
        log = viewModel.getLog();
        assertEquals(log.get(1), "Operation was changed to Variance");

        viewModel.setOperation(Operation.IM);
        log = viewModel.getLog();
        assertEquals(log.get(2), "Operation was changed to Initial moment");
    }

    @Test
    public void isLogNotAddingNewlineWhenOperationAreNotChanged() {
        viewModel.setOperation(Operation.EV);
        viewModel.setOperation(Operation.EV);

        List<String> log = viewModel.getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenArrayOfValuesAreChanged() {
        int size = 10;
        viewModel.setArraysSize(size);

        List<String> log = viewModel.getLog();

        assertEquals(log.get(0), "Count of samples was changed to " + Integer.toString(size));
    }

    @Test
    public void isLogNotAddingNewlineWhenCountSamplesAreNotChanged() {
        viewModel.setArraysSize(10);
        viewModel.setArraysSize(10);

        List<String> log = viewModel.getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenDeltaAreChanged() {
        String delta = "0.01";
        viewModel.setDelta("0.01");

        List<String> log = viewModel.getLog();
        assertEquals(log.get(0), "Delta was changed to " + delta);
    }
}
