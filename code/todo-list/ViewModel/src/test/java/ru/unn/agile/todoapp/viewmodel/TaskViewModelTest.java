package ru.unn.agile.todoapp.viewmodel;

import org.junit.Test;
import ru.unn.agile.todoapp.model.Task;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskViewModelTest {
    private final Task task = new Task("Wash the car", LocalDate.now());
    private final TaskViewModel viewModel = new TaskViewModel(task, new TestLogger());

    @Test
    public void byDefaultDoneCheckboxIsUnchecked() {
        assertFalse(viewModel.getDoneCheckboxChecked());
    }

    @Test
    public void whenDoneCheckboxIsClickedTaskIsMarkedAsDone() {
        viewModel.clickIsDoneCheckBox();

        assertTrue(viewModel.getDoneCheckboxChecked());
    }

    @Test
    public void whenDoneCheckboxIsClickedItIsDisabled() {
        viewModel.clickIsDoneCheckBox();

        assertTrue(viewModel.getDoneCheckboxDisable());
    }
}
