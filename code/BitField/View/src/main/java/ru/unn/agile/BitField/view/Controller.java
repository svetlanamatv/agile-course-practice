package ru.unn.agile.BitField.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

import ru.unn.agile.BitField.infrastructure.TextLog;
import ru.unn.agile.BitField.viewmodel.ViewModel;


public class Controller {
    private final ObservableList<String> numBitList =
            FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7");

    @FXML
    private ViewModel viewModel;

    // Field A

    @FXML
    private Button inputAButton;

    @FXML
    private TextField inputATextField;

    @FXML
    private ComboBox<String> setBitAComboBox;

    @FXML
    private Button setBitAButton;

    @FXML
    private Button getBitAButton;

    @FXML
    private Button clearBitAButton;

    @FXML
    private TextField showATextField;

    @FXML
    private TextField showChooseBitATextField;

    @FXML
    private Button notAButton;

    @FXML
    private TextArea errorATextArea;

    // Field B

    @FXML
    private Button inputBButton;

    @FXML
    private TextField inputBTextField;

    @FXML
    private ComboBox<String> setBitBComboBox;

    @FXML
    private Button setBitBButton;

    @FXML
    private Button getBitBButton;

    @FXML
    private Button clearBitBButton;

    @FXML
    private TextField showBTextField;

    @FXML
    private TextField showChooseBitBTextField;

    @FXML
    private Button notBButton;

    @FXML
    private TextArea errorBTextArea;

    // Logic operations

    @FXML
    private Button logicAndButton;

    @FXML
    private Button logicOrButton;

    @FXML
    private Button logicXorButton;

    @FXML
    private TextField showResultLogicTextField;

    @FXML
    void initialize() {
        viewModel.setThisOneLog(new TextLog("./TextLog-lab3.log"));

        setBitAComboBox.setItems(numBitList);
        setBitBComboBox.setItems(numBitList);

        setBitAComboBox.setValue("0");
        setBitBComboBox.setValue("0");

        inputAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.setBitFieldStringA(inputATextField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        inputBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.setBitFieldStringB(inputBTextField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        setBitAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.setBitFieldBitA(setBitAComboBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        setBitBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.setBitFieldBitB(setBitBComboBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clearBitAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.clearBitFieldBitA(setBitAComboBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clearBitBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.clearBitFieldBitB(setBitBComboBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        getBitAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.getBitFieldBitA(setBitAComboBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        getBitBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.getBitFieldBitB(setBitBComboBox.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        notAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.logicNotA();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        notBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.logicNotB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // Logic operations
        logicAndButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.logicAAndB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        logicOrButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.logicAOrB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        logicXorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    viewModel.logicAXorB();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
