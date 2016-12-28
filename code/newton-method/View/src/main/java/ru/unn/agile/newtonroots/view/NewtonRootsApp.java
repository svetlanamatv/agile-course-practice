package ru.unn.agile.newtonroots.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.newtonroots.viewmodel.NewtonRootAppViewModel;
import ru.unn.agile.newtonroots.model.NewtonMethod.StoppingCriterion;

public class NewtonRootsApp  {
    @FXML
    private NewtonRootAppViewModelProvider viewModelProvider;
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
        NewtonRootAppViewModel viewModel = viewModelProvider.getViewModel();

        leftPointText.textProperty().bindBidirectional(viewModel.leftPointProperty());
        rightPointText.textProperty().bindBidirectional(viewModel.rightPointProperty());
        derivativeStepText.textProperty().bindBidirectional(viewModel.derivativeStepProperty());
        accuracyText.textProperty().bindBidirectional(viewModel.accuracyProperty());
        functionText.textProperty().bindBidirectional(viewModel.functionProperty());
        startPointText.textProperty().bindBidirectional(viewModel.startPointProperty());

        stopCriterionSelector.valueProperty().bindBidirectional(viewModel.stopCriterionProperty());

        findRootButton.setOnAction(value -> viewModel.findRoot());
    }
}
