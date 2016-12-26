package ru.unn.agile.MassConverter.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.unn.agile.MassConverter.Model.MassConverter.ConversionSystem;
import ru.unn.agile.MassConverter.ViewModel.ViewModel;
import ru.unn.agile.MassConverter.ViewModel.ILogger;
import ru.unn.agile.MassConverter.infrastructure.TxtLogger;

public class MassConverter {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtInput;
    @FXML
    private Label lbResult;
    @FXML
    private Label lbStatus;
    @FXML
    private Label lbLastMessage;
    @FXML
    private ListView<String> listLog;
    @FXML
    private ComboBox<ConversionSystem> cmbSystemToConvert;
    @FXML
    private ComboBox<ConversionSystem> cmbSystemFromConvert;

    @FXML
    void initialize() {
        ILogger logger = new TxtLogger("./TxtLogger-lab3.log");
        viewModel.setLogger(logger);
        txtInput.textProperty().bindBidirectional(viewModel.inputProperty());
        lbResult.textProperty().bindBidirectional(viewModel.resultProperty());
        lbStatus.textProperty().bindBidirectional(viewModel.statusProperty());
        lbLastMessage.textProperty().bindBidirectional(viewModel.lastLogMessageProperty());
        listLog.itemsProperty().bindBidirectional(viewModel.logMessagesProperty());
        cmbSystemToConvert.valueProperty()
                .bindBidirectional(viewModel.systemToConvertProperty());
        cmbSystemFromConvert.valueProperty()
                .bindBidirectional(viewModel.systemFromConvertProperty());

    }
}
