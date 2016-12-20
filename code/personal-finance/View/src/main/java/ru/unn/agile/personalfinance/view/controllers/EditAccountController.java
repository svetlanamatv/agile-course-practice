package ru.unn.agile.personalfinance.view.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.unn.agile.PersonalFinance.ViewModel.AccountViewModel;
import ru.unn.agile.personalfinance.view.WindowsManager;
import ru.unn.agile.personalfinance.view.controls.CurrencyTextField;

public class EditAccountController extends DataContextController<AccountViewModel> {
    @FXML
    private JFXTextField nameField;

    @FXML
    private CurrencyTextField balanceField;

    @FXML
    private Button addButton;

    @FXML
    private void handleAddButton(final ActionEvent actionEvent) {
        getDataContext().save();
        WindowsManager.getInstance().goBack();
    }

    @FXML
    private void handleCancelButton(final ActionEvent actionEvent) {
        WindowsManager.getInstance().goBack();
    }

    @Override
    protected void removeBindings(final AccountViewModel account) {
        Bindings.unbindBidirectional(nameField.textProperty(), account.nameProperty());
        Bindings.unbindBidirectional(balanceField.textProperty(), account.balanceProperty());
        balanceField.disableProperty().unbind();
        addButton.disableProperty().unbind();

        account.revertChanges();
    }

    @Override
    protected void addBindings(final AccountViewModel account) {
        account.startChanging();

        /* nameField.text <-> account.name */
        Bindings.bindBidirectional(nameField.textProperty(), account.nameProperty());

        /* balanceField.value <-> account.balance */
        Bindings.bindBidirectional(
                balanceField.valueProperty(),
                account.balanceProperty());

        /* account.changingProperty -> balanceField.disabled */
        balanceField.disableProperty().bind(account.changingProperty());

        /* account.isAbleToSave -> addButton.disabled */
        addButton.disableProperty().bind(Bindings.or(
                account.ableToSaveProperty().not(),
                balanceField.valueValidProperty().not()));
    }
}
