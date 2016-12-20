package ru.unn.agile.Credit.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Credit.Model.Credit;

import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;


enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate' or Enter"),
    BAD_FORMAT("Bad format"),
    BAD_VALUES("Wrong input data"),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

public class ViewModel {

    private final StringProperty sum = new SimpleStringProperty();
    private final StringProperty months = new SimpleStringProperty();
    private final StringProperty percent = new SimpleStringProperty();
    private final StringProperty payment = new SimpleStringProperty();
    private final StringProperty overpayment = new SimpleStringProperty();
    private final StringProperty totalSum = new SimpleStringProperty();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final StringProperty status = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    // FXML needs default c-tor for binding
    public ViewModel() {
        sum.set("");
        months.set("");
        percent.set("");
        payment.set("");
        overpayment.set("");
        totalSum.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(sum, months, percent);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(sum);
            add(percent);
            add(months);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        if (!calculationDisabled.get()) {
            try {
                Credit credit = new Credit(Double.parseDouble(sum.get()),
                        Double.parseDouble(months.get()),
                        Double.parseDouble(percent.get()));

                double countPayment = credit.countPayment();
                double countOverpayment = credit.countOverpayment();
                double countTotalSum = credit.countTotalSum();
                payment.set(Double.toString(countPayment));
                overpayment.set(Double.toString(countOverpayment));
                totalSum.set(Double.toString(countTotalSum));
                status.set(Status.SUCCESS.toString());
            } catch (InvalidParameterException ipe) {
                status.set(Status.BAD_VALUES.toString());
                payment.set("");
                overpayment.set("");
                totalSum.set("");
            }
        }
    }

    public StringProperty sumProperty() {
        return sum;
    }
    public StringProperty monthsProperty() {
        return months;
    }
    public StringProperty percentProperty() {
        return percent;
    }
    public StringProperty paymentProperty() {
        return payment;
    }
    public StringProperty overpaymentProperty() {
        return overpayment;
    }
    public StringProperty totalSumProperty() {
        return totalSum;
    }
    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }
    public final boolean getCalculationDisabled() {
        return calculationDisabled.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public final String getStatus() {
        return status.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (sum.get().isEmpty() || percent.get().isEmpty()
         || months.get().isEmpty()) {
            inputStatus = Status.WAITING;
            payment.set("");
            overpayment.set("");
            totalSum.set("");
        }
        try {
            if (!sum.get().isEmpty()) {
                Double.parseDouble(sum.get());
            }
            if (!months.get().isEmpty()) {
                Double.parseDouble(months.get());
            }
            if (!percent.get().isEmpty()) {
                Double.parseDouble(percent.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    public void clear() {
        payment.set("");
        overpayment.set("");
        totalSum.set("");
        sum.set("");
        months.set("");
        percent.set("");
        status.set(Status.WAITING.toString());
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }
}

