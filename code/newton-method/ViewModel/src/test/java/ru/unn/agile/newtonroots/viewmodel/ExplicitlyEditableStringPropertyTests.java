package ru.unn.agile.newtonroots.viewmodel;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.newtonroots.viewmodel.testutils.ArgumentSavingConsumer;

import static org.junit.Assert.*;

public class ExplicitlyEditableStringPropertyTests {
    private ExplicitlyEditableStringProperty property;
    private ArgumentSavingConsumer<String> editFinishHandler;

    @Before
    public void setUp() {
        editFinishHandler = new ArgumentSavingConsumer<>();
        property = new ExplicitlyEditableStringProperty("Old", editFinishHandler);
    }

    @Test
    public void afterInstantiationIsNotEdited() {
        assertFalse(property.isBeingEdited());
    }

    @Test(expected = ExplicitlyEditableStringProperty.SetOusideOfEditException.class)
    public void changeOutsideOfEditCausesException() {
        property.set("New");
    }

    @Test
    public void beforeAnEditSavedValueIsNull() {
        assertNull(property.getValueBeforeEdit());
    }

    @Test
    public void startingAnEditIsReflectedInStatus() {
        property.startEdit();

        assertTrue(property.isBeingEdited());
    }

    @Test
    public void startingAnEditCausesCurrentValueToBeSaved() {
        property.startEdit();

        assertEquals("Old", property.getValueBeforeEdit());
    }

    @Test
    public void changeWithinAnEditIsAccepted() {
        property.startEdit();
        property.set("New");

        assertEquals("New", property.get());
    }

    @Test
    public void finishingEditIsReflectedInStatus() {
        property.startEdit();
        property.finishEdit();

        assertFalse(property.isBeingEdited());
    }

    @Test
    public void finishingEditCausesPreviousValueToBeDiscarded() {
        property.startEdit();
        property.set("New");
        property.finishEdit();

        assertNull(property.getValueBeforeEdit());
    }

    @Test
    public void editWithoutChangeDoesNotResultInCallingHandler() {
        property.startEdit();
        property.finishEdit();

        assertEquals(null, editFinishHandler.getArgument());
    }

    @Test
    public void editWithChangeResultsInCallingHandler() {
        property.startEdit();
        property.set("New");
        property.finishEdit();

        assertEquals("New", editFinishHandler.getArgument());
    }

    @Test
    public void finishingAnEditWithoutStartingIsANoOp() {
        property.finishEdit();

        assertFalse(property.isBeingEdited());
        assertNull(property.getValueBeforeEdit());
    }
}
