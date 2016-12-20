package ru.unn.agile.personalfinance.view.controllers;

import com.cathive.fonts.fontawesome.FontAwesomeIcon;
import com.cathive.fonts.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.converter.CurrencyStringConverter;
import ru.unn.agile.PersonalFinance.ViewModel.TransactionViewModel;
import ru.unn.agile.personalfinance.view.utils.Converters;

public class TransactionListCellController extends DataContextController {

    @FXML
    private FontAwesomeIconView directionIcon;

    @FXML
    private Label counterpartyLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private GridPane root;

    private final ChangeListener<Boolean> styleUpdater =
            (observable, oldValue, newValue) -> updateStyleClasses();

    @Override
    protected void addBindings(final Object newDataContext) {
        TransactionViewModel transaction = (TransactionViewModel) newDataContext;

        transaction.isIncomeProperty().addListener(styleUpdater);
        transaction.counterpartyMarkedAsDeletedProperty().addListener(styleUpdater);
        updateStyleClasses();

        /* amountLabel.text <-> transaction.amount */
        Bindings.bindBidirectional(
                amountLabel.textProperty(),
                transaction.amountProperty(),
                new CurrencyStringConverter());

        /* transaction.date <-> dateLabel.text */
        Bindings.bindBidirectional(
                dateLabel.textProperty(),
                transaction.dateProperty(),
                Converters.getLocalDateToStringConverter());

        /* transaction.counterparty -> counterpartyLabel.text */
        counterpartyLabel.textProperty().bind(transaction.displayCounterpartyProperty());

        /* transaction.description -> descriptionLabel.text */
        descriptionLabel.textProperty().bind(transaction.displayTitleProperty());
    }

    @Override
    protected void removeBindings(final Object oldDataContext) {
        TransactionViewModel transaction = (TransactionViewModel) oldDataContext;

        transaction.isIncomeProperty().removeListener(styleUpdater);
        transaction.deletedProperty().removeListener(styleUpdater);

        descriptionLabel.textProperty().unbind();
        counterpartyLabel.textProperty().unbind();

        Bindings.unbindBidirectional(
                amountLabel.textProperty(),
                transaction.amountProperty());

        Bindings.unbindBidirectional(
                dateLabel.textProperty(),
                transaction.dateProperty());
    }

    private void updateStyleClasses() {
        TransactionViewModel transaction = (TransactionViewModel) getDataContext();

        root.getStyleClass().clear();

        if (transaction.isIncome()) {
            root.getStyleClass().add("income");
            directionIcon.setIcon(FontAwesomeIcon.ICON_ARROW_LEFT);
        } else {
            root.getStyleClass().add("expense");
            directionIcon.setIcon(FontAwesomeIcon.ICON_ARROW_RIGHT);
        }

        if (transaction.isCounterpartyMarkedAsDeleted()) {
            root.getStyleClass().add("deleted");
        } else {
            root.getStyleClass().add("normal");
        }
    }
}
