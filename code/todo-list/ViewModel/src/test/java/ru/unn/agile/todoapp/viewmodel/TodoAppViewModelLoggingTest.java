package ru.unn.agile.todoapp.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TodoAppViewModelLoggingTest {
    private static final LocalDate TODAY = LocalDate.now();
    private static final String TEST_TASK_DESCRIPTION = "Test task";
    private TodoAppViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TodoAppViewModel(new TestLogger());
    }

    @Test
    public void logIsEmptyAfterStart() {
        List<String> log = viewModel.getLog();

        assertEquals(true, log.isEmpty());
    }

    @Test
    public void setNewTaskDescriptionMentionedInTheLog()  {
        viewModel.setNewTaskDescription("just a task");
        viewModel.onNewTaskDescriptionChanged(Boolean.TRUE, Boolean.FALSE);
        String lastLogMessage = viewModel.getLastLogMessage();

        assertTrue(lastLogMessage.matches(".*" + viewModel.getNewTaskDescription() + ".*"));
    }

    @Test
    public void setNewTaskDueDateMentionedInTheLog()  {
        viewModel.setNewTaskDueDate(TODAY);
        viewModel.onTaskDueDateChanged(Boolean.TRUE, Boolean.FALSE);
        String lastLogMessage = viewModel.getLastLogMessage();

        assertTrue(lastLogMessage.matches(".*"+ TODAY.toString() +".*"));
    }
}
