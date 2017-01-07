package ru.unn.agile.newtonroots.model;

import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

public class MathFunctionTests {

    private static final double EPSILON = 1e-15;

    @Test(expected = Exception.class)
    public void createFunctionFromInvalidExpression() throws Exception {
        AnalyticallyDefinedScalarFunction func = new AnalyticallyDefinedScalarFunction("-z");
    }

    @Test
    public void evaluateValidFunction() {
        try  {
            AnalyticallyDefinedScalarFunction func = new AnalyticallyDefinedScalarFunction("x^2");
            double x = 1.5;

            assertTrue(Math.abs(func.compute(x) - x * x) < EPSILON);
        } catch (Exception e)  {
            fail(e.getMessage());
        }
    }
}
