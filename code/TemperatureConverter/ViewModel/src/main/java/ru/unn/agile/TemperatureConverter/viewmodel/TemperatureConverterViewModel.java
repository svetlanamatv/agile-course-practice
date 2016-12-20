package ru.unn.agile.TemperatureConverter.viewmodel;

import ru.unn.agile.TemperatureConverter.model.TemperatureConverter;
import ru.unn.agile.TemperatureConverter.model.TemperatureScale;

import java.util.List;

public class TemperatureConverterViewModel {
    private static String emptyString = "";
    private String firstValue;
    private String secondValue;
    private TemperatureScale firstScale;
    private TemperatureScale secondScale;
    private String warningLabelText;

    private ILogger logger;

    private void init() {
        firstValue = "";
        secondValue = "";
        warningLabelText = "";
        firstScale = TemperatureScale.CELSIUS;
        secondScale = TemperatureScale.CELSIUS;
    }
    public TemperatureConverterViewModel() {
        init();
    }

    public TemperatureConverterViewModel(final ILogger logger) {
        init();
        setLogger(logger);
    }

    private void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Error! Logger can't be null!");
        }
        this.logger = logger;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public String getFirstValue() {
        return firstValue;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setFirstValue(final String firstValue) {
        if (firstValue.equals(this.firstValue)) {
            return;
        }
        firstValueChanged(this.firstValue, firstValue);
        this.firstValue = firstValue;
    }

    public void setSecondValue(final String secondValue) {
        if (secondValue.equals(this.secondValue)) {
            return;
        }
        secondValueChanged(this.secondValue, secondValue);
        this.secondValue = secondValue;
    }

    public void convertFirstToSecondValue() {
        secondValue = convertValues(firstValue, firstScale, secondScale);
    }

    public void convertSecondToFirstValue() {
        firstValue = convertValues(secondValue, secondScale, firstScale);
    }

    private String convertValues(final String value,
                                 final TemperatureScale firstScale,
                                 final TemperatureScale secondScale) {
        if (value.equals(emptyString)) {
            warningLabelText = "";
            return "";
        }

        try {
            double dFirstValue = Double.parseDouble(value);
            double dSecondValue =
                    TemperatureConverter.convert(dFirstValue, firstScale, secondScale);
            warningLabelText = "";
            logger.log(firstScale + " to " + secondScale + " convert success");
            return Double.toString(dSecondValue);
        } catch (Exception e) {
            warningLabelText = "We wrote incorrect value of temperature!";
        }

        return "";
    }

    public TemperatureScale getFirstScale() {
        return firstScale;
    }

    public TemperatureScale getSecondScale() {
        return secondScale;
    }

    public void setFirstScale(final String firstScale) {
        if (firstScale.equals(this.firstScale.toString())) {
            return;
        }
        firstScaleChanged(this.firstScale.toString(), firstScale);
        this.firstScale = setScaleValue(firstScale);
    }

    public void setSecondScale(final String secondScale) {

        if (secondScale.equals(this.secondScale.toString())) {
            return;
        }
        secondScaleChanged(this.secondScale.toString(), secondScale);
        this.secondScale = setScaleValue(secondScale);
    }


    private TemperatureScale setScaleValue(final String value) {
        try {
            return TemperatureScale.valueOf(value);
        } catch (Exception e) {
        }

        return TemperatureScale.CELSIUS;
    }

    public String getWarningLabelText() {
        return warningLabelText;
    }

    public void firstScaleChanged(final String oldScale,
                                  final String newScale) {
        if (oldScale.equals(newScale)) {
            return;
        }
        logger.log("First Scale was changed to " + newScale);
    }
    public void secondScaleChanged(final String oldScale,
                                   final String newScale) {
        if (oldScale.equals(newScale)) {
            return;
        }
        logger.log("Second Scale was changed to " + newScale);
   }
    public void firstValueChanged(final String oldValue,
                                  final String newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        logger.log("First Value was changed to " + newValue);
    }
    public void secondValueChanged(final String oldValue,
                                   final String newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        logger.log("Second Value was changed to " + newValue);
    }
}
