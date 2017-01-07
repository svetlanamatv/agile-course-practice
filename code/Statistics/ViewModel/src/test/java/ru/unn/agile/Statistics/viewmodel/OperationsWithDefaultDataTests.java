package ru.unn.agile.Statistics.viewmodel;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static ru.unn.agile.Statistics.viewmodel.Operation.EV;
import static ru.unn.agile.Statistics.viewmodel.Operation.IM;
import static ru.unn.agile.Statistics.viewmodel.Operation.VAR;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.DEFAULT_DELTA;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.Status.WAITING;


public class OperationsWithDefaultDataTests extends ViewModelTestBase {
    @After
    public void checkPersistentParameters() {
        assertEquals(DEFAULT_DELTA, vm().getDelta());

        assertStatusIs(WAITING);
        assertFalse(vm().isCalculateButtonEnabled());
    }

    @Test
    public void canSetEVOperation() {
        vm().setOperation(EV);
        assertOperationIs(EV);
    }

    @Test
    public void canSetVAROperation() {
        vm().setOperation(VAR);
        assertOperationIs(VAR);
    }

    @Test
    public void canSetIMOperation() {
        vm().setOperation(IM);
        assertOperationIs(IM);
    }
}
