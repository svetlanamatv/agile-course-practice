package ru.unn.agile.PersonalFinance.ViewModel.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class SavableViewModelObject {
    private final BooleanProperty ableToSaveProperty = new SimpleBooleanProperty();
    private final BooleanProperty changingProperty = new SimpleBooleanProperty();
    private final BooleanProperty deletedProperty = new SimpleBooleanProperty();

    private boolean isSaved;

    protected BooleanProperty ableToSaveMutableProperty() {
        return ableToSaveProperty;
    }

    // region Properties

    public final ReadOnlyBooleanProperty ableToSaveProperty() {
        return ableToSaveProperty;
    }

    public final boolean isAbleToSave() {
        return ableToSaveProperty.get();
    }

    protected final void setIsAbleToSave(final boolean isAbleToSave) {
        ableToSaveProperty.set(isAbleToSave);
    }

    public final ReadOnlyBooleanProperty changingProperty() {
        return changingProperty;
    }

    public final boolean isChanging() {
        return changingProperty.get();
    }

    private void setIsChanging(final boolean isChanging) {
        changingProperty.set(isChanging);
    }

    public final ReadOnlyBooleanProperty deletedProperty() {
        return deletedProperty;
    }

    public final boolean isDeleted() {
        return deletedProperty.get();
    }

    // endregion

    protected final void markAsSaved() {
        isSaved = true;
    }

    protected final void markAsDeleted() {
        deletedProperty.set(true);
    }

    public final boolean isSaved() {
        return isSaved;
    }

    public final void save() {
        if (!isAbleToSave()) {
            throw new UnsupportedOperationException("Object is in the "
                    + "unsavable state, isAbleToSave property is false");
        }

        checkForDeletion();
        if (isSaved) {
            updateInternal();
        } else {
            saveInternal();
            markAsSaved();
        }
        setIsChanging(false);
    }

    public final void delete() {
        checkForDeletion();

        if (!isSaved) {
            throw new UnsupportedOperationException(
                    "Object should be saved before deletion");
        }

        if (isChanging()) {
            throw new UnsupportedOperationException(
                    "Object is in the modifiable state, save or revert "
                    + "changes before deletion");
        }

        deleteInternal();
        markAsDeleted();
    }

    public final void startChanging() {
        checkForDeletion();
        if (isSaved) {
            saveState();
            setIsChanging(true);
        }
    }

    public final void revertChanges() {
        checkForDeletion();
        if (isChanging()) {
            recoverState();
            setIsChanging(false);
        }
    }

    protected abstract void saveInternal();
    protected abstract void updateInternal();
    protected abstract void deleteInternal();
    protected abstract void saveState();
    protected abstract void recoverState();

    private void checkForDeletion() {
        if (isDeleted()) {
            throw new UnsupportedOperationException("Object has already been deleted");
        }
    }
}
