package ru.unn.agile.newtonroots.model;

import static java.lang.Double.NaN;

public class NewtonMethod {
    enum ResultStatus { RootSuccessfullyFound, NoRootInInterval,
        NonmonotonicFunctionOnInterval, InitialPointOutsideInterval, IncorrectIntervalBoundaries }
    private StoppingCriterion stoppingCriterion;
    private ResultStatus resultStatus;
    private double accuracyEps;
    private double derivativeStep;
    private int iterationsCounter;
    private double finalAccuracy;
    private static final double MONOTONIC_CHECK_STEP = 1e-5;

    public NewtonMethod(final double accuracy, final double derivativeComputeStep) {
        accuracyEps = accuracy;
        derivativeStep = derivativeComputeStep;
        stoppingCriterion = StoppingCriterion.FunctionModulus;
    }

    public static boolean isMonotonicFunctionOnInterval(final FunctionInterface func,
                                                  final double intervalStart,
                                                  final double intervalEnd) {
        double x = intervalStart;
        double xStep = MONOTONIC_CHECK_STEP;
        double val = func.compute(x + xStep), dif;
        double oldVal = func.compute(x);
        double oldDif = val - oldVal;
        x += xStep;

        while (x <= intervalEnd) {
            x += xStep;
            val = func.compute(x);
            dif = val - oldVal;
            if (Math.signum(dif) != Math.signum(oldDif)) {
                return false;
            }
            oldVal = val;
            oldDif = dif;
        }
        return true;
    }

    private boolean isMonotonicFunctionHasRoot(final FunctionInterface func,
                                               final double intervalStart,
                                               final double intervalEnd) {
        return func.compute(intervalStart) * func.compute(intervalEnd) <= 0;
    }

    public double findRoot(final FunctionInterface func, final double initialPoint,
                    final double intervalStart, final double intervalEnd) {
        iterationsCounter = 0;
        double x = initialPoint;
        double xPrev;
        double h = derivativeStep;

        if (intervalEnd <= intervalStart) {
            resultStatus = ResultStatus.IncorrectIntervalBoundaries;
            return NaN;
        }

        if (initialPoint < intervalStart || initialPoint > intervalEnd) {
            resultStatus = ResultStatus.InitialPointOutsideInterval;
            return NaN;
        }

        boolean isMonotonic = isMonotonicFunctionOnInterval(func, intervalStart, intervalEnd);
        if (!isMonotonic) {
            resultStatus = ResultStatus.NonmonotonicFunctionOnInterval;
            return NaN;
        }

        boolean isHasRoot = isMonotonicFunctionHasRoot(func, intervalStart, intervalEnd);
        if (!isHasRoot) {
            resultStatus = ResultStatus.NoRootInInterval;
            return NaN;
        }

        do {
            iterationsCounter++;
            xPrev = x;
            x = x - func.compute(x) / (func.compute(x + h) - func.compute(x)) * h;
            while (x < intervalStart || x > intervalEnd) {
                x = (x + xPrev) / 2;
            }
            finalAccuracy = stoppingCriterion.getCriterionValue(func, x, xPrev);
        } while (finalAccuracy >= accuracyEps);
        resultStatus = ResultStatus.RootSuccessfullyFound;
        return x;
    }

    public int getIterationsCounter()  {
        return iterationsCounter;
    }

    public double getFinalAccuracy()  {
        return finalAccuracy;
    }

    public void setStoppingCriterion(final StoppingCriterion newStoppingCriterion) {
        stoppingCriterion = newStoppingCriterion;
    }

    public StoppingCriterion getStoppingCriterion() {
        return stoppingCriterion;
    }

    public void setAccuracyEps(final double accuracy) throws Exception {
        if (accuracy > 0) {
            accuracyEps = accuracy;
        } else {
            throw new Exception("Accuracy is incorrect");
        }
    }

    public double getAccuracyEps() {
        return accuracyEps;
    }

    public void setDerivativeStep(final double derivativeComputeStep) throws Exception {
        if (derivativeComputeStep > 0) {
            derivativeStep = derivativeComputeStep;
        } else {
            throw new Exception("Derivative step is incorrect");
        }
    }

    public double getDerivativeStep() {
        return derivativeStep;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

}
