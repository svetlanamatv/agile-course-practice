package ru.unn.agile.TemperatureConverter.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.unn.agile.TemperatureConverter.model.TemperatureScale.*;

@RunWith(Parameterized.class)
public class TemperatureConverterTest {
    private static final double DELTA = 0.000001;

    @Parameterized.Parameters(name = "{index}: from {0} {2}  to {1} {3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0.0, 273.15, CELSIUS, KELVIN}, {100.0, 373.15, CELSIUS, KELVIN},
                {0.0, 32.0, CELSIUS, FAHRENHEIT}, {100.0, 212.0, CELSIUS, FAHRENHEIT},
                {0.0, 0.0, CELSIUS, NEWTON}, {100.0, 33.0, CELSIUS, NEWTON},
                {0.0, -273.15, KELVIN, CELSIUS}, {100.0, -173.15, KELVIN, CELSIUS},
                {0.0, -90.1395, KELVIN, NEWTON}, {100.0, -57.1395, KELVIN, NEWTON},
                {0.0, -459.67, KELVIN, FAHRENHEIT}, {100.0, -279.67, KELVIN, FAHRENHEIT},
                {0.0, 273.15, NEWTON, KELVIN}, {33.0, 373.15, NEWTON, KELVIN},
                {0.0, 32.0, NEWTON, FAHRENHEIT}, {11.0, 92.0, NEWTON, FAHRENHEIT},
                {0.0, 0.0, NEWTON, CELSIUS}, {33.0, 100.0, NEWTON, CELSIUS},
                {8.33, 260.0, FAHRENHEIT, KELVIN},
                {5.0, -15.0, FAHRENHEIT, CELSIUS},
                {2.0, -5.5, FAHRENHEIT, NEWTON},
        });
    }

    private final double source;
    private final double destination;
    private final TemperatureScale sourceScale;
    private final TemperatureScale destinationScale;

    public TemperatureConverterTest(final double source,
                                    final double destination,
                                    final TemperatureScale sourceScale,
                                    final TemperatureScale destinationScale) {
        this.source = source;
        this.destination = destination;
        this.sourceScale = sourceScale;
        this.destinationScale = destinationScale;
    }

    @Test
    public void convertTemperatureFromSourceScaleToDestinationScale() {
        double converted = TemperatureConverter.convert(source, sourceScale, destinationScale);
        assertEquals(destination, converted, DELTA);
    }
}

