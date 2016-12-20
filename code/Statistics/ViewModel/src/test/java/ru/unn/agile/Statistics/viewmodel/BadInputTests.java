package ru.unn.agile.Statistics.viewmodel;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static ru.unn.agile.Statistics.viewmodel.Operation.IM;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.DEFAULT_OPERATION;
import static ru.unn.agile.Statistics.viewmodel.ViewModel.Status.BAD_FORMAT;


public class BadInputTests extends Core {
    @After
    public void checkPersistentParameters() {
        assertStatusIs(BAD_FORMAT);
        assertEquals("", vm.getResult());
        assertFalse(vm.isCalculateButtonEnabled());
    }

    @Test
    public void whenBadDelta() {
        vm.setDelta("a");
        assertEquals("a", vm.getDelta());
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void whenCalculateWithBadDelta() {
        vm.setDelta("a");
        vm.calculate();
        assertEquals("a", vm.getDelta());
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void whenBadDeltaAndFilled() {
        setInputFields();
        vm.setDelta("a");
        assertEquals(false, vm.isCalculateButtonEnabled());
        assertEquals("a", vm.getDelta());
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void whenCalculateWithBadDeltaAndFilled() {
        setInputFields();
        vm.setDelta("a");
        vm.calculate();
        assertEquals("a", vm.getDelta());
        assertOperationIs(DEFAULT_OPERATION);
    }

    @Test
    public void whenBadMomentOrder() {
        vm.setOperation(IM);
        vm.setMomentOrder("x");
        assertEquals("x", vm.getMomentOrder());
        assertOperationIs(IM);
    }
}
