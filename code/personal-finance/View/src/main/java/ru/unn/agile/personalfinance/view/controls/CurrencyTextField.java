package ru.unn.agile.personalfinance.view.controls;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.util.StringConverter;
import ru.unn.agile.personalfinance.view.utils.Converters;

public class CurrencyTextField extends JFXTextField {
    private final BooleanProperty valueValidProperty = new SimpleBooleanProperty();
    private final IntegerProperty valueProperty = new SimpleIntegerProperty();

    public CurrencyTextField() {
        StringConverter<Number> currencyConverter =
                Converters.getCurrencyStringConverter();

        Bindings.bindBidirectional(textProperty(), valueProperty, currencyConverter);

        BooleanBinding valueValidBinding = Bindings.createBooleanBinding(() ->
                currencyConverter.fromString(getText()) != null, textProperty());

        valueValidProperty.bind(valueValidBinding);
    }

    public final IntegerProperty valueProperty() {
        return valueProperty;
    }

    public final int getValue() {
        return valueProperty.get();
    }

    public final void setValue(final int value) {
        valueProperty.set(value);
    }

    public final ReadOnlyBooleanProperty valueValidProperty() {
        return valueValidProperty;
    }

    public final boolean isValueValid() {
        return valueValidProperty.get();
    }

    private void setValueValid(final boolean isValid) {
        valueValidProperty.set(isValid);
    }
}
