package ru.unn.agile.PomodoroManager.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.PomodoroManager.model.PomodoroManager;
import ru.unn.agile.PomodoroManager.model.PomodoroState;
import ru.unn.agile.PomodoroManager.viewmodel.PomodoroManagerAppViewModel.Status;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private PomodoroManagerAppViewModel viewModel;

    public void setViewModel(final PomodoroManagerAppViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new PomodoroManagerAppViewModel(logger);
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
        assertEquals(true, viewModel.isInputChanged());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFilled() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isPomodoroEnableWhenEnterPress() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);

        assertEquals(PomodoroState.Pomodoro, viewModel.getPomodoroState());
    }

    @Test
    public void isPomodoroNotEnableWhenBadInput() {
        fillInputFieldsWithBadFormat();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);

        assertEquals(PomodoroState.Off, viewModel.getPomodoroState());
    }

    @Test
    public void isMinutesToNextStateDecreaseWhenTimeEventHappen() {
        fillInputFieldsWithAcceptDurations();
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);
        int minutes = viewModel.getMinutesToNextState();
        viewModel.minuteLastEvent();


        assertEquals(1, minutes - viewModel.getMinutesToNextState());
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
    public void isDurationsFieldsEnabledInitially() {

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
    public void isShortBreakLabelAfterPomodoro() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);
        skipState(1);

        assertEquals(PomodoroManagerAppViewModel.StateLabels.POMODORO_SHORT_BREAK_LABEL,
                viewModel.getPomodoroStateLabel());
    }

    @Test
    public void isLongBreakLabelAfterFourPomodoros() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);
        skipState(7);

        assertEquals(PomodoroManagerAppViewModel.StateLabels.POMODORO_LONG_BREAK_LABEL,
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
        viewModel.setPomodoroDuration("25");
        viewModel.setShortBreakDuration("5");
        viewModel.setLongBreakDuration("15");
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

    private void skipState(final int times) {
        for (int i = 0; i < times; i++) {
            nextState();
        }
    }

    private void forceTime(final int minutes) {
        for (int i = 0; i < minutes; i++) {
            viewModel.minuteLastEvent();
        }
    }

    private void nextState() {
        PomodoroState state = viewModel.getPomodoroState();
        while (state == viewModel.getPomodoroState()) {
            viewModel.minuteLastEvent();
        }
    }

    @Test
    public void canCreatePomodoroViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        PomodoroManagerAppViewModel viewModelLogged =
                new PomodoroManagerAppViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelThrowsExceptionWithNullLogger() {
        new PomodoroManagerAppViewModel(null);
    }

    @Test
    public void isSwitchOnOffPuttingSomething() {
        fillInputFieldsWithAcceptDurations();

        viewModel.switchOnOff();

        List<String> log = viewModel.getLog();
        assertNotEquals(log.size(), 0);
    }

    @Test
    public void isLogEmptyOnStart() {
        List<String> log = viewModel.getLog();

        assertEquals(log.size(), 0);
    }

    @Test
    public void isLogContainsNeedMessage() {
        fillInputFieldsWithAcceptDurations();
        viewModel.switchOnOff();

        String message = viewModel.getLastLogMsg();

        assertTrue(message.matches(".*"
                + PomodoroManagerAppViewModel.LogMessages.SWITCH_BTN_WAS_PRESSED + ".*"));
    }

    @Test
    public void isProperlyFormattingInfoAboutDurations() {
        fillInputFieldsWithAcceptDurations();
        viewModel.switchOnOff();

        String message = viewModel.getLastLogMsg();

        assertTrue(message.matches(".*Pomodoro start. Durations"
                + ": pomodoro = " + viewModel.getPomodoroDuration()
                + "; short break = " + viewModel.getShortBreakDuration()
                + "; long break = " + viewModel.getLongBreakDuration() + ".*"
        ));
    }

    @Test
    public void isLogContainsInputDurations() {
        fillInputFieldsWithAcceptDurations();
        viewModel.switchOnOff();

        String message = viewModel.getLastLogMsg();
        assertTrue(message.matches(".*" + viewModel.getPomodoroDuration()
                + ".*" + viewModel.getShortBreakDuration()
                + ".*" + viewModel.getLongBreakDuration() + ".*"
        ));
    }

    @Test
    public void canPutThreeLogMessages() {
        fillInputFieldsWithAcceptDurations();

        viewModel.switchOnOff();
        viewModel.switchOnOff();
        viewModel.switchOnOff();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void isLogInputsCalledOnEnterKey() {
        fillInputFieldsWithAcceptDurations();

        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);

        String message = viewModel.getLastLogMsg();
        assertTrue(message.matches(".*"
                + PomodoroManagerAppViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void areDurationsCorrectlyLoggedOnEditingFinish() {
        fillInputFieldsWithUnacceptableDurations();
        viewModel.focusLost();
        String message = viewModel.getLastLogMsg();
        assertTrue(message.matches(".*"
                + PomodoroManagerAppViewModel.LogMessages.EDITING_FINISHED
                + "Input durations are: \\["
                + viewModel.getPomodoroDuration() + "; "
                + viewModel.getShortBreakDuration() + "; "
                + viewModel.getLongBreakDuration() + "\\]"));
    }

    @Test
    public void isEditingFinishLogExist() {
        fillInputFieldsWithAcceptDurations();

        viewModel.focusLost();

        String message = viewModel.getLastLogMsg();
        assertTrue(message.matches(".*"
                + PomodoroManagerAppViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void doNotLogSameDurationsTwice() {
        fillInputFieldsWithAcceptDurations();
        fillInputFieldsWithAcceptDurations();

        viewModel.focusLost();
        viewModel.focusLost();

        String message = viewModel.getLastLogMsg();
        assertTrue(message.matches(".*"
                + PomodoroManagerAppViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(viewModel.getLog().size(), 1);
    }

    @Test
    public void isSwitchOnOffNotCalledWhenButtonIsDisabled() {
        fillInputFieldsWithBadFormat();
        viewModel.processKeyInTextField(viewModel.KEYBOARD_KEY_ENTER);

        String message = viewModel.getLastLogMsg();

        assertTrue(message.matches(".*"
                + PomodoroManagerAppViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void doNotLogSameDurationsTwiceWithPartialInput() {
        viewModel.setPomodoroDuration("30");
        viewModel.setPomodoroDuration("25");
        viewModel.setPomodoroDuration("70");

        viewModel.focusLost();
        viewModel.focusLost();
        viewModel.focusLost();

        assertEquals(viewModel.getLog().size(), 1);
    }

}
