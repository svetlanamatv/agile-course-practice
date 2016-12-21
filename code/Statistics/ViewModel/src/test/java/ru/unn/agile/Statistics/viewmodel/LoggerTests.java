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
        List<String> log = vm.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void isLogContainingSomethingWhenNotDefaultOperationIsSet() {
        vm.setOperation(Operation.VAR);
        List<String> log = vm.getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenOperationIsChanged() {
        //Operation.EV is default for viewModel, so a message isn't added to log
        vm.setOperation(Operation.EV);

        List<String> log = vm.getLog();
        assertEquals(0, log.size());

        vm.setOperation(Operation.VAR);
        String message = vm.getLog().get(0);
        assertTrue(message.matches(".*" + Operation.VAR.toString() + ".*"));

        vm.setOperation(Operation.IM);
        message = vm.getLog().get(1);
        assertTrue(message.matches(".*" + Operation.IM.toString() + ".*"));

        vm.setOperation(Operation.EV);
        message = vm.getLog().get(2);
        assertTrue(message.matches(".*" + Operation.EV.toString() + ".*"));
    }

    @Test
    public void isLogNotAddingNewlineWhenOperationAreNotChanged() {
        vm.setOperation(Operation.VAR);
        vm.setOperation(Operation.VAR);

        List<String> log = vm.getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenArrayOfValuesAreChanged() {
        int size = 10;
        vm.setArraysSize(size);

        String message = vm.getLog().get(0);

        assertTrue(message.matches(".*" + Integer.toString(size) + ".*"));
    }

    @Test
    public void isLogNotAddingNewlineWhenCountSamplesAreNotChanged() {
        vm.setArraysSize(10);
        vm.setArraysSize(10);

        List<String> log = vm.getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenDeltaAreChanged() {
        String delta = "0.01";
        vm.setDelta("0.01");

        String message = vm.getLog().get(0);

        assertTrue(message.matches(".*" + delta + ".*"));
    }

    @Test
    public void isLogContainingProperMessagesWhenTableAreChanged() {
        setInputFields();
        List<String> log = vm.getLog();

        int logIndex = 0;
        assertTrue(log.get(logIndex++).matches(".*" + Integer.toString(TEST_VALUES.length) + ".*"));

        for (int i = 0; i < TEST_VALUES.length; i++) {
            String setValuePattern = ".*" + Double.toString(TEST_VALUES[i]) + ".*" + Integer.toString(i) + ".*";
            assertTrue(log.get(logIndex++).matches(setValuePattern));
            String setPossibilityPattern = ".*" + Double.toString(TEST_POSSIBILITIES[i]) + ".*" + Integer.toString(i) + ".*";
            assertTrue(log.get(logIndex++).matches(setPossibilityPattern));
        }
    }

    @Test
    public void isLogContainingProperMessageWhenResultAreCalculated() {
        setInputFields();
        vm.calculate();

        List<String> log = vm.getLog();
        //skip messages about input
        String message = vm.getLog().get(7);
        //and check message about result
        assertTrue(message.matches(".*" + vm.getResult() + ".*"));
    }

    @Test
    public void isLogContainingProperMessageWhenMomentOrderAreChanged() {
        setInputFields();
        vm.setOperation(Operation.IM);
        vm.setMomentOrder("5");

        List<String> log = vm.getLog();
        //skip messages about input and setting operation
        String message = vm.getLog().get(8);
        //and check message about result
        assertTrue(message.matches(".*" + vm.getMomentOrder() + ".*"));
    }
}
