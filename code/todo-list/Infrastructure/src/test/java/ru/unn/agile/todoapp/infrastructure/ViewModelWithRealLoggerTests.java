package ru.unn.agile.todoapp.infrastructure;

import ru.unn.agile.todoapp.viewmodel.TodoAppViewModelLoggingTest;
import ru.unn.agile.todoapp.viewmodel.TodoAppViewModel;

public class ViewModelWithRealLoggerTests extends TodoAppViewModelLoggingTest  {
    @Override
    public void setUp() {
        PlainTextLogger realLogger =
                new PlainTextLogger("./todoApp_viewModel_logger_test.log");
        super.setViewModel(new TodoAppViewModel(realLogger));
    }
}
