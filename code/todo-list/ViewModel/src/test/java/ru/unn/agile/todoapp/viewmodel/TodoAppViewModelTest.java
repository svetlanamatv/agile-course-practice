package ru.unn.agile.todoapp.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TodoAppViewModelTest {
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate DAY_AFTER_TOMORROW = TODAY.plusDays(2);
    private static final String DAY_AFTER_TOMORROW_STRING =
            DAY_AFTER_TOMORROW.format(TaskViewModel.DATE_FORMATTER);
    private static final String TASK_DESCRIPTION = "Water the plants";
    private static final String NONURGENT_TASK_DESCRIPTION = "Wash the car";
    private static final String URGENT_TASK_DESCRIPTION = "Do taxes";
    private static final String DONE_TASK_DESCRIPTION = "Party with friends";
    private static final String UNDONE_TASK_DESCRIPTION = "Study for exam";
    private TodoAppViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TodoAppViewModel(new TestLogger());
    }

    @Test
    public void byDefaultNewTaskDateIsToday() {
        assertEquals(TODAY, viewModel.getNewTaskDueDate());
    }

    @Test
    public void byDefaultNewTaskDescriptionIsEmpty() {
        assertEquals("", viewModel.getNewTaskDescription());
    }

    @Test
    public void byDefaultAddNewTaskButtonIsDisabled() {
        assertTrue(viewModel.getAddNewTaskButtonDisable());
    }

    @Test
    public void whenDescriptionIsNonEmptyAddNewTaskButtonIsEnabled() {
        viewModel.setNewTaskDescription(TASK_DESCRIPTION);

        assertFalse(viewModel.getAddNewTaskButtonDisable());
    }

    @Test
    public void whenDescriptionIsClearedAddNewTaskButtonIsDisabled() {
        viewModel.setNewTaskDescription(TASK_DESCRIPTION);

        viewModel.setNewTaskDescription("");

        assertTrue(viewModel.getAddNewTaskButtonDisable());
    }

    @Test
    public void whenAddingNewTaskDescriptionFieldShouldClear() {
        addTask(viewModel, TASK_DESCRIPTION, DAY_AFTER_TOMORROW);

        assertEquals("", viewModel.getNewTaskDescription());
    }

    @Test
    public void whenAddingNewTaskDueDateShouldJumpBackToToday() {
        addTask(viewModel, TASK_DESCRIPTION, DAY_AFTER_TOMORROW);

        assertEquals(TODAY, viewModel.getNewTaskDueDate());
    }

    @Test
    public void whenAddingNewTaskItAppearsInTheList() {
        addTask(viewModel, TASK_DESCRIPTION, DAY_AFTER_TOMORROW);
        TaskViewModel lastTask = viewModel.getSortedTasksViewModels().get(0);

        assertEquals(TASK_DESCRIPTION, lastTask.getDescription());
        assertEquals(DAY_AFTER_TOMORROW_STRING, lastTask.getDueDateString());
        assertFalse(lastTask.getDoneCheckboxChecked());
    }

    @Test
    public void whenDeleteButtonIsPressedTaskIsDeletedFromTheList() {
        addTask(viewModel, TASK_DESCRIPTION, DAY_AFTER_TOMORROW);

        TaskViewModel deletedTask = viewModel.getSortedTasksViewModels().get(0);
        viewModel.pressDeleteButton(deletedTask);

        assertTrue(viewModel.getSortedTasksViewModels().isEmpty());
    }

    @Test
    public void undoneTasksAreSortedByDueDate() {
        addTask(viewModel, NONURGENT_TASK_DESCRIPTION, DAY_AFTER_TOMORROW);
        addTask(viewModel, URGENT_TASK_DESCRIPTION, TOMORROW);

        assertEquals(URGENT_TASK_DESCRIPTION,
                viewModel.getSortedTasksViewModels().get(0).getDescription());
        assertEquals(NONURGENT_TASK_DESCRIPTION,
                viewModel.getSortedTasksViewModels().get(1).getDescription());
    }

    @Test
    public void doneTasksAreReverseSortedByDueDate() {
        addTask(viewModel, NONURGENT_TASK_DESCRIPTION, DAY_AFTER_TOMORROW);
        addTask(viewModel, URGENT_TASK_DESCRIPTION, TOMORROW);

        viewModel.getSortedTasksViewModels().get(0).clickIsDoneCheckBox();
        viewModel.getSortedTasksViewModels().get(1).clickIsDoneCheckBox();

        assertEquals(NONURGENT_TASK_DESCRIPTION,
                viewModel.getSortedTasksViewModels().get(0).getDescription());
        assertEquals(URGENT_TASK_DESCRIPTION,
                viewModel.getSortedTasksViewModels().get(1).getDescription());
    }

    @Test
    public void undoneTasksAreAboveDoneTasks() {
        addTask(viewModel, DONE_TASK_DESCRIPTION, DAY_AFTER_TOMORROW);
        addTask(viewModel, UNDONE_TASK_DESCRIPTION, DAY_AFTER_TOMORROW);

        viewModel.getTasksViewModels().get(0).clickIsDoneCheckBox();

        assertEquals(UNDONE_TASK_DESCRIPTION,
                viewModel.getSortedTasksViewModels().get(0).getDescription());
        assertEquals(DONE_TASK_DESCRIPTION,
                viewModel.getSortedTasksViewModels().get(1).getDescription());
    }

    private void addTask(final TodoAppViewModel viewModel, final String description,
                         final LocalDate dueDate) {
        viewModel.setNewTaskDescription(description);
        viewModel.setNewTaskDueDate(dueDate);
        viewModel.pressAddNewTaskButton();
    }
}
