package ru.unn.agile.PersonalFinance.Model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import static org.junit.Assert.*;

public class WhenQueryingAccount {
    private Account cash = new Account(75, "Cash");
    private Account debitCard = new Account(150, "Debit card");
    private ExternalTransaction friendlyLoan = ExternalTransaction.incomeBuilder(50)
            .date(new GregorianCalendar(2015, Calendar.AUGUST, 19))
            .build();
    private ExternalTransaction cinemaTicket = ExternalTransaction.expenseBuilder(25).build();

    @Test
    public void transactionListShouldBeSortedByDateInReverseOrder() {
        ExternalTransaction buyingGroceries = ExternalTransaction.expenseBuilder(25)
                .date(new GregorianCalendar(2015, Calendar.AUGUST, 17))
                .build();

        cash.addExternalTransaction(buyingGroceries);
        cash.addExternalTransaction(friendlyLoan);
        debitCard.transferTo(cash, 40, new GregorianCalendar(2015, Calendar.JULY, 31));
        Transfer cashWithdrawal = (Transfer) debitCard.getTransactions().get(0);

        assertEquals(friendlyLoan, cash.getTransactions().get(0));
        assertEquals(buyingGroceries, cash.getTransactions().get(1));
        assertEquals(cashWithdrawal, cash.getTransactions().get(2));
    }

    @Test
    public void datelessTransactionsShouldBeAtTheEndOfTheList() {
        cash.addExternalTransaction(cinemaTicket);
        cash.addExternalTransaction(friendlyLoan);
        debitCard.transferTo(cash, 40, new GregorianCalendar(2015, Calendar.AUGUST, 30));
        Transfer cashWithdrawal = (Transfer) debitCard.getTransactions().get(0);

        assertEquals(cashWithdrawal, cash.getTransactions().get(0));
        assertEquals(friendlyLoan, cash.getTransactions().get(1));
        assertEquals(cinemaTicket, cash.getTransactions().get(2));
    }

    @Test
    public void afterChangingDatesTransactionListShouldBeSorted() {
        cash.addExternalTransaction(cinemaTicket);
        cash.addExternalTransaction(friendlyLoan);

        ExternalTransaction datedCinemaTicket = ExternalTransaction.expenseBuilder(25)
                .date(new GregorianCalendar(2015, Calendar.AUGUST, 21))
                .build();
        cash.replaceExternalTransaction(cinemaTicket, datedCinemaTicket);

        assertEquals(datedCinemaTicket, cash.getTransactions().get(0));
        assertEquals(friendlyLoan, cash.getTransactions().get(1));
    }

    @Test
    public void afterChangingDateOfTransferTransactionListShouldBeSorted() {
        cash.addExternalTransaction(friendlyLoan);
        debitCard.transferTo(cash, 40, new GregorianCalendar(2015, Calendar.JULY, 31));
        Transfer cashWithdrawal = (Transfer) debitCard.getTransactions().get(0);

        Transfer updatedCashWithdrawal = new Transfer(40, debitCard, cash,
                new GregorianCalendar(2015, Calendar.AUGUST, 20));
        cash.replaceTransfer(cashWithdrawal, updatedCashWithdrawal);

        assertEquals(updatedCashWithdrawal, cash.getTransactions().get(0));
        assertEquals(friendlyLoan, cash.getTransactions().get(1));
    }
}
