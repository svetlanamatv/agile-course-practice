package ru.unn.agile.personalfinance.view.controls;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.Objects;
import java.util.function.Function;

public class StringListCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {
    private final Function<T, String> toStringConverter;

    public StringListCellFactory(final Function<T, String> converter) {
        Objects.requireNonNull(converter);
        toStringConverter = converter;
    }

    @Override
    public ListCell<T> call(final ListView<T> param) {
        return new StringListCell<T>(toStringConverter);
    }
}
