package ru.unn.agile.BitField.model;

public class BitField {
    private final int bitLen;
    private int[] mem;
    private final int memLen;

    public BitField(final int len) {
        bitLen = len;
        memLen = (len + Integer.SIZE - 1) >> (int) (Math.log(Integer.SIZE) / Math.log(2));
        mem = new int[memLen];
    }

    public BitField(final BitField bitfield) {
        bitLen = bitfield.bitLen;
        memLen = bitfield.memLen;
        mem = new int[memLen];
        System.arraycopy(bitfield.mem, 0, mem, 0, memLen);
    }

    private void checkBounds(final int idx) {
        if (!(idx > -1 && idx < bitLen)) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    private void checkSizes(final BitField field) {
        if (bitLen != field.bitLen) {
            throw new ArrayIndexOutOfBoundsException("Fields have different sizes!");
        }
    }

    private int getMemIndex(final int idx) {
        return idx >> (int) (Math.log(Integer.SIZE) / Math.log(2));
    }

    private int getMemMask(final int idx) {
        return 1 << (idx & (Integer.SIZE - 1));
    }

    public void setBit(final int idx) {
        checkBounds(idx);
        mem[getMemIndex(idx)] |= getMemMask(idx);
    }

    public void clrBit(final int idx) {
        checkBounds(idx);
        mem[getMemIndex(idx)] &= ~getMemMask(idx);
    }

    public int getBit(final int idx) {
        checkBounds(idx);
        return ((mem[getMemIndex(idx)] & getMemMask(idx)) == 0) ? 0 : 1;
    }

    public boolean isEquals(final BitField field) {
        if (bitLen == field.bitLen) {
            for (int i = 0; i < memLen; i++) {
                if (mem[i] != field.mem[i]) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < bitLen; i++) {
            if (getBit(i) == 1) {
                result += "1";
            } else {
                result += "0";
            }
        }
        return result;
    }

    public static BitField fromString(final String str) {
        if (str.matches("[01]+")) {
            BitField result = new BitField(str.length());
            int i = 0;
            char ch;
            while (i < result.bitLen) {
                ch = str.charAt(i);
                if (ch == '0') {
                    result.clrBit(i++);
                } else if (ch == '1') {
                    result.setBit(i++);
                } else {
                    break;
                }
            }
            return result;
        }
        return null;
    }

    public BitField and(final BitField field) {
        checkSizes(field);
        BitField res = new BitField(this);
        for (int i = 0; i < res.memLen; i++) {
            res.mem[i] &= field.mem[i];
        }
        return res;
    }

    public BitField or(final BitField field) {
        checkSizes(field);
        BitField res = new BitField(this);
        for (int i = 0; i < res.memLen; i++) {
            res.mem[i] |= field.mem[i];
        }
        return res;
    }

    public BitField xor(final BitField field) {
        checkSizes(field);
        BitField res = new BitField(this);
        for (int i = 0; i < res.memLen; i++) {
            res.mem[i] ^= field.mem[i];
        }
        return res;
    }

    public BitField not() {
        BitField res = new BitField(bitLen);
        for (int i = 0; i < memLen; i++) {
            res.mem[i] = ~mem[i];
        }
        return res;
    }
}

