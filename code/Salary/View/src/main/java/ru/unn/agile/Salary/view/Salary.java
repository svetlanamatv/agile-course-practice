package ru.unn.agile.Salary.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.Salary.infrastructure.FileLogger;
import ru.unn.agile.Salary.viewmodel.SalaryViewModel;

public class Salary {
    @FXML
    private SalaryViewModel viewModel;
    @FXML
    private TextField baseSalary;
    @FXML
    private TextField hoursPerWeek;
    @FXML
    private TextField extraHours;
    @FXML
    private TextField leaveDays;
    @FXML
    private TextArea monthlySalary;
    @FXML
    private Button calculateButton;
    @FXML
    private TextArea log;

    @FXML
    void initialize() {
        viewModel.setLogger(new FileLogger("logFile.log"));
        baseSalary.textProperty().bindBidirectional(viewModel.baseSalaryProperty());
        hoursPerWeek.textProperty().bindBidirectional(viewModel.hoursPerWeekProperty());
        extraHours.textProperty().bindBidirectional(viewModel.extraHoursProperty());
        leaveDays.textProperty().bindBidirectional(viewModel.leaveDaysProperty());
        monthlySalary.textProperty().bindBidirectional(viewModel.salaryProperty());
        monthlySalary.setEditable(false);
        log.textProperty().bindBidirectional(viewModel.getLogsProperty());
        log.setEditable(false);

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculateSalary();
            }
        });
    }
}
