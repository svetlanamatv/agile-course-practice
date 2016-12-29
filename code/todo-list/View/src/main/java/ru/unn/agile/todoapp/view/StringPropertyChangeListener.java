package ru.unn.agile.todoapp.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

class StringPropertyChangeListener implements ChangeListener<String> {
    private String prevValue = new String();
    private String curValue = new String();
    @Override
    public void changed(final ObservableValue<? extends String> observable,
                        final String oldValue, final String newValue) {
        if (!oldValue.equals(newValue)) {
            curValue = newValue;
        }
    }
    public boolean isChanged() {
        return !prevValue.equals(curValue);
    }
    public void cache() {
        prevValue = curValue;
    }
}
