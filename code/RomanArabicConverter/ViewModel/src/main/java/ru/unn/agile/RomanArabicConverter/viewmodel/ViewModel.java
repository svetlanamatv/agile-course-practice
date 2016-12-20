package ru.unn.agile.RomanArabicConverter.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.RomanArabicConverter.Model.RomanNumberValidator;
import ru.unn.agile.RomanArabicConverter.Model.ArabicToRomanNumberConverter;
import ru.unn.agile.RomanArabicConverter.Model.RomanToArabicNumberConverter;

import static ru.unn.agile.RomanArabicConverter.Model.MapData.MAX_NUMBER;

public class ViewModel {

    private final RomanNumberValidator romanValidator = new RomanNumberValidator();
    private final BooleanProperty convertButtonDisable = new SimpleBooleanProperty(true);
    private final StringProperty romanNumberValue = new SimpleStringProperty("");
    private final StringProperty arabicNumberValue = new SimpleStringProperty("");
    private final BooleanProperty rbArabToRomChooseValue = new SimpleBooleanProperty(true);
    private final BooleanProperty rbRomToArabChooseValue = new SimpleBooleanProperty(false);
    private final BooleanProperty tfArabDisableValue = new SimpleBooleanProperty(false);
    private final BooleanProperty tfRomDisableValue = new SimpleBooleanProperty(true);
    private final StringProperty statusValue = new SimpleStringProperty(Status.WAITING.toString());


    public ViewModel() {
        romanNumberValue.addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                      final String oldValue, final String newValue) {
               boolean isValid = validateRomanNumber(newValue);
               setConverterBtnDisableProperty(!isValid);
               statusValue.set(getInputStatus().toString());
            }
        });
        arabicNumberValue.addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                      final String oldValue, final String newValue) {
                boolean isValid = validateArabicNumber(newValue);
                setConverterBtnDisableProperty(!isValid);
                statusValue.set(getInputStatus().toString());
            }
        });
        rbArabToRomChooseValue.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                      final Boolean oldValue, final Boolean newValue) {
                setTFArabDisableProperty(!newValue);
                setTFRomDisableProperty(newValue);
                statusValue.set(getInputStatus().toString());
            }
        });
        rbRomToArabChooseValue.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                      final Boolean oldValue, final Boolean newValue) {
                setTFRomDisableProperty(!newValue);
                setTFArabDisableProperty(newValue);
                statusValue.set(getInputStatus().toString());
            }
        });
    }

    private void setTFRomDisableProperty(final Boolean value) {
        tfRomDisableValue.set(value);
    }

    private void setTFArabDisableProperty(final Boolean value) {
        tfArabDisableValue.set(value);
    }

    public BooleanProperty getConverterBtnDisableProperty() {
        return convertButtonDisable;
    }

    public void setConverterBtnDisableProperty(final boolean value) {
        convertButtonDisable.set(value);
    }

    public boolean validateRomanNumber(final String romanNumber) {
        try {
            romanValidator.validate(romanNumber);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public void setRomanNumberProperty(final String value) {
        romanNumberValue.set(value);
    }

    public final StringProperty getRomanNumberProperty() {
        return romanNumberValue;
    }

    public final StringProperty getArabicNumberProperty() {
        return arabicNumberValue;
    }

    public void setArabicNumberProperty(final String value) {
        arabicNumberValue.set(value);
    }

    public final StringProperty getArabicNumberValue() {
        return arabicNumberValue;
    }

    public void setArabicNumberValue(final String value) {
        arabicNumberValue.set(value);
    }

    public boolean validateArabicNumber(final String arabicNumber) {
        Integer aramicNum = 0;
        try {
            aramicNum = Integer.parseInt(arabicNumber);
        } catch (Exception ex) {
            return false;
        }
        return !((aramicNum < 0) || (aramicNum > MAX_NUMBER));
    }

    public final BooleanProperty getRBArabToRomChooseProperty() {
        return rbArabToRomChooseValue;
    }

    public void setRBArabToRomChooseProperty(final boolean value) {
        rbArabToRomChooseValue.set(value);
        rbRomToArabChooseValue.set(!value);
    }

    public final BooleanProperty getRBRomToArabChooseProperty() {
        return rbRomToArabChooseValue;
    }

    public void setRBRomToArabChooseProperty(final boolean value) {
        rbRomToArabChooseValue.set(value);
        rbArabToRomChooseValue.set(!value);
    }

    public final BooleanProperty getTFRomDisableProperty() {
        return tfRomDisableValue;
    }

    public final BooleanProperty getTFArabDisableProperty() {
        return tfArabDisableValue;
    }

    public final StringProperty getStatusProperty() {
        return statusValue;
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;

        if (rbArabToRomChooseValue.get()) {
            if (arabicNumberValue.get().isEmpty()) {
                inputStatus = Status.WAITING;
            } else if (validateArabicNumber(arabicNumberValue.get())) {
                inputStatus = Status.READY;
            } else {
                inputStatus = Status.BAD_FORMAT;
            }
        } else {
            if (romanNumberValue.get().isEmpty()) {
                inputStatus = Status.WAITING;
            } else {
                if (validateRomanNumber(romanNumberValue.get())) {
                    inputStatus = Status.READY;
                } else {
                    inputStatus = Status.BAD_FORMAT;
                }
            }
        }

        return inputStatus;
    }

    public void convertNumber() {
        if (convertButtonDisable.get()) {
            return;
        }

        if (rbArabToRomChooseValue.get()) {
            ArabicToRomanNumberConverter arabRomConverter
                    = new ArabicToRomanNumberConverter();
            romanNumberValue.set(arabRomConverter
                    .convert(Integer.parseInt(arabicNumberValue.get())));
        } else {
            RomanToArabicNumberConverter romArabConverter
                    = new RomanToArabicNumberConverter();
            arabicNumberValue.set(String
                    .valueOf(romArabConverter.convert(romanNumberValue.get())));
        }

        statusValue.set(Status.SUCCESS.toString());
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Convert' or 'Enter'"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
