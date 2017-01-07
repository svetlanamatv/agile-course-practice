package ru.unn.agile.newtonroots.model;

interface StoppingCriterionCalculator {
    double getCriterionValue(ScalarFunction func, double x, double xPrev);
}
