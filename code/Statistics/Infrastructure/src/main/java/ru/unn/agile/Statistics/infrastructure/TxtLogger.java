package ru.unn.agile.Statistics.infrastructure;

import ru.unn.agile.Statistics.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final ArrayList<String> logInMemory = new ArrayList<String>();

    private static String now() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public TxtLogger(final String filename) throws Exception {
        BufferedWriter logWriter = null;
        FileWriter fileWriter = new FileWriter(filename);
        logWriter = new BufferedWriter(fileWriter);

        writer = logWriter;
    }

    @Override
    public List<String> getLog() {
        return logInMemory;
    }

    @Override
    public void log(final String message) {
        try {
            String logMsg = now() + " > " + message;
            writer.write(logMsg);
            writer.newLine();
            writer.flush();

            logInMemory.add(logMsg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
