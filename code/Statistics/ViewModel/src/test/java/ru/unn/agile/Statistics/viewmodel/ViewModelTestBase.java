package ru.unn.agile.Statistics.viewmodel;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import ru.unn.agile.Statistics.viewmodel.ViewModel.Status;


abstract class ViewModelTestBase {
    private ViewModel vm;

    protected static final String
            TEST_RESULT_EV = "0.75",
            TEST_RESULT_VAR = "3.1875",
            TEST_RESULT_IM1 = TEST_RESULT_EV,
            TEST_RESULT_IM2 = "3.75";
    protected static final double[] TEST_VALUES = new double[] {-1, 2, 3};
    protected static final double[] TEST_POSSIBILITIES = new double[] {0.5, 0.25, 0.25};

    @Before
    public void before() {
        FakeLogger logger = new FakeLogger();
        vm = new ViewModel(logger);
    }

    public ViewModel vm() {
        return vm;
    }

    public void setVM(final ViewModel vm) {
        this.vm = vm;
    }

    public void setInputFields() {
        vm.setArraysSize(TEST_VALUES.length);
        for (int i = 0; i < TEST_VALUES.length; i++) {
            vm.setValue(i, TEST_VALUES[i]);
            vm.setPossibility(i, TEST_POSSIBILITIES[i]);
        }
    }

    public void assertStatusIs(final Status status) {
        assertEquals(status.toString(), vm.getStatus());
    }

    public void assertOperationIs(final Operation operation) {
        assertEquals(operation, vm.getOperation());
    }

    public void assertArraysAreFromTest() {
        for (int i = 0; i < TEST_VALUES.length; i++) {
            assertEquals(TEST_VALUES[i], vm.getValue(i), 0.001);
            assertEquals(TEST_POSSIBILITIES[i], vm.getPossibility(i), 0.001);
        }
    }
}
