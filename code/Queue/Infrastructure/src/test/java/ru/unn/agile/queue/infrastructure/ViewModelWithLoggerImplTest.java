package ru.unn.agile.queue.infrastructure;


import ru.unn.agile.queue.viewmodel.ViewModel;
import ru.unn.agile.queue.viewmodel.ViewModelTest;

public class ViewModelWithLoggerImplTest extends ViewModelTest {
    @Override
    public void beforeTest() {
        QueueLoggerImpl loggerImpl =
                new QueueLoggerImpl("./ViewModel_with_LoggerImpl_Test.log");
        super.setOuterViewModel(new ViewModel<String>(loggerImpl));
        super.setInitialData();
    }
}
