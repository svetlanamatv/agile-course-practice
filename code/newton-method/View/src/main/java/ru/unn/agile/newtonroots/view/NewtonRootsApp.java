package ru.unn.agile.newtonroots.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.newtonroots.viewmodel.ExplicitlyEditableStringProperty;
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
        functionText.textProperty().bindBidirectional(viewModel.functionExpressionProperty());
        startPointText.textProperty().bindBidirectional(viewModel.startPointProperty());

        stopCriterionSelector.valueProperty().bindBidirectional(viewModel.stopCriterionProperty());

        findRootButton.setOnAction(value -> viewModel.findRoot());

        leftPointText.focusedProperty().addListener(
                new TextFieldFocusChangeListener(viewModel.leftPointProperty()));
        rightPointText.focusedProperty().addListener(
                new TextFieldFocusChangeListener(viewModel.rightPointProperty()));
        derivativeStepText.focusedProperty().addListener(
                new TextFieldFocusChangeListener(viewModel.derivativeStepProperty()));
        accuracyText.focusedProperty().addListener(
                new TextFieldFocusChangeListener(viewModel.accuracyProperty()));
        functionText.focusedProperty().addListener(
                new TextFieldFocusChangeListener(viewModel.functionExpressionProperty()));
        startPointText.focusedProperty().addListener(
                new TextFieldFocusChangeListener(viewModel.startPointProperty()));
    }

    @FXML
    private void finishEdit() {
        viewModelProvider.getViewModel().finishAllEdits();
    }


    private class TextFieldFocusChangeListener implements ChangeListener<Boolean> {
        private final ExplicitlyEditableStringProperty property;

        TextFieldFocusChangeListener(final ExplicitlyEditableStringProperty property) {
            this.property = property;
        }

        @Override
        public void changed(final ObservableValue<? extends Boolean> observable,
                            final Boolean wasFocused, final Boolean nowFocused) {
            boolean focusReceived = !wasFocused && nowFocused;
            boolean focusLost = wasFocused && !nowFocused;
            if (focusReceived) {
                property.startEdit();
            } else if (focusLost) {
                property.finishEdit();
            }
        }
    }
}
