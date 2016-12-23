package ru.unn.agile.BitField.infrastructure;

import ru.unn.agile.BitField.viewmodel.ViewModel;
import ru.unn.agile.BitField.viewmodel.ViewModelTests;

public class TextLoggerTestsIncVModel extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger usedLogger =
                new TxtLogger("#3lab.log");
        super.setExternalViewModel(new ViewModel(usedLogger));
    }
}
