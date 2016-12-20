package ru.unn.agile.Statistics.view;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import ru.unn.agile.Statistics.viewmodel.Operation;
import ru.unn.agile.Statistics.viewmodel.ViewModel;





public class Calculator {
    private JPanel mainPanel;
    private JTable table;
    private JComboBox<Operation> operationComboBox;
    private JButton computeButton;
    private JTextField momentOrderText;
    private JTextField resultText;
    private JTextField statusText;
    private JTextField deltaText;
    private JSpinner nSpinner;
    private JList lstLog;

    private PossibilityTable possibilityTable;

    private final ViewModel viewModel;


    private void createUIComponents() {
        possibilityTable = new PossibilityTable(viewModel) {
            @Override
            public void stateChanged(final ChangeEvent e) {
                updateStatus();
            }
        };

        table = possibilityTable.getTable();
    }


    public Calculator(final ViewModel viewModel) {
        this.viewModel = viewModel;

        backBindAll();
        loadListOfOperations();

        DocumentListener updateOnTextChangedListener = new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                updateStatus();
            }
            @Override
            public void removeUpdate(final DocumentEvent e) {
                updateStatus();
            }
            @Override
            public void changedUpdate(final DocumentEvent e) {
                updateStatus();
            }
        };
        KeyAdapter calculateOnEnterReleasedListener = new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    calculate();
                }
            }
        };

        nSpinner.addChangeListener(e -> updateTableSize());
        nSpinner.addKeyListener(calculateOnEnterReleasedListener);

        deltaText.addActionListener(e -> calculate());
        deltaText.getDocument().addDocumentListener(updateOnTextChangedListener);

        table.addKeyListener(calculateOnEnterReleasedListener);

        operationComboBox.addActionListener(e -> updateAll());
        operationComboBox.addKeyListener(calculateOnEnterReleasedListener);

        momentOrderText.addActionListener(e -> calculate());
        momentOrderText.getDocument().addDocumentListener(updateOnTextChangedListener);

        computeButton.addActionListener(e -> calculate());
    }


    public void applyTo(final JFrame frame) {
        frame.setContentPane(mainPanel);
    }

    private void loadListOfOperations() {
        Operation[] operations = Operation.values();
        operationComboBox.setModel(new JComboBox<>(operations).getModel());
    }


    private void bind() {
        viewModel.setDelta(deltaText.getText());
        if (viewModel.isMomentOrderEnabled()) {
            viewModel.setMomentOrder(momentOrderText.getText());
        }
        viewModel.setOperation((Operation) operationComboBox.getSelectedItem());
    }

    private void bindArraysSize(final int newSize) {
        viewModel.setArraysSize(newSize);
    }


    private void backBindAll() {
        deltaText.setText(viewModel.getDelta());
        backBindResultAndStatus();

        momentOrderText.setEnabled(viewModel.isMomentOrderEnabled());
        if (viewModel.isMomentOrderEnabled()) {
            momentOrderText.setText(viewModel.getMomentOrder());
        }

        nSpinner.setValue(viewModel.getTableSize());

        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        lstLog.setListData(items);
    }

    private void backBindResultAndStatus() {
        resultText.setText(viewModel.getResult());
        statusText.setText(viewModel.getStatus());
        computeButton.setEnabled(viewModel.isCalculateButtonEnabled());
    }


    private void updateAll() {
        bind();
        backBindAll();
    }

    private void updateStatus() {
        bind();
        backBindResultAndStatus();
    }

    private void updateTableSize() {
        bindArraysSize((int) nSpinner.getValue());
        ((AbstractTableModel) table.getModel()).fireTableStructureChanged();
        possibilityTable.update();
        backBindAll();
    }


    private void calculate() {
        bind();
        viewModel.calculate();
        backBindResultAndStatus();
    }
}
