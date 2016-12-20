package ru.unn.agile.MyDeque.Model;

public class Deque {
    public static final int START_SIZE = 100;
    public static final int ZERO = 0;
    private int[] deque;
    private int currentSize = ZERO;
    private int currentMaxSize = START_SIZE;

    public Deque() {
        this.deque = new int[currentMaxSize];
    }

    public int getCurrentSize() {

        return currentSize;
    }

    public void pushHeadElement(final int element) {
        this.increaseDeque();
        for (int i = this.currentSize; i > 0; i--) {
            this.deque[i] = this.deque[i - 1];
        }
        this.deque[0] = element;
        this.currentSize++;
    }

    public void clear() {
        this.currentSize = 0;
    }

    public int popHeadElement() {
        if (this.isEmpty()) {
            throw new RuntimeException("This deque doesn't contain items.");
        } else {
            int popElement = this.deque[0];
            for (int i = 0; i < this.currentSize - 1; i++) {
                this.deque[i] = this.deque[i + 1];
            }
            this.currentSize--;
            return popElement;
        }
    }

    public void pushTailElement(final int element) {
        this.increaseDeque();
        this.deque[currentSize] = element;
        this.currentSize++;
    }

    public int popTailElement() {
        if (this.isEmpty()) {
            throw new RuntimeException("This deque doesn't contain items.");
        } else {
            return this.deque[--currentSize];
        }
    }

    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    private void increaseDeque() {
        if (this.currentMaxSize == this.currentSize) {
            this.currentMaxSize += START_SIZE;
            int[] newDeque = new int[this.currentMaxSize];
            for (int i = 0; i < this.currentSize; i++) {
                newDeque[i] = this.deque[i];
            }
            this.deque = newDeque;
        }
    }

    public String toString() {
        String printedDeque = "";
        for (int i = 0; i < this.currentSize; i++) {
            printedDeque += this.deque[i];
        }
        return printedDeque;
    }
}
