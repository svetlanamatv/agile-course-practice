package ru.unn.agile.triangle.viewmodel;

import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.mock.FakeLogger;

import static org.junit.Assert.*;

public class LoggerViewModelTests {
    private static final String TEST_MESSAGE = "Test message";
    private static final String TEST_PATTERN = "Test {0} {1}";
    private static final String TEST_FORMATTED_MESSAGE = "Test 1 2";

    private LoggerViewModel loggerViewModel;
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = new FakeLogger();
        loggerViewModel = new LoggerViewModel(logger);
    }

    @Test
    public void recordsListIsEmptyIfLoggerWasNotCalled() throws Exception {
        assertTrue(loggerViewModel.getRecords().isEmpty());
    }

    @Test
    public void recordsPropertyIsNotNull() throws Exception {
        assertNotEquals(loggerViewModel.recordsProperty(), null);
    }

    @Test
    public void recordAddedIfLoggerPrintWasCalled() throws Exception {
        logger.print(TEST_MESSAGE);

        LoggerRecordViewModel addedRecordViewModel =
                loggerViewModel.getRecords().get(0);
        assertEquals(TEST_MESSAGE, addedRecordViewModel.getMessage());
    }

    @Test
    public void recordAddedIfLoggerPrintWithPatternWasCalled() throws Exception {
        logger.print(TEST_PATTERN, 1, 2);

        LoggerRecordViewModel addedRecordViewModel =
                loggerViewModel.getRecords().get(0);
        assertEquals(TEST_FORMATTED_MESSAGE, addedRecordViewModel.getMessage());
    }

    @Test
    public void storedNumberOfRecordsIsNotGreaterThanMaxNumberOfRecords() throws Exception {
        int maxRecords = loggerViewModel.getMaxNumberOfRecords();

        for (int i = 0; i < 2 * maxRecords; i++) {
            logger.print(TEST_MESSAGE);
        }

        int actualNumberOfRecords = loggerViewModel.getRecords().size();
        assertEquals(maxRecords, actualNumberOfRecords);
    }

    @Test
    public void messagesAreStoredInOrderTheyWerePrinted() throws Exception {
        int numberOfRecords = loggerViewModel.getMaxNumberOfRecords() / 2;

        for (int i = 0; i < numberOfRecords; i++) {
            logger.print(TEST_MESSAGE + " " + Integer.toString(i));
        }

        ObservableList<LoggerRecordViewModel> addedRecordViewModels =
                loggerViewModel.getRecords();
        for (int i = 0; i < numberOfRecords; i++) {
            String requiredMessage = TEST_MESSAGE + " " + Integer.toString(i);
            assertEquals(requiredMessage, addedRecordViewModels.get(i).getMessage());
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failsWhenChangingRecords() throws Exception {
        logger.print(TEST_MESSAGE);

        loggerViewModel.getRecords().clear();
    }
}
