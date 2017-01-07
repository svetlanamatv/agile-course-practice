package ru.unn.agile.MassConverter.infrastructure;

import ru.unn.agile.MassConverter.ViewModel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        writer = logWriter;
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String message = reader.readLine();

            while (message != null) {
                log.add(message);
                message = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }

    @Override
    public String getLastMessage() {
        List<String> log = getLog();
        String lastMessage;
        if (log.isEmpty()) {
            lastMessage = "";
        } else {
            lastMessage = log.get(log.size() - 1);
        }
        return lastMessage;
    }

    @Override
    public void log(final String message) {
        try {
            writer.write(time() + " > " + message);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String time() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }
}
