package ru.unn.agile.BitField.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static ru.unn.agile.BitField.infrastructure.Matcher.matchesPattern;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TestsForTextLogger {
    private static final String FILENAME = "./TxtLogger_Tests-lab3.log";
    private TxtLogger txtLogger;

    @Before
    public void setOn() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void creatingLogWithNameofFile() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canTextingNotOneMessage() {
        String[] mess = {"test 1", "test 2"};

        txtLogger.log(mess[0]);
        txtLogger.log(mess[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + mess[i] + "$"));
        }
    }

    @Test
    public void canCreateDiskLogger() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " not found!");
        }
    }

    @Test
    public void doesEstTimeAndDateInThisLog() {
        String test = "Some message";

        txtLogger.log(test);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteInThisNiceLog() {
        String testMes = "Test message";

        txtLogger.log(testMes);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMes + "$"));
    }
}
