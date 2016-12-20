package ru.unn.agile.TemperatureConverter.model;

public enum TemperatureScale {
    CELSIUS(1.0, 0.0),
    KELVIN(1.0, 273.15),
    FAHRENHEIT(1.8, 32.0),
    NEWTON(0.33, 0.0);

    private final double factor;
    private final double celsiusShift;

    TemperatureScale(final double factor, final double celsiusShift) {
        this.factor = factor;
        this.celsiusShift = celsiusShift;
    }

    public double getFactor() {
        return factor;
    }

    public double getCelsiusShift() {
        return celsiusShift;
    }
}
