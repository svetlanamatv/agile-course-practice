package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PersonalFinance.Model.Category;
import ru.unn.agile.PersonalFinance.Model.ExternalTransaction;
import ru.unn.agile.PersonalFinance.ViewModel.utils.GregorianCalendarHelper;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WhenEditingExternalTransaction {
    private static final int AMOUNT_CHANGE = 100;
    private static final String CHANGED_COUNTERPARTY = "Changed counteparty";
    private static final String CHANGED_DESCRIPTION = "Changed description";
    private static final String CHANGED_CATEGORY = "Changed category";

    private ExternalTransactionViewModel transaction;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        transaction = maker.makeSavedExternalTransaction();
        transaction.startChanging();
    }

    @Test
    public void andModelTransactionAmountWasChangedIfItWasSaved() throws Exception {
        int amountAfterChange = transaction.getAmount() + AMOUNT_CHANGE;

        transaction.setAmount(amountAfterChange);
        transaction.save();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(amountAfterChange, Math.abs(modelTransaction.getAmount()));
    }

    @Test
    public void andModelTransactionDateWasChangedIfItWasSaved() throws Exception {
        LocalDate dateAfterChange = transaction.getDate().plusDays(1);

        transaction.setDate(dateAfterChange);
        transaction.save();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertTrue(GregorianCalendarHelper.compareToLocalDate(
                modelTransaction.getDate(), dateAfterChange));
    }

    @Test
    public void andModelTransactionCounterpartyWasChangedIfItWasSaved() throws Exception {
        transaction.setCounterparty(CHANGED_COUNTERPARTY);
        transaction.save();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(CHANGED_COUNTERPARTY, modelTransaction.getCounterparty());
    }

    @Test
    public void andModelTransactionDescriptionWasChangedIfItWasSaved() throws Exception {
        transaction.setDescription(CHANGED_DESCRIPTION);
        transaction.save();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(CHANGED_DESCRIPTION, modelTransaction.getDescription());
    }

    @Test
    public void andModelTransactionCategoryWasChangedIfItWasSaved() throws Exception {
        transaction.setCategory(new CategoryViewModel(CHANGED_CATEGORY));
        transaction.save();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(CHANGED_CATEGORY, modelTransaction.getCategory().getName());
    }

    @Test
    public void andTransactionAmountWasNotChangedIfChangesWereNotApplied() throws Exception {
        int amountBeforeChange = transaction.getAmount();
        int amountAfterChange = amountBeforeChange + AMOUNT_CHANGE;

        transaction.setAmount(amountAfterChange);
        transaction.revertChanges();

        assertEquals(amountBeforeChange, transaction.getAmount());
    }

    @Test
    public void andModelTransactionAmountWasNotChangedIfChangesWereNotApplied()
            throws Exception {
        int amountBeforeChange = transaction.getAmount();
        int amountAfterChange = amountBeforeChange + AMOUNT_CHANGE;

        transaction.setAmount(amountAfterChange);
        transaction.revertChanges();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(amountBeforeChange, Math.abs(modelTransaction.getAmount()));
    }

    @Test
    public void andTransactionDateWasNotChangedIfChangesWereNotApplied() throws Exception {
        LocalDate dateBeforeChange = transaction.getDate();
        LocalDate dateAfterChange = dateBeforeChange.plusDays(1);

        transaction.setDate(dateAfterChange);
        transaction.revertChanges();

        assertTrue(dateBeforeChange.isEqual(transaction.getDate()));
    }

    @Test
    public void andModelTransactionDateWasNotChangedIfChangesWereNotApplied() throws Exception {
        LocalDate dateBeforeChange = transaction.getDate();
        LocalDate dateAfterChange = dateBeforeChange.plusDays(1);

        transaction.setDate(dateAfterChange);
        transaction.revertChanges();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertTrue(GregorianCalendarHelper.compareToLocalDate(
                modelTransaction.getDate(), dateBeforeChange));
    }

    @Test
    public void andTransactionCounterpartyWasNotChangedIfChangesWereNotApplied()
            throws Exception {
        String counterpartyBeforeChange = transaction.getCounterparty();

        transaction.setCounterparty(CHANGED_COUNTERPARTY);
        transaction.revertChanges();

        assertEquals(counterpartyBeforeChange, transaction.getCounterparty());
    }

    @Test
    public void andModelTransactionCounterpartyWasChangedIfChangesWereNotApplied()
            throws Exception {
        String counterpartyBeforeChange = transaction.getCounterparty();

        transaction.setCounterparty(CHANGED_COUNTERPARTY);
        transaction.revertChanges();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(counterpartyBeforeChange, modelTransaction.getCounterparty());
    }

    @Test
    public void andTransactionDescriptionWasNotChangedIfChangesWereNotApplied()
            throws Exception {
        String descriptionBeforeChange = transaction.getDescription();

        transaction.setDescription(CHANGED_DESCRIPTION);
        transaction.revertChanges();

        assertEquals(descriptionBeforeChange, transaction.getDescription());
    }

    @Test
    public void andModelTransactionDescriptionWasNotChangedIfChangesWereNotApplied()
            throws Exception {
        String descriptionBeforeChange = transaction.getDescription();

        transaction.setDescription(CHANGED_DESCRIPTION);
        transaction.revertChanges();

        ExternalTransaction modelTransaction = transaction.getModelExternalTransaction();
        assertEquals(descriptionBeforeChange, modelTransaction.getDescription());
    }

    @Test
    public void andTransactionCategoryWasNotChangedIfChangesWereNotApplied()
            throws Exception {
        CategoryViewModel categoryBeforeChange = transaction.getCategory();

        transaction.setCategory(new CategoryViewModel(CHANGED_CATEGORY));
        transaction.revertChanges();

        assertEquals(categoryBeforeChange.getName(), transaction.getCategory().getName());
    }

    @Test
    public void andModelTransactionCategoryWasNotChangedIfChangesWereNotApplied()
            throws Exception {
        Category categoryModelBeforeChange =
                transaction.getModelExternalTransaction().getCategory();

        transaction.setCategory(new CategoryViewModel(CHANGED_CATEGORY));
        transaction.revertChanges();

        ExternalTransaction modelTransaction =
                transaction.getModelExternalTransaction();
        assertEquals(categoryModelBeforeChange.getName(),
                modelTransaction.getCategory().getName());
    }
}
