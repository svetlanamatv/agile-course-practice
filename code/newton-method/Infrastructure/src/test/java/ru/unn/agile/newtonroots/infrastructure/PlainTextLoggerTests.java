package ru.unn.agile.newtonroots.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.agile.newtonroots.viewmodel.testutils.StringSuffixMatcher.endsWith;

public class PlainTextLoggerTests {
    private static final String LOG_FILENAME = "newton-method.log";
    private static final String TEST_MESSAGE = "TEST MESSAGE";
    private PlainTextLogger logger;

    @Before
    public void setUp() throws IOException {
        logger = new PlainTextLogger(LOG_FILENAME);
    }

    @After
    public void tearDown() {
        Path logPath = Paths.get(LOG_FILENAME);
        try {
            Files.deleteIfExists(logPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uponInstantiationLogFileIsCreated() throws IOException {
        assertTrue(Files.exists(Paths.get(LOG_FILENAME)));
    }

    @Test
    public void messageIsAppendedToLogFile() throws IOException {
        logger.appendMessage(TEST_MESSAGE);

        BufferedReader logReader = new BufferedReader(new FileReader(LOG_FILENAME));
        String actualMessage = logReader.readLine();
        assertThat(actualMessage, endsWith(TEST_MESSAGE));
    }

    @Test
    public void messageCountQueryIsSatisfied() {
        logger.appendMessage(TEST_MESSAGE);

        assertEquals(1, logger.getMessageCount());
    }

    @Test
    public void lastMessageQueryIsSatisfied() {
        logger.appendMessage(TEST_MESSAGE);

        assertThat(logger.getLastMessage(), endsWith(TEST_MESSAGE));
    }

    @Test
    public void messageListQueryIsSatisfied() {
        logger.appendMessage(TEST_MESSAGE);

        List<String> logMessageList = logger.getMessageList();
        assertEquals(1, logMessageList.size());
        assertThat(logMessageList.get(0), endsWith(TEST_MESSAGE));
    }
}
