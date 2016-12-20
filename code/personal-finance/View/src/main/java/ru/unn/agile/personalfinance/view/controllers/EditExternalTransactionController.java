package ru.unn.agile.personalfinance.view.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.PersonalFinance.ViewModel.CategoryViewModel;
import ru.unn.agile.PersonalFinance.ViewModel.ExternalTransactionViewModel;
import ru.unn.agile.PersonalFinance.ViewModel.LedgerViewModel;
import ru.unn.agile.personalfinance.view.ViewModelService;
import ru.unn.agile.personalfinance.view.WindowsManager;
import ru.unn.agile.personalfinance.view.controls.CurrencyTextField;
import ru.unn.agile.personalfinance.view.controls.StringListCellFactory;
import ru.unn.agile.personalfinance.view.utils.Converters;

import java.net.URL;
import java.util.ResourceBundle;

public class EditExternalTransactionController
        extends DataContextController<ExternalTransactionViewModel> {

    private static final StringListCellFactory<CategoryViewModel> CATEGORY_LIST_CELL_FACTORY =
            new StringListCellFactory<>(category -> category.getName());

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private Button addButton;

    @FXML
    private TextField counterpartyField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<CategoryViewModel> categoryComboBox;

    @FXML
    private CurrencyTextField transactionAmountField;

    @FXML
    private JFXToggleButton incomeToggleButton;

    @FXML
    private void handleAddButtonAction(final ActionEvent actionEvent) {
        getDataContext().save();
        WindowsManager.getInstance().goBack();
    }

    @FXML
    private void handleCancelButtonAction(final ActionEvent actionEvent) {
        WindowsManager.getInstance().goBack();
    }

    @FXML
    private void handleMangeButtonAction(final ActionEvent actionEvent) {
        WindowsManager.getInstance().showEditCategoriesView();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        categoryComboBox.setCellFactory(CATEGORY_LIST_CELL_FACTORY);
        categoryComboBox.setConverter(Converters.getCategoryToStringConverter());

        /* categories -> categoryComboBox.items */
        LedgerViewModel ledger = ViewModelService.getViewModel();
        ReadOnlyListProperty<CategoryViewModel> categories =
                ledger.getCategoriesManager().categoriesProperty();
        categoryComboBox.itemsProperty().bind(categories);
    }

    @Override
    protected void addBindings(final ExternalTransactionViewModel transaction) {
        transaction.startChanging();

        /* transactionAmountField.value <-> transaction.amount */
        Bindings.bindBidirectional(
                transactionAmountField.valueProperty(),
                transaction.amountProperty());

        /* incomeToggleButton.isSelected <-> transaction.isIncome */
        Bindings.bindBidirectional(
                incomeToggleButton.selectedProperty(),
                transaction.isIncomeProperty());

        /* descriptionTextArea.text <-> transaction.description */
        Bindings.bindBidirectional(
                descriptionTextArea.textProperty(),
                transaction.descriptionProperty());

        /* categoryComboBox.selectedItem -> transaction.category */
        ObjectProperty<CategoryViewModel> categoryProperty = transaction.categoryProperty();
        categoryProperty.bind(categoryComboBox.getSelectionModel().selectedItemProperty());

        /* counterpartyField.text <-> transaction.counterparty */
        Bindings.bindBidirectional(
                counterpartyField.textProperty(),
                transaction.counterpartyProperty());

        /* datePicker.value <-> transaction.date */
        Bindings.bindBidirectional(
                datePicker.valueProperty(),
                transaction.dateProperty());

        /* !transaction.isAbleToSave -> addButton.disabled  */
        addButton.disableProperty().bind(Bindings.or(
                transaction.ableToSaveProperty().not(),
                transactionAmountField.valueValidProperty().not()));

        if (transaction.getCategory() == null) {
            categoryComboBox.getSelectionModel().selectFirst();
        } else {
            categoryComboBox.getSelectionModel().select(transaction.getCategory());
        }
    }

    @Override
    protected void removeBindings(final ExternalTransactionViewModel transaction) {
        Bindings.unbindBidirectional(
                transactionAmountField.textProperty(),
                transaction.amountProperty());

        Bindings.unbindBidirectional(
                incomeToggleButton.selectedProperty(),
                transaction.isIncomeProperty());

        Bindings.unbindBidirectional(
                descriptionTextArea.textProperty(),
                transaction.descriptionProperty());

        transaction.categoryProperty().unbind();

        Bindings.unbindBidirectional(
                counterpartyField.textProperty(),
                transaction.counterpartyProperty());

        Bindings.unbindBidirectional(
                datePicker.valueProperty(),
                transaction.dateProperty());

        addButton.disableProperty().unbind();

        transaction.revertChanges();
    }
}
