package ru.unn.agile.PersonalFinance.ViewModel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.PersonalFinance.ViewModel.utils.StringHelper;

import java.util.ArrayList;

public class CategoriesManagerViewModel {
    private final StringProperty newCategoryNameProperty = new SimpleStringProperty();
    private final BooleanProperty ableToAddNewCategoryProperty = new SimpleBooleanProperty();

    private final ListProperty<CategoryViewModel> categoriesProperty =
            new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    private final ObjectProperty<CategoryViewModel> selectedCategoryProperty =
            new SimpleObjectProperty<>();

    CategoriesManagerViewModel() {
        makeDefaultCategories();
        setUpBindings();
    }

    // region Properties

    public final StringProperty newCategoryNameProperty() {
        return newCategoryNameProperty;
    }

    public final String getNewCategoryName() {
        return newCategoryNameProperty.get();
    }

    public final void setNewCategoryName(final String newCategoryName) {
        newCategoryNameProperty.set(newCategoryName);
    }

    public final ReadOnlyBooleanProperty ableToAddNewCategoryProperty() {
        return ableToAddNewCategoryProperty;
    }

    public final boolean isAbleToAddNewCategory() {
        return ableToAddNewCategoryProperty.get();
    }

    public final ReadOnlyListProperty<CategoryViewModel> categoriesProperty() {
        return categoriesProperty;
    }

    public final ObservableList<CategoryViewModel> getCategories() {
        return categoriesProperty.get();
    }

    public final ObjectProperty<CategoryViewModel> selectedCategoryProperty() {
        return selectedCategoryProperty;
    }

    public final CategoryViewModel getSelectedCategory() {
        return selectedCategoryProperty.get();
    }

    public final void setSelectedCategory(final CategoryViewModel category) {
        selectedCategoryProperty.set(category);
    }

    // endregion

    public CategoryViewModel saveCategory() {
        String newCategoryName = getNewCategoryName().trim();
        CategoryViewModel newCategory = new CategoryViewModel(newCategoryName);
        categoriesProperty.add(newCategory);
        setNewCategoryName(null);
        return newCategory;
    }

    public void deleteSelectedCategory() {
        if (getSelectedCategory() != null) {
            categoriesProperty.remove(getSelectedCategory());
        }
    }

    private void setUpBindings() {
        BooleanBinding isCategoryExists = Bindings.createBooleanBinding(() ->
            hasCategoryWithName(getNewCategoryName()), newCategoryNameProperty);

        BooleanBinding isNewCategoryNameEmpty =
                StringHelper.isNullOrEmpty(newCategoryNameProperty);

        ableToAddNewCategoryProperty.bind(Bindings.and(
                isNewCategoryNameEmpty.not(),
                isCategoryExists.not()));
    }

    private boolean hasCategoryWithName(final String categoryName) {
        return categoriesProperty.stream().anyMatch(category ->
                StringHelper.areEqualTrimmed(category.getName(), categoryName));
    }

    private void makeDefaultCategories() {
        String[] categoryNames = {
                "Bad debts",
                "Business taxes, fees, licences",
                "Delivery, freight, and express",
                "Fuel costs",
                "Insurance",
                "Interest",
                "Maintenance and repairs",
                "Management and administration fees",
                "Meals and entertainment",
                "Motor vehicle expenses (automobile)",
                "Office expenses",
                "Prepaid expenses",
                "Property taxes",
                "Rent",
                "Salaries, wages, and benefits",
                "Supplies",
                "Telephone and utilities",
                "Travel",
                "Other expenses"
        };
        for (String name : categoryNames) {
            categoriesProperty.add(new CategoryViewModel(name));
        }
    }
}
