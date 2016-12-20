package ru.unn.agile.PersonalFinance.Model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Account {
    private int balance;
    private String name;
    private final List<Transaction> transactions;

    public Account(final int startingBalance, final String name) {
        this.balance = startingBalance;
        this.name = name;
        this.transactions = new ArrayList<>();
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addExternalTransaction(final ExternalTransaction expense) {
        balance += expense.getAmount();
        addTransactionKeepingOrder(transactions, expense);
    }

    public Transfer transferTo(final Account other, final int amount,
                           final GregorianCalendar date) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Consider adding a transfer in opposite direction");
        }

        Transfer transfer = new Transfer(amount, this, other, date);
        addTransfer(transfer);
        return transfer;
    }

    public Transfer transferTo(final Account other, final int amount) {
        return transferTo(other, amount, null);
    }

    public void deleteTransaction(final Transaction transaction) {
        if (transaction.isExternal()) {
            balance -= transaction.getAmount();
            transactions.remove(transaction);
        } else {
            Transfer transfer = (Transfer) transaction;
            transfer.getSource().balance += transfer.getAmount();
            transfer.getTarget().balance -= transfer.getAmount();
            transactions.remove(transfer);
            if (transfer.getSource() == this) {
                transfer.getTarget().getTransactions().remove(transfer);
            } else {
                transfer.getSource().getTransactions().remove(transfer);
            }
        }
    }

    public void replaceExternalTransaction(
            final ExternalTransaction oldTransaction,
            final ExternalTransaction newTransaction) {

        deleteTransaction(oldTransaction);
        addExternalTransaction(newTransaction);
    }

    public void replaceTransfer(final Transfer oldTransfer, final Transfer newTransfer) {
        deleteTransaction(oldTransfer);
        addTransfer(newTransfer);
    }

    public void changeName(final String name) {
        this.name = name;
    }

    private static void addTransactionKeepingOrder(
            final List<Transaction> transactions,
            final Transaction replacement) {

        int insertionIndex = transactions.size() - 1;
        while (insertionIndex >= 0
                && transactions.get(insertionIndex).compareTo(replacement) == 1) {
            insertionIndex--;
        }

        transactions.add(insertionIndex + 1, replacement);
    }

    private static void addTransfer(final Transfer transfer) {
        transfer.getSource().balance -= transfer.getAmount();
        transfer.getTarget().balance += transfer.getAmount();
        addTransactionKeepingOrder(transfer.getSource().transactions, transfer);
        addTransactionKeepingOrder(transfer.getTarget().transactions, transfer);
    }
}
