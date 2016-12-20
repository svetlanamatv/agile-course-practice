package ru.unn.agile.Statistics.viewmodel;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static ru.unn.agile.Statistics.viewmodel.Operation.IM;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.DEFAULT_DELTA;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.DEFAULT_OPERATION;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.Status.WAITING;


public class DefaultStateTests extends Core {
    @Test
    public void delta() {
        assertEquals(DEFAULT_DELTA, vm.getDelta());
        assertNotNull(Double.valueOf(DEFAULT_DELTA));
    }

    @Test
    public void arrays() {
        assertEquals(0, vm.getTableSize());
    }

    @Test
    public void result() {
        assertEquals("", vm.getResult());
    }

    @Test
    public void status() {
        assertStatusIs(WAITING);
    }

    @Test
    public void calculateButton() {
        assertFalse(vm.isCalculateButtonEnabled());
    }

    @Test
    public void momentOrderEnabled() {
        assertFalse(vm.isMomentOrderEnabled());
    }

    @Test(expected = IllegalStateException.class)
    public void cannotGetMomentOrder() {
        assertNotNull(vm.getMomentOrder());
    }

    @Test(expected = IllegalStateException.class)
    public void cannotSetMomentOrder() {
        vm.setMomentOrder("1");
    }

    @Test
    public void momentOrder() {
        vm.setOperation(IM);
        assertEquals("", vm.getMomentOrder());
    }

    @Test
    public void operation() {
        assertOperationIs(DEFAULT_OPERATION);
    }
}
