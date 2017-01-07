package ru.unn.agile.todoapp.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.todoapp.model.Task;

import java.time.LocalDate;

public class TaskViewModelLoggingTest {
    private TaskViewModel viewModel;
    private static final Task TEST_TASK = new Task("Test", LocalDate.now());

    @Before
    public void setUp() {
        viewModel = new TaskViewModel(TEST_TASK, new TestLogger());
    }

    @Test(expected = RuntimeException.class)
    public void taskViewModelConstructorThrowsExceptionWithNullLogger() {
        new TaskViewModel(TEST_TASK, null);
    }
}
