package ru.unn.agile.Statistics.viewmodel;

import org.junit.Test;

import static ru.unn.agile.Statistics.viewmodel.Operation.EV;
import static ru.unn.agile.Statistics.viewmodel.Operation.VAR;


public class ExceptionInputTests extends Core {
    @Test(expected = IllegalStateException.class)
    public void cannotSetMomentWhenEV() {
        vm().setOperation(EV);
        vm().setMomentOrder("0");
    }

    @Test(expected = IllegalStateException.class)
    public void cannotSetMomentWhenVAR() {
        vm().setOperation(VAR);
        vm().setMomentOrder("0");
    }
}
