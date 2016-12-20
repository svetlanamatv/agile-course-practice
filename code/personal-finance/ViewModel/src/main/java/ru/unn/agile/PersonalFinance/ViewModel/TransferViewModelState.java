package ru.unn.agile.PersonalFinance.ViewModel;

import java.time.LocalDate;

final class TransferViewModelState {
    private final int amount;
    private final LocalDate date;

    private TransferViewModelState(final TransferViewModel transfer) {
        amount = transfer.getAmount();
        date = transfer.getDate();
    }

    static TransferViewModelState save(final TransferViewModel transfer) {
        return new TransferViewModelState(transfer);
    }

    void recover(final TransferViewModel transfer) {
        transfer.setAmount(amount);
        transfer.setDate(date);
    }
}
