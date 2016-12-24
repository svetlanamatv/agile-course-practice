package ru.unn.agile.queue.view;

import ru.unn.agile.queue.infrastructure.QueueLoggerImpl;
import ru.unn.agile.queue.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    private QueueWorker(final ViewModel<T> viewModel) throws IOException {
        this.viewModel = viewModel;
        dfm = new DefaultListModel<>();
        queueList.setModel(dfm);
        backBind();
        queueList.setLayoutOrientation(JList.VERTICAL);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    bind();
                    QueueWorker.this.viewModel.add();
                    backBind();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    bind();
                    QueueWorker.this.viewModel.remove();
                    backBind();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    bind();
                    QueueWorker.this.viewModel.search();
                    backBind();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                try {
                    QueueWorker.this.viewModel.getSize();
                    backBind();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(final String[] args) {
        QueueWorker dialog = new QueueWorker();
        try {
            dialog.setContentPane(new QueueWorker<>(new ViewModel<>(new QueueLoggerImpl(""
                    + "./queworker.log"))).contentPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void bind() {
        viewModel.setValue((T) addTextField.getText());
    }

    private void backBind() throws IOException {
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
