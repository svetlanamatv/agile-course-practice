package ru.unn.agile.matrixdiff.view;

import com.github.audice.matrixdiff.viewmodel.MatrixDiffViewModel;
import ru.unn.agile.matrixdiff.infrastructure.TextLogger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Denis on 23.12.2016.
 */
public class MatrixDisplay {
    private JButton toFillOfMatrixButton;
    private JPanel panel1;
    private JTextField sizeOfMatrix;
    private JTextArea matrix;
    private JButton calculateTheDeterminantButton;
    private JLabel value;
    private JTextArea textArea1;
    private JTextPane textPane1;
    private JList<String> loggerAction;
    private final MatrixDiffViewModel viewModel;


    public MatrixDisplay(final MatrixDiffViewModel viewModel) {
        this.viewModel = viewModel;

        toFillOfMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matrix.setText(viewModel.getEmptyMatrix());
            }
        });
        sizeOfMatrix.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                backBind();
                bind();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                backBind();
                bind();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                backBind();
                bind();
            }
        });
        calculateTheDeterminantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                backBind();
                bind();
            }
        });
        matrix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                super.keyTyped(e);
                backBind();
                bind();
            }
        });
        bind();
        matrix.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseClicked(e);
                backBind();
                bind();
            }
        });
        calculateTheDeterminantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                viewModel.calculateMatrixDiff();
                value.setText(Float.toString(viewModel.getResultOfCalculate()));
            }
        });
    }

    private void bind() {
        toFillOfMatrixButton.setEnabled(viewModel.isToFillButtonEnabled());
        calculateTheDeterminantButton.setEnabled(viewModel.getIsCalculateButton());
    }

    private void backBind() {
        viewModel.setSizeOfMatrix(sizeOfMatrix.getText());
        viewModel.fillStringImgMatrixConvertToArray(matrix.getText());
        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        loggerAction.setListData(items);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Сalculate the determinant");
        TextLogger logger = new TextLogger();
        frame.setContentPane(new MatrixDisplay(new MatrixDiffViewModel(logger)).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
