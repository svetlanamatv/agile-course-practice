package ru.unn.agile.Salary.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.Salary.Model.Salary;
import java.security.InvalidParameterException;
import java.util.List;

enum Status {
    WAITING("Not Enough Input Data"),
    INVALID_PARAMS("Invalid params"),
    ERR_NONE("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

public class SalaryViewModel {
    private final StringProperty baseSalary = new SimpleStringProperty();
    private final StringProperty hoursPerWeek = new SimpleStringProperty();
    private final StringProperty extraHours = new SimpleStringProperty();
    private final StringProperty leaveDays = new SimpleStringProperty();
    private final StringProperty salary = new SimpleStringProperty();

    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private ILogger logger;

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger params are null");
        }
        this.logger = logger;
    }

    private void setDefaultValues() {
        baseSalary.set("");
        hoursPerWeek.set("");
        extraHours.set("");
        leaveDays.set("");
        salary.set("");
        logs.set("");

        status.set(Status.WAITING.toString());
    }

    public SalaryViewModel(final ILogger logger) {
        setLogger(logger);
        setDefaultValues();
    }

    public SalaryViewModel() {
        setDefaultValues();
    }

    public void calculateSalary() {
        StringBuilder message = new StringBuilder("Calculating with data: \n");
        message.append(" Base salary: ").append(baseSalaryProperty().get()).append("\n");
        message.append(" Hours per week: ").append(hoursPerWeekProperty().get()).append("\n");
        message.append(" Extra hours: ").append(extraHoursProperty().get()).append("\n");
        message.append(" Leave days: ").append(leaveDaysProperty().get()).append(" \n");

        if (baseSalaryProperty().get() == "" || hoursPerWeekProperty().get() == ""
            || extraHoursProperty().get() == "" || leaveDaysProperty().get() == "") {
            status.set(Status.WAITING.toString());
            message.append(statusProperty().get()).append("\n");

        } else {
            try {
                Salary outSalary = new Salary(Double.parseDouble(baseSalaryProperty().get()),
                        Integer.parseInt(hoursPerWeekProperty().get()),
                        Integer.parseInt(extraHoursProperty().get()),
                        Integer.parseInt(leaveDaysProperty().get()));
                salary.set(String.valueOf(outSalary.countSalary()));
                status.set(Status.ERR_NONE.toString());
                message.append(statusProperty().get()).append("\n");
            }  catch (InvalidParameterException | NumberFormatException e) {
                status.set(Status.INVALID_PARAMS.toString());
                salary.set("");
                message.append(statusProperty().get()).append("\n");
            }
        }
        logger.log(message.toString());
        updateLogs();
    }

    public StringProperty baseSalaryProperty() {
        return baseSalary;
    }

    public StringProperty hoursPerWeekProperty() {
        return hoursPerWeek;
    }

    public StringProperty extraHoursProperty() {
        return extraHours;
    }

    public StringProperty leaveDaysProperty() {
        return leaveDays;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty salaryProperty() {
        return salary;
    }

    public StringProperty getLogsProperty() {
        return logs;
    }

    private void updateLogs() {
        List<String> allLogMessages = logger.getLog();
        String logMessage = new String();
        for (String log : allLogMessages) {
            logMessage += log + "\n";
        }
        logs.set(logMessage);
    }

    public final List<String> getLogger() {
        return logger.getLog();
    }
}

