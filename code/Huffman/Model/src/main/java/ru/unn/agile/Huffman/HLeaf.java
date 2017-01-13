package ru.unn.agile.Huffman;

public final class HLeaf extends HTree {
    public HLeaf(final int frequency, final char c) {
        super(frequency, String.valueOf(c));
    }
}
