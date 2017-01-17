package ru.unn.agile.PomodoroManager.view;

import ru.unn.agile.PomodoroManager.viewmodel.PomodoroManagerAppViewModel;
import ru.unn.agile.PomodoroManager.infrastructure.TextLogger;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.List;

public final class PomodoroManagerApp {

    private PomodoroManagerAppViewModel viewModel;
    private JPanel mainPanel;
    private JButton switchOnOffBtn;
    private JLabel stateLabel;
    private JTextField shortBreakDurationTextField;
    private JTextField pomodoroDurationTextField;
    private JTextField longBreakDurationTextField;
    private JLabel settingsStatusLabel;
    private JList<String> listLog;
    private Timer timer;
    private static final int MILLISEC_IN_MINUTE = 60000;
    private static final int FRAME_MINIMUM_WIDTH = 420;
    private static final int FRAME_MINIMUM_HEIGHT = 450;

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

        FocusAdapter focusLostListener = new FocusAdapter() {
            public void focusLost(final FocusEvent e) {
                bind();
                PomodoroManagerApp.this.viewModel.focusLost();
                backBind();
            }
        };
        pomodoroDurationTextField.addFocusListener(focusLostListener);
        shortBreakDurationTextField.addFocusListener(focusLostListener);
        longBreakDurationTextField.addFocusListener(focusLostListener);

        timer = new Timer(MILLISEC_IN_MINUTE, timerListener);
        timer.setInitialDelay(0);
        timer.setRepeats(true);
        timer.start();
    }


    public static void main(final String[] args) {
        JFrame frame = new JFrame("Pomodoro manager");
        TextLogger logger = new TextLogger("./Pomodoro.log");

        PomodoroManagerAppViewModel viewModel = new PomodoroManagerAppViewModel(logger);
        PomodoroManagerApp pomodoroManApp = new PomodoroManagerApp(viewModel);
        frame.setContentPane(pomodoroManApp.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
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

        List<String> log = viewModel.getLog();
        Collections.reverse(log);
        String[] items = log.toArray(new String[log.size()]);
        listLog.setListData(items);
    }
}
