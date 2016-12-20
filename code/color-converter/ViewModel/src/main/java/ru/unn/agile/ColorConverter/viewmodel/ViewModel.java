package ru.unn.agile.ColorConverter.viewmodel;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.ColorConverter.model.ColorSpaces;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.ColorConverter.model.Converter;

import java.util.ArrayList;
import java.util.List;

import static ru.unn.agile.ColorConverter.model.CheckParameters.*;


public class ViewModel {

    private final StringProperty firstValue = new SimpleStringProperty();
    private final StringProperty secondValue = new SimpleStringProperty();
    private final StringProperty thirdValue = new SimpleStringProperty();
    private final StringProperty firstValueResult = new SimpleStringProperty();
    private final StringProperty secondValueResult = new SimpleStringProperty();
    private final StringProperty thirdValueResult = new SimpleStringProperty();
    private final StringProperty statusMessage = new SimpleStringProperty();
    private final BooleanProperty convertingDisabled = new SimpleBooleanProperty();
    private final ObjectProperty<ColorSpaces> fromColorSpace = new SimpleObjectProperty<>();
    private final ObjectProperty<ColorSpaces> toColorSpace = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<ColorSpaces>> colorSpaces =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(ColorSpaces.values()));
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    public ViewModel() {
        firstValue.set("");
        secondValue.set("");
        thirdValue.set("");
        firstValueResult.set("");
        secondValueResult.set("");
        thirdValueResult.set("");
        fromColorSpace.set(ColorSpaces.LAB);
        toColorSpace.set(ColorSpaces.HSV);
        statusMessage.set(Status.WAITING.toString());
        BooleanBinding couldConvert = new BooleanBinding() {
            {
                super.bind(firstValue, secondValue, thirdValue, fromColorSpace);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        convertingDisabled.bind(couldConvert.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(firstValue);
            add(secondValue);
            add(thirdValue);
        }};

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (firstValue.get().isEmpty() || secondValue.get().isEmpty()
                || thirdValue.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!firstValue.get().isEmpty() && !secondValue.get().isEmpty()
                    && !thirdValue.get().isEmpty()) {
                double[] params = {Double.parseDouble(firstValue.get()),
                    Double.parseDouble(secondValue.get()),
                    Double.parseDouble(thirdValue.get())};
                checkParameters(fromColorSpace.getValue(), params);
            }
        } catch (IllegalArgumentException ex) {
            inputStatus = Status.BAD_FORMAT;
        }
        return inputStatus;
    }

    public ObjectProperty<ObservableList<ColorSpaces>> colorSpacesProperty() {
        return colorSpaces;
    }

    public final ObservableList<ColorSpaces> getColorSpaces() {
        return colorSpaces.get();
    }

    public ObjectProperty<ColorSpaces> getFromColorSpace() {
        return fromColorSpace;
    }

    public ObjectProperty<ColorSpaces> getToColorSpace() {
        return toColorSpace;
    }

    public Property<String> getFirstValueProperty() {
        return firstValue;
    }

    public Property<String> getSecondValueProperty() {
        return secondValue;
    }

    public Property<String> getThirdValueProperty() {
        return thirdValue;
    }

    public boolean isConvertingDisabled() {
        return convertingDisabled.get();
    }

    public BooleanProperty convertingDisabledProperty() {
        return convertingDisabled;
    }


    public String getFirstValueResult() {
        return firstValueResult.get();
    }

    public StringProperty firstValueResultProperty() {
        return firstValueResult;
    }


    public String getSecondValueResult() {
        return secondValueResult.get();
    }

    public StringProperty secondValueResultProperty() {
        return secondValueResult;
    }

    public String getThirdValueResult() {
        return thirdValueResult.get();
    }

    public StringProperty thirdValueResultProperty() {
        return thirdValueResult;
    }

    public String getStatusMessage() {
        return statusMessage.get();
    }

    public StringProperty statusMessageProperty() {
        return statusMessage;
    }

    public String getFirstValue() {
        return firstValue.get();
    }

    public StringProperty firstValueProperty() {
        return firstValue;
    }

    public String getSecondValue() {
        return secondValue.get();
    }

    public StringProperty secondValueProperty() {
        return secondValue;
    }

    public String getThirdValue() {
        return thirdValue.get();
    }

    public StringProperty thirdValueProperty() {
        return thirdValue;
    }



    public void convert() {
        if (convertingDisabled.get()) {
            return;
        }
        double[] params = {Double.parseDouble(firstValue.get()),
                Double.parseDouble(secondValue.get()),
                Double.parseDouble(thirdValue.get())};
        double[] roots =
                Converter.convert(fromColorSpace.get(), toColorSpace.get(), params);

        firstValueResult.set(String.valueOf(roots[0]));
        secondValueResult.set(String.valueOf(roots[1]));
        thirdValueResult.set(String.valueOf(roots[2]));
        statusMessage.set(Status.SUCCESS.toString());
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            statusMessage.set(getInputStatus().toString());
        }
    }

    enum Status {
        WAITING("Please enter coefficients of color space"),
        READY("Press 'Convert' for converting"),
        BAD_FORMAT("Entered coefficients are incorrect!"),
        SUCCESS("Success");

        private final String name;

        Status(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}
