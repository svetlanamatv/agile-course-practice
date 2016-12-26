package ru.unn.agile.triangle.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.mock.FakeLogger;

import java.util.Locale;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        Logger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.axProperty().get());
        assertEquals("", viewModel.ayProperty().get());
        assertEquals("", viewModel.bxProperty().get());
        assertEquals("", viewModel.byProperty().get());
        assertEquals("", viewModel.cxProperty().get());
        assertEquals("", viewModel.cyProperty().get());

        assertEquals("", viewModel.getArea());
        assertEquals("", viewModel.getPerimeter());
        assertEquals("", viewModel.getCircumcircleRadius());
        assertEquals("", viewModel.getCircumcircleCenterX());
        assertEquals("", viewModel.getCircumcircleCenterX());
        assertEquals("", viewModel.getIncircleRadius());
        assertEquals("", viewModel.getIncircleCenterX());
        assertEquals("", viewModel.getIncircleCenterY());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.getCalculationDisabled());
    }

    @Test
    public void whenInputIsEnteredCalculateButtonIsEnabled() {
        setInputData();

        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void whenInputHasEmptyFieldsCalculateButtonIsDisabled() {
        setInputData();
        viewModel.axProperty().set("");

        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void whenInputIsIncorrectCalculateButtonIsDisabled() {
        setInputData();
        viewModel.axProperty().set("atata");

        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void canCalculateResultsWithCorrectInput() {
        setInputData();
        viewModel.calculate();

        assertEquals("0.5", viewModel.areaProperty().get());
        assertEquals("3.414", viewModel.perimeterProperty().get());
        assertEquals("0.707", viewModel.circumcircleRadiusProperty().get());
        assertEquals("0.5", viewModel.circumcircleCenterXProperty().get());
        assertEquals("0.5", viewModel.getCircumcircleCenterY());
        assertEquals("0.293", viewModel.incircleRadiusProperty().get());
        assertEquals("0.293", viewModel.incircleCenterXProperty().get());
        assertEquals("0.293", viewModel.incircleCenterYProperty().get());
    }

    @Test
    public void canProcessCaseWhenTriangleIsDegenerate() {
        viewModel.axProperty().set("2.0");
        viewModel.ayProperty().set("0.0");
        viewModel.bxProperty().set("2.0");
        viewModel.byProperty().set("0.0");
        viewModel.cxProperty().set("0.0");
        viewModel.cyProperty().set("0.0");

        viewModel.calculate();

        assertEquals("0", viewModel.areaProperty().get());
        assertEquals("4", viewModel.perimeterProperty().get());
        assertEquals("0", viewModel.incircleRadiusProperty().get());
        assertEquals("2", viewModel.incircleCenterXProperty().get());
        assertEquals("0", viewModel.incircleCenterYProperty().get());
        assertEquals("undefined", viewModel.circumcircleRadiusProperty().get());
        assertEquals("undefined", viewModel.circumcircleCenterXProperty().get());
        assertEquals("undefined", viewModel.circumcircleCenterYProperty().get());
    }

    @Test
    public void canUsePointOnlyAsDecimalSeparatorForOutputDespiteLocale() {
        Locale locale = new Locale("de");
        Locale.setDefault(locale);

        setInputData();

        viewModel.calculate();

        assertEquals("0.5", viewModel.areaProperty().get());
    }

    @Test(expected = NullPointerException.class)
    public void creatingFailsIfLoggerIsNull() throws Exception {
        new ViewModel(null);
    }

    @Test
    public void loggerViewModelIsNotNullByDefault() throws Exception {
        assertNotEquals(null, viewModel.getLoggerViewModel());
    }

    private void setInputData() {
        viewModel.axProperty().set("0.0");
        viewModel.ayProperty().set("0.0");
        viewModel.bxProperty().set("1.0");
        viewModel.byProperty().set("0.0");
        viewModel.cxProperty().set("0.0");
        viewModel.cyProperty().set("1.0");
    }
}
