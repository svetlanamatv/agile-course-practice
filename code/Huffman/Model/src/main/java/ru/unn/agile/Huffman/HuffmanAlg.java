package ru.unn.agile.Huffman;

import java.util.PriorityQueue;

public class HuffmanAlg {
    private HTree hTree = null;
    private static final int ASCII_SIZE = 256;

    private void buildTree(final String stringForBuilding) {
        PriorityQueue<HTree> nodesMaker = new PriorityQueue<HTree>();
        int[] frequenciesOfSymbols = countingFrequencies(stringForBuilding.toCharArray());

        for (int i = 0; i < frequenciesOfSymbols.length; ++i) {
            if (frequenciesOfSymbols[i] != 0) {
                nodesMaker.offer(new HLeaf(frequenciesOfSymbols[i], (char) i));
            }
        }
        while (nodesMaker.size() > 1) {
            HTree l = nodesMaker.poll();
            HTree r = nodesMaker.poll();
            nodesMaker.offer(new HNode(l, r));
        }
        hTree = nodesMaker.poll();
    }


    private int[] countingFrequencies(final char[] charArray) {
        int[] frequenciesOfSymbols = new int[ASCII_SIZE];
        for (char c : charArray) {
            frequenciesOfSymbols[c]++;
        }
        return frequenciesOfSymbols;
    }

    private void builtHTreeCheck() {
        if (hTree == null) {
            throw new IllegalArgumentException("HTree hasn't been built.");
        }
    }

    private void nullInputCheck(final String stringForCheck) {
        if (stringForCheck == null) {
            throw new IllegalArgumentException("NULL input! "
                    + "Use encode/decode methods for string arguments.");
        }
    }

    public String encodeStringByCurrentHTree(final String stringForEncoding) {
        nullInputCheck(stringForEncoding);
        builtHTreeCheck();
        String codedString = "";
        char currentCharacterInInputString;

        for (int i = 0; i < stringForEncoding.length(); ++i) {
            currentCharacterInInputString = stringForEncoding.charAt(i);
            HTree currentNode = hTree;
            if (!currentNode.contains(currentCharacterInInputString)) {
                throw new IllegalArgumentException("Any characters "
                        + "in input string"
                        + " hasn't been used for HTree building!");
            }
            if (hTree instanceof HLeaf) {
                codedString += '1';
            }
            while (currentNode instanceof HNode) {
                if (((HNode) currentNode).leftTreeContains(currentCharacterInInputString)) {
                    currentNode = ((HNode) currentNode).getLeftTree();
                    codedString += '0';
                } else {
                    currentNode = ((HNode) currentNode).getRightTree();
                    codedString += '1';
                }
            }
        }
        return codedString;
    }

    public String encodeString(final String stringForEncoding) {
        nullInputCheck(stringForEncoding);
        if (stringForEncoding == "") {
            return "";
        }
        buildTree(stringForEncoding);
        return encodeStringByCurrentHTree(stringForEncoding);
    }

    public String decodeBinarySequenceByCurrentHTree(final String stringForDecoding) {
        nullInputCheck(stringForDecoding);
        builtHTreeCheck();
        String decodedString = "";
        HTree currentNode = hTree;
        char currentCharacterInInputString;

        for (int i = 0; i < stringForDecoding.length(); ++i) {
            currentCharacterInInputString = stringForDecoding.charAt(i);
                switch (currentCharacterInInputString) {
                    case '0':
                        currentNode = ((HNode) currentNode).getLeftTree();
                        break;
                    case '1':
                        currentNode = ((HNode) currentNode).getRightTree();
                        break;
                    default:
                        throw new IllegalArgumentException("Decoding input string must contain"
                                + " only '1' or '0' symbols!");
                }
            if (currentNode instanceof  HLeaf) {
                decodedString += currentNode.getSymbolsInTree();
                currentNode = hTree;
            }
        }
        if (!currentNode.equals(hTree)) {
            throw new IllegalArgumentException("Incorrect binary sequence for current HTree!");
        }
        return decodedString;
    }
}
