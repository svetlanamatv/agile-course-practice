package ru.unn.agile.ColorConverter.model;

import static ru.unn.agile.ColorConverter.model.Constants.*;

final class ToRGBSpace {
    private ToRGBSpace() {

    }
    static double[] converterLABtoRGB(final double[] lab) {
        double[] param = toXYZ(lab);
        double paramX = param[0] / ONEHUNDRED;
        double paramY = param[1] / ONEHUNDRED;
        double paramZ = param[2] / ONEHUNDRED;
        double paramR = paramX * THREEP24 + paramY * (-ONEPF) + paramZ * (-ZPFN);
        double paramG = paramX * (-ZPNSEN) + paramY * OPESFE + paramZ * ZPZFOF;
        double paramB = paramX * ZPZFFS + paramY * (-ZPTZFZ) + paramZ * OPZFSZ;
        paramR = paramR > ZPZZTOT ? (ONEPZFF * (Math.pow(paramR, (ONE / TPF))) - ZEROPZERO5)
                : TWELVEPNT * paramR;
        paramG = paramG > ZPZZTOT ? (ONEPZFF * (Math.pow(paramG, (ONE / TPF))) - ZEROPZERO5)
                : TWELVEPNT * paramG;
        paramB = paramB > ZPZZTOT ? (ONEPZFF * (Math.pow(paramB, (ONE / TPF))) - ZEROPZERO5)
                : TWELVEPNT * paramB;
        paramR = Math.round(paramR * TWOHUNDREDFF);
        paramG = Math.round(paramG * TWOHUNDREDFF);
        paramB = Math.round(paramB * TWOHUNDREDFF);
        return changeParam(new double[] {paramR, paramG, paramB});
    }

    static double[] converterHSVtoRGB(final double[] hsv) {
        double[] param = hsv;
        double paramR, paramG, paramB;

        if (param[1] == 0) {
            paramR = param[2] * TWOHUNDREDFF;
            paramG = param[2] * TWOHUNDREDFF;
            paramB = param[2] * TWOHUNDREDFF;
        } else {
            double paramH = param[0] / THS * SIX;
            param[1] /= ONEHUNDRED;
            param[2] /= ONEHUNDRED;
            if (paramH == SIX) {
                paramH = 0;
            }
            int tmpI = (int) paramH;
            double first = param[2] * (ONE - param[1]);
            double second = param[2] * (ONE - param[1] * (paramH - tmpI));
            double third = param[2] * (ONE - param[1] * (ONE - (paramH - tmpI)));

            switch (tmpI) {
                case 0:
                    paramR = param[2];
                    paramG = third;
                    paramB = first;
                    break;
                case 1:
                    paramR = second;
                    paramG = param[2];
                    paramB = first;
                    break;
                case 2:
                    paramR = first;
                    paramG = param[2];
                    paramB = third;
                    break;
                case (int) THREEPZ:
                    paramR = first;
                    paramG = second;
                    paramB = param[2];
                    break;
                case FOUR:
                    paramR = third;
                    paramG = first;
                    paramB = param[2];
                    break;
                default:
                    paramR = param[2];
                    paramG = first;
                    paramB = second;
                    break;
            }
            paramR *= TWOHUNDREDFF;
            paramG *= TWOHUNDREDFF;
            paramB *= TWOHUNDREDFF;
        }
        changeParam(new double[]{paramR, paramG, paramB});
        return new double[] {Math.round(paramR), Math.round(paramG), Math.round(paramB)};
    }

    private static double[] toXYZ(final double[] rgb) {
        double[] labParameters = rgb;
        double paramY = (labParameters[0] + SIXTEEN) / ONEHUNDREED16;
        double paramX = labParameters[1] / FIVEHUNDRED + paramY;
        double paramZ = paramY - (labParameters[2] / TWOHUNDRED);
        paramX = Math.pow(paramX, THREEPZ) > ZPZZEEFS ? Math.pow(paramX, THREEPZ)
                : (paramX - SIXTEEN / ONEHUNDREED16) / SEVPSEV;
        paramY = Math.pow(paramY, THREEPZ) > ZPZZEEFS ? Math.pow(paramY, THREEPZ)
                : (paramY - SIXTEEN / ONEHUNDREED16) / SEVPSEV;
        paramZ = Math.pow(paramZ, THREEPZ) > ZPZZEEFS ? Math.pow(paramZ, THREEPZ)
                : (paramZ - SIXTEEN / ONEHUNDREED16) / SEVPSEV;
        paramX = Math.rint(ONETHOUSAND * (paramX * REFX)) / ONETHOUSAND;
        paramY = Math.rint(ONETHOUSAND * (paramY * REFY)) / ONETHOUSAND;
        paramZ = Math.rint(ONETHOUSAND * (paramZ * REFZ)) / ONETHOUSAND;
        return new double[] {paramX, paramY, paramZ};
    }

    private static double[] changeParam(final double[] doubles) {
        for (int i = 0; i < doubles.length; i++) {
            if (doubles[i] < 0) {
                doubles[i] = 0;
            } else if (doubles[i] > TWOHUNDREDFF) {
                doubles[i] = TWOHUNDREDFF;
            }
        }
        return doubles;
    }
}
