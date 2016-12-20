package ru.unn.agile.TemperatureConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.TemperatureConverter.model.TemperatureScale;

import java.util.List;

import static org.junit.Assert.*;

public class TemperatureConverterViewModelTest {
    private TemperatureConverterViewModel viewModel;

    public void setNewViewModel(final TemperatureConverterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new TemperatureConverterViewModel(new DummyLogger());
        }
    }

    @Test
    public void canCreateViewModelWithoutLogger() {
        TemperatureConverterViewModel viewModel = new TemperatureConverterViewModel();
        assertNotNull(viewModel);
    }
    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void msgWhenSetNullLogger() {
        DummyLogger nullLogger = null;
        viewModel = new TemperatureConverterViewModel(nullLogger);
    }
    @Test
    public void setDefaultValuesOfTemperatureFields() {
        assertEquals("", viewModel.getFirstValue());
        assertEquals("", viewModel.getSecondValue());
    }

    @Test
    public void canSetValueInFirstTemperatureValueField() {
        viewModel.setFirstValue("10");

        assertEquals("10", viewModel.getFirstValue());
    }

    @Test
    public void canSetValueInSecondTemperatureValueField() {
        viewModel.setSecondValue("af");

        assertEquals("af", viewModel.getSecondValue());
    }

    @Test
    public void canSetDefaultValuesOfTemperatureScalesFields() {
        assertEquals(TemperatureScale.CELSIUS, viewModel.getFirstScale());
        assertEquals(TemperatureScale.CELSIUS, viewModel.getSecondScale());
    }

    @Test
    public void canSetValuesFromComboBoxes() {
        viewModel.setFirstScale("KELVIN");
        viewModel.setSecondScale("KELVIN");

        assertEquals(TemperatureScale.KELVIN, viewModel.getFirstScale());
        assertEquals(TemperatureScale.KELVIN, viewModel.getSecondScale());
    }

    @Test
    public void canSetDefaultValueInTemperatureScaleFieldsWhenInputValueScaleIsIncorrect() {
        viewModel.setFirstScale("AD");
        viewModel.setSecondScale("1R");

        assertEquals(TemperatureScale.CELSIUS, viewModel.getFirstScale());
        assertEquals(TemperatureScale.CELSIUS, viewModel.getSecondScale());
    }

    @Test
    public void canCorrectlyConvertTemperatureValuesWhenSetFirstValueAndScalesAreEqual() {
        viewModel.setFirstScale("CELSIUS");
        viewModel.setSecondScale("CELSIUS");
        viewModel.setFirstValue("0");
        viewModel.convertFirstToSecondValue();

        assertEquals("0.0", viewModel.getSecondValue());
    }

    @Test
    public void canCorrectlyConvertTemperatureValuesWhenSetSecondValueAndScalesAreEqual() {
        viewModel.setFirstScale("CELSIUS");
        viewModel.setSecondScale("CELSIUS");
        viewModel.setSecondValue("0");
        viewModel.convertSecondToFirstValue();

        assertEquals("0.0", viewModel.getFirstValue());
    }

    @Test
    public void canCorrectlyConvertTemperatureValuesWhenSetFirstValueAndScalesAreDifferent() {
        viewModel.setFirstScale("CELSIUS");
        viewModel.setSecondScale("FAHRENHEIT");
        viewModel.setFirstValue("0");
        viewModel.convertFirstToSecondValue();

        assertEquals("32.0", viewModel.getSecondValue());
    }

    @Test
    public void canCorrectlyConvertTemperatureValuesWhenSetSecondValueAndScalesAreDifferent() {
        viewModel.setFirstScale("KELVIN");
        viewModel.setSecondScale("CELSIUS");
        viewModel.setFirstValue("0");
        viewModel.convertFirstToSecondValue();

        assertEquals("-273.15", viewModel.getSecondValue());
    }

    @Test
    public void setDefaultValuesOfWarningLabelField() {
        assertEquals("", viewModel.getWarningLabelText());
    }

    @Test
    public void canSetWarningMessageWhenInputIsIncorrect() {
        viewModel.setFirstValue("a");
        viewModel.convertFirstToSecondValue();

        assertEquals("We wrote incorrect value of temperature!", viewModel.getWarningLabelText());
    }

    @Test
    public void canSetWarningMessageWhenInputValueIsLowerThanAbsoluteZero() {
        viewModel.setSecondScale("KELVIN");
        viewModel.setFirstScale("FAHRENHEIT");
        viewModel.setFirstValue("-300");
        viewModel.convertFirstToSecondValue();

        assertEquals("We wrote incorrect value of temperature!", viewModel.getWarningLabelText());
    }

    @Test
    public void canSetEmptyStringInWarningLabelWhenInputEmpty() {
        viewModel.setFirstValue("");
        viewModel.convertFirstToSecondValue();

        assertEquals("", viewModel.getWarningLabelText());
    }

    @Test
    public void canSetEmptyStringInSecondValueWhenInputIsIncorrect() {
        viewModel.setFirstValue("a");
        viewModel.convertFirstToSecondValue();

        assertEquals("", viewModel.getSecondValue());
    }

    @Test
    public void canSetEmptyStringInSecondValueWhenInputValueIsLowerThanAbsoluteZero() {
        viewModel.setSecondScale("KELVIN");
        viewModel.setFirstScale("FAHRENHEIT");
        viewModel.setFirstValue("-300");
        viewModel.convertFirstToSecondValue();

        assertEquals("", viewModel.getSecondValue());
    }

    @Test
    public void canSetEmptyStringInWarningLabelWhenUserWroteCorrectInputValue() {
        viewModel.setSecondScale("KELVIN");
        viewModel.setFirstScale("FAHRENHEIT");
        viewModel.setFirstValue("as");
        viewModel.convertFirstToSecondValue();

        viewModel.setFirstValue("250");
        viewModel.convertFirstToSecondValue();

        assertEquals("", viewModel.getWarningLabelText());
    }

    @Test
    public void canConvertTemperatureAfterChangeSecondScale() {
        viewModel.setFirstScale("KELVIN");
        viewModel.setSecondScale("FAHRENHEIT");
        viewModel.setFirstValue("250");
        viewModel.convertFirstToSecondValue();

        viewModel.setSecondScale("CELSIUS");
        viewModel.convertFirstToSecondValue();

        assertEquals("-23.149999999999977", viewModel.getSecondValue());
    }

    @Test
    public void canConvertTemperatureAfterChangeFirstScale() {
        viewModel.setFirstScale("CELSIUS");
        viewModel.setSecondScale("FAHRENHEIT");
        viewModel.setSecondValue("50");
        viewModel.convertSecondToFirstValue();

        viewModel.setFirstScale("KELVIN");
        viewModel.convertSecondToFirstValue();

        assertEquals("283.15", viewModel.getFirstValue());
    }
    @Test
    public void logIsEmptyWhenStarted() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }
    @Test
    public void logContainsRightMsgWhenFirstScaleChanged() {
        viewModel.firstScaleChanged("CELSIUS", "FAHRENHEIT");
        String msg = viewModel.getLog().get(0);
        assertTrue(msg.matches(".*First Scale was changed to FAHRENHEIT"));
    }
    @Test
    public void logContainsRightMsgWhenSecondScaleChanged() {
        viewModel.secondScaleChanged("CELSIUS", "FAHRENHEIT");
        String msg = viewModel.getLog().get(0);
        assertTrue(msg.matches(".*Second Scale was changed to FAHRENHEIT"));
    }
    @Test
    public void logContainsRightMsgWhenFirstValueChanged() {
        viewModel.firstValueChanged("1", "2");
        String msg = viewModel.getLog().get(0);
        assertTrue(msg.matches(".*First Value was changed to 2"));
    }
    @Test
    public void logContainsRightMsgWhenSecondValueChanged() {
        viewModel.secondValueChanged("1", "2");
        String msg = viewModel.getLog().get(0);
        assertTrue(msg.matches(".*Second Value was changed to 2"));
    }
    @Test
    public void canWriteThreeLogs() {
        viewModel.secondScaleChanged("CELSIUS", "FAHRENHEIT");
        viewModel.secondScaleChanged("CELSIUS", "FAHRENHEIT");
        viewModel.secondScaleChanged("CELSIUS", "FAHRENHEIT");
        assertEquals(3, viewModel.getLog().size());
    }
    @Test
    public void logContainsRightMsgAfterConvertFirstToSecond() {
        viewModel.setFirstValue("50");
        viewModel.convertFirstToSecondValue();
        String msg = viewModel.getLog().get(1);
        assertTrue(msg.matches(".*" + viewModel.getFirstScale() + " to "
                + viewModel.getSecondScale() + " convert success"));
    }
    @Test
    public void logContainsRightMsgAfterConvertSecondToFirst() {
        viewModel.setSecondValue("50");
        viewModel.convertSecondToFirstValue();
        String msg = viewModel.getLog().get(1);
        assertTrue(msg.matches(".*" + viewModel.getSecondScale() + " to "
                + viewModel.getFirstScale() + " convert success"));
    }
    @Test
    public void noLogIfFirstValueNotChanged() {
        viewModel.firstValueChanged("1", "1");
        assertEquals(0, viewModel.getLog().size());
    }
    @Test
    public void noLogIfSecondValueNotChanged() {
        viewModel.secondValueChanged("1", "1");
        assertEquals(0, viewModel.getLog().size());
    }
    @Test
    public void noLogIfFirstScaleNotChanged() {
        viewModel.firstScaleChanged("FAHRENHEIT", "FAHRENHEIT");
        assertEquals(0, viewModel.getLog().size());
    }
    @Test
    public void noLogIfSecondScaleNotChanged() {
        viewModel.secondScaleChanged("FAHRENHEIT", "FAHRENHEIT");
        assertEquals(0, viewModel.getLog().size());
    }
    @Test
    public void noLogIfSetSameValue() {
        viewModel.setSecondValue("");
        assertEquals(0, viewModel.getLog().size());
    }
}
