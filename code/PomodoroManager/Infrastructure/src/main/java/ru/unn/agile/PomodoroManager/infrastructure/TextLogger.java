package ru.unn.agile.PomodoroManager.infrastructure;

import ru.unn.agile.PomodoroManager.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Calendar;
import java.util.List;

public class TextLogger implements ILogger {

    private static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufWriter;
    private final String logFilename;

    private static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDF = new SimpleDateFormat(LOG_DATE_FORMAT, Locale.ENGLISH);
        return simpleDF.format(calendar.getTime());
    }

    public TextLogger(final String logFilename) {
        this.logFilename = logFilename;

        BufferedWriter logBufferedWriter = null;
        try {
            logBufferedWriter = new BufferedWriter(new FileWriter(logFilename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufWriter = logBufferedWriter;
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufReader;
        ArrayList<String> returnLog = new ArrayList<String>();
        try {
            bufReader = new BufferedReader(new FileReader(logFilename));
            String readLine = bufReader.readLine();

            while (readLine != null) {
                returnLog.add(readLine);
                readLine = bufReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return returnLog;
    }

    @Override
    public void log(final String s) {
        try {
            bufWriter.write(getNowTime() + " > " + s);
            bufWriter.newLine();
            bufWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
