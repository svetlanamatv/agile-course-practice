package ru.unn.agile.personalfinance.view.utils;

import javafx.util.StringConverter;
import ru.unn.agile.PersonalFinance.ViewModel.CategoryViewModel;

class CategoryViewModelNameExtractor extends StringConverter<CategoryViewModel> {
    @Override
    public String toString(final CategoryViewModel object) {
        return object.getName();
    }

    @Override
    public CategoryViewModel fromString(final String string) {
        // NOTE: Need only one side conversion, from object to string
        return null;
    }
}
