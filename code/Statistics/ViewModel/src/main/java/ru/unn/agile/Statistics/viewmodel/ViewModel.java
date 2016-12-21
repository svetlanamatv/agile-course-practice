package ru.unn.agile.Statistics.viewmodel;

import ru.unn.agile.Statistics.model.Statistics;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ViewModel {
    private double[] values;
    private double[] possibilities;
    private String delta;
    private String momentOrder;
    private Operation operation;
    private String result;
    private Status status;
    private boolean isMomentOrderEnabled;
    private boolean isCalculateButtonEnabled;

    static final String DEFAULT_DELTA = "0";
    static final Operation DEFAULT_OPERATION = Operation.EV;

    private ILogger logger;

    public ViewModel(final ILogger logger) {
        this.logger = logger;

        values = new double[0];
        possibilities = new double[0];
        delta = DEFAULT_DELTA;
        Statistics.setDelta(Double.valueOf(delta));
        momentOrder = "";
        operation = DEFAULT_OPERATION;
        result = "";
        status = Status.WAITING;

        isMomentOrderEnabled = false;
        isCalculateButtonEnabled = false;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public void setValue(int i, double value) {
        logger.log(String.format(LogMessages.VALUES_WERE_CHANGED,
                   value,
                   i));

        values[i] = value;
        updateStatus();
    }

    public double getValue(int i) {
        return values[i];
    }

    public void setPossibility(int i, double possibility) {
        logger.log(String.format(LogMessages.POSSIBILITIES_WERE_CHANGED,
                                 possibility,
                                 i));

        possibilities[i] = possibility;
        updateStatus();

    }

    public double getPossibility(int i) {
        return possibilities[i];
    }

    public int getTableSize() {
        return possibilities.length;
    }

    enum Status {
        WAITING("Please provide input data"),
        READY("Press 'Calculate' or Enter"),
        BAD_FORMAT("Bad format"),
        SUCCESS("Success");

        private final String name;
        Status(final String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    public final class LogMessages {
        public static final String OPERATION_WAS_CHANGED = "Operation was changed to %s";
        public static final String DELTA_WAS_CHANGED = "Delta was changed to %s";
        public static final String COUNT_SAMPLES_WAS_CHANGED = "Count of samples was changed to %d";
        public static final String VALUES_WERE_CHANGED = "Set value = %f at %d position";
        public static final String POSSIBILITIES_WERE_CHANGED = "Set possibility = %f at %d position";

        private LogMessages() { }
    }

    private boolean isInputAvailable() {
        return !delta.isEmpty()
            && values.length != 0
            && possibilities.length != 0
            && (!isMomentOrderEnabled || !momentOrder.isEmpty());
    }

    private void updateStatus() {
        parseInput();
    }

    private void parseInput() {
        try {
            if (!delta.isEmpty()) {
                Statistics.setDelta(Double.valueOf(delta));
            }
            if (isMomentOrderEnabled && !momentOrder.isEmpty()) {
                Integer.valueOf(momentOrder);
            }
        } catch (NumberFormatException e) {
            status = Status.BAD_FORMAT;
            isCalculateButtonEnabled = false;
            return;
        }

        try {
            if (values.length != 0 && possibilities.length != 0) {
                new Statistics(values, possibilities);
            }
        } catch (IllegalArgumentException e) {
            status = Status.BAD_FORMAT;
            isCalculateButtonEnabled = false;
            return;
        }

        if (isInputAvailable()) {
            isCalculateButtonEnabled = true;
            status = Status.READY;
        } else {
            isCalculateButtonEnabled = false;
            status = Status.WAITING;
        }
    }

    public void calculate() {
        parseInput();
        if (!isCalculateButtonEnabled) {
            return;
        }

        Statistics statistics = new Statistics(values, possibilities);
        double res;
        if (operation.is(Computable.class)) {
            Computable op = operation.toComputable();
            res = op.compute(statistics);
        } else if (operation.is(ComputableWithMomentOrder.class)) {
            ComputableWithMomentOrder op = operation.toComputableWithOrder();
            int order = Integer.valueOf(momentOrder);
            res = op.compute(statistics, order);
        } else {
            throw new IllegalStateException("unknown type of operation");
        }
        result = String.valueOf(res);

        status = Status.SUCCESS;
    }

    public void setArraysSize(final int arraysSize) {
        final int size = max(arraysSize, 0);

        if (values.length != size) {
            logger.log(String.format(LogMessages.COUNT_SAMPLES_WAS_CHANGED, arraysSize));

            double[] v = new double[size];
            double[] p = new double[size];
            System.arraycopy(values, 0, v, 0, min(size, values.length));
            System.arraycopy(possibilities, 0, p, 0, min(size, possibilities.length));
            values = v;
            possibilities = p;
        }
    }

    public String getDelta() {
        return delta;
    }
    public void setDelta(final String delta) {
        if (!this.delta.equals(delta)) {
            logger.log(String.format(LogMessages.DELTA_WAS_CHANGED,
                                     delta));
            this.delta = delta;
            updateStatus();
        }
    }

    public String getMomentOrder() {
        if (!isMomentOrderEnabled) {
            throw new IllegalStateException("attempting to get momentOrder when it is disabled");
        }
        return momentOrder;
    }
    public void setMomentOrder(final String momentOrder) {
        if (!isMomentOrderEnabled) {
            throw new IllegalStateException("attempting to set momentOrder when it is disabled");
        }
        this.momentOrder = momentOrder;
        updateStatus();
    }

    public Operation getOperation() {
        return operation;
    }
    public void setOperation(final Operation operation) {
        if (this.operation != operation) {
            logger.log(String.format(LogMessages.OPERATION_WAS_CHANGED, operation.toString()));

            this.operation = operation;
            isMomentOrderEnabled = operation.is(ComputableWithMomentOrder.class);
            updateStatus();
        }
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status.toString();
    }

    public boolean isMomentOrderEnabled() {
        return isMomentOrderEnabled;
    }
    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }
}

