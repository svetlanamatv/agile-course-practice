package ru.unn.agile.Integration.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntegrationMethodTest {

    @Test
    public void isIncorrectFunctionNumber() {

        try
        {
            IntegrationMethod integration = new IntegrationMethod(100,0,1);
            integration.Rectangle(0);
        }
        catch(Exception e)
        {
            final String msg = "Incorrect function number";
            assertEquals(msg, e.getMessage());
        }
    }

    @Test
    public void isSolutionsEqualFunctionsEqual() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);

        double solve1 = integration.Rectangle(1);
        double solve2 = integration.Rectangle(1);

        assertEquals(solve1, solve2, 0.0000001);
    }

    @Test
    public void isSolutionsDifferentFunctionsNotEqual() {
        IntegrationMethod integration1 = new IntegrationMethod(100,0,1);
        IntegrationMethod integration2 = new IntegrationMethod(100,0,1);

        double solve1 = integration1.Rectangle(1);
        double solve2 = integration2.Rectangle(2);

        assertNotEquals(solve1, solve2);
    }

    @Test
    public void isSolutionsDifferentMethodsEqual() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);

        double solve1 = integration.Rectangle(2);
        double solve2 = integration.Trapezoid(2);

        assertEquals(solve1, solve2, 0.0001);
    }

    @Test
    public void isAppropriateIntegrationErrorRectangleMethodFunction1() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4;
        double delta = 0.00001;
        assertEquals(integration.Rectangle(1), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorRectangleMethodFunction2() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4 + Math.sin(10)/10;
        double delta = 0.0001;
        assertEquals(integration.Rectangle(2), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorRectangleMethodFunction3() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4 + Math.sin(100)/100;
        double delta = 0.001;
        assertEquals(integration.Rectangle(3), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorTrapezoidMethodFunction1() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4;
        double delta = 0.00001;
        assertEquals(integration.Trapezoid(1), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorTrapezoidMethodFunction2() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4 + Math.sin(10)/10;
        double delta = 0.0001;
        assertEquals(integration.Trapezoid(2), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorTrapezoidMethodFunction3() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4 + Math.sin(100)/100;
        double delta = 0.001;
        assertEquals(integration.Trapezoid(3), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorSimpsonMethodFunction1() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4;
        double delta = 0.000001;
        assertEquals(integration.Simpson(1), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorSimpsonMethodFunction2() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4 + Math.sin(10)/10;
        double delta = 0.00001;
        assertEquals(integration.Simpson(2), exactSolution, delta);
    }

    @Test
    public void isAppropriateIntegrationErrorSimpsonMethodFunction3() {
        IntegrationMethod integration = new IntegrationMethod(100,0,1);
        double exactSolution = Math.PI / 4 + Math.sin(100)/100;
        double delta = 0.0001;
        assertEquals(integration.Simpson(3), exactSolution, delta);
    }
}
