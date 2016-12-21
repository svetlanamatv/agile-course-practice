package ru.unn.agile.Statistics.view;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
                update();
            }
        };

        table = possibilityTable.getTable();
    }


    public Calculator(final ViewModel viewModel) {
        this.viewModel = viewModel;

        backBind();
        loadListOfOperations();

        DocumentListener updateOnTextChangedListener = new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) { }
            @Override
            public void removeUpdate(final DocumentEvent e) { }
            @Override
            public void changedUpdate(final DocumentEvent e) {
                bind();
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

        PropertyChangeListener propertyChange = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("tableCellEditor".equals(e.getPropertyName())) {
                    backBind();
                }
            }
        };

        nSpinner.addChangeListener(e -> update());
        nSpinner.addKeyListener(calculateOnEnterReleasedListener);

        deltaText.addActionListener(e -> update());
        deltaText.getDocument().addDocumentListener(updateOnTextChangedListener);

        table.addKeyListener(calculateOnEnterReleasedListener);
        table.addPropertyChangeListener(propertyChange);

        operationComboBox.addActionListener(e -> update());
        operationComboBox.addKeyListener(calculateOnEnterReleasedListener);

        momentOrderText.addActionListener(e -> update());
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

        viewModel.setArraysSize((int)nSpinner.getValue());
        ((AbstractTableModel) table.getModel()).fireTableStructureChanged();
        possibilityTable.update();
    }

    private void backBind() {
        deltaText.setText(viewModel.getDelta());

        momentOrderText.setEnabled(viewModel.isMomentOrderEnabled());
        if (viewModel.isMomentOrderEnabled()) {
            momentOrderText.setText(viewModel.getMomentOrder());
        }

        resultText.setText(viewModel.getResult());
        statusText.setText(viewModel.getStatus());
        computeButton.setEnabled(viewModel.isCalculateButtonEnabled());

        nSpinner.setValue(viewModel.getTableSize());
        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        lstLog.setListData(items);
    }

    private void update() {
        bind();
        backBind();
    }

    private void calculate() {
        bind();
        viewModel.calculate();
        backBind();
    }
}
