package ru.unn.agile.ColorConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.unn.agile.ColorConverter.model.ColorSpaces.*;
import static ru.unn.agile.ColorConverter.viewmodel.ViewModel.Status.*;

public class ViewModelTest {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
        viewModel.firstValueProperty().set("23");
        viewModel.secondValueProperty().set("24");
        viewModel.thirdValueProperty().set("25");
        viewModel.getFromColorSpace().set(RGB);
        viewModel.getToColorSpace().set(HSV);
    }
    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        ViewModel testViewModel = new ViewModel();
        assertEquals("", testViewModel.getFirstValueProperty().getValue());
        assertEquals("", testViewModel.getSecondValueProperty().getValue());
        assertEquals("", testViewModel.getThirdValueProperty().getValue());
        assertEquals(LAB, testViewModel.getFromColorSpace().get());
        assertEquals(HSV, testViewModel.getToColorSpace().get());
        assertEquals("", testViewModel.getFirstValueResult());
        assertEquals("", testViewModel.getSecondValueResult());
        assertEquals("", testViewModel.getThirdValueResult());
        assertEquals(WAITING.toString(), testViewModel.statusMessageProperty().get());
    }
    @Test
    public void canMessageWaitingFormatWithEmptyColorSpaceValues() {
        ViewModel testViewModel = new ViewModel();
        testViewModel.convert();
        assertEquals(WAITING.toString(), testViewModel.statusMessageProperty().get());
    }
    @Test
    public void canMessageReadyFormatWithFilledValues() {
        assertEquals(READY.toString(), viewModel.statusMessageProperty().get());
    }
    @Test
    public void canMessageSuccessFormatAfterConvert() {
        viewModel.convert();
        assertEquals(SUCCESS.toString(), viewModel.statusMessageProperty().get());
    }
    @Test
    public void canMessageBadFormat() {
        viewModel.firstValueProperty().set("one");
        assertEquals(BAD_FORMAT.toString(), viewModel.statusMessageProperty().get());
    }
    @Test
    public void canMessageBadFormatFormatForNegativeValue() {
        viewModel.firstValueProperty().set("-78");
        assertEquals(BAD_FORMAT.toString(), viewModel.statusMessageProperty().get());
    }
    @Test
    public void canSetRGBFromColorSpace() {
        viewModel.getFromColorSpace().set(RGB);
        assertEquals(RGB, viewModel.getFromColorSpace().get());
    }
    @Test
    public void canSetRGBToColorSpace() {
        viewModel.getToColorSpace().set(RGB);
        assertEquals(RGB, viewModel.getToColorSpace().get());
    }
    @Test
    public void canSetHSVFromColorSpace() {
        viewModel.getFromColorSpace().set(HSV);
        assertEquals(HSV, viewModel.getFromColorSpace().get());
    }
    @Test
    public void canSetHSVToColorSpace() {
        viewModel.getToColorSpace().set(HSV);
        assertEquals(HSV, viewModel.getToColorSpace().get());
    }
    @Test
    public void canSetLABFromColorSpace() {
        viewModel.getFromColorSpace().set(LAB);
        assertEquals(LAB, viewModel.getFromColorSpace().get());
    }
    @Test
    public void canSetLABToColorSpace() {
        viewModel.getToColorSpace().set(LAB);
        assertEquals(LAB, viewModel.getToColorSpace().get());
    }
    @Test
    public void checkDisabledButtonForEmptyValues() {
        assertTrue(new ViewModel().convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonForCorrectValues() {
        assertFalse(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonForIncorrectValue() {
        viewModel.firstValueProperty().set("Two");
        assertTrue(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonForNegativeValue() {
        viewModel.secondValueProperty().set("-98");
        assertTrue(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonAfterConvert() {
        viewModel.convert();
        assertFalse(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void convertFromLABToHSV() {
        viewModel.firstValueProperty().set("23");
        viewModel.secondValueProperty().set("24");
        viewModel.thirdValueProperty().set("25");
        viewModel.getFromColorSpace().set(LAB);
        viewModel.getToColorSpace().set(HSV);
        viewModel.convert();
        assertEquals("16.0", viewModel.firstValueResultProperty().get());
        assertEquals("80.851", viewModel.secondValueResultProperty().get());
        assertEquals("36.863", viewModel.thirdValueResultProperty().get());
    }
    @Test
    public void checkTheFirstColorSpaceInBox() {
        assertEquals(RGB, viewModel.getColorSpaces().get(0));
    }
    @Test
    public void checkTheSecondColorSpaceInBox() {
        assertEquals(LAB, viewModel.getColorSpaces().get(1));
    }
    @Test
    public void checkTheThirdColorSpaceInBox() {
        assertEquals(HSV, viewModel.getColorSpaces().get(2));
    }
    @Test
    public void checkStringResultingValue() {
        viewModel.convert();
        assertEquals("210.0", viewModel.getFirstValueResult());
        assertEquals("8.0", viewModel.getSecondValueResult());
        assertEquals("9.804", viewModel.getThirdValueResult());
    }
    @Test
    public void checkStringMessage() {
        viewModel.convert();
        assertEquals(SUCCESS.toString(), viewModel.getStatusMessage());
    }
    @Test
    public void isButtonDisabledWithEmptyData() {
        assertTrue(new ViewModel().isConvertingDisabled());
    }
}
