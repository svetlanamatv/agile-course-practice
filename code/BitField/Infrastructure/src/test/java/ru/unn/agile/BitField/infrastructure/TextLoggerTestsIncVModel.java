package ru.unn.agile.BitField.infrastructure;

import ru.unn.agile.BitField.viewmodel.ViewModel;
import ru.unn.agile.BitField.viewmodel.ViewModelTests;

public class TextLoggerTestsIncVModel extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
