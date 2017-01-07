package ru.unn.agile.Statistics.view;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ru.unn.agile.Statistics.infrastructure.TxtLogger;
import ru.unn.agile.Statistics.viewmodel.ViewModel;

public final class Main {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JFrame frame = new JFrame("Calculator");
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            TxtLogger logger = new TxtLogger("./Calculator.log");
            Calculator calculator = new Calculator(new ViewModel(logger));
            calculator.applyTo(frame);
            frame.pack();
            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Main() { }
}
