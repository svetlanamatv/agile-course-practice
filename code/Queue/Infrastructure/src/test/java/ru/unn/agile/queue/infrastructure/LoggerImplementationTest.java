package ru.unn.agile.queue.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.queue.infrastructure.RegularExpressionMatcher.matchesPattern;

public class LoggerImplementationTest {
    private static final String LOG_FILE_NAME = "./queue.log";
    private QueueLoggerImpl queueLoggerImpl;

    @Before
    public void setUp() {
        try {
            queueLoggerImpl = new QueueLoggerImpl(LOG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThatCanCreateLogger() {
        assertNotNull(queueLoggerImpl);
    }

    @Test
    public void testThatCanCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOG_FILE_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_FILE_NAME + " wasn't created!");
        }
    }

    @Test
    public void testThatCanLogOneMessage() {
        String testMessage = "Test message";

        try {
            queueLoggerImpl.log(testMessage);
            String message = queueLoggerImpl.getLog().get(0);
            assertThat(message, matchesPattern(".*" + testMessage + "$"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThatCanLogMoreMessages() {
        String[] messages = {"Test message 1", "Test message 2"};

        try {
            queueLoggerImpl.log(messages[0]);
            queueLoggerImpl.log(messages[1]);

            List<String> actualMessages = queueLoggerImpl.getLog();
            for (int i = 0; i < actualMessages.size(); i++) {
                assertThat(actualMessages.get(i), matchesPattern(".*" + messages[i] + "$"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTestThatLogContainsDateAndTime() {
        String testMessage = "Test message";

        try {
            queueLoggerImpl.log(testMessage);
            String message = queueLoggerImpl.getLog().get(0);
            assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} --- "
                    + ".*"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
