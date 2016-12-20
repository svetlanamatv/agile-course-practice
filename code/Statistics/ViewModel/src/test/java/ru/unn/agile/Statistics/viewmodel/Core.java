package ru.unn.agile.Statistics.viewmodel;

import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import ru.unn.agile.Statistics.viewmodel.ViewModel.Status;


abstract class Core {
    private ViewModel vm;

    static final String
            TEST_RESULT_EV = "0.75",
            TEST_RESULT_VAR = "3.1875",
            TEST_RESULT_IM1 = TEST_RESULT_EV,
            TEST_RESULT_IM2 = "3.75";
    private static final double[] TEST_VALUES = new double[] {-1, 2, 3};
    private static final double[] TEST_POSSIBILITIES = new double[] {0.5, 0.25, 0.25};

    @Before
    public void before() {
        FakeLogger logger = new FakeLogger();
        vm = new ViewModel(logger);
    }

    ViewModel vm() {
        return vm;
    }

    void fillInputFields() {
        vm.setValues(TEST_VALUES);
        vm.setPossibilities(TEST_POSSIBILITIES);
    }

    void assertStatusIs(final Status status) {
        assertEquals(status.toString(), vm.getStatus());
    }

    void assertOperationIs(final Operation operation) {
        assertEquals(operation, vm.getOperation());
    }

    void assertArraysAreFromTest() {
        assertSame(TEST_VALUES, vm.getValues());
        assertSame(TEST_POSSIBILITIES, vm.getPossibilities());
    }

    void assertArraysAreEmpty() {
        assertEquals(0, vm.getValues().length);
        assertEquals(0, vm.getPossibilities().length);
    }
}
