package ru.unn.agile.PersonalFinance.ViewModel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.unn.agile.PersonalFinance.Model.Account;
import ru.unn.agile.PersonalFinance.Model.Transfer;
import ru.unn.agile.PersonalFinance.ViewModel.utils.GregorianCalendarHelper;

import java.util.GregorianCalendar;

public class TransferViewModel extends TransactionViewModel {
    private final ObjectProperty<AccountViewModel> accountFromProperty =
            new SimpleObjectProperty<>();

    private final ObjectProperty<AccountViewModel> accountToProperty =
            new SimpleObjectProperty<>();

    private Transfer modelTransfer;
    private AccountViewModel associatedAccount;
    private TransferViewModel linkedTransfer;
    private TransferViewModelState savedState;

    private boolean isInProcessOfDeletion;

    public TransferViewModel() {
        setDisplayTitle("Transfer");
        setUpBindings();
    }

    // region Properties

    public final ObjectProperty<AccountViewModel> accountFromProperty() {
        return accountFromProperty;
    }

    public final AccountViewModel getSourceAccount() {
        return accountFromProperty.get();
    }

    public final void setSourceAccount(final AccountViewModel account) {
        accountFromProperty.set(account);
    }

    public final ObjectProperty<AccountViewModel> accountToProperty() {
        return accountToProperty;
    }

    public final AccountViewModel getTargetAccount() {
        return accountToProperty.get();
    }

    public final void setTargetAccount(final AccountViewModel account) {
        accountToProperty.set(account);
    }

    // endregion

    Transfer getModelTransfer() {
        return modelTransfer;
    }

    TransferViewModel getLinkedTransfer() {
        return linkedTransfer;
    }

    @Override
    protected void saveInternal() {
        modelTransfer = buildModelTransfer();
        getSourceAccount().registerTransaction(this.asOutcoming());
        getTargetAccount().registerTransaction(this.duplicate().asIncoming());
    }

    @Override
    protected void updateInternal() {
        deleteModelTransfer();
        modelTransfer = buildModelTransfer();
        getSourceAccount().forceUpdateBalance();
        getTargetAccount().forceUpdateBalance();
        synchronizeStateWithLinkedTransfer();
    }

    @Override
    protected void deleteInternal() {
        startProcessOfDeletion();
        if (!linkedTransfer.isInProcessOfDeletion()) {
            deleteModelTransfer();
            linkedTransfer.delete();
        }
        associatedAccount.unregisterTransaction(this);
        endProcessOfDeletion();
    }

    @Override
    protected void saveState() {
        savedState = TransferViewModelState.save(this);
    }

    @Override
    protected void recoverState() {
        savedState.recover(this);
    }

    private TransferViewModel duplicate() {
        TransferViewModel other = new TransferViewModel();
        other.setAmount(getAmount());
        other.setIsIncome(isIncome());
        other.setDate(getDate());
        other.setSourceAccount(getSourceAccount());
        other.setTargetAccount(getTargetAccount());
        other.modelTransfer = modelTransfer;
        other.linkedTransfer = this;
        linkedTransfer = other;
        other.markAsSaved();
        return other;
    }

    private void synchronizeStateWithLinkedTransfer() {
        linkedTransfer.setAmount(getAmount());
        linkedTransfer.setDate(getDate());
        linkedTransfer.modelTransfer = modelTransfer;
    }

    private Transfer buildModelTransfer() {
        AccountViewModel sourceAccount = getSourceAccount();
        AccountViewModel targetAccount = getTargetAccount();
        Account modelSourceAccount = sourceAccount.getModelAccount();
        Account modelTargetAccount = targetAccount.getModelAccount();

        GregorianCalendar transferDate =
                GregorianCalendarHelper.convertFromLocalDate(getDate());
        return modelSourceAccount.transferTo(modelTargetAccount, getAmount(), transferDate);
    }

    private void setUpBindings() {
        BooleanBinding accountsNotNull =
                accountFromProperty.isNotNull().and(
                accountToProperty.isNotNull());

        BooleanBinding accountsNotEqual =
                accountFromProperty.isNotEqualTo(
                accountToProperty);

        BooleanBinding isAmountPositive = amountProperty().greaterThan(0);

        ableToSaveMutableProperty().bind(
                accountsNotNull.and(
                accountsNotEqual).and(
                isAmountPositive));

        isIncomeProperty().addListener((observable, oldValue, newValue) ->
                updateDisplayCounterparty());

        accountToProperty.addListener((observable, oldValue, newValue) ->
                updateDisplayCounterparty());

        accountFromProperty.addListener((observable, oldValue, newValue) ->
                updateDisplayCounterparty());
    }

    private void updateDisplayCounterparty() {
        if (isIncome()) {
            setDisplayCounterpartyFromAccount(getSourceAccount());
        } else {
            setDisplayCounterpartyFromAccount(getTargetAccount());
        }
    }

    private void setDisplayCounterpartyFromAccount(final AccountViewModel account) {
        displayCounterpartyMutableProperty().unbind();
        counterpartyMarkedAsDeletedMutableProperty().unbind();

        if (account == null) {
            setDisplayCounterparty(null);
        } else {
            displayCounterpartyMutableProperty().bind(account.nameProperty());
            counterpartyMarkedAsDeletedMutableProperty().bind(account.deletedProperty());
        }
    }

    private TransferViewModel asIncoming() {
        associatedAccount = getTargetAccount();
        this.setIsIncome(true);
        return this;
    }

    private TransferViewModel asOutcoming() {
        associatedAccount = getSourceAccount();
        this.setIsIncome(false);
        return this;
    }

    private void deleteModelTransfer() {
        Account modelSourceAccount = getSourceAccount().getModelAccount();
        modelSourceAccount.deleteTransaction(modelTransfer);
    }

    private void startProcessOfDeletion() {
        isInProcessOfDeletion = true;
    }
    private boolean isInProcessOfDeletion() {
        return isInProcessOfDeletion;
    }
    private void endProcessOfDeletion() {
        isInProcessOfDeletion = false;
    }
}
