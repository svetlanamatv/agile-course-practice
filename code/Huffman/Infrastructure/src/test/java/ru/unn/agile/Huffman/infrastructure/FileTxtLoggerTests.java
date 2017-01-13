package ru.unn.agile.Huffman.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileTxtLoggerTests {
    private static final String FILE = "./huffmanFile.log";
    private FileTextLogger textFileLogCreator;

    @Before
    public void setUp() {
        textFileLogCreator = new FileTextLogger(FILE);
    }

    @Test
    public void canCreateLogCreator() {
        assertNotNull(textFileLogCreator);
    }

    @Test
    public void canCreateFile() {
        try {
            new BufferedReader(new FileReader(FILE));
        } catch (FileNotFoundException e) {
            fail("File: " + FILE + " not found!");
        }
    }

    @Test
    public void canWriteMessageToLog() {
        String testMessage = "Test message";

        textFileLogCreator.log(testMessage);

        String message = textFileLogCreator.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void testLogContainDateAndTime() {
        String testMessage = "Testing message";

        textFileLogCreator.log(testMessage);

        String message = textFileLogCreator.getLog().get(0);
        assertTrue(message.matches("^< \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} >: .*"));
    }

    @Test(expected = Exception.class)
    public void testMessageCantWrite() {
        FileTextLogger failedLogger = new FileTextLogger(null);
        String testMessage = "Test message";

        failedLogger.log(testMessage);

        String message = failedLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }
}
