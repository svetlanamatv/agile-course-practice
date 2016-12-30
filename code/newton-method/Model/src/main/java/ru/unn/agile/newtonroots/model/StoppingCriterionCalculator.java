package ru.unn.agile.newtonroots.model;

interface StoppingCriterionCalculator {
    double getCriterionValue(FunctionInterface func, double x, double xPrev);
}
