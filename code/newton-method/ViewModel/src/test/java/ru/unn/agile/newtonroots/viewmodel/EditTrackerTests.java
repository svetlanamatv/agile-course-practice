package ru.unn.agile.newtonroots.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.newtonroots.viewmodel.testutils.ArgumentSavingConsumer;

import static org.junit.Assert.*;

public class EditTrackerTests {
    private EditTracker<String> tracker;
    private ArgumentSavingConsumer<String> argSaver;

    @Before
    public void setUp() {
        tracker = new EditTracker<>();
        argSaver = new ArgumentSavingConsumer<>();
    }

    @Test
    public void afterInstantiationIsInNonTrackingState() {
        assertFalse(tracker.isTracking());
    }

    @Test
    public void afterInstantiationInitialAndCurrentValuesAreNull() {
        assertNull(tracker.getInitialValue());
        assertNull(tracker.getCurrentValue());
    }

    @Test
    public void whenTrackingIsStartedStatusChanges() {
        tracker.startTracking("Old", null);

        assertTrue(tracker.isTracking());
    }

    @Test
    public void whenTrackingIsStartedInitialValueIsSaved() {
        tracker.startTracking("Old", null);

        assertEquals("Old", tracker.getInitialValue());
    }

    @Test
    public void whenTrackingIsStartedCurrentValueIsEqualToInitial() {
        tracker.startTracking("Old", null);

        assertEquals(tracker.getInitialValue(), tracker.getCurrentValue());
    }

    @Test
    public void whenTrackingAndUpdatePerformedUpdatedValueIsRecorded() {
        tracker.startTracking("Old", null);

        tracker.updateValue("New");

        assertEquals("New", tracker.getCurrentValue());
    }

    @Test
    public void whenTrackingAndUpdateChangesValueChangeIsRegistered() {
        tracker.startTracking("Old", null);

        tracker.updateValue("New");

        assertTrue(tracker.valueChanged());
    }

    @Test
    public void whenTrackingAndUpdateRevertsValueToInitialChangeIsNotReported() {
        tracker.startTracking("Old", null);

        tracker.updateValue("New");
        tracker.updateValue("Old");

        assertFalse(tracker.valueChanged());
    }

    @Test
    public void whenTrackingIsFinishedIsInNonTrackingState() {
        tracker.startTracking("Old", null);

        tracker.finishTracking();

        assertFalse(tracker.isTracking());
    }

    @Test
    public void whenTrackingIsFinishedInitialAndCurrentValuesAreReset() {
        tracker.startTracking("Old", null);

        tracker.finishTracking();

        assertNull(tracker.getInitialValue());
        assertNull(tracker.getCurrentValue());
    }

    @Test
    public void whenTrackingIsFinishedTrackingFinishHandlerIsCalledWithCurrentValue() {
        tracker.startTracking("Old", argSaver);

        tracker.updateValue("New");
        tracker.finishTracking();

        assertEquals("New", argSaver.getArgument());
    }

    @Test
    public void whenTrackingIsFinishedAndValueWasNotChangedHandlerWasNotCalled() {
        tracker.startTracking("Old", argSaver);

        tracker.updateValue("New");
        tracker.updateValue("Old");
        tracker.finishTracking();

        assertNull(argSaver.getArgument());
    }
}
