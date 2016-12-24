package ru.unn.agile.queue.infrastructure;


import ru.unn.agile.queue.viewmodel.ViewModel;
import ru.unn.agile.queue.viewmodel.ViewModelTest;

import java.io.IOException;

public class ViewModelWithLoggerImplTest extends ViewModelTest {
    @Override
    public void initBeforeTest() {
        try {
            QueueLoggerImpl loggerImpl = new QueueLoggerImpl("./ViewModel_with_LoggerImpl_Test"
                    + ".log");
            super.setOuterViewModel(new ViewModel<String>(loggerImpl));
            super.setInitialData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
