package ru.unn.agile.newtonroots.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootsViewModel;
import ru.unn.agile.newtonroots.model.StoppingCriterion;

public class NewtonRootsApp  {
    @FXML
    private NewtonRootsViewModelProvider viewModelProvider;
    @FXML
    private TextField leftPointText;
    @FXML
    private TextField rightPointText;
    @FXML
    private TextField derivativeStepText;
    @FXML
    private TextField accuracyText;
    @FXML
    private TextField functionText;
    @FXML
    private Button findRootButton;
    @FXML
    private TextField startPointText;
    @FXML
    private ChoiceBox<StoppingCriterion> stopCriterionSelector;

    @FXML
    private void initialize() {
        NewtonRootsViewModel viewModel = viewModelProvider.getViewModel();

        leftPointText.textProperty().bindBidirectional(viewModel.leftPointProperty());
        rightPointText.textProperty().bindBidirectional(viewModel.rightPointProperty());
        derivativeStepText.textProperty().bindBidirectional(viewModel.derivativeStepProperty());
        accuracyText.textProperty().bindBidirectional(viewModel.accuracyProperty());
        functionText.textProperty().bindBidirectional(viewModel.functionProperty());
        startPointText.textProperty().bindBidirectional(viewModel.startPointProperty());

        stopCriterionSelector.valueProperty().bindBidirectional(viewModel.stopCriterionProperty());

        findRootButton.setOnAction(value -> viewModel.findRoot());

        leftPointText.focusedProperty().addListener(new FocusLossListener(viewModel));
        rightPointText.focusedProperty().addListener(new FocusLossListener(viewModel));
        derivativeStepText.focusedProperty().addListener(new FocusLossListener(viewModel));
        accuracyText.focusedProperty().addListener(new FocusLossListener(viewModel));
        functionText.focusedProperty().addListener(new FocusLossListener(viewModel));
        startPointText.focusedProperty().addListener(new FocusLossListener(viewModel));
    }

    @FXML
    private void finishEdit() {
        viewModelProvider.getViewModel().finishEdit();
    }


    private class FocusLossListener implements ChangeListener<Boolean> {
        private final NewtonRootsViewModel viewModel;

        FocusLossListener(final NewtonRootsViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public void changed(final ObservableValue<? extends Boolean> observable,
                            final Boolean wasFocused, final Boolean nowFocused) {
            if (!nowFocused) {
                viewModel.finishEdit();
            }
        }
    }
}
