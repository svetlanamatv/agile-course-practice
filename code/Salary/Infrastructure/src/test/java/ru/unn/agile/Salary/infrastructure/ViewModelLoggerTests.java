package ru.unn.agile.Salary.infrastructure;

import ru.unn.agile.Salary.viewmodel.SalaryViewModel;
import ru.unn.agile.Salary.viewmodel.SalaryViewModelTest;

public class ViewModelLoggerTests extends SalaryViewModelTest {
    @Override
    public void setUp() {
        FileLogger realLogger =
                new FileLogger("logViewModel.log");
        super.setViewModel(new SalaryViewModel(realLogger));
    }


}
