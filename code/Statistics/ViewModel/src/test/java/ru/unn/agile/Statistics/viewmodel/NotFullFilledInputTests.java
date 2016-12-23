package ru.unn.agile.Statistics.viewmodel;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static ru.unn.agile.Statistics.viewmodel.Operation.EV;
import static ru.unn.agile.Statistics.viewmodel.Operation.IM;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.DEFAULT_DELTA;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.DEFAULT_OPERATION;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.Status.WAITING;


public class NotFullFilledInputTests extends Core {
    @After
    public void checkPersistentParameters() {
        assertStatusIs(WAITING);
        assertEquals("", vm().getResult());
        assertFalse(vm().isCalculateButtonEnabled());
    }

    @Test
    public void whenCalculateWithEmptyFields() {
        vm().calculate();

        assertEquals(DEFAULT_DELTA, vm().getDelta());
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void whenDeltaSet() {
        vm().setDelta("1.0");

        assertEquals("1.0", vm().getDelta());
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void canCleanStatusIfDeltaWasBadButNowIsOK() {
        vm().setDelta("a");
        vm().setDelta("1.0");
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void isMomentOrderDisabledAfterSelectIMThenEV() {
        vm().setOperation(IM);
        vm().setOperation(EV);
        assertFalse(vm().isMomentOrderEnabled());
    }

    @Test(expected = IllegalStateException.class)
    public void cannotGetMomentOrderAfterSelectIMThenEV() {
        vm().setOperation(IM);
        vm().setMomentOrder("123");
        vm().setOperation(EV);
        assertEquals("123", vm().getMomentOrder());
    }

    @Test
    public void isMomentOrderSavedAfterSelectIMThanEVThanIM() {
        vm().setOperation(IM);
        vm().setMomentOrder("123");
        vm().setOperation(EV);
        vm().setOperation(IM);
        assertEquals("123", vm().getMomentOrder());
    }

    @Test
    public void whenFilledButMomentOrderNotSet() {
        setInputFields();
        vm().setOperation(IM);
    }
}
