package ru.unn.agile.newtonroots.model;

public enum StoppingCriterion {
    FunctionModulus("Function modulus",
            (func, x, xPrev) -> Math.abs(func.compute(x))),

    DifferenceBetweenApproximations("Diff. between approximations",
            (func, x, xPrev) -> Math.abs(x - xPrev));


    private final String description;
    private final StoppingCriterionCalculator calculator;

    StoppingCriterion(final String description, final StoppingCriterionCalculator calculator) {
        this.description = description;
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return description;
    }

    public double getCriterionValue(FunctionInterface func, double x, double xPrev) {
        return calculator.getCriterionValue(func, x, xPrev);
    }
}
