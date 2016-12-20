package ru.unn.agile.MyDeque.Model;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WhenAddDeleteElementToDeque {
    private Deque deque;

    @Before
    public void setUp() throws  Exception {
            deque = new Deque();
    }

    @Test
    public void getSizeEmptyDeque() {
        Deque emptyDeque = new Deque();
        int sizeDeque = emptyDeque.getCurrentSize();
        assertEquals(0, sizeDeque);
    }

    @Test
    public void pushHeadIntElement() {
        deque.pushHeadElement(5);
        assertEquals(1, deque.getCurrentSize());
    }

    @Test
    public void checkSizeAfterClear() {
        deque.pushHeadElement(5);
        deque.clear();
        assertEquals(0, deque.getCurrentSize());
    }

    @Test
    public void popHeadIntElement() {
        deque.pushHeadElement(5);
        int element =  deque.popHeadElement();
        assertEquals(5, element);
    }

    @Test
    public void pushTailIntElement() {
        deque.pushTailElement(5);
        assertEquals(1, deque.getCurrentSize());
    }

    @Test
    public void popTailIntElement() {
        deque.pushTailElement(5);
        int element = deque.popTailElement();
        assertEquals(5, element);
    }

    @Test
    public void checkIsEmptyMethod() {
        deque.pushTailElement(5);
        assertEquals(false, deque.isEmpty());
        deque.popHeadElement();
        assertEquals(true, deque.isEmpty());
    }

    @Test
    public void pushToHeadPopFromHead() {
        for (int i = 0; i < 5; i++) {
            deque.pushHeadElement(i);
        }
        int result = 0;
        for (int i = 0; i < 5; i++) {
            result = result * 10 + deque.popHeadElement();
        }
        assertEquals(43210, result);
    }

    @Test
    public void pushToTailPopFromTail() {
        for (int i = 0; i < 5; i++) {
            deque.pushTailElement(i);
        }
        int result = 0;
        for (int i = 0; i < 5; i++) {
            result = result * 10 + deque.popTailElement();
        }
        assertEquals(43210, result);
    }

    @Test
    public void pushElementsMoreDequeSize() {
        for (int i = 0; i < 101; i++) {
            deque.pushTailElement(i);
        }
        assertEquals(101, deque.getCurrentSize());
    }

    @Test
    public void pushToHeadPopFromTail() {
        for (int i = 0; i < 5; i++) {
            deque.pushHeadElement(i);
        }
        int result = 0;
        for (int i = 0; i < 5; i++) {
            result = result * 10 + deque.popTailElement();
        }
        assertEquals(1234, result);
    }

    @Test
    public void pushToTailPopFromHead() {
        for (int i = 0; i < 5; i++) {
            deque.pushTailElement(i);
        }
        int result = 0;
        for (int i = 0; i < 5; i++) {
            result = result * 10 + deque.popHeadElement();
        }
        assertEquals(1234, result);
    }

}
