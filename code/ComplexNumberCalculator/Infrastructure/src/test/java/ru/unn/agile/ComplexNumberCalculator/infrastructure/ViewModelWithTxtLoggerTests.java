package ru.unn.agile.ComplexNumberCalculator.infrastructure;

import ru.unn.agile.ComplexNumberCalculator.viewmodel.ComplexNumberCalculatorViewModel;
import ru.unn.agile.ComplexNumberCalculator.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ComplexNumberCalculatorViewModel(realLogger));
    }
}
