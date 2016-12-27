package ru.unn.agile.todoapp.viewmodel;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ru.unn.agile.todoapp.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskViewModel {
    static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Task task;
    private BooleanProperty doneCheckboxChecked;
    private BooleanProperty doneCheckboxDisable;
    private ILogger logger;

    private void initialize(final Task task)  {
        this.task = task;
        doneCheckboxChecked = new SimpleBooleanProperty(task.isDone());
        doneCheckboxDisable = new SimpleBooleanProperty(task.isDone());
    }

    public TaskViewModel(final Task task) {
        initialize(task);
    }

    public TaskViewModel(final Task task, final ILogger logger) {
        if (logger == null) {
            throw new RuntimeException("Logger parameter can't be null");
        }
        this.logger = logger;
        initialize(task);
    }

    public static Observable[] extractor(final TaskViewModel viewModel) {
        return new Observable[]{
                viewModel.doneCheckboxCheckedProperty(),
                viewModel.doneCheckboxDisableProperty()
        };
    }

    public static int comparator(final TaskViewModel tvm1, final TaskViewModel tvm2) {
        LocalDate dueDate1 = tvm1.getTask().getDueDate();
        LocalDate dueDate2 = tvm2.getTask().getDueDate();
        boolean taskDone1 = tvm1.getTask().isDone();
        boolean taskDone2 = tvm2.getTask().isDone();
        int dueDateComparison = dueDate1.compareTo(dueDate2);

        if (taskDone1 && taskDone2) {
            return -dueDateComparison;
        } else if (taskDone1 && !taskDone2) {
            return 1;
        } else if (!taskDone1 && taskDone2) {
            return -1;
        } else {
            return dueDateComparison;
        }
    }

    public String getDescription() {
        return task.getDescription();
    }

    public String getDueDateString() {
        return task.getDueDate().format(DATE_FORMATTER);
    }

    public Task getTask() {
        return task;
    }

    public BooleanProperty doneCheckboxCheckedProperty() {
        return doneCheckboxChecked;
    }

    public BooleanProperty doneCheckboxDisableProperty() {
        return doneCheckboxDisable;
    }

    public boolean getDoneCheckboxChecked() {
        return doneCheckboxChecked.get();
    }

    public boolean getDoneCheckboxDisable() {
        return doneCheckboxDisable.get();
    }

    public void clickIsDoneCheckBox() {
        task.markAsDone();
        doneCheckboxChecked.set(true);
        doneCheckboxDisable.set(true);
        logger.addToLog(LogMessages.TASK_FINISHED + task.getDescription());
    }
}
