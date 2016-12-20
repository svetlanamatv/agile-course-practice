package ru.unn.agile.personalfinance.view.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DataContextController<T> implements Initializable {
    private T dataContext;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // Empty implementation
    }

    public final void setDataContext(final T dataContext) {
        if (this.dataContext != dataContext) {
            T oldDataContext = this.dataContext;
            this.dataContext = dataContext;
            updateDataContext(oldDataContext, dataContext);
        }
    }

    protected final T getDataContext() {
        return dataContext;
    }

    protected void updateDataContext(final T oldDataContext,
                                     final T newDataContext) {
        if (oldDataContext != null) {
            removeBindings(oldDataContext);
        }

        if (newDataContext != null) {
            addBindings(newDataContext);
        }
    }

    protected void removeBindings(final T oldDataContext) {
        // Empty implementation
    }

    protected void addBindings(final T newDataContext) {
        // Empty implementation
    }
}
