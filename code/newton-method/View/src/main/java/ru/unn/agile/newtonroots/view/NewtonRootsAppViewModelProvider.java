package ru.unn.agile.newtonroots.view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.unn.agile.newtonroots.infrastructure.PlainTextLogger;
import ru.unn.agile.newtonroots.viewmodel.Logger;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootAppViewModel;
import ru.unn.agile.newtonroots.viewmodel.TimestampingInMemoryLogger;

import java.io.IOException;

public class NewtonRootsAppViewModelProvider {
    private final ReadOnlyObjectProperty<NewtonRootAppViewModel> viewModel;

    public NewtonRootsAppViewModelProvider() {
        Logger logger;
        try {
            logger = new PlainTextLogger("newton-method.log");
        } catch (IOException e) {
            logger = new TimestampingInMemoryLogger();
            logger.appendMessage("Unable to create `newton-method.log`. " +
                    "Log of this run won't be saved on hard drive");
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
