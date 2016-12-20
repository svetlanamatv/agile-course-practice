package ru.unn.agile.PersonalFinance.ViewModel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.PersonalFinance.Model.Account;
import ru.unn.agile.PersonalFinance.ViewModel.utils.SavableViewModelObject;
import ru.unn.agile.PersonalFinance.ViewModel.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountViewModel extends SavableViewModelObject {
    private static final int DEFAULT_ACCOUNT_BALANCE = 10000;
    private static final String DEFAULT_ACCOUNT_NAME = "New Account";

    private final StringProperty nameProperty = new SimpleStringProperty();
    private final IntegerProperty balanceProperty = new SimpleIntegerProperty();
    private final ListProperty<TransactionViewModel> transactionsProperty =
            new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    private Account modelAccount;
    private Account savedState;
    private final LedgerViewModel parentLedger;

    public AccountViewModel(final LedgerViewModel parentLedger) {
        Objects.requireNonNull(parentLedger);
        this.parentLedger = parentLedger;
        setUpBindings();
        setName(DEFAULT_ACCOUNT_NAME);
        setBalance(DEFAULT_ACCOUNT_BALANCE);
    }

    // region Properties

    public final StringProperty nameProperty() {
        return nameProperty;
    }

    public final String getName() {
        return nameProperty.get();
    }

    public final void setName(final String name) {
        nameProperty.set(name);
    }

    public final IntegerProperty balanceProperty() {
        return balanceProperty;
    }

    public final int getBalance() {
        return balanceProperty.get();
    }

    public final void setBalance(final int balance) {
        balanceProperty.set(balance);
    }

    public final ListProperty<TransactionViewModel> transactionsProperty() {
        return transactionsProperty;
    }

    public final ObservableList<TransactionViewModel> getTransactions() {
        return transactionsProperty.get();
    }

    // endregion

    Account getModelAccount() {
        if (modelAccount == null) {
            throw new UnsupportedOperationException("Account should be "
                    + "saved before getting the model account");
        }
        return modelAccount;
    }

    @Override
    protected void saveInternal() {
        modelAccount = new Account(getBalance(), getName());
        parentLedger.addAccount(this);
    }

    @Override
    protected void updateInternal() {
        modelAccount.changeName(getName());
    }

    @Override
    protected void deleteInternal() {
        parentLedger.deleteAccount(this);
    }

    @Override
    protected void saveState() {
        savedState = new Account(getBalance(), getName());
    }

    @Override
    protected void recoverState() {
        setName(savedState.getName());
        setBalance(savedState.getBalance());
    }

    void registerTransaction(final TransactionViewModel transaction) {
        getTransactions().add(transaction);
        updateBalance();
    }

    void unregisterTransaction(final TransactionViewModel transaction) {
        getTransactions().remove(transaction);
        updateBalance();
    }

    void forceUpdateBalance() {
        updateBalance();
    }

    private void setUpBindings() {
        BooleanBinding isAccountNameExists = Bindings.createBooleanBinding(
                this::checkIfAnotherAccountWithSameNameExists,
                parentLedger.accountsProperty(), nameProperty);

        ableToSaveMutableProperty().bind(isAccountNameExists.not());
    }

    private void updateBalance() {
        setBalance(modelAccount.getBalance());
    }

    private boolean checkIfAnotherAccountWithSameNameExists() {
        List<AccountViewModel> accounts = parentLedger.getAccounts();
        return accounts.stream().anyMatch((account) -> {
            boolean namesEqual = StringHelper.areEqualTrimmed(account.getName(), getName());
            return account != this && namesEqual;
        });
    }
}
