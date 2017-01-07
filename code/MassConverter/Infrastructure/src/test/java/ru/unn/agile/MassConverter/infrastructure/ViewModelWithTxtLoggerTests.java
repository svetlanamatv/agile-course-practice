package ru.unn.agile.MassConverter.infrastructure;

import ru.unn.agile.MassConverter.ViewModel.ViewModel;
import ru.unn.agile.MassConverter.ViewModel.ViewModelTest;

public class ViewModelWithTxtLoggerTests extends ViewModelTest {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests-lab3.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
