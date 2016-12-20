package ru.unn.agile.ColorConverter.model;

import static ru.unn.agile.ColorConverter.model.ColorSpaces.*;

public final class CheckParameters {
    private CheckParameters() {

    }

    public static void checkParameters(final ColorSpaces colorSpaces, final double[] params) {
        if (colorSpaces == HSV) {
            checkParametersHSV(params);
        } else if (colorSpaces == LAB) {
            checkParametersLAB(params);
        } else {
            checkParametersRGB(params);
        }
    }

    private static void checkParametersRGB(final double[] params) {
        if (!checkNumberParameters(params)) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            if (params[i] < 0 || params[i] > Constants.TWOHUNDREDFF) {
                throw new IllegalArgumentException();
            }
            if (params[i] % Constants.ONE != 0) {
                throw new NumberFormatException();
            }
        }
    }

    private static void checkParametersHSV(final double[] params) {
        if (!checkNumberParameters(params)) {
            return;
        }
        if (params[0] < 0 || params[0] > Constants.THS) {
            throw new IllegalArgumentException();
        }
        if (params[0] % Constants.ONE != 0) {
            throw new NumberFormatException();
        }
        if (params[1] < 0 || params[1] > Constants.ONEHUNDRED) {
                throw new IllegalArgumentException();
        }
        if (params[2] < 0 || params[2] > Constants.ONEHUNDRED) {
            throw new IllegalArgumentException();
        }
    }

    private static void checkParametersLAB(final double[] params) {
        if (!checkNumberParameters(params)) {
            return;
        }
        if (params[0] < 0 || params[0] > Constants.ONEHUNDRED) {
            throw new IllegalArgumentException();
        }
        if (params[1] < -Constants.OHTE || params[1] > Constants.OHTE) {
            throw new IllegalArgumentException();
        }
        if (params[2] < -Constants.OHTE || params[2] > Constants.OHTE) {
            throw new IllegalArgumentException();
        }
    }
    static boolean checkNumberParameters(final double[] params) {
        if (params != null && params.length == Constants.THREEPZ) {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
