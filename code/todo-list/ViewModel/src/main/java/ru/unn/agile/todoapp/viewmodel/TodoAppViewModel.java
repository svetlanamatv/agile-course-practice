package ru.unn.agile.todoapp.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import ru.unn.agile.todoapp.model.Task;
import ru.unn.agile.todoapp.model.TaskList;

import java.time.LocalDate;
import java.util.*;

public class TodoAppViewModel {
    private final TaskList tasks = new TaskList();
    private final StringProperty newTaskDescription =
            new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> newTaskDueDate =
            new SimpleObjectProperty<>(LocalDate.now());
    private final BooleanProperty addNewTaskButtonDisable =
            new SimpleBooleanProperty(true);
    private final ObservableList<TaskViewModel> tasksViewModels =
            FXCollections.observableArrayList(TaskViewModel::extractor);
    private final SortedList<TaskViewModel> sortedTasksViewModels =
            new SortedList<>(tasksViewModels, TaskViewModel::comparator);
    private final StringProperty logsString = new SimpleStringProperty();
    private ILogger logger;

    public TodoAppViewModel() {
        addNewTaskButtonDisable.bind(newTaskDescription.isEmpty());
    }

    public TodoAppViewModel(final ILogger logger) {
        setLogger(logger);
        addNewTaskButtonDisable.bind(newTaskDescription.isEmpty());
    }

    private TaskViewModel wrapTaskInListCellViewModel(final Task task) {
        return new TaskViewModel(task, logger);
    }

    public final void setLogger(final ILogger logger)  {
        if (logger == null) {
            throw new RuntimeException("Logger parameter can't be null");
        }
        this.logger = logger;
        this.logger.setOnLogUpdateAction(() -> this.updateLogsString());
    }

    public ObjectProperty<LocalDate> newTaskDueDateProperty() {
        return newTaskDueDate;
    }

    public LocalDate getNewTaskDueDate() {
        return newTaskDueDate.get();
    }

    public void setNewTaskDueDate(final LocalDate dueDate) {
        newTaskDueDate.set(dueDate);
    }

    public StringProperty newTaskDescriptionProperty() {
        return newTaskDescription;
    }

    public String getNewTaskDescription() {
        return newTaskDescription.get();
    }

    public void setNewTaskDescription(final String description) {
        newTaskDescription.set(description);
    }

    public BooleanProperty addNewTaskButtonDisableProperty() {
        return addNewTaskButtonDisable;
    }

    public boolean getAddNewTaskButtonDisable() {
        return addNewTaskButtonDisable.get();
    }

    public SortedList<TaskViewModel> getSortedTasksViewModels() {
        return sortedTasksViewModels;
    }

    List<TaskViewModel> getTasksViewModels() {
        return tasksViewModels;
    }

    public final String getLogsString() {
        return logsString.get();
    }

    public StringProperty logsStringProperty()  {
        return logsString;
    }

    public void pressAddNewTaskButton() {
        Task newTask = new Task(newTaskDescription.getValue(), newTaskDueDate.getValue());
        tasks.add(newTask);
        tasksViewModels.add(wrapTaskInListCellViewModel(newTask));

        newTaskDescription.set("");
        newTaskDueDate.set(LocalDate.now());
        logger.addToLog(LogMessages.NEW_TASK_PRESSED);
    }

    public void pressDeleteButton(final TaskViewModel taskViewModel) {
        tasks.remove(taskViewModel.getTask());
        tasksViewModels.remove(taskViewModel);
        logger.addToLog(LogMessages.TASK_DELETED + taskViewModel.getTask().getDescription());
    }

    public List<String> getLog()  {
        return logger.getLog();
    }

    public String getLastLogMessage()  {
        return logger.getLastLogMessage();
    }

    public void onNewTaskDescriptionFocusChanged()  {
        logger.addToLog(LogMessages.TASK_DESCRIPTION_CHANGED + getNewTaskDescription());
    }

    public void onTaskDueDateChanged(final LocalDate oldValue, final LocalDate newValue)  {
        if (oldValue == null || oldValue.equals(newValue)) {
            return;
        }
        logger.addToLog(LogMessages.TASK_DUE_DATE_CHANGED + getNewTaskDueDate().toString());
    }

    private void updateLogsString()  {
        List<String> log = logger.getLog();
        String newLogString = new String();
        ListIterator<String> logIterator = log.listIterator(log.size());
        while (logIterator.hasPrevious()) {
            newLogString += logIterator.previous() + "\n";
        }
        logsString.set(newLogString);
    }
}

final class LogMessages {
    public static final String NEW_TASK_PRESSED = "New task button was pressed.";
    public static final String TASK_DESCRIPTION_CHANGED = "Task description was changed to: ";
    public static final String TASK_DUE_DATE_CHANGED = "New task dua date changed to ";
    public static final String TASK_FINISHED = "Task is done: ";
    public static final String TASK_DELETED = "Task deleted: ";

    private LogMessages() { }
}

