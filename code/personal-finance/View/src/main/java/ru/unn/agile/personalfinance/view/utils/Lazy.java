package ru.unn.agile.personalfinance.view.utils;

import java.util.Objects;
import java.util.function.Supplier;

public class Lazy<T> {
    private final Supplier<T> supplier;
    private T value;

    public Lazy(final Supplier<T> supplier) {
        Objects.requireNonNull(supplier);
        this.supplier = supplier;
    }

    final T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }
}
