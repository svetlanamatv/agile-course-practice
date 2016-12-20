package ru.unn.agile.RomanArabicConverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void isConvertButtonDisableByDefault() {
        assertEquals(true, viewModel.getConverterBtnDisableProperty().get());
    }

    @Test
    public void whenRomanNumberIsQWERTY() {
        viewModel.setRomanNumberProperty("QWERTY");
        assertEquals(false, viewModel
                .validateRomanNumber(viewModel.getRomanNumberProperty().get()));
    }

    @Test
    public void whenRomanNumberIsIII() {
        viewModel.setRomanNumberProperty("III");
        assertEquals(true, viewModel
                .validateRomanNumber(viewModel.getRomanNumberProperty().get()));
    }

    @Test
    public void whenRomanNumberIs123() {
        viewModel.setRomanNumberProperty("123");
        assertEquals(false, viewModel
                .validateRomanNumber(viewModel.getRomanNumberProperty().get()));
    }

    @Test
    public void whenRomanNumberIsIIIIIII() {
        viewModel.setRomanNumberProperty("IIIIIII");
        assertEquals(false, viewModel
                .validateRomanNumber(viewModel.getRomanNumberProperty().get()));
    }

    @Test
    public void whenRomanNumberIsIIIQWERTY() {
        viewModel.setRomanNumberProperty("IIIQWERTY");
        assertEquals(false, viewModel
                .validateRomanNumber(viewModel.getRomanNumberProperty().get()));
    }

    @Test
    public void whenRomanNumberIsValidIsConvertBtnDisable() {
        viewModel.setRomanNumberProperty("III");
        assertEquals(false, viewModel.getConverterBtnDisableProperty().get());
    }

    @Test
    public void whenRomanNumberIsNotValidIsConvertBtnDisable() {
        viewModel.setRomanNumberProperty("QWERTY");
        assertEquals(true, viewModel.getConverterBtnDisableProperty().get());
    }

    @Test
    public void whenArabicNumberIsQWERTY() {
        viewModel.setArabicNumberProperty("QWERTY");
        assertEquals(false, viewModel
                .validateArabicNumber(viewModel.getArabicNumberProperty().get()));
    }

    @Test
    public void whenArabicNumberIs123() {
        viewModel.setArabicNumberProperty("123");
        assertEquals(true, viewModel
                .validateArabicNumber(viewModel.getArabicNumberProperty().get()));
    }

    @Test
    public void whenArabicNumberIsIII() {
        viewModel.setArabicNumberProperty("III");
        assertEquals(false, viewModel
                .validateArabicNumber(viewModel.getArabicNumberProperty().get()));
    }

    @Test
    public void whenArabicNumberIsMinus5() {
        viewModel.setArabicNumberProperty("-5");
        assertEquals(false, viewModel
                .validateArabicNumber(viewModel.getArabicNumberProperty().get()));
    }

    @Test
    public void whenArabicNumberIsValidIsConvertBtnDisable() {
        viewModel.setArabicNumberProperty("123");
        assertEquals(false, viewModel.getConverterBtnDisableProperty().get());
    }

    @Test
    public void whenChooseRBArabicToRoman() {
        viewModel.setRBArabToRomChooseProperty(true);
        assertEquals(true, viewModel.getRBArabToRomChooseProperty().get());
    }

    @Test
    public void whenNotChooseRBArabicToRoman() {
        viewModel.setRBArabToRomChooseProperty(false);
        assertEquals(false, viewModel.getRBArabToRomChooseProperty().get());
    }

    @Test
    public void whenChooseRBRomanToArabic() {
        viewModel.setRBRomToArabChooseProperty(true);
        assertEquals(true, viewModel.getRBRomToArabChooseProperty().get());
    }

    @Test
    public void whenNotChooseRBRomanToArabic() {
        viewModel.setRBRomToArabChooseProperty(false);
        assertEquals(false, viewModel.getRBRomToArabChooseProperty().get());
    }

    @Test
    public void whenChooseRBArabicToRomanTFRomDisable() {
        viewModel.setRBArabToRomChooseProperty(true);
        assertEquals(true, viewModel.getTFRomDisableProperty().get());
    }

    @Test
    public void whenChooseRBRomanToArabicTFRomNotDisable() {
        viewModel.setRBRomToArabChooseProperty(true);
        assertEquals(false, viewModel.getTFRomDisableProperty().get());
    }

    @Test
    public void whenChooseRBArabicToRomanTFArabNotDisable() {
        viewModel.setRBArabToRomChooseProperty(true);
        assertEquals(false, viewModel.getTFArabDisableProperty().get());
    }

    @Test
    public void whenChooseRBRomanToArabicTFArabDisable() {
        viewModel.setRBRomToArabChooseProperty(true);
        assertEquals(true, viewModel.getTFArabDisableProperty().get());
    }

    @Test
    public void whenConvertArabicToRomanNumberArabicIs122StatusIsREADY() {
        viewModel.setRBArabToRomChooseProperty(true);
        viewModel.setArabicNumberProperty("122");
        assertEquals(Status.READY.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertArabicToRomanNumberArabicIsQWERTYStatusIsBADFORMAT() {
        viewModel.setRBArabToRomChooseProperty(true);
        viewModel.setArabicNumberProperty("QWERTY");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertArabicToRomanNumberArabicIsEmptyStatusIsWAITING() {
        viewModel.setRBArabToRomChooseProperty(true);
        viewModel.setArabicNumberProperty("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertRomanToArabicNumberRomanIsIIIStatusIsREADY() {
        viewModel.setRBRomToArabChooseProperty(true);
        viewModel.setRomanNumberProperty("III");
        assertEquals(Status.READY.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertRomanToArabicNumberRomanIsQWERTYStatusIsBADFORMAT() {
        viewModel.setRBRomToArabChooseProperty(true);
        viewModel.setRomanNumberProperty("QWERTY");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertRomanToArabicNumberRomanIsEmptyStatusIsWAITING() {
        viewModel.setRBRomToArabChooseProperty(true);
        viewModel.setRomanNumberProperty("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertArabicNumber5ToRomanNumberResultIsV() {
        viewModel.setRBArabToRomChooseProperty(true);
        viewModel.setArabicNumberProperty("5");
        viewModel.convertNumber();
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatusProperty().get());
        assertEquals("V", viewModel.getRomanNumberProperty().get());
    }

    @Test
    public void whenConvertArabicNumberQWERTYToRomanNumberResultStatusIsBADFORMAT() {
        viewModel.setRBArabToRomChooseProperty(true);
        viewModel.setArabicNumberProperty("QWERTY");
        viewModel.convertNumber();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatusProperty().get());
    }

    @Test
    public void whenConvertRomanNumberVIToArabicNumberResultIs6() {
        viewModel.setRBRomToArabChooseProperty(true);
        viewModel.setRomanNumberProperty("VI");
        viewModel.convertNumber();
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatusProperty().get());
        assertEquals("6", viewModel.getArabicNumberProperty().get());
    }

    @Test
    public void whenConvertRomanNumberQWERTYToArabicNumberResultStatusIsBADFORMAT() {
        viewModel.setRBRomToArabChooseProperty(true);
        viewModel.setRomanNumberProperty("QWERTY");
        viewModel.convertNumber();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatusProperty().get());
    }
}
