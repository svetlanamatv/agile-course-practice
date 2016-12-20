package ru.unn.agile.personalfinance.view.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import ru.unn.agile.PersonalFinance.ViewModel.AccountViewModel;
import ru.unn.agile.PersonalFinance.ViewModel.LedgerViewModel;
import ru.unn.agile.PersonalFinance.ViewModel.TransferViewModel;
import ru.unn.agile.personalfinance.view.ViewModelService;
import ru.unn.agile.personalfinance.view.WindowsManager;
import ru.unn.agile.personalfinance.view.controls.CurrencyTextField;
import ru.unn.agile.personalfinance.view.controls.StringListCellFactory;
import ru.unn.agile.personalfinance.view.utils.Converters;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTransferController extends DataContextController<TransferViewModel> {
    private static final StringListCellFactory<AccountViewModel> ACCOUNT_LIST_CELL_FACTORY =
            new StringListCellFactory<>(account -> account.getName());

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox<AccountViewModel> accountFromComboBox;

    @FXML
    private JFXComboBox<AccountViewModel> accountToComboBox;

    @FXML
    private CurrencyTextField amountField;

    @FXML
    private Button addButton;

    @FXML
    private void handleSaveTransferButtonAction(final ActionEvent actionEvent) {
        getDataContext().save();
        WindowsManager.getInstance().goBack();
    }

    @FXML
    private void handleCancelButtonAction(final ActionEvent actionEvent) {
        WindowsManager.getInstance().goBack();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setUpAccountComboBox(accountFromComboBox);
        setUpAccountComboBox(accountToComboBox);
    }

    @Override
    protected void removeBindings(final TransferViewModel transfer) {
        Bindings.unbindBidirectional(
                amountField.textProperty(),
                transfer.amountProperty());

        transfer.accountFromProperty().unbind();

        transfer.accountToProperty().unbind();

        Bindings.unbindBidirectional(
                datePicker.valueProperty(),
                transfer.dateProperty());

        addButton.disableProperty().unbind();

        transfer.revertChanges();
    }

    @Override
    protected void addBindings(final TransferViewModel transfer) {
        transfer.startChanging();

        /* ledger.accounts -> accountFromComboBox.items */
        LedgerViewModel ledger = ViewModelService.getViewModel();
        accountFromComboBox.itemsProperty().bind(ledger.accountsProperty());

        /* ledger.accounts -> accountToComboBox.items */
        accountToComboBox.itemsProperty().bind(ledger.accountsProperty());

        /* amountField.value <-> transfer.amount */
        Bindings.bindBidirectional(
                amountField.valueProperty(),
                transfer.amountProperty());

        /* accountFromComboBox.selected -> transfer.accountFrom */
        ReadOnlyObjectProperty<AccountViewModel> selectedAccountFromProperty =
                accountFromComboBox.getSelectionModel().selectedItemProperty();
        transfer.accountFromProperty().bind(selectedAccountFromProperty);

        /* accountFromComboBox.selected -> transfer.accountTo */
        ReadOnlyObjectProperty<AccountViewModel> selectedAccountToProperty =
                accountToComboBox.getSelectionModel().selectedItemProperty();
        transfer.accountToProperty().bind(selectedAccountToProperty);

        /* datePicker.value <-> transfer.date */
        Bindings.bindBidirectional(
                datePicker.valueProperty(),
                transfer.dateProperty());

        /* transfer.isAbleToSave -> addButton.disabled */
        addButton.disableProperty().bind(Bindings.or(
                transfer.ableToSaveProperty().not(),
                amountField.valueValidProperty().not()));

        /* transfer.changing -> accountToComboBox.disabled */
        accountToComboBox.disableProperty().bind(transfer.changingProperty());

        /* transfer.changing -> accountFromComboBox.disabled */
        accountFromComboBox.disableProperty().bind(transfer.changingProperty());

        if (transfer.getSourceAccount() == null && transfer.getTargetAccount() == null) {
            accountFromComboBox.getSelectionModel().select(0);
            accountToComboBox.getSelectionModel().select(1);
        } else {
            accountFromComboBox.getSelectionModel().select(transfer.getSourceAccount());
            accountToComboBox.getSelectionModel().select(transfer.getTargetAccount());
        }
    }

    private void setUpAccountComboBox(final ComboBox<AccountViewModel> comboBox) {
        comboBox.setCellFactory(ACCOUNT_LIST_CELL_FACTORY);
        comboBox.setConverter(Converters.getAccountToStringConverter());
    }
}
