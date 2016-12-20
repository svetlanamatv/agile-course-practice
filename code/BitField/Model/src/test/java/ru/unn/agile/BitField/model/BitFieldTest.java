package ru.unn.agile.BitField.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitFieldTest {
    private BitField fieldA = BitField.fromString("0011");
    private BitField fieldB = BitField.fromString("0101");

    @Test
    public void shouldSetBit() {
        BitField field = new BitField(1);
        field.setBit(0);
        assertEquals("Bit was not set!", field.getBit(0), 1);
    }

    @Test
    public void shouldClearBit() {
        BitField field = new BitField(1);
        field.setBit(0);
        field.clrBit(0);
        assertEquals("Bit was not cleared!", field.getBit(0), 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowOutOfBoundException() {
        BitField field = new BitField(1);
        field.setBit(10);
    }

    @Test
    public void shouldFieldsEquals() {
        BitField sourceField = new BitField(10);
        BitField copiedField = new BitField(sourceField);

        assertEquals("Fields are not equal!", sourceField.isEquals(copiedField), true);
    }

    @Test
    public void shouldFieldsNotEquals() {
        BitField fieldA = new BitField(10);
        BitField fieldB = new BitField(10);
        fieldB.setBit(0);
        assertEquals("Fields are equal!", fieldA.isEquals(fieldB), false);
    }

    @Test
    public void shouldFieldsNotEqualsDifferentLength() {
        BitField fieldA = new BitField(10);
        BitField fieldB = new BitField(11);
        assertEquals("Fields are equal!", fieldA.isEquals(fieldB), false);
    }

    @Test
    public void shouldConvertToString() {
        BitField field = new BitField(5);
        field.setBit(0);
        field.setBit(4);
        assertEquals("Wrong string received!", "10001", field.toString());
    }

    @Test
    public void shouldConvertFromString() {
        BitField field = BitField.fromString("0011");
        assert field != null;
        assertEquals("Wrong string received!", "0011", field.toString());
    }

    @Test
    public void shouldNotConvertFromString() {
        assertEquals("Wrong string received!", null, BitField.fromString("00a11"));
    }

    @Test
    public void shouldDoAndOperation() {
        assertEquals("Wrong string received!", "0001", fieldA.and(fieldB).toString());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldNotDoAndOperation() {
        BitField field1 = BitField.fromString("1");
        BitField field11 = BitField.fromString("11");
        assert field1 != null;
        field1.and(field11);
    }

    @Test
    public void shouldDoOrOperation() {
        assertEquals("Wrong string received!", "0111", fieldA.or(fieldB).toString());
    }

    @Test
    public void shouldDoXorOperation() {
        assertEquals("Wrong string received!", "0110", fieldA.xor(fieldB).toString());
    }

    @Test
    public void shouldDoNotOperation() {
        BitField field = BitField.fromString("0011");
        assert field != null;
        assertEquals("Wrong string received!", "1100", field.not().toString());
    }
}
