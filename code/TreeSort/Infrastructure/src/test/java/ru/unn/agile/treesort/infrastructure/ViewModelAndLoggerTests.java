package ru.unn.agile.treesort.infrastructure;

import ru.unn.agile.treesort.viewmodel.ViewModel;
import ru.unn.agile.treesort.viewmodel.ViewModelTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ViewModelAndLoggerTests extends ViewModelTest {
    private TxtLogger txtLogger;

    @Override
    public void setUp() {
        super.setUp();
        txtLogger = new TxtLogger();
        setViewModel(new ViewModel(txtLogger));
    }

    @Override
    public void tearDown() {
        super.tearDown();
        File file = new File(txtLogger.getFileName());
        if (file.isFile()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        txtLogger = null;
    }
}
