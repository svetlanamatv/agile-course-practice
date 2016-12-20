package ru.unn.agile.PersonalFinance.ViewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.PersonalFinance.Model.Ledger;

import java.util.ArrayList;

public class LedgerViewModel {
    private final BooleanProperty ableToAddTransactionProperty = new SimpleBooleanProperty();
    private final BooleanProperty ableToAddTransferProperty = new SimpleBooleanProperty();

    private final ListProperty<AccountViewModel> accountsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    private final ObjectProperty<AccountViewModel> selectedAccountProperty =
            new SimpleObjectProperty<>();

    private final ObjectProperty<CategoriesManagerViewModel> categoriesManagerProperty =
            new SimpleObjectProperty<>(new CategoriesManagerViewModel());

    private final Ledger modelLedger = new Ledger();

    public LedgerViewModel() {
        setUpBindings();
    }

    // region Properties

    public final ListProperty<AccountViewModel> accountsProperty() {
        return accountsProperty;
    }

    public final ObservableList<AccountViewModel> getAccounts() {
        return accountsProperty.get();
    }

    public final CategoriesManagerViewModel getCategoriesManager() {
        return categoriesManagerProperty.get();
    }

    public final ObjectProperty<AccountViewModel> selectedAccountProperty() {
        return selectedAccountProperty;
    }

    public final void setSelectedAccount(final AccountViewModel selectedAccount) {
        selectedAccountProperty.set(selectedAccount);
    }

    public final BooleanProperty ableToAddTransactionProperty() {
        return ableToAddTransactionProperty;
    }

    public final boolean isAbleToAddTransaction() {
        return ableToAddTransactionProperty.get();
    }

    public final BooleanProperty ableToAddTransferProperty() {
        return ableToAddTransferProperty;
    }

    public final boolean isAbleToAddTransfer() {
        return ableToAddTransferProperty.get();
    }

    // endregion

    void addAccount(final AccountViewModel account) {
        modelLedger.addAccount(account.getModelAccount());
        accountsProperty.add(account);
    }

    void deleteAccount(final AccountViewModel account) {
        modelLedger.deleteAccount(account.getModelAccount());
        accountsProperty.remove(account);
    }

    private void setUpBindings() {
        ReadOnlyIntegerProperty accountsSizeProperties = accountsProperty.sizeProperty();
        ableToAddTransferProperty.bind(accountsSizeProperties.greaterThan(1));

        ableToAddTransactionProperty.bind(selectedAccountProperty().isNotNull());
    }
}
