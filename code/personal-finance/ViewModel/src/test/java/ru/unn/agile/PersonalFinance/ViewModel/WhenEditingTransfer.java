package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PersonalFinance.Model.Transfer;
import ru.unn.agile.PersonalFinance.ViewModel.utils.GregorianCalendarHelper;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WhenEditingTransfer {
    private static final int AMOUNT_CHANGE = 100;

    private TransferViewModel transfer;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        transfer = maker.makeSavedTransfer();
        transfer.startChanging();
    }

    @Test
    public void andModelTransferAmountChangedIfItWasSaved() throws Exception {
        int amountAfterChange = transfer.getAmount() + AMOUNT_CHANGE;

        transfer.setAmount(amountAfterChange);
        transfer.save();

        Transfer modelTransfer = this.transfer.getModelTransfer();
        assertEquals(amountAfterChange, Math.abs(modelTransfer.getAmount()));
    }

    @Test
    public void andModelTransactionDateWasChangedIfItWasSaved() throws Exception {
        LocalDate dateAfterChange = transfer.getDate().plusDays(1);

        transfer.setDate(dateAfterChange);
        transfer.save();

        Transfer modelTransfer = transfer.getModelTransfer();
        assertTrue(GregorianCalendarHelper.compareToLocalDate(
                modelTransfer.getDate(), dateAfterChange));
    }

    @Test
    public void andTransferAmountWasNotChangedIfChangesWereNotApplied() throws Exception {
        int amountBeforeChange = transfer.getAmount();
        int amountAfterChange = amountBeforeChange + AMOUNT_CHANGE;

        transfer.setAmount(amountAfterChange);
        transfer.revertChanges();

        assertEquals(amountBeforeChange, transfer.getAmount());
    }

    @Test
    public void andModelTransferAmountWasNotChangedIfChangesWereNotApplied() throws Exception {
        int amountBeforeChange = transfer.getAmount();
        int amountAfterChange = amountBeforeChange + AMOUNT_CHANGE;

        transfer.setAmount(amountAfterChange);
        transfer.revertChanges();

        Transfer modelTransfer = this.transfer.getModelTransfer();
        assertEquals(amountBeforeChange, modelTransfer.getAmount());
    }

    @Test
    public void andTransactionDateWasNotChangedIfChangesWereNotApplied() throws Exception {
        LocalDate dateBeforeChange = transfer.getDate();
        LocalDate dateAfterChange = dateBeforeChange.plusDays(1);

        transfer.setDate(dateAfterChange);
        transfer.revertChanges();

        assertTrue(dateBeforeChange.isEqual(transfer.getDate()));
    }

    @Test
    public void andModelTransactionDateWasNotChangedIfChangesWereNotApplied() throws Exception {
        LocalDate dateBeforeChange = transfer.getDate();
        LocalDate dateAfterChange = dateBeforeChange.plusDays(1);

        transfer.setDate(dateAfterChange);
        transfer.revertChanges();

        Transfer modelTransfer = transfer.getModelTransfer();
        assertTrue(GregorianCalendarHelper.compareToLocalDate(
                modelTransfer.getDate(), dateBeforeChange));
    }

    @Test
    public void andSourceAccountBalanceChanged() throws Exception {
        AccountViewModel sourceAccount = transfer.getSourceAccount();
        int sourceAccountBalance = sourceAccount.getBalance();

        transfer.setAmount(transfer.getAmount() + AMOUNT_CHANGE);
        transfer.save();

        assertEquals(sourceAccountBalance - AMOUNT_CHANGE, sourceAccount.getBalance());
    }

    @Test
    public void andTargetAccountBalanceChanged() throws Exception {
        AccountViewModel targetAccount = transfer.getTargetAccount();
        int targetAccountBalance = targetAccount.getBalance();

        transfer.setAmount(transfer.getAmount() + AMOUNT_CHANGE);
        transfer.save();

        assertEquals(targetAccountBalance + AMOUNT_CHANGE, targetAccount.getBalance());
    }
}
