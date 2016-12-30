package ru.unn.agile.newtonroots.model;

import org.junit.Test;
import static java.lang.Double.isNaN;
import static org.junit.Assert.*;

public class NewtonRootsTests {
    private double intervalStart = 0;
    private double intervalEnd = 2;
    private double initialPoint = 1.5;
    private double eps = 1e-8;
    private double derivativeStep = 1e-3;
    private NewtonMethod newtonMethod = new NewtonMethod(eps, derivativeStep);

    private static ScalarFunction createFunction(final String expression)  {
        ScalarFunction function = null;
        try  {
            function = new AnalyticallyDefinedScalarFunction(expression);
        } catch (Exception e)  {
            fail(e.getMessage());
        }
        return function;
    }

    @Test
    public void findRootIfExistInIntervalWithFunctionModuleStopping() {
        newtonMethod.setStoppingCriterion(StoppingCriterion.FunctionModulus);
        ScalarFunction func = createFunction("x^2 - 1");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);

        assertTrue(Math.abs(func.compute(root)) < eps);
    }

    @Test
    public void findRootIfExistInIntervalWithDifferenceBetweenApproxStopping() {
        newtonMethod.setStoppingCriterion(StoppingCriterion.DifferenceBetweenApproximations);
        ScalarFunction func = createFunction("x^2 - 1");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);

        assertTrue(Math.abs(func.compute(root)) < eps);
    }

    @Test
    public void findRootOnTheLeftBorder() {
        ScalarFunction func = createFunction("x");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);

        assertTrue(Math.abs(func.compute(root)) < eps);
    }

    @Test
    public void findRootOnTheRightBorder() {
        ScalarFunction func = createFunction("2 - x");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);

        assertTrue(Math.abs(func.compute(root)) < eps);
    }

    @Test
    public void findRootIfNotExistInInterval() {
        ScalarFunction func = createFunction("x + 1");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);

        assertTrue(isNaN(root));
    }

    @Test
    public void findRootIfIncorrectIntervalBoundaries() {
        ScalarFunction func = createFunction("x + 1");

        double root = newtonMethod.findRoot(func, initialPoint, intervalEnd, intervalStart);

        assertEquals(newtonMethod.getResultStatus(),
                NewtonMethod.ResultStatus.IncorrectIntervalBoundaries);
    }

    @Test
    public void findRootIfInitialPointOutsideInterval() {
        ScalarFunction func = createFunction("x+1");

        double root = newtonMethod.findRoot(func, -10, intervalStart, intervalEnd);

        assertEquals(newtonMethod.getResultStatus(),
                NewtonMethod.ResultStatus.InitialPointOutsideInterval);
    }

    @Test
    public void findRootIfNonmonotonicFunction() {
        ScalarFunction func = createFunction("(x-1)*(x-1)");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);

        assertEquals(newtonMethod.getResultStatus(),
                NewtonMethod.ResultStatus.NonmonotonicFunctionOnInterval);
    }

    @Test
    public void setStoppingCriterionAsFunctionModule() {
        newtonMethod.setStoppingCriterion(StoppingCriterion.FunctionModulus);

        assertEquals(newtonMethod.getStoppingCriterion(), StoppingCriterion.FunctionModulus);
    }

    @Test
    public void setStoppingCriterionAsDifferenceBetweenApproximates() {
        newtonMethod.setStoppingCriterion(StoppingCriterion.DifferenceBetweenApproximations);

        assertEquals(newtonMethod.getStoppingCriterion(),
                StoppingCriterion.DifferenceBetweenApproximations);
    }

    @Test
    public void setCorrectAccuracyEps() throws Exception {
        newtonMethod.setAccuracyEps(1e-4);

        assertTrue(newtonMethod.getAccuracyEps() == 1e-4);
    }

    @Test(expected = Exception.class)
    public void setZeroAccuracyEps() throws Exception {
        newtonMethod.setAccuracyEps(0);
    }

    @Test(expected = Exception.class)
    public void setNegativeAccuracyEps() throws Exception {
        newtonMethod.setAccuracyEps(-1);
    }

    @Test
    public void setCorrectDerivativeStep() throws Exception {
        newtonMethod.setDerivativeStep(1e-2);

        assertTrue(newtonMethod.getDerivativeStep() == 1e-2);
    }

    @Test(expected = Exception.class)
    public void setZeroDerivativeStep() throws Exception {
        newtonMethod.setDerivativeStep(0);
    }

    @Test(expected = Exception.class)
    public void setNegativeDerivativeStep() throws Exception {
        newtonMethod.setDerivativeStep(-1e-2);
    }

    @Test
    public void iterationsCounterNonZeroAfterSolving() {
        ScalarFunction func = createFunction("x - 1");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);
        assertTrue(newtonMethod.getIterationsCount() > 0);
    }

    @Test
    public void achievedAccuracyLessThanInStopCriterion()  {
        ScalarFunction func = createFunction("x - 1");

        double root = newtonMethod.findRoot(func, initialPoint, intervalStart, intervalEnd);
        assertTrue(newtonMethod.getFinalAccuracy() < newtonMethod.getAccuracyEps());
    }
}
