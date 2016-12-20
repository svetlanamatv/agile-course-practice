package ru.unn.agile.PersonalFinance.ViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class WhenSavingCategory {
    private CategoriesManagerViewModel categoriesManger;

    @Before
    public void setUp() throws Exception {
        categoriesManger = new CategoriesManagerViewModel();
    }

    @Test
    public void andItIsAddedToCategoriesList() throws Exception {
        final String newCategoryName = "Clothes";

        categoriesManger.setNewCategoryName(newCategoryName);
        categoriesManger.saveCategory();

        boolean isCategoryFound = checkManagerHasCategoryWithName(newCategoryName);
        assertTrue(isCategoryFound);
    }

    private boolean checkManagerHasCategoryWithName(final String categoryName) {
        List<CategoryViewModel> categories = categoriesManger.getCategories();
        return categories.stream().anyMatch(category ->
                category.getName().equals(categoryName));
    }
}
