package ru.unn.agile.todoapp.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

class StateSavingStringChangeListener implements ChangeListener<String> {
    private String previousState = new String();
    private String currentState = new String();

    @Override
    public void changed(final ObservableValue<? extends String> observable,
                        final String oldValue, final String newValue) {
        if (!oldValue.equals(newValue)) {
            currentState = newValue;
        }
    }
    public boolean isStringContentChanged() {
        return !previousState.equals(currentState);
    }
    public void saveState() {
        previousState = currentState;
    }
}
