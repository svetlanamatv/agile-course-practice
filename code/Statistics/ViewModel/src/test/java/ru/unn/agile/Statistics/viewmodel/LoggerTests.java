package ru.unn.agile.Statistics.viewmodel;

import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LoggerTests extends Core {
    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void isLogEmptyInBeginning() {
        List<String> log = vm().getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isLogContainingSomethingWhenOperationIsChanged() {
        vm().setOperation(Operation.EV);
        List<String> log = vm().getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenOperationIsChanged() {
        vm().setOperation(Operation.EV);
        String message = vm().getLog().get(0);
        assertTrue(message.matches(".*" + Operation.EV.toString() + ".*"));

        vm().setOperation(Operation.VAR);
        message = vm().getLog().get(1);
        assertTrue(message.matches(".*" + Operation.VAR.toString() + ".*"));

        vm().setOperation(Operation.IM);
        message = vm().getLog().get(2);
        assertTrue(message.matches(".*" + Operation.IM.toString() + ".*"));
    }

    @Test
    public void isLogNotAddingNewlineWhenOperationAreNotChanged() {
        vm().setOperation(Operation.EV);
        vm().setOperation(Operation.EV);

        List<String> log = vm().getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenArrayOfValuesAreChanged() {
        int size = 10;
        vm().setArraysSize(size);

        String message = vm().getLog().get(0);

        assertTrue(message.matches(".*" + Integer.toString(size) + ".*"));
    }

    @Test
    public void isLogNotAddingNewlineWhenCountSamplesAreNotChanged() {
        vm().setArraysSize(10);
        vm().setArraysSize(10);

        List<String> log = vm().getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenDeltaAreChanged() {
        String delta = "0.01";
        vm().setDelta("0.01");

        String message = vm().getLog().get(0);

        assertTrue(message.matches(".*" + delta + ".*"));
    }
}
