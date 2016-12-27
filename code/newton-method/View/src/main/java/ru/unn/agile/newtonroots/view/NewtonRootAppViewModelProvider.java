package ru.unn.agile.newtonroots.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.unn.agile.newtonroots.infrastructure.PlainTextLogger;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootAppViewModel;

import java.io.IOException;

public class NewtonRootAppViewModelProvider {
    private final ReadOnlyObjectProperty<NewtonRootAppViewModel> viewModel;

    public NewtonRootAppViewModelProvider() {
        PlainTextLogger logger;
        try {
            logger = new PlainTextLogger("newton-method.log");
        } catch (IOException e) {
            logger = null; // TODO: replace with in-memory logger
        }

        viewModel = new SimpleObjectProperty<>(new NewtonRootAppViewModel(logger));
    }

    public ReadOnlyObjectProperty<NewtonRootAppViewModel> viewModelProperty() {
        return viewModel;
    }

    public NewtonRootAppViewModel getViewModel() {
        return viewModel.get();
    }
}
