package ru.unn.agile.personalfinance.view.controls;

import com.jfoenix.controls.JFXListCell;

import java.util.Objects;
import java.util.function.Function;

public class StringListCell<T> extends JFXListCell<T> {
    private final Function<T, String> toStringConverter;

    public StringListCell(final Function<T, String> converter) {
        Objects.requireNonNull(converter);
        toStringConverter = converter;
    }

    @Override
    public void updateItem(final T item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(null);
            setText(toStringConverter.apply(item));
        }
    }
}
