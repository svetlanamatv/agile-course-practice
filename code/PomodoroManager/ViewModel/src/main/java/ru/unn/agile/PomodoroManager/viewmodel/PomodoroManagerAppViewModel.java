package ru.unn.agile.PomodoroManager.viewmodel;

import ru.unn.agile.PomodoroManager.model.*;

import javax.management.RuntimeErrorException;

public class PomodoroManagerAppViewModel {

    private String pomodoroDuration;
    private String shortBreakDuration;
    private String longBreakDuration;
    private PomodoroState pomodoroState;
    private boolean isTimeSettingsFieldsEnabled;
    private boolean isSwitchOnOffButtonEnabled;
    private final PomodoroManager pomodoroManager;
    private String status;

    public PomodoroManagerAppViewModel() {
        isTimeSettingsFieldsEnabled = true;
        isSwitchOnOffButtonEnabled = true;
        pomodoroManager = new PomodoroManager();
        pomodoroDuration = String.valueOf(PomodoroManager.POMODORO_DEFAULT_DURATION);
        shortBreakDuration = String.valueOf(PomodoroManager.SHORT_BREAK_DEFAULT_DURATION);
        longBreakDuration = String.valueOf(PomodoroManager.LONG_BREAK_DEFAULT_DURATION);
        pomodoroState = PomodoroState.Off;
        status = Status.READY;
    }

    public void processKeyInTextField(final int keyCode) {
        parseInput();

        if (keyCode == KeyboardKeys.ENTER) {
            enterPressed();
        }
    }

    private void enterPressed() {

        if (isSwitchOnOffButtonEnabled()) {
            switchOnOff();
        }
    }

    private boolean parseInput() {
        int pomodoroDurationMin = 0;
        int shortBreakDurationMin = 0;
        int longBreakDurationMin = 0;
        isSwitchOnOffButtonEnabled = true;
        try {
            if (!pomodoroDuration.isEmpty()) {
                pomodoroDurationMin = Integer.parseInt(pomodoroDuration);
            }
            if (!shortBreakDuration.isEmpty()) {
                shortBreakDurationMin = Integer.parseInt(shortBreakDuration);
            }
            if (!longBreakDuration.isEmpty()) {
                longBreakDurationMin = Integer.parseInt(longBreakDuration);
            }
        } catch (Exception e) {
            status = Status.BAD_FORMAT;
            isSwitchOnOffButtonEnabled = false;
            return false;
        }

        try {
            pomodoroManager.setPomodoroDuration(pomodoroDurationMin);
            pomodoroManager.setShortBreakDuration(shortBreakDurationMin);
            pomodoroManager.setLongBreakDuration(longBreakDurationMin);
        } catch (RuntimeErrorException e) {
            status = Status.UNACCEPTABLE_DURATIONS;
            isSwitchOnOffButtonEnabled = false;
            return false;
        }
        status = Status.READY;

        return isSwitchOnOffButtonEnabled;
    }

    public void switchOnOff() {
        if (pomodoroManager.getState() == PomodoroState.Off) {
            if (!parseInput()) {
                return;
            }
            pomodoroManager.startCycle();
            isTimeSettingsFieldsEnabled = false;
        } else {
            isTimeSettingsFieldsEnabled = true;
            pomodoroManager.resetState();
        }
    }

    public void minuteLastEvent() {
        pomodoroManager.minuteLastEvent();
    }

    public final class Status {
        public static final String READY = "Input is ok, press 'Start/Stop' for work";
        public static final String BAD_FORMAT =
                "Bad format of durations. Please, enter a integer values";
        public static final String UNACCEPTABLE_DURATIONS =
                "Such duration unacceptable in Pomodoro";

        private Status() { }
    }

    public final class StateLabels {
        public static final String POMODORO_OFF_LABEL = "Pomodoro is OFF";
        public static final String POMODORO_LABEL = "Pomodoro, work!";
        public static final String POMODORO_SHORT_BREAK_LABEL = "Short break!";
        public static final String POMODORO_LONG_BREAK_LABEL = "Long break!";

        private StateLabels() { }
    }

    public String getPomodoroDuration() {
        return pomodoroDuration;
    }

    public void setPomodoroDuration(final String input) {
        if (input.equals(this.pomodoroDuration)) {
            return;
        }
        pomodoroDuration = input;
    }

    public String getShortBreakDuration() {
        return shortBreakDuration;
    }

    public void setShortBreakDuration(final String input) {
        if (input.equals(this.shortBreakDuration)) {
            return;
        }
        shortBreakDuration = input;
    }

    public String getLongBreakDuration() {
        return longBreakDuration;
    }

    public void setLongBreakDuration(final String input) {
        if (input.equals(this.longBreakDuration)) {
            return;
        }
        longBreakDuration = input;
    }

    public String getStatus() {
        return status;
    }

    public PomodoroState getPomodoroState() {
        pomodoroState = pomodoroManager.getState();
        return pomodoroState;
    }

    public String getPomodoroStateLabel() {
        pomodoroState = pomodoroManager.getState();
        switch (pomodoroState) {
            case Off:
                return StateLabels.POMODORO_OFF_LABEL;
            case Pomodoro:
                return StateLabels.POMODORO_LABEL;
            case ShortBreak:
                return StateLabels.POMODORO_SHORT_BREAK_LABEL;
            case LongBreak:
                return StateLabels.POMODORO_LONG_BREAK_LABEL;
            default:
                throw new RuntimeErrorException(new Error("Manager is in unknown state"));
        }
    }

    public boolean isSwitchOnOffButtonEnabled() {
        return isSwitchOnOffButtonEnabled;
    }

    public boolean isTimeSettingsFieldsEnabled() {
        return isTimeSettingsFieldsEnabled;
    }
}
