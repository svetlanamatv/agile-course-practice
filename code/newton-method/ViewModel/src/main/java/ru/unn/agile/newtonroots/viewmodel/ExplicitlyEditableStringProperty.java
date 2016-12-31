package ru.unn.agile.newtonroots.viewmodel;

import javafx.beans.property.SimpleStringProperty;

import java.util.function.Consumer;

public class ExplicitlyEditableStringProperty extends SimpleStringProperty {
    private boolean beingEdited;
    private String valueBeforeEdit;
    private final Consumer<String> editFinishHandler;

    public ExplicitlyEditableStringProperty(final String initialValue,
                                            final Consumer<String> editFinishHandler) {
        super(initialValue);
        beingEdited = false;
        this.editFinishHandler = editFinishHandler;
    }

    public boolean isBeingEdited() {
        return beingEdited;
    }

    @Override
    public void set(final String newValue) {
        if (!beingEdited) {
            throw new SetOusideOfEditException(
                    "StringProperty can not be set without starting an edit first");
        }

        super.set(newValue);
    }

    public void startEdit() {
        beingEdited = true;
        valueBeforeEdit = get();
    }

    public String getValueBeforeEdit() {
        return valueBeforeEdit;
    }

    public void finishEdit() {
        if (isBeingEdited() && !get().equals(valueBeforeEdit)) {
            editFinishHandler.accept(get());
        }

        beingEdited = false;
        valueBeforeEdit = null;
    }

    public class SetOusideOfEditException extends RuntimeException {
        public SetOusideOfEditException(final String message) {
            super(message);
        }
    }
}
