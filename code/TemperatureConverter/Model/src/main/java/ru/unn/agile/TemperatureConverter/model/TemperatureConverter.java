package ru.unn.agile.TemperatureConverter.model;

public final class TemperatureConverter {
    public static final double CELSIUS_IN_ABSOLUTE = -273.15;
    public static final double KELVIN_IN_ABSOLUTE = 0.0;
    public static final double NEWTON_IN_ABSOLUTE = -90.14;
    public static final double FAHRENHEIT_IN_ABSOLUTE = -459.67;
    public static double convert(final double degrees,
                                 final TemperatureScale sourceScale,
                                 final TemperatureScale destinationScale) {
        if (isLowerThanAbsoluteZero(degrees, sourceScale)) {
            String message = "Cannot convert temperature lower than absolute zero!";
            throw new IllegalArgumentException(message);
        }
        double source = (degrees - sourceScale.getCelsiusShift()) / sourceScale.getFactor();

        return source * destinationScale.getFactor() + destinationScale.getCelsiusShift();
    }

    private TemperatureConverter() {
    }

    private static boolean isLowerThanAbsoluteZero(final double degrees,
                                                   final TemperatureScale scale) {
        switch (scale) {
            case CELSIUS:
                if (degrees < CELSIUS_IN_ABSOLUTE) {
                    return true;
                }
                break;
            case KELVIN:
                if (degrees < KELVIN_IN_ABSOLUTE) {
                    return true;
                }
                break;
            case FAHRENHEIT:
                if (degrees < FAHRENHEIT_IN_ABSOLUTE) {
                    return true;
                }
            case NEWTON:
                if (degrees < NEWTON_IN_ABSOLUTE) {
                    return true;
                }
            default:
                return false;
        }

        return false;
    }
}

