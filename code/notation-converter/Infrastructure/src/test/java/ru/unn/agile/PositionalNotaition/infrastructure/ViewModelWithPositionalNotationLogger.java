package ru.unn.agile.PositionalNotaition.infrastructure;
import ru.unn.agile.PositioanalNotation.infrastructure.PositionalNotationLogger;
import ru.unn.agile.PositionalNotation.viewmodel.ViewModel;
import ru.unn.agile.PositionalNotation.viewmodel.ViewModelTests;

public class ViewModelWithPositionalNotationLogger extends ViewModelTests {
    @Override
    public void setUp() {
        PositionalNotationLogger realLogger =
                new PositionalNotationLogger("./ViewModel_with_PositionalNotationLogger.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
