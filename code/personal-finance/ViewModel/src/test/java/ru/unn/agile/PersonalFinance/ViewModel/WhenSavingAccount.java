package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PersonalFinance.Model.Account;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertContains;

public class WhenSavingAccount {
    private ViewModelObjectsMaker maker;
    private LedgerViewModel ledger;
    private AccountViewModel account;

    @Before
    public void setUp() throws Exception {
        maker = new ViewModelObjectsMaker();
        ledger = maker.getLedger();
        account = maker.makeAccount();
    }

    @Test
    public void andItIsAddedToAccountList() throws Exception {
        account.save();

        List<AccountViewModel> accounts = ledger.getAccounts();
        assertContains(accounts, account);
    }

    @Test
    public void andModelAccountNameEqualsToViewModelAccountName() throws Exception {
        String accountName = "Cash";
        account.setName(accountName);

        account.save();

        Account modelAccount = this.account.getModelAccount();
        assertEquals(modelAccount.getName(), accountName);
    }

    @Test
    public void andModelAccountBalanceEqualsToViewModelAccountBalance() throws Exception {
        int accountBalance = 10000;
        account.setBalance(accountBalance);

        account.save();

        Account modelAccount = this.account.getModelAccount();
        assertEquals(modelAccount.getBalance(), accountBalance);
    }

    @Test
    public void andCanAddTransferPropertyChangesToTrueIfAtLeastTwoAccounts() throws Exception {
        AccountViewModel cacheAccount = maker.makeAccount("Cash");
        AccountViewModel debitCardAccount = maker.makeAccount("Debit card");

        cacheAccount.save();
        debitCardAccount.save();

        boolean canAddTransfer = ledger.isAbleToAddTransfer();
        assertTrue(canAddTransfer);
    }
}
