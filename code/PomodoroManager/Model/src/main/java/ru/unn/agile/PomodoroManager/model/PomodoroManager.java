package ru.unn.agile.PomodoroManager.model;

public class PomodoroManager {
    private PomodoroState state;
    private int checkmarksCounter;
    private int finishedCyclesCounter;
    private int pomodoroDurationMin;
    private int shortBreakDurationMin;
    private int longBreakDurationMin;
    private int minutesToNextState;
    private static final int POMODOROS_PER_CYCLE = 4;
    public static final int POMODORO_DEFAULT_DURATION = 25;
    public static final int SHORT_BREAK_DEFAULT_DURATION = 5;
    public static final int LONG_BREAK_DEFAULT_DURATION = 20;

    private static final int POMODORO_MIN_DURATION = 15;
    private static final int POMODORO_MAX_DURATION = 60;

    private static final int SHORT_BREAK_MIN_DURATION = 2;
    private static final int SHORT_BREAK_MAX_DURATION = 15;

    private static final int LONG_BREAK_MIN_DURATION  = 10;
    private static final int LONG_BREAK_MAX_DURATION = 30;

    public PomodoroManager() {
        state = PomodoroState.Off;
        minutesToNextState = 0;
        checkmarksCounter = 0;
        finishedCyclesCounter = 0;
        pomodoroDurationMin = POMODORO_DEFAULT_DURATION;
        shortBreakDurationMin = SHORT_BREAK_DEFAULT_DURATION;
        longBreakDurationMin = LONG_BREAK_DEFAULT_DURATION;
    }

    public PomodoroState getState()  {
        return state;
    }

    public void resetState()  {
        state = PomodoroState.Off;
        checkmarksCounter = 0;
    }

    public void startCycle()  {
        if (state != PomodoroState.Off) {
            throw new RuntimeException("Cycle already started");
        }
        state = PomodoroState.Pomodoro;
        minutesToNextState = pomodoroDurationMin;
        checkmarksCounter = 0;
    }

    public void minuteLastEvent() throws RuntimeException {
        if (state != PomodoroState.Off) {
            if (minutesToNextState == 0) {
                nextState();
            } else {
                minutesToNextState--;
            }
        }
    }

    private void nextState()  {
        switch (state)  {
            case Pomodoro:
                checkmarksCounter++;
                if (checkmarksCounter < POMODOROS_PER_CYCLE)  {
                    state = PomodoroState.ShortBreak;
                    minutesToNextState = shortBreakDurationMin;
                }  else if (checkmarksCounter == POMODOROS_PER_CYCLE)  {
                    state = PomodoroState.LongBreak;
                    minutesToNextState = longBreakDurationMin;
                }
                break;
            case LongBreak:
                resetState();
                finishedCyclesCounter++;
                break;
            case ShortBreak:
                state = PomodoroState.Pomodoro;
                minutesToNextState = pomodoroDurationMin;
                break;
            default:
                throw new RuntimeException("Manager is in Off state");
        }
    }

    public int getCheckmarksCounter()  {
        return checkmarksCounter;
    }

    public int getFinishedCyclesCounter()  {
        return finishedCyclesCounter;
    }

    private void checkOffState() {
        if (state != PomodoroState.Off) {
            throw new RuntimeException("Can't change parameters, manager is in On state");
        }
    }

    public void setPomodoroDuration(final int minutes) {
        checkOffState();
        if (minutes >= POMODORO_MIN_DURATION
                && minutes <= POMODORO_MAX_DURATION) {
            pomodoroDurationMin = minutes;
        } else {
            throw new RuntimeException("This pomodoro duration don't support");
        }
    }

    public void setShortBreakDuration(final int minutes) {
        checkOffState();
        if (minutes >= SHORT_BREAK_MIN_DURATION
                && minutes <= SHORT_BREAK_MAX_DURATION) {
            shortBreakDurationMin = minutes;
        } else {
            throw new RuntimeException("This short break duration don't support");
        }
    }

    public void setLongBreakDuration(final int minutes) {
        checkOffState();
        if (minutes >= LONG_BREAK_MIN_DURATION
                && minutes <= LONG_BREAK_MAX_DURATION) {
            longBreakDurationMin = minutes;
        } else {
            throw new RuntimeException("This long break duration don't support");
        }
    }

    public int getPomodoroDuration() {
        return pomodoroDurationMin;
    }

    public int getShortBreakDuration() {
        return shortBreakDurationMin;
    }

    public int getLongBreakDuration() {
        return longBreakDurationMin;
    }

    public int getMinutesToNextState() {
        return minutesToNextState;
    }
}
