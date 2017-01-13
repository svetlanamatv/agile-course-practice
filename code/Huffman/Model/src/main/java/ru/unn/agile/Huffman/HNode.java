package ru.unn.agile.Huffman;

public final class HNode extends HTree {
    private final HTree leftNode;
    private final HTree rightNode;

    public HNode(final HTree lNode, final HTree rNode) {
        super(lNode.getFrequency() + rNode.getFrequency(),
                lNode.getSymbolsInTree() + rNode.getSymbolsInTree());
        leftNode = lNode;
        rightNode = rNode;
    }

    public HTree getLeftTree() {
        return leftNode;
    }

    public HTree getRightTree() {
        return rightNode;
    }

    public boolean leftTreeContains(final char c) {
        return leftNode.contains(c);
    }
}
