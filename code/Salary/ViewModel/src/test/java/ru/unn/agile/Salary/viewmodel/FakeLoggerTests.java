package ru.unn.agile.Salary.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FakeLoggerTests {
    private SalaryViewModel viewModel;

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
    public void viewModelConstructorThrowsException() {
        try {
            new SalaryViewModel(null);
            fail("Exception do not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Logger params are null", e.getMessage());
        } catch (Exception e) {
            fail("Wrong exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLogger();

        assertTrue(log.isEmpty());
    }
}
