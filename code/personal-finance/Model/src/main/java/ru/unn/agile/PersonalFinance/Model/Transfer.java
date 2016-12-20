package ru.unn.agile.PersonalFinance.Model;

import java.util.GregorianCalendar;

public class Transfer extends Transaction {
    private Account source;
    private Account target;

    public Transfer(final int amount,
                    final Account source,
                    final Account target,
                    final GregorianCalendar date) {

        super(amount, date);
        this.source = source;
        this.target = target;
    }

    public Transfer(
            final int amount,
            final Account source,
            final Account target) {
        this(amount, source, target, null);
    }

    public Account getSource() {
        return source;
    }

    public Account getTarget() {
        return target;
    }

    @Override
    public boolean isExternal() {
        return false;
    }

    @Override
    public boolean isTransfer() {
        return true;
    }

    public void setSource(final Account source) {
        this.source = source;
    }

    public void setTarget(final Account target) {
        this.target = target;
    }
}
