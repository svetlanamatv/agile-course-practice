package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Test;

import static ru.unn.agile.PersonalFinance.ViewModel.AssertHelper.assertNotContains;

public class WhenDeletingCategory {
    private static final String NEW_CATEGORY_NAME = "New category";

    @Test
    public void andItIsDeletedFromCategoriesList() throws Exception {
        CategoriesManagerViewModel categoriesManager = new CategoriesManagerViewModel();
        categoriesManager.setNewCategoryName(NEW_CATEGORY_NAME);
        CategoryViewModel addedCategory = categoriesManager.saveCategory();

        categoriesManager.setSelectedCategory(addedCategory);
        categoriesManager.deleteSelectedCategory();

        assertNotContains(categoriesManager.getCategories(), addedCategory);
    }
}
