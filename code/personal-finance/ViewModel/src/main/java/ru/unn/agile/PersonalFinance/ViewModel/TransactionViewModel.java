package ru.unn.agile.PersonalFinance.ViewModel;

import javafx.beans.property.*;
import ru.unn.agile.PersonalFinance.ViewModel.utils.SavableViewModelObject;

import java.time.LocalDate;

public abstract class TransactionViewModel extends SavableViewModelObject {
    private static final int DEFAULT_AMOUNT = 100;

    private final IntegerProperty amountProperty = new SimpleIntegerProperty();
    private final BooleanProperty isIncomeProperty = new SimpleBooleanProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private final StringProperty displayTitleProperty = new SimpleStringProperty();
    private final StringProperty displayCounterpartyProperty = new SimpleStringProperty();
    private final BooleanProperty counterpartyMarkedAsDeletedProperty =
            new SimpleBooleanProperty();

    public TransactionViewModel() {
        setDate(LocalDate.now());
        setAmount(DEFAULT_AMOUNT);
    }

    protected StringProperty displayTitleMutableProperty() {
        return displayTitleProperty;
    }

    protected StringProperty displayCounterpartyMutableProperty() {
        return displayCounterpartyProperty;
    }

    protected BooleanProperty counterpartyMarkedAsDeletedMutableProperty() {
        return counterpartyMarkedAsDeletedProperty;
    }

    // region Properties

    public final IntegerProperty amountProperty() {
        return amountProperty;
    }

    public final int getAmount() {
        return amountProperty.get();
    }

    public final void setAmount(final int amount) {
        amountProperty.set(amount);
    }

    public final ObjectProperty<LocalDate> dateProperty() {
        return dateProperty;
    }

    public final LocalDate getDate() {
        return dateProperty.get();
    }

    public final void setDate(final LocalDate date) {
        dateProperty.set(date);
    }

    public final BooleanProperty isIncomeProperty() {
        return isIncomeProperty;
    }

    public final boolean isIncome() {
        return isIncomeProperty.get();
    }

    public final void setIsIncome(final boolean isIncome) {
        isIncomeProperty.set(isIncome);
    }

    public final ReadOnlyStringProperty displayTitleProperty() {
        return displayTitleProperty;
    }

    protected final void setDisplayTitle(final String displayTitle) {
        displayTitleProperty.set(displayTitle);
    }

    public final ReadOnlyStringProperty displayCounterpartyProperty() {
        return displayCounterpartyProperty;
    }

    protected final void setDisplayCounterparty(final String displayCounterparty) {
        displayCounterpartyProperty.set(displayCounterparty);
    }

    public final ReadOnlyBooleanProperty counterpartyMarkedAsDeletedProperty() {
        return counterpartyMarkedAsDeletedProperty;
    }

    public final boolean isCounterpartyMarkedAsDeleted() {
        return counterpartyMarkedAsDeletedProperty.get();
    }

    // endregion
}
