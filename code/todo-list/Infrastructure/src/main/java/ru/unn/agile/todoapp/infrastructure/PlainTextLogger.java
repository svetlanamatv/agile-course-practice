package ru.unn.agile.todoapp.infrastructure;

import  ru.unn.agile.todoapp.viewmodel.ILogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlainTextLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final ArrayList<String> inMemoryLog = new ArrayList<>();
    private Runnable onLogUpdate;

    private static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public PlainTextLogger(final String pathToLog)  {
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(pathToLog));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        writer = logWriter;
    }

    @Override
    public void addToLog(final String message) {
        String logMessage = getCurrentTime() + " > " + message;

        inMemoryLog.add(logMessage);
        onLogUpdate.run();
        try {
            writer.write(logMessage);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        return inMemoryLog;
    }

    @Override
    public String getLastLogMessage()  {
        return inMemoryLog.get(inMemoryLog.size() - 1);
    };

    @Override
    public void setOnLogUpdateAction(final Runnable onLogUpdate)  {
        this.onLogUpdate = onLogUpdate;
    }
}
