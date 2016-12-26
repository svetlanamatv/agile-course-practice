package ru.unn.agile.triangle.viewmodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.utils.LoggerWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ViewModelLoggingCalcTestsBase {
    private ViewModel viewModel;
    private LoggerWrapper logger;
    private String expectedLoggerMessage;

    public static Iterable loggerMessages() {
        List<String> messages = Arrays.asList(
                ViewModel.LoggerMessages.CALCULATING_STARTED,
                ViewModel.LoggerMessages.CALCULATING_FINISHED,
                ViewModel.LoggerMessages.POINTS_COORDINATES_RECEIVED,
                ViewModel.LoggerMessages.AREA_CALCULATED,
                ViewModel.LoggerMessages.PERIMETER_CALCULATED,
                ViewModel.LoggerMessages.INCIRCLE_RADIUS_CALCULATED,
                ViewModel.LoggerMessages.INCIRCLE_CENTER_CALCULATED,
                ViewModel.LoggerMessages.CIRCUMCIRCLE_RADIUS_CALCULATED,
                ViewModel.LoggerMessages.CIRCUMCIRCLE_CENTER_CALCULATED
        );
        return messages.stream()
                .map((x) -> new String[] { x })
                .collect(Collectors.toList());
    }

    protected abstract Logger constructLogger() throws Exception;

    public ViewModelLoggingCalcTestsBase(final String expectedMessage) {
        expectedLoggerMessage = expectedMessage;
    }

    @Before
    public void setUp() throws Exception {
        logger = new LoggerWrapper(constructLogger());
        viewModel = new ViewModel(logger);
        setViewModelValidParameters();
        viewModel.calculate();
    }


    @Test
    public void loggerContainsExpectedMessage() throws Exception {
        Assert.assertTrue(logger.hasRecordWithMessage(expectedLoggerMessage));
    }

    protected void setViewModelValidParameters() {
        viewModel.axProperty().set("0.0");
        viewModel.bxProperty().set("1.0");
        viewModel.ayProperty().set("0.0");
        viewModel.cxProperty().set("0.0");
        viewModel.byProperty().set("0.0");
        viewModel.cyProperty().set("1.0");
    }
}
