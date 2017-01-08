package ru.unn.agile.Statistics.viewmodel;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class LoggerTests extends ViewModelTestBase {
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
    public void isLogContainingSomethingWhenNotDefaultOperationIsSet() {
        vm().setOperation(Operation.VAR);
        List<String> log = vm().getLog();
        assertNotEquals(0, log.size());
    }
    @Test
    public void isLogContainingProperCountOfMessagesWhenOperationIsChangedInSeries() {
        vm().setOperation(Operation.VAR);
        vm().setOperation(Operation.IM);
        vm().setOperation(Operation.EV);

        List<String> log = vm().getLog();
        assertEquals(3, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenOperationIsSetToIM() {
        vm().setOperation(Operation.IM);
        String message = vm().getLog().get(0);
        assertTrue(message.matches(".*" + Operation.IM.toString() + ".*"));
    }

    @Test
    public void isLogContainingProperMessageWhenOperationIsSetToVAR() {
        vm().setOperation(Operation.VAR);
        String message = vm().getLog().get(0);
        assertTrue(message.matches(".*" + Operation.VAR.toString() + ".*"));
    }

    @Test
    public void isLogContainingProperMessageWhenOperationIsSetToEV() {
        //Operation.EV is default for viewModel, so initially operation is set to VAR
        vm().setOperation(Operation.VAR);
        vm().setOperation(Operation.EV);
        String message = vm().getLog().get(1);
        assertTrue(message.matches(".*" + Operation.EV.toString() + ".*"));
    }

    @Test
    public void isLogNotAddingNewlineWhenOperationAreNotChanged() {
        vm().setOperation(Operation.VAR);
        vm().setOperation(Operation.VAR);

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
        int size = 10;
        vm().setArraysSize(size);
        vm().setArraysSize(size);

        List<String> log = vm().getLog();
        assertEquals(1, log.size());
    }

    @Test
    public void isLogContainingProperMessageWhenDeltaAreChanged() {
        String delta = "0.01";
        vm().setDelta(delta);

        String message = vm().getLog().get(0);

        assertTrue(message.matches(".*" + delta + ".*"));
    }

    @Test
    public void isLogContainingProperMessagesWhenTableAreChanged() {
        setInputFields();
        List<String> log = vm().getLog();

        int logIndex = 0;
        assertTrue(log.get(logIndex++).matches(".*" + Integer.toString(TEST_VALUES.length) + ".*"));

        for (int i = 0; i < TEST_VALUES.length; i++) {
            String setValuePattern = ".*" + Integer.toString(i) + ".*";
            assertTrue(log.get(logIndex++).matches(setValuePattern));
            String setPossibilityPattern = ".*" + Integer.toString(i) + ".*";
            assertTrue(log.get(logIndex++).matches(setPossibilityPattern));
        }
    }

    @Test
    public void isLogContainingProperMessageWhenResultAreCalculated() {
        setInputFields();
        vm().calculate();

        int resultMessageIndex = 7;
        String message = vm().getLog().get(resultMessageIndex);
        assertTrue(message.matches(".*" + vm().getResult() + ".*"));
    }

    @Test
    public void isLogContainingProperMessageWhenMomentOrderAreChanged() {
        setInputFields();
        vm().setOperation(Operation.IM);
        vm().setMomentOrder("5");

        int momentOrderMessageIndex = 8;
        String message = vm().getLog().get(momentOrderMessageIndex);
        assertTrue(message.matches(".*" + vm().getMomentOrder() + ".*"));
    }
}
