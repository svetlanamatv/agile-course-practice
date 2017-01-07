package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhenCreatingCategory {
    private CategoriesManagerViewModel categoriesManager;

    @Before
    public void setUp() throws Exception {
        categoriesManager = new CategoriesManagerViewModel();
    }

    @Test
    public void andItCanNotBeAddedIfNameIsNull() throws Exception {
        categoriesManager.setNewCategoryName(null);

        assertFalse(categoriesManager.isAbleToAddNewCategory());
    }

    @Test
    public void andItCanNotBeAddedIfNameIsEmpty() throws Exception {
        categoriesManager.setNewCategoryName("  ");

        assertFalse(categoriesManager.isAbleToAddNewCategory());
    }

    @Test
    public void andItCanNotBeAddedIfCategoryWithSameNameExists() throws Exception {
        final String sameCategoryName = "Travels";
        categoriesManager.setNewCategoryName(sameCategoryName);
        categoriesManager.saveCategory();

        categoriesManager.setNewCategoryName(" " + sameCategoryName + " ");

        assertFalse(categoriesManager.isAbleToAddNewCategory());
    }

    @Test
    public void andItCanBeAddedIfCategoryNameUnique() throws Exception {
        categoriesManager.setNewCategoryName("Gifts");
        categoriesManager.saveCategory();

        categoriesManager.setNewCategoryName("Clothes");

        assertTrue(categoriesManager.isAbleToAddNewCategory());
    }

    @Test
    public void andAbleToAddNewCategoryPropertyIsNotNull() throws Exception {
        assertNotNull(categoriesManager.ableToAddNewCategoryProperty());
    }

    @Test
    public void andCategoriesManagerIsNotNull() throws Exception {
        assertNotNull(categoriesManager.categoriesProperty());
    }

    @Test
    public void andNewCategoryNamePropertyIsNotNull() throws Exception {
        assertNotNull(categoriesManager.newCategoryNameProperty());
    }

    @Test
    public void andSelectedCategoryPropertyIsNotNull() throws Exception {
        assertNotNull(categoriesManager.selectedCategoryProperty());
    }
}
