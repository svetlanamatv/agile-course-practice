package ru.unn.agile.PomodoroManager.view;

import ru.unn.agile.PomodoroManager.viewmodel.PomodoroManagerAppViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class PomodoroManagerApp {

    private PomodoroManagerAppViewModel viewModel;
    private JPanel mainPanel;
    private JButton switchOnOffBtn;
    private JLabel stateLabel;
    private JTextField shortBreakDurationTextField;
    private JTextField pomodoroDurationTextField;
    private JTextField longBreakDurationTextField;
    private JLabel settingsStatusLabel;
    private Timer timer;
    private static final int MILLISEC_IN_MINUTE = 60000;
    private PomodoroManagerApp() {
    }

    private PomodoroManagerApp(final PomodoroManagerAppViewModel viewModel) {
        this.viewModel = viewModel;

        switchOnOffBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                timer.restart();
                PomodoroManagerApp.this.viewModel.switchOnOff();
                backBind();
            }
        });

        ActionListener timerListener = new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                bind();
                PomodoroManagerApp.this.viewModel.minuteLastEvent();
                backBind();
            }
        };

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                PomodoroManagerApp.this.viewModel.processKeyInTextField(e.getKeyCode());
                backBind();
            }
        };
        pomodoroDurationTextField.addKeyListener(keyListener);
        shortBreakDurationTextField.addKeyListener(keyListener);
        longBreakDurationTextField.addKeyListener(keyListener);

        pomodoroDurationTextField.setText(viewModel.getPomodoroDuration());
        shortBreakDurationTextField.setText(viewModel.getShortBreakDuration());
        longBreakDurationTextField.setText(viewModel.getLongBreakDuration());

        timer = new Timer(MILLISEC_IN_MINUTE, timerListener);
        timer.setInitialDelay(0);
        timer.setRepeats(true);
        timer.start();
    }



    public static void main(final String[] args) {
        JFrame frame = new JFrame("Pomodoro manager");

        frame.setContentPane(new PomodoroManagerApp(new PomodoroManagerAppViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setPomodoroDuration(pomodoroDurationTextField.getText());
        viewModel.setShortBreakDuration(shortBreakDurationTextField.getText());
        viewModel.setLongBreakDuration(longBreakDurationTextField.getText());
    }

    private void backBind() {
        switchOnOffBtn.setEnabled(viewModel.isSwitchOnOffButtonEnabled());
        pomodoroDurationTextField.setEnabled(viewModel.isTimeSettingsFieldsEnabled());
        shortBreakDurationTextField.setEnabled(viewModel.isTimeSettingsFieldsEnabled());
        longBreakDurationTextField.setEnabled(viewModel.isTimeSettingsFieldsEnabled());

        stateLabel.setText(viewModel.getPomodoroStateLabel());
        settingsStatusLabel.setText(viewModel.getStatus());
    }


}
