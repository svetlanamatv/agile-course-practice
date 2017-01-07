package ru.unn.agile.todoapp.infrastructure;

import ru.unn.agile.todoapp.viewmodel.TodoAppViewModelLoggingTest;
import ru.unn.agile.todoapp.viewmodel.TodoAppViewModel;

import java.io.IOException;

public class ViewModelWithRealLoggerTests extends TodoAppViewModelLoggingTest  {
    @Override
    public void setUp() {
        try {
            PlainTextLogger realLogger =
                    new PlainTextLogger("./todoApp_viewModel_logger_test.log");
            super.setViewModel(new TodoAppViewModel(realLogger));
        } catch (IOException e)  {
            System.out.println(e.getMessage());
        }
    }
}
