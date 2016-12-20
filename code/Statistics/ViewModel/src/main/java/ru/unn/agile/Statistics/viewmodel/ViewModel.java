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
    static final Operation DEFAULT_OPERATION = Operation.VAR;

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
        public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
        public static final String DELTA_WAS_CHANGED = "Delta was changed to ";
        public static final String COUNT_SAMPLES_WAS_CHANGED = "Count of samples was changed to ";

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


    public double[] getValues() {
        return values;
    }
    public void setValues(final double[] values) {
        this.values = values;
        updateStatus();
    }

    public double[] getPossibilities() {
        return possibilities;
    }
    public void setPossibilities(final double[] possibilities) {
        this.possibilities = possibilities;
        updateStatus();
    }

    public void setArraysSize(final int arraysSize) {
        final int size = max(arraysSize, 0);

        if (values.length != size) {
            logger.log(LogMessages.COUNT_SAMPLES_WAS_CHANGED + Integer.toString(arraysSize));
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
        if (this.delta != delta) {
            logger.log(LogMessages.DELTA_WAS_CHANGED + delta);
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
            logger.log(LogMessages.OPERATION_WAS_CHANGED + operation.toString());

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

