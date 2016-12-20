package ru.unn.agile.TemperatureConverter.view;

import ru.unn.agile.TemperatureConverter.viewmodel.TemperatureConverterViewModel;
import ru.unn.agile.TemperatureConverter.infrastructure.TxtLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public final class TemperatureConverterView {
    private TemperatureConverterViewModel tempConverterViewModel;
    private JPanel mainPanel;
    private JTextField firstTextField;
    private JTextField secondTextField;
    private JComboBox firstComboBoxScales;
    private JComboBox secondComboBoxScales;
    private JLabel warningLabel;
    private JList<String> lstLog;

    private TemperatureConverterView() {
    }

    private TemperatureConverterView(final TemperatureConverterViewModel tempConverterViewModel) {
        this.tempConverterViewModel = tempConverterViewModel;

        firstComboBoxScales.setSelectedItem(tempConverterViewModel.getFirstScale().toString());
        secondComboBoxScales.setSelectedItem(tempConverterViewModel.getSecondScale().toString());

        firstTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bindFirstValue();
                tempConverterViewModel.convertFirstToSecondValue();
                backBindSecondValue();
            }
        });

        secondTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bindSecondValue();
                tempConverterViewModel.convertSecondToFirstValue();
                backBindFirstValue();
            }
        });

        firstComboBoxScales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bindFirstScale();
                backBindFirstValue();
            }
        });

        secondComboBoxScales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bindSecondScale();
                backBindSecondValue();
            }
        });
    }

    private void getLog() {
        List<String> log = tempConverterViewModel.getLog();
        String[] str = log.toArray(new String[log.size()]);
        lstLog.setListData(str);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("TemperatureConverterView");

        TxtLogger logger = new TxtLogger("./Converter.log");
        frame.setContentPane(new TemperatureConverterView(
                new TemperatureConverterViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bindFirstValue() {
        tempConverterViewModel.setFirstValue(firstTextField.getText());
    }

    private void backBindSecondValue() {
        secondTextField.setText(tempConverterViewModel.getSecondValue());
        warningLabel.setText(tempConverterViewModel.getWarningLabelText());
        getLog();
    }

    private void bindSecondValue() {
        tempConverterViewModel.setSecondValue(secondTextField.getText());
    }

    private void backBindFirstValue() {
        firstTextField.setText(tempConverterViewModel.getFirstValue());
        warningLabel.setText(tempConverterViewModel.getWarningLabelText());
        getLog();
    }

    private void bindFirstScale() {
        tempConverterViewModel.setFirstScale((String) firstComboBoxScales.getSelectedItem());
    }

    private void bindSecondScale() {
        tempConverterViewModel.setSecondScale((String) secondComboBoxScales.getSelectedItem());
    }
}
