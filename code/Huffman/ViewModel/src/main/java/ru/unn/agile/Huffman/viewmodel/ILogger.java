package ru.unn.agile.Huffman.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String str);

    List<String> getLog();
}
