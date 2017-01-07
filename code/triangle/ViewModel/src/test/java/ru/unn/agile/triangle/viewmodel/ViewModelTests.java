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

        assertFalse(viewModel.getCalculationDisabled());
    }

    @Test
    public void whenInputHasEmptyFieldsCalculateButtonIsDisabled() {
        setInputData();
        viewModel.axProperty().set("");

        assertTrue(viewModel.getCalculationDisabled());
    }

    @Test
    public void whenInputIsIncorrectCalculateButtonIsDisabled() {
        setInputData();
        viewModel.axProperty().set("atata");

        assertTrue(viewModel.getCalculationDisabled());
    }

    @Test
    public void canCalculateResultsWithCorrectInput() {
        setInputData();
        viewModel.calculate();

        assertEquals("0.5", viewModel.getArea());
        assertEquals("3.414", viewModel.getPerimeter());
        assertEquals("0.707", viewModel.getCircumcircleRadius());
        assertEquals("0.5", viewModel.getCircumcircleCenterX());
        assertEquals("0.5", viewModel.getCircumcircleCenterY());
        assertEquals("0.293", viewModel.getIncircleRadius());
        assertEquals("0.293", viewModel.getIncircleCenterX());
        assertEquals("0.293", viewModel.getIncircleCenterY());
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

        assertEquals("0", viewModel.getArea());
        assertEquals("4", viewModel.getPerimeter());
        assertEquals("0", viewModel.getIncircleRadius());
        assertEquals("2", viewModel.getIncircleCenterX());
        assertEquals("0", viewModel.getIncircleCenterY());
        assertEquals("undefined", viewModel.getCircumcircleRadius());
        assertEquals("undefined", viewModel.getCircumcircleCenterX());
        assertEquals("undefined", viewModel.getCircumcircleCenterY());
    }

    @Test
    public void canUsePointOnlyAsDecimalSeparatorForOutputDespiteLocale() {
        Locale locale = new Locale("de");
        Locale.setDefault(locale);

        setInputData();

        viewModel.calculate();

        assertEquals("0.5", viewModel.getArea());
    }

    @Test(expected = NullPointerException.class)
    public void creatingFailsIfLoggerIsNull() throws Exception {
        new ViewModel(null);
    }

    @Test
    public void loggerViewModelIsNotNullByDefault() throws Exception {
        assertNotEquals(null, viewModel.getLoggerViewModel());
    }

    @Test
    public void calculationDisabledPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.calculationDisabledProperty());
    }

    @Test
    public void areaPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.areaProperty());
    }

    @Test
    public void perimeterPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.perimeterProperty());
    }

    @Test
    public void circumcircleRadiusPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.circumcircleRadiusProperty());
    }

    @Test
    public void circumcircleCenterXPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.circumcircleCenterXProperty());
    }

    @Test
    public void circumcircleCenterYPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.circumcircleCenterYProperty());
    }

    @Test
    public void incircleRadiusPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.incircleRadiusProperty());
    }

    @Test
    public void incircleCenterXPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.incircleCenterXProperty());
    }

    @Test
    public void incircleCenterYPropertyIsNotNull() throws Exception {
        assertNotNull(viewModel.incircleCenterYProperty());
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
