package ru.unn.agile.triangle.view.services;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.unn.agile.triangle.viewmodel.ViewModel;

public class ViewModelProvider {
    private final ObjectProperty<ViewModel> viewModelProperty
            = new SimpleObjectProperty<>();

    public ViewModelProvider() {
        ViewModel viewModel = new ViewModel(LoggerService.getLogger());
        viewModelProperty.set(viewModel);
    }

    public final ReadOnlyObjectProperty<ViewModel> viewModelProperty() {
        return viewModelProperty;
    }

    public final ViewModel getViewModel() {
        return viewModelProperty.get();
    }
}
