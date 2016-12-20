package ru.unn.agile.PersonalFinance.ViewModel.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.StringProperty;

public final class StringHelper {
    static boolean isNullOrEmpty(final String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean areEqualTrimmed(final String lhs, final String rhs) {
        if (lhs != null && rhs != null) {
            return lhs.trim().equals(rhs.trim());
        }
        return lhs == null && rhs == null;
    }

    public static BooleanBinding isNullOrEmpty(final StringProperty property) {
        return Bindings.createBooleanBinding(() ->
                StringHelper.isNullOrEmpty(property.get()), property);
    }

    private StringHelper() {
    }
}
