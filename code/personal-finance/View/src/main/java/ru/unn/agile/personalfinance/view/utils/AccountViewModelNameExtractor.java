package ru.unn.agile.personalfinance.view.utils;

import javafx.util.StringConverter;
import ru.unn.agile.PersonalFinance.ViewModel.AccountViewModel;

class AccountViewModelNameExtractor extends StringConverter<AccountViewModel> {
    @Override
    public String toString(final AccountViewModel object) {
        return object.getName();
    }

    @Override
    public AccountViewModel fromString(final String string) {
        // NOTE: Need only one side conversion, from object to string
        return null;
    }
}
