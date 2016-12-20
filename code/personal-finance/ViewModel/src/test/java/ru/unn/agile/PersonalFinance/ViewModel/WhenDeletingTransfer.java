package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertNotContains;

public class WhenDeletingTransfer {
    private AccountViewModel sourceAccount;
    private AccountViewModel targetAccount;
    private TransferViewModel transfer;

    @Before
    public void setUp() throws Exception {
        ViewModelObjectsMaker maker = new ViewModelObjectsMaker();
        sourceAccount = maker.makeSavedAccount();
        targetAccount = maker.makeSavedAccount();
        transfer = maker.makeSavedTransfer(sourceAccount, targetAccount);
    }

    @Test
    public void andItIsDeletedFromSourceAccount() throws Exception {
        TransactionViewModel onlyTransaction =
                sourceAccount.getTransactions().get(0);

        transfer.delete();

        assertNotContains(sourceAccount.getTransactions(), onlyTransaction);
    }

    @Test
    public void andItIsDeletedFromTargetAccount() throws Exception {
        TransactionViewModel onlyTransaction =
                targetAccount.getTransactions().get(0);

        transfer.delete();

        assertNotContains(targetAccount.getTransactions(), onlyTransaction);
    }
}
