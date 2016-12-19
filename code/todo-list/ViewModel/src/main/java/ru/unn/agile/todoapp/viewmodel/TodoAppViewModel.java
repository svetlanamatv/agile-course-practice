package ru.unn.agile.todoapp.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import ru.unn.agile.todoapp.model.Task;
import ru.unn.agile.todoapp.model.TaskList;

import java.time.LocalDate;
import java.util.List;

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
    private ILogger logger;

    public TodoAppViewModel() {
        addNewTaskButtonDisable.bind(newTaskDescription.isEmpty());
    }

    public TodoAppViewModel(final ILogger logger) {
        if (logger == null) {
            throw new RuntimeException("Logger parameter can't be null");
        }
        this.logger = logger;
        addNewTaskButtonDisable.bind(newTaskDescription.isEmpty());
    }

    private static TaskViewModel wrapTaskInListCellViewModel(final Task task) {
        return new TaskViewModel(task);
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

    public void pressAddNewTaskButton() {
        Task newTask = new Task(newTaskDescription.getValue(), newTaskDueDate.getValue());
        tasks.add(newTask);
        tasksViewModels.add(wrapTaskInListCellViewModel(newTask));

        newTaskDescription.set("");
        newTaskDueDate.set(LocalDate.now());
    }

    public void pressDeleteButton(final TaskViewModel taskViewModel) {
        tasks.remove(taskViewModel.getTask());
        tasksViewModels.remove(taskViewModel);
    }

    public List<String> getLog()  {
        return logger.getLog();
    }

    public String getLastLogMessage()  {
        return logger.getLastLogMessage();
    }

    public void onNewTaskDescriptionChanged(final Boolean oldValue, final Boolean newValue)  {
        if (!oldValue && newValue) {
            return;
        }
        logger.addToLog("New task description changed to " + getNewTaskDescription());
    }

    public void onTaskDueDateChanged(final Boolean oldValue, final Boolean newValue)  {
        if (!oldValue && newValue) {
            return;
        }
        logger.addToLog("New task dua date changed to " + getNewTaskDueDate().toString());;
    }
}
