package unn.agile.SolvingQuadraticEquation.infrastructure;

import ru.unn.agile.SolvingQuadraticEquation.viewmodel.ViewModel;
import ru.unn.agile.SolvingQuadraticEquation.viewmodel.ViewModelTests;


public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        FileQuadraticEquationSolverLogger logger =
                new FileQuadraticEquationSolverLogger(
                        "./ViewModel_with_FileLogger_Tests-quadraticEquationSolver.log");
        super.setExteriorlViewModel(new ViewModel(logger));
    }
}
