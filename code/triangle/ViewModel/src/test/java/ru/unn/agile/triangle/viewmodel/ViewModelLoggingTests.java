package ru.unn.agile.triangle.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.fake.FakeLogger;

public class ViewModelLoggingTests {
    private ViewModel viewModel;
    private Logger logger;

    @Before
    public void setUp() {
        logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @Test(expected = NullPointerException.class)
    public void viewModelCreatingFailsIfLoggerIsNull() throws Exception {
        new ViewModel(null);
    }
}
