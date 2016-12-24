package ru.unn.agile.queue.infrastructure;


import ru.unn.agile.queue.viewmodel.QueueLogger;

import java.io.*;
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

    public QueueLoggerImpl(final String logFileName) throws IOException {
        this.logFileName = logFileName;
        fileWriter = new BufferedWriter(new FileWriter(logFileName));
    }

    @Override
    public void log(final String s) throws IOException {
        fileWriter.write(getCurrentTime() + " --- " + s);
        fileWriter.newLine();
        fileWriter.flush();
    }

    @Override
    public List<String> getLog() throws IOException {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
            logReader = new BufferedReader(new FileReader(logFileName));
            String line = logReader.readLine();

            while (line != null) {
                log.add(line);
                line = logReader.readLine();
            }
        return log;
    }

}
