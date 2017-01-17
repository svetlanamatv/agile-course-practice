package ru.unn.agile.Salary.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SalaryViewModelTest {
    private SalaryViewModel viewModel;

    public void setViewModel(final SalaryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new SalaryViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testCalculate() {
        viewModel.baseSalaryProperty().set("10000");
        viewModel.hoursPerWeekProperty().set("40");
        viewModel.extraHoursProperty().set("10");
        viewModel.leaveDaysProperty().set("2");
        viewModel.calculateSalary();
        assertEquals(String.valueOf(9625.0), viewModel.salaryProperty().get());
    }

    @Test
    public void testRecognizeInvalidParams() {
        viewModel.baseSalaryProperty().set("-10000");
        viewModel.hoursPerWeekProperty().set("40");
        viewModel.extraHoursProperty().set("10");
        viewModel.leaveDaysProperty().set("2");
        viewModel.calculateSalary();
        assertEquals(Status.INVALID_PARAMS.toString(), viewModel.statusProperty().get());
    }


    @Test
    public void testRecognizeNotNumberParams() {
        viewModel.baseSalaryProperty().set("qwerty");
        viewModel.hoursPerWeekProperty().set("20");
        viewModel.extraHoursProperty().set("5");
        viewModel.leaveDaysProperty().set("0");
        viewModel.calculateSalary();
        assertEquals(Status.INVALID_PARAMS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testSetDefaultValues() {
        assertEquals("", viewModel.baseSalaryProperty().get());
        assertEquals("", viewModel.hoursPerWeekProperty().get());
        assertEquals("", viewModel.extraHoursProperty().get());
        assertEquals("", viewModel.leaveDaysProperty().get());
        assertEquals("", viewModel.getLogsProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testSetNotEnoughData() {
        viewModel.baseSalaryProperty().set("10000");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testCalculateWithEmptyFields() {
        viewModel.calculateSalary();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testToCreateModelWithDefaultValues() {
        viewModel = new SalaryViewModel();
        assertNotNull(viewModel);
    }
}
