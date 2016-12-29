package ru.unn.agile.todoapp.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.todoapp.model.Task;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TaskViewModelLoggingTest {
    private TaskViewModel viewModel;

    @Before
    public void setUp() {
        Task testTask = new Task("Test", LocalDate.now());
        viewModel = new TaskViewModel(testTask, new TestLogger());
    }

    @Test
    public void taskViewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new TaskViewModel(null, null);
            fail("Exception wasn't thrown");
        } catch (RuntimeException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }
}
