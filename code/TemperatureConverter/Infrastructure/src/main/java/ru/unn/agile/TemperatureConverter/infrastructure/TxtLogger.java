package ru.unn.agile.TemperatureConverter.infrastructure;

import ru.unn.agile.TemperatureConverter.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jane on 16.12.2016.
 */
public class TxtLogger implements ILogger {
    private final String file;
    private final BufferedWriter writer;

    public TxtLogger(final String file) {
        this.file = file;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }
    private String time() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm:ss", Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    @Override
    public void log(final String msg) {
        try {
            writer.write(time() + " >> " + msg);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> logs = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                logs.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }
}
