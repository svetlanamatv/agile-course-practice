package ru.unn.agile.personalfinance.view.utils;

import javafx.util.converter.NumberStringConverter;

public class CurrencyStringConverter extends NumberStringConverter {
    @Override
    public Number fromString(final String value) {
        if (!value.matches("(\\+|-)?\\d+\\$")) {
            return null;
        }

        String valueNoPlus = value.replace("+", "");
        String valueNoDollar = valueNoPlus.replace("$", "");
        return super.fromString(valueNoDollar);
    }

    @Override
    public String toString(final Number value) {
        return value.toString() + "$";
    }

}
