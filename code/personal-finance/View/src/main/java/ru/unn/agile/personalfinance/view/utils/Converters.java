package ru.unn.agile.personalfinance.view.utils;

import javafx.util.StringConverter;
import ru.unn.agile.PersonalFinance.ViewModel.AccountViewModel;
import ru.unn.agile.PersonalFinance.ViewModel.CategoryViewModel;

import java.time.LocalDate;

public final class Converters {
    private static final Lazy<AccountViewModelNameExtractor> ACCOUNT_TO_STRING_CONVERTER =
            new Lazy<>(() -> new AccountViewModelNameExtractor());

    private static final Lazy<CategoryViewModelNameExtractor> CATEGORY_TO_STRING_CONVERTER =
            new Lazy<>(() -> new CategoryViewModelNameExtractor());

    private static final Lazy<LocalDateToStringConverter> LOCAL_DATE_TO_STRING_CONVERTER =
            new Lazy<>(() -> new LocalDateToStringConverter());

    private static final Lazy<CurrencyStringConverter> CURRENCY_STRING_CONVERTER =
            new Lazy<>(() -> new CurrencyStringConverter());

    public static StringConverter<AccountViewModel> getAccountToStringConverter() {
        return ACCOUNT_TO_STRING_CONVERTER.get();
    }

    public static StringConverter<CategoryViewModel> getCategoryToStringConverter() {
        return CATEGORY_TO_STRING_CONVERTER.get();
    }

    public static StringConverter<LocalDate> getLocalDateToStringConverter() {
        return LOCAL_DATE_TO_STRING_CONVERTER.get();
    }

    public static StringConverter<Number> getCurrencyStringConverter() {
        return CURRENCY_STRING_CONVERTER.get();
    }

    private Converters() {
    }
}
