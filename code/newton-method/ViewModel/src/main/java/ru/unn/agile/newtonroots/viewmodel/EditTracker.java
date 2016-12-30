package ru.unn.agile.newtonroots.viewmodel;

import java.util.function.Consumer;

public class EditTracker<T> {
    private boolean isTracking;
    private T initialValue;
    private T currentValue;
    private Consumer<T> trackingFinishHandler;

    public boolean isTracking() {
        return isTracking;
    }

    public void startTracking(final T initialValue, final Consumer<T> handler) {
        isTracking = true;
        this.initialValue = initialValue;
        this.currentValue = initialValue;
        this.trackingFinishHandler = handler;
    }

    public T getInitialValue() {
        return initialValue;
    }

    public void updateValue(final T newValue) {
        this.currentValue = newValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public boolean valueChanged() {
        return !(currentValue.equals(initialValue));
    }

    public void finishTracking() {
        if (isTracking && trackingFinishHandler != null && valueChanged()) {
            trackingFinishHandler.accept(currentValue);
        }

        isTracking = false;
        initialValue = null;
        currentValue = null;
    }
}
