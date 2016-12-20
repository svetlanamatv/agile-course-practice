package ru.unn.agile.RomanArabicConverter.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.unn.agile.RomanArabicConverter.viewmodel.ViewModel;

public class RomanArabicConverter {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField tfArab;
    @FXML
    private TextField tfRom;
    @FXML
    private RadioButton rbArabToRom;
    @FXML
    private RadioButton rbRomToArab;
    @FXML
    private Button btnConvert;
    @FXML
    private Text tfStatus;

    @FXML
    void initialize() {
        viewModel = new ViewModel();
        btnConvert.disableProperty().bindBidirectional(viewModel.getConverterBtnDisableProperty());
        tfRom.textProperty().bindBidirectional(viewModel.getRomanNumberProperty());
        tfArab.textProperty().bindBidirectional(viewModel.getArabicNumberProperty());
        rbArabToRom.selectedProperty().bindBidirectional(viewModel.getRBArabToRomChooseProperty());
        rbRomToArab.selectedProperty().bindBidirectional(viewModel.getRBRomToArabChooseProperty());
        tfArab.disableProperty().bindBidirectional(viewModel.getTFArabDisableProperty());
        tfRom.disableProperty().bindBidirectional(viewModel.getTFRomDisableProperty());
        tfStatus.textProperty().bindBidirectional(viewModel.getStatusProperty());
        btnConvert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.convertNumber();
            }
        });
    }
}
