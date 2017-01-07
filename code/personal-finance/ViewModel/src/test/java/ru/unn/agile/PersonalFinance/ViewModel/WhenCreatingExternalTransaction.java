package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class WhenCreatingExternalTransaction {
    private ExternalTransactionViewModel transaction;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        transaction = maker.makeExternalTransaction();
    }

    @Test(expected = NullPointerException.class)
    public void andIfAccountIsNullItCausesFail() throws Exception {
        new ExternalTransactionViewModel(null);
    }

    @Test
    public void andItCanBeSavedIfCounterpartyIsNormalString() throws Exception {
        transaction.setCounterparty("Tesla Motors");

        assertTrue(transaction.isAbleToSave());
    }

    @Test
    public void andItCanBeSavedIfDescriptionIsNormalString() throws Exception {
        transaction.setDescription("Tesla Model S");

        assertTrue(transaction.isAbleToSave());
    }

    @Test
    public void andItCanBeSavedIfDateIsNotNull() throws Exception {
        transaction.setDate(LocalDate.now());

        assertTrue(transaction.isAbleToSave());
    }

    @Test
    public void andItCanBeSavedIfAmountIsPositive() throws Exception {
        transaction.setAmount(100);

        assertTrue(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfCounterpartyIsNull() throws Exception {
        transaction.setCounterparty(null);

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfCounterpartyIsEmpty() throws Exception {
        transaction.setCounterparty("");

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfCounterpartyIsWhitespacesString() throws Exception {
        transaction.setCounterparty("    ");

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfAmountIsNegative() throws Exception {
        transaction.setAmount(-100);

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfAmountIsZero() throws Exception {
        transaction.setAmount(0);

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfCategoryIsNull() throws Exception {
        transaction.setCategory(null);

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfDescriptionIsNull() throws Exception {
        transaction.setDescription(null);

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfDescriptionIsEmpty() throws Exception {
        transaction.setDescription("");

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andItCanNotBeSavedIfDescriptionIsWhitespacesString() throws Exception {
        transaction.setDescription("   ");

        assertFalse(transaction.isAbleToSave());
    }

    @Test
    public void andCategoryPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.categoryProperty());
    }

    @Test
    public void andCounterpartyPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.counterpartyProperty());
    }

    @Test
    public void andDescriptionPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.descriptionProperty());
    }

    @Test
    public void andAmountPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.amountProperty());
    }

    @Test
    public void andCounterpartyMarkedAsDeletedMutablePropertyIsNotNull() throws Exception {
        assertNotNull(transaction.counterpartyMarkedAsDeletedMutableProperty());
    }

    @Test
    public void andCounterpartyMarkedAsDeletedPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.counterpartyMarkedAsDeletedProperty());
    }

    @Test
    public void andSescriptionPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.descriptionProperty());
    }

    @Test
    public void andDatePropertyIsNotNull() throws Exception {
        assertNotNull(transaction.dateProperty());
    }

    @Test
    public void andDisplayCounterpartyMutablePropertyIsNotNull() throws Exception {
        assertNotNull(transaction.displayCounterpartyMutableProperty());
    }

    @Test
    public void andDisplayCounterpartyPropertyIsNotNull() throws Exception {
        assertNotNull(transaction.displayCounterpartyProperty());
    }
}
