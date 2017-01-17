package ru.unn.agile.PomodoroManager.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PomodoroManagerTests {
    private PomodoroManager manager = new PomodoroManager();

    private void skipState(final int times)  {
        for (int i = 0; i < times; i++)  {
            nextState();
        }
    }

    private void forceTime(final int minutes) {
        for (int i = 0; i < minutes; i++) {
            manager.minuteLastEvent();
        }
    }

    private void nextState() {
        PomodoroState state = manager.getState();
        while (state == manager.getState()) {
            manager.minuteLastEvent();
        }
    }

    @Test
    public void defaultState()  {
        assertEquals(PomodoroState.Off, manager.getState());
    }

    @Test
    public void resetState()  {
        manager.resetState();
        assertEquals(PomodoroState.Off, manager.getState());
        assertEquals(0, manager.getCheckmarksCounter());
    }

    @Test
    public void startCycle()  {
        manager.startCycle();
        assertEquals(PomodoroState.Pomodoro, manager.getState());
        assertEquals(0, manager.getCheckmarksCounter());
    }

    @Test(expected = RuntimeException.class)
    public void throwWhenDoubleStartCycle()  {
        manager.startCycle();
        manager.startCycle();
    }

    @Test
    public void switchToShortBreak()  {
        manager.startCycle();
        skipState(1);
        assertEquals(PomodoroState.ShortBreak, manager.getState());
        assertEquals(1, manager.getCheckmarksCounter());
    }

    @Test
    public void switchToNextPomodoro()  {
        manager.startCycle();
        skipState(2);
        assertEquals(PomodoroState.Pomodoro, manager.getState());
        assertEquals(1, manager.getCheckmarksCounter());
    }

    @Test
    public void switchToLongBreak()  {
        manager.startCycle();
        skipState(7);
        assertEquals(PomodoroState.LongBreak, manager.getState());
        assertEquals(4, manager.getCheckmarksCounter());
    }

    @Test
    public void switchToOffAfterCycle()  {
        manager.startCycle();
        skipState(8);
        assertEquals(PomodoroState.Off, manager.getState());
        assertEquals(0, manager.getCheckmarksCounter());
    }

    @Test
    public void cyclesCounterIncreasedAfterCycle() {
        manager.startCycle();
        skipState(8);
        assertEquals(1, manager.getFinishedCyclesCounter());
    }

    @Test
    public void setCorrectUserPomodoroDurationBeforeStart() {
        manager.setPomodoroDuration(30);
        assertEquals(30, manager.getPomodoroDuration());
    }

    @Test(expected = RuntimeException.class)
    public void setIncorrectUserPomodoroDurationBeforeStart() {
        manager.setPomodoroDuration(0);
    }

    @Test(expected = RuntimeException.class)
    public void setCorrectUserPomodoroDurationAfterStart() {
        manager.startCycle();
        manager.setPomodoroDuration(30);
    }

    @Test
    public void setCorrectUserShortBreakDurationBeforeStart() {
        manager.setShortBreakDuration(10);
        assertEquals(10, manager.getShortBreakDuration());
    }

    @Test(expected = RuntimeException.class)
    public void setIncorrectUserShortBreakDurationBeforeStart() {
        manager.setShortBreakDuration(100);
    }

    @Test(expected = RuntimeException.class)
    public void setCorrectUserShortBreakDurationAfterStart() {
        manager.startCycle();
        manager.setShortBreakDuration(8);
    }

    @Test
    public void setCorrectUserLongBreakDurationBeforeStart() {
        manager.setLongBreakDuration(25);
        assertEquals(25, manager.getLongBreakDuration());
    }

    @Test(expected = RuntimeException.class)
    public void setIncorrectUserLongBreakDurationBeforeStart() {
        manager.setLongBreakDuration(5);
    }

    @Test(expected = RuntimeException.class)
    public void setCorrectUserLongBreakDurationAfterStart() {
        manager.startCycle();
        manager.setLongBreakDuration(20);
    }
}
