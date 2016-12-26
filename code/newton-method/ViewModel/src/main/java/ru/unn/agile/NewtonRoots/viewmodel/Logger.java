package ru.unn.agile.NewtonRoots.viewmodel;

import java.util.List;

public interface Logger {
    List<String> getLog();

    void appendMessage(String message);
}
