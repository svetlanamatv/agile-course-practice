package ru.unn.agile.TemperatureConverter.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static ru.unn.agile.TemperatureConverter.model.TemperatureScale.*;

@RunWith(Parameterized.class)
public class TemperatureConverterExceptionTest {
    private static final double DELTA = 0.000001;
    public static final double CELSIUS_IN_ABSOLUTE = -273.15;
    public static final double KELVIN_IN_ABSOLUTE = 0.0;
    public static final double NEWTON_IN_ABSOLUTE = -90.14;
    public static final double FAHRENHEIT_IN_ABSOLUTE = -459.67;

    @Parameterized.Parameters(name = "{index}: from {0} {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {CELSIUS_IN_ABSOLUTE - DELTA, CELSIUS},
                {KELVIN_IN_ABSOLUTE - DELTA, KELVIN},
                {NEWTON_IN_ABSOLUTE - DELTA, NEWTON},
                {FAHRENHEIT_IN_ABSOLUTE - DELTA, FAHRENHEIT},
        });
    }

    private final double source;
    private final TemperatureScale sourceScale;

    public TemperatureConverterExceptionTest(final double source,
                                             final TemperatureScale sourceScale) {
        this.source = source;
        this.sourceScale = sourceScale;
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryConvertingTemperatureLowerThanAbsoluteZero() {
        TemperatureConverter.convert(source, sourceScale, KELVIN);
    }
}
