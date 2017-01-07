package ru.unn.agile.todoapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private final String description;
    private final LocalDate dueDate;
    private boolean isDoneFlag = false;

    public Task(final String description, final LocalDate dueDate) {
        Objects.requireNonNull(description, "description is not able to be null");
        Objects.requireNonNull(dueDate, "dueDate is not able to be null");
        if (description.trim().length() == 0) {
            String message = "description is not able to be whitespaces string";
            throw new IllegalArgumentException(message);
        }
        this.description = description;
        this.dueDate = dueDate;
    }

    public final String getDescription() {
        return description;
    }

    public final LocalDate getDueDate() {
        return dueDate;
    }

    public final boolean isDone() {
        return isDoneFlag;
    }

    public void markAsDone() {
        isDoneFlag = true;
    }
}
