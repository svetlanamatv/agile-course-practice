package ru.unn.agile.newtonroots.viewmodel.testutils;

import java.util.function.Consumer;

public class ArgumentSavingConsumer<T> implements Consumer<T> {
    private T argument;

    @Override
    public void accept(T argument) {
        this.argument = argument;
    }

    public T getArgument() {
        return argument;
    }
}
