package ru.unn.agile.ColorConverter.model;

import static ru.unn.agile.ColorConverter.model.Constants.*;

final class FromRGBSpace {

    private FromRGBSpace() {

    }
    static double[] convertToLAB(final double[] rgb) {
        double[] xyzParameters = toXYZ(rgb);
        double x = xyzParameters[0] / REFX;
        double y = xyzParameters[1] / REFY;
        double z = xyzParameters[2] / REFZ;

        if (x > ZPZZEEFS) {
            x = Math.pow(x, (ONE / THREEPZ));
        } else {
            x = (SEVPSEV * x) + (SIXTEEN / ONEHUNDREED16);
        }

        if (y > ZPZZEEFS) {
            y = Math.pow(y, (ONE / THREEPZ));
        } else {
            y = (SEVPSEV * y) + (SIXTEEN / ONEHUNDREED16);
        }

        if (z > ZPZZEEFS) {
            z = Math.pow(z, (ONE / THREEPZ));
        } else {
            z = (SEVPSEV * z) + (SIXTEEN / ONEHUNDREED16);
        }

        double paramL = (ONEHUNDREED16 * y) - SIXTEEN;
        double paramA = FIVEHUNDRED * (x - y);
        double paramB = TWOHUNDRED * (y - z);

        paramL = Math.rint(ONETHOUSAND * paramL) / ONETHOUSAND;
        paramA = Math.rint(ONETHOUSAND * paramA) / ONETHOUSAND;
        paramB = Math.rint(ONETHOUSAND * paramB) / ONETHOUSAND;
        return new double[] {paramL, paramA, paramB};
    }

    static double[] convertToHSV(final double[] rgb) {
        double[] paramsOfHSV = rgb;
        double r = paramsOfHSV[0] / TWOHUNDREDFF;
        double g = paramsOfHSV[1] / TWOHUNDREDFF;
        double b = paramsOfHSV[2] / TWOHUNDREDFF;

        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);

        paramsOfHSV[2] = max * ONEHUNDRED;

        double delta = max - min;
        if (delta == 0) {
            paramsOfHSV[0] = 0;
            paramsOfHSV[1] = 0;
        } else {
            paramsOfHSV[1] = (delta / max) * ONEHUNDRED;
            double delR = (((max - r) / SIX) + (delta / TWO)) / delta;
            double delG = (((max - g) / SIX) + (delta / TWO)) / delta;
            double delB = (((max - b) / SIX) + (delta / TWO)) / delta;

            if (r == max) {
                paramsOfHSV[0] = delB - delG;
            } else if (g == max) {
                paramsOfHSV[0] = (ONE / THREEPZ) + delR - delB;
            } else if (b == max) {
                paramsOfHSV[0] = (TWO / THREEPZ) + delG - delR;
            }

            if (paramsOfHSV[0] < 0) {
                paramsOfHSV[0] += ONE;
            }
            if (paramsOfHSV[0] > ONE) {
                paramsOfHSV[0] -= ONE;
            }
        }
        paramsOfHSV[0] *= THS;
        paramsOfHSV[0] = Math.rint(paramsOfHSV[0]);
        for (int i = 0; i < paramsOfHSV.length; i++) {
            paramsOfHSV[i] = Math.rint(ONETHOUSAND * paramsOfHSV[i]) / ONETHOUSAND;
        }

        return paramsOfHSV;
    }

    private static double[] toXYZ(final double[] rgb) {
        double[] xyzParameters = rgb;
        double r = xyzParameters[0] / TWOHUNDREDFF;
        double g = xyzParameters[1] / TWOHUNDREDFF;
        double b = xyzParameters[2] / TWOHUNDREDFF;

        if (r > ZPZFZFF) {
            r = Math.pow(((r + ZEROPZERO5) / ONEPZFF), TPF);
        } else {
            r = r / TWELVEPNT;
        }

        if (g > ZPZFZFF) {
            g = Math.pow(((g + ZEROPZERO5) / ONEPZFF), TPF);
        } else {
            g = g / TWELVEPNT;
        }

        if (b > ZPZFZFF) {
            b = Math.pow(((b + ZEROPZERO5) / ONEPZFF), TPF);
        } else {
            b = b / TWELVEPNT;
        }

        r = r * ONEHUNDRED;
        g = g * ONEHUNDRED;
        b = b * ONEHUNDRED;

        xyzParameters[0] = r * ZPFOTF + g * ZPTFSS + b * ZPOEZF;
        xyzParameters[1] = r * ZPT1TS + g * ZPSOFT + b * ZPZSTT;
        xyzParameters[2] = r * ZPZONT + g * ZPOONT + b * ZPNFZF;

        for (int i = 0; i < xyzParameters.length; i++) {
            xyzParameters[i] = Math.rint(ONETHOUSAND * xyzParameters[i]) / ONETHOUSAND;
        }
        return xyzParameters;
    }
}
