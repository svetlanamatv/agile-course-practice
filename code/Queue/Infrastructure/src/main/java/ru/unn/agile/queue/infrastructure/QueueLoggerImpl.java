package ru.unn.agile.queue.infrastructure;


import ru.unn.agile.queue.viewmodel.QueueLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QueueLoggerImpl implements QueueLogger {
    private static final String CURRENT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter fileWriter;
    private final String logFileName;

    private static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(CURRENT_TIME_FORMAT, Locale.ENGLISH);
        return format.format(cal.getTime());
    }

    public QueueLoggerImpl(final String logFileName) {
        this.logFileName = logFileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileWriter = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            fileWriter.write(getCurrentTime() + " --- " + s);
            fileWriter.newLine();
            fileWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            logReader = new BufferedReader(new FileReader(logFileName));
            String line = logReader.readLine();

            while (line != null) {
                log.add(line);
                line = logReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
