package ru.unn.agile.MyDeque.view;

import ru.unn.agile.MyDeque.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class DequeForm {

    private JPanel mainPanel;
    private JButton acceptButton;
    private final ViewModel viewmodel;

    private JTextField txtValue;
    private JComboBox<ViewModel.Operations> cbOperationChoice;
    private JLabel lbStatus;
    private JTextField txtResult;

    private DequeForm(final ViewModel viewmodel) {
        this.viewmodel = viewmodel;
        backBind();

        getOperationsList();

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewmodel.accept();
                backBind();
            }
        });

        cbOperationChoice.addActionListener(actionEvent -> {
            bind();
            backBind();
        });

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                viewmodel.textFieldKey(e.getKeyCode());
                backBind();
            }
        };
        txtValue.addKeyListener(keyListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("DequeForm");
        frame.setContentPane(new DequeForm(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewmodel.setValue(txtValue.getText());
        viewmodel.setOperation((ViewModel.Operations) cbOperationChoice.getSelectedItem());
    }

    private void getOperationsList() {
        ViewModel.Operations[] operations = ViewModel.Operations.values();
        cbOperationChoice.setModel(new JComboBox<>(operations).getModel());
    }

    private void backBind() {
        acceptButton.setEnabled(viewmodel.isAcceptButtonEnabled());

        txtResult.setText(viewmodel.getResult());
        lbStatus.setText(viewmodel.getStatus());
    }

}
