package ru.unn.agile.triangle.infrastructure.mock;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class AlwaysThrowsIOExceptionFileWriter extends FileWriter {
    public AlwaysThrowsIOExceptionFileWriter(final String fileName)
            throws IOException {
        super(fileName);
    }

    @Override
    public void write(final char[] cbuf) throws IOException {
        throw new IOException();
    }

    @Override
    public void write(final int c) throws IOException {
        throw new IOException();
    }

    @Override
    public void write(final char[] cbuf, final int off, final int len)
            throws IOException {
        throw new IOException();
    }

    @Override
    public void write(final String str) throws IOException {
        throw new IOException();
    }

    @Override
    public void write(final String str, final int off, final int len)
            throws IOException {
        throw new IOException();
    }

    @Override
    public Writer append(final char c) throws IOException {
        throw new IOException();
    }

    @Override
    public Writer append(final CharSequence csq) throws IOException {
        throw new IOException();
    }

    @Override
    public Writer append(final CharSequence csq, final int start, final int end)
            throws IOException {
        throw new IOException();
    }

    @Override
    public void close() throws IOException {
        throw new IOException();
    }

    @Override
    public void flush() throws IOException {
        throw new IOException();
    }
}
