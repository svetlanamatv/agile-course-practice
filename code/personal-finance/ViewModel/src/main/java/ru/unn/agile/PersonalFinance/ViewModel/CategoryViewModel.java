package ru.unn.agile.PersonalFinance.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.PersonalFinance.Model.Category;

public class CategoryViewModel {
    private Category internalCategory;
    private final StringProperty nameProperty = new SimpleStringProperty();

    CategoryViewModel(final String name) {
        setName(name);
    }

    // region Properties

    public final StringProperty nameProperty() {
        return nameProperty;
    }

    public final String getName() {
        return nameProperty.get();
    }

    public final void setName(final String name) {
        nameProperty.set(name);
    }

    // endregion

    Category getModelCategory() {
        if (internalCategory == null) {
            internalCategory = new Category(getName());
        }
        return internalCategory;
    }
}
