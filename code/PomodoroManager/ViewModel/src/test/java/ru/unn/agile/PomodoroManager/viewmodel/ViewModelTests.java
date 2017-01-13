package ru.unn.agile.PomodoroManager.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PomodoroManager.model.PomodoroManager;
import ru.unn.agile.PomodoroManager.model.PomodoroState;
import ru.unn.agile.PomodoroManager.viewmodel.PomodoroManagerAppViewModel.Status;

import static org.junit.Assert.*;

public class ViewModelTests {
    private PomodoroManagerAppViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new PomodoroManagerAppViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals(String.valueOf(PomodoroManager.POMODORO_DEFAULT_DURATION),
                viewModel.getPomodoroDuration());
        assertEquals(String.valueOf(PomodoroManager.SHORT_BREAK_DEFAULT_DURATION),
                viewModel.getShortBreakDuration());
        assertEquals(String.valueOf(PomodoroManager.LONG_BREAK_DEFAULT_DURATION),
                viewModel.getLongBreakDuration());
        assertEquals(Status.READY, viewModel.getStatus());
        assertEquals(PomodoroState.Off, viewModel.getPomodoroState());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFilled() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusBadDurationsWhenFieldsAreFilled() {
        fillInputFieldsWithUnacceptableDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(Status.UNACCEPTABLE_DURATIONS, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.setPomodoroDuration("r");
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseIsOK() {
        viewModel.setShortBreakDuration("a");
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        viewModel.setShortBreakDuration("5");
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isSwitchOnOffButtonEnabledInitially() {

        assertEquals(true, viewModel.isSwitchOnOffButtonEnabled());
    }

    @Test
    public void isFieldsEnabledInitially() {

        assertEquals(true, viewModel.isTimeSettingsFieldsEnabled());
    }

    @Test
    public void isSwitchOnOffButtonDisabledWhenFormatIsBad() {
        fillInputFieldsWithAcceptDurations();
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        assertEquals(true, viewModel.isSwitchOnOffButtonEnabled());

        viewModel.setPomodoroDuration("bad");
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(false, viewModel.isSwitchOnOffButtonEnabled());
    }

    @Test
    public void isSwitchOnOffButtonDisabledWithIncompleteInput() {
        viewModel.setPomodoroDuration("30");
        viewModel.setShortBreakDuration("5");
        viewModel.setLongBreakDuration("");

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(false, viewModel.isSwitchOnOffButtonEnabled());
    }


    @Test
    public void isSettingsFieldsDisabledWhenStarted() {
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        viewModel.switchOnOff();
        assertEquals(false, viewModel.isTimeSettingsFieldsEnabled());
    }

    @Test
    public void isSettingsFieldsEnabledAfterStop() {
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        viewModel.switchOnOff();
        viewModel.switchOnOff();
        assertEquals(true, viewModel.isTimeSettingsFieldsEnabled());
    }

    @Test
    public void isSwitchOnOffButtonEnabledWithCorrectInput() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(true, viewModel.isSwitchOnOffButtonEnabled());
    }


    @Test
    public void isPomodoroStateLabelAfterStart() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        viewModel.switchOnOff();

        assertEquals(PomodoroManagerAppViewModel.StateLabels.POMODORO_LABEL,
                viewModel.getPomodoroStateLabel());
    }

    @Test
    public void isPomodoroOffStateLabelAfterStop() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);
        viewModel.switchOnOff();
        assertEquals(PomodoroManagerAppViewModel.StateLabels.POMODORO_LABEL,
                viewModel.getPomodoroStateLabel());

        viewModel.switchOnOff();
        assertEquals(PomodoroManagerAppViewModel.StateLabels.POMODORO_OFF_LABEL,
                viewModel.getPomodoroStateLabel());
    }

    private void fillInputFieldsWithAcceptDurations() {
        viewModel.setPomodoroDuration("30");
        viewModel.setShortBreakDuration("5");
        viewModel.setLongBreakDuration("20");
    }

    private void fillInputFieldsWithUnacceptableDurations() {
        viewModel.setPomodoroDuration("0");
        viewModel.setShortBreakDuration("1000");
        viewModel.setLongBreakDuration("-5");
    }

    private void fillInputFieldsWithBadFormat() {
        viewModel.setPomodoroDuration("a");
        viewModel.setShortBreakDuration("(");
        viewModel.setLongBreakDuration("20");
    }
}
