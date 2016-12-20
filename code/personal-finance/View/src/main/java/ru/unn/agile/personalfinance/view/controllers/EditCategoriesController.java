package ru.unn.agile.personalfinance.view.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ru.unn.agile.PersonalFinance.ViewModel.CategoriesManagerViewModel;
import ru.unn.agile.PersonalFinance.ViewModel.CategoryViewModel;
import ru.unn.agile.personalfinance.view.ViewModelService;
import ru.unn.agile.personalfinance.view.controls.StringListCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCategoriesController extends DataContextController {
    private static final StringListCellFactory<CategoryViewModel> CATEGORY_LIST_CELL_FACTORY =
            new StringListCellFactory<>(category -> category.getName());

    private final CategoriesManagerViewModel categoriesManager =
            ViewModelService.getViewModel().getCategoriesManager();

    @FXML
    private JFXTextField newCategoryField;

    @FXML
    private JFXButton addCategoryButton;

    @FXML
    private JFXListView<CategoryViewModel> categoriesList;

    @FXML
    private void handleAddCategoryButton(final ActionEvent actionEvent) {
        categoriesManager.saveCategory();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        categoriesList.setCellFactory(CATEGORY_LIST_CELL_FACTORY);

        /* categoriesList.selectedItem -> categoriesManager.selectedCategory */
        ReadOnlyObjectProperty<CategoryViewModel> selectedItemProperty =
                categoriesList.getSelectionModel().selectedItemProperty();
        categoriesManager.selectedCategoryProperty().bind(selectedItemProperty);

        /* categoriesManager.categories -> categoriesList.items */
        categoriesList.itemsProperty().bind(categoriesManager.categoriesProperty());

        /* categoriesManager.isAbleToAddNewCategory -> addCategoryButton.disabled */
        addCategoryButton.disableProperty().bind(
                categoriesManager.ableToAddNewCategoryProperty().not());

        /* categoriesManager.newCategoryName <-> newCategoryField.text */
        Bindings.bindBidirectional(newCategoryField.textProperty(),
                categoriesManager.newCategoryNameProperty());
    }

    @FXML
    private void handleDeleteCategoryAction(final ActionEvent actionEvent) {
        categoriesManager.deleteSelectedCategory();
    }
}
