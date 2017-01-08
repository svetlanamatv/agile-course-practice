package ru.unn.agile.Statistics.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Statistics.viewmodel.LoggerTests;
import ru.unn.agile.Statistics.viewmodel.ViewModel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.*;

public class TxtLoggerTests extends LoggerTests {
    private static final String FILENAME = "./TxtLoggerTests.log";
    private TxtLogger txtLogger;

    @Before
    public void before() {
        try {
            txtLogger = new TxtLogger(FILENAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ViewModel vm = new ViewModel(txtLogger);
        setVM(vm);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String msg = "tratata";

        txtLogger.log(msg);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteLogMessage() {
        String msg = "tratata";

        txtLogger.log(msg);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(".*" + msg + "$"));
    }
}
