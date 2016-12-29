package ru.unn.agile.todoapp.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import ru.unn.agile.todoapp.viewmodel.TaskViewModel;
import ru.unn.agile.todoapp.viewmodel.TodoAppViewModel;
import ru.unn.agile.todoapp.infrastructure.PlainTextLogger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoApp {
    static final String LOG_FILE_PATH = "./todoApp_log.log";
    @FXML
    private TodoAppViewModel viewModel;
    @FXML
    private TextField taskDescriptionTextField;
    @FXML
    private DatePicker taskDueDatePicker;
    @FXML
    private Button addTaskButton;
    @FXML
    private ListView<TaskViewModel> taskListView;
    @FXML
    private TextArea logTextArea;
    @FXML
    private void initialize() {
        try {
            viewModel.setLogger(new PlainTextLogger(LOG_FILE_PATH));
        }  catch (IOException e)  {
            System.out.println(e.getMessage());
        }
        taskDueDatePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(final LocalDate date) {
                return date.format(dateFormatter);
            }

            @Override
            public LocalDate fromString(final String dateString) {
                return LocalDate.parse(dateString, dateFormatter);
            }
        });

        StringPropertyChangeListener taskDesckStringListener = new StringPropertyChangeListener();
        final ChangeListener<Boolean> taskDescrFocusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                if (!oldValue && newValue) {
                    return;
                } else if (taskDesckStringListener.isChanged())  {
                    viewModel.onNewTaskDescriptionFocusChanged();
                    taskDesckStringListener.cache();
                }
            }
        };

        taskDescriptionTextField.textProperty().bindBidirectional(
                viewModel.newTaskDescriptionProperty());
        taskDescriptionTextField.textProperty().addListener(taskDesckStringListener);
        taskDescriptionTextField.focusedProperty().addListener(taskDescrFocusChangeListener);

        taskDueDatePicker.valueProperty().bindBidirectional(viewModel.newTaskDueDateProperty());
        taskDueDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(final ObservableValue<? extends LocalDate> observable,
                                final LocalDate oldValue,
                                final LocalDate newValue) {
                viewModel.onTaskDueDateChanged(oldValue, newValue);
            }
        });
        addTaskButton.setOnAction(value -> viewModel.pressAddNewTaskButton());

        taskListView.setItems(viewModel.getSortedTasksViewModels());
        taskListView.setCellFactory(taskListView -> new TaskListCell(viewModel));
        logTextArea.textProperty().bind(viewModel.logsStringProperty());
    }
}
