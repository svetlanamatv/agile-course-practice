package ru.unn.agile.triangle.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObservableLoggerTests {
    private static final String TEST_MESSAGE = "Test message";
    private static final String TEST_PATTERN = "Test {1} {0}";
    private static final String TEST_FORMATTED = "Test 2 1";

    private TestObservableLogger observableLogger;

    @Before
    public void setUp() throws Exception {
        observableLogger = new TestObservableLogger();
    }

    @Test
    public void printCalledWasAndAddRecordMethodWasCalled() throws Exception {
        observableLogger.print(TEST_MESSAGE);

        assertEquals(TEST_MESSAGE, observableLogger.getLastAddedRecord().getMessage());
    }

    @Test
    public void printWithPatternWasCalledAndAddRecordMethodWasCalled() throws Exception {
        observableLogger.print(TEST_PATTERN, 1, 2);

        assertEquals(TEST_FORMATTED, observableLogger.getLastAddedRecord().getMessage());
    }

    @Test
    public void printWasCalledAndListenerWasFired() throws Exception {
        observableLogger.addListenerForNewRecord((record) ->
            assertEquals(TEST_MESSAGE, record.getMessage())
        );

        observableLogger.print(TEST_MESSAGE);
    }

    @Test
    public void printWithPatternWasCalledAndListenerWasFired() throws Exception {
        observableLogger.addListenerForNewRecord((record) ->
            assertEquals(TEST_FORMATTED, record.getMessage())
        );

        observableLogger.print(TEST_PATTERN, 1, 2);
    }
}
