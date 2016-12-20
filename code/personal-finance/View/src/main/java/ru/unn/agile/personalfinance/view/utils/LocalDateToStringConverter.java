package ru.unn.agile.personalfinance.view.utils;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class LocalDateToStringConverter extends StringConverter<LocalDate> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String toString(final LocalDate object) {
        return object.format(DATE_TIME_FORMATTER);
    }

    @Override
    public LocalDate fromString(final String string) {
        // NOTE: Need only one side conversion, from object to string
        return null;
    }
}
