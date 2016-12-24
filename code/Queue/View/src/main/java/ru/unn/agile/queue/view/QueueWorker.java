package ru.unn.agile.queue.view;

import ru.unn.agile.queue.infrastructure.QueueLoggerImpl;
import ru.unn.agile.queue.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public final class QueueWorker<T> extends JDialog {
    private ViewModel<T> viewModel;

    private JPanel contentPane;

    private DefaultListModel<Object> dfm;
    private JList<Object> queueList;

    private JButton addButton;
    private JButton removeButton;
    private JButton getSizeButton;
    private JButton searchButton;

    private JTextField addTextField;
    private JTextField resultTextField;
    private JTextField logTextField;

    private QueueWorker() {

    }

    private QueueWorker(final ViewModel<T> viewModel) {
        this.viewModel = viewModel;
        dfm = new DefaultListModel<>();
        queueList.setModel(dfm);
        backBind();
        queueList.setLayoutOrientation(JList.VERTICAL);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                QueueWorker.this.viewModel.add();
                backBind();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                QueueWorker.this.viewModel.remove();
                backBind();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                QueueWorker.this.viewModel.search();
                backBind();
            }
        });

        getSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                QueueWorker.this.viewModel.getSize();
                backBind();
            }
        });
    }

    public static void main(final String[] args) {
        QueueWorker dialog = new QueueWorker();
        dialog.setContentPane(new QueueWorker<>(new ViewModel<>(new QueueLoggerImpl("./queworker"
                + ".log"))).contentPane);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void bind() {
        viewModel.setValue((T) addTextField.getText());
    }

    private void backBind() {
        addTextField.setText("");
        updateList();
        resultTextField.setText(viewModel.getResult());
        logTextField.setText(viewModel.getLog());
    }

    private void updateList() {
        List<T> queueAsList = viewModel.getQueue();
        if (!dfm.isEmpty() || !queueAsList.isEmpty()) {
            dfm.clear();
            for (Object o : queueAsList) {
                dfm.addElement(o);
            }
        }
    }
}
