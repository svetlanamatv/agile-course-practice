package ru.unn.agile.triangle.viewmodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.utils.LoggerWrapper;

public abstract class ViewModelLoggingTestsBase {
    private ViewModel viewModel;
    private LoggerWrapper logger;

    protected abstract Logger constructLogger() throws Exception;

    protected final ViewModel getViewModel() {
        return viewModel;
    }

    @Before
    public void setUp() throws Exception {
        logger = new LoggerWrapper(constructLogger());
        viewModel = new ViewModel(logger);
    }

    @Test
    public void loggerContainsMessageAboutViewModelCreatingHasStarted() throws Exception {
        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.VIEW_MODEL_OBJECT_CREATING_STARTED));
    }

    @Test
    public void loggerContainsMessageAboutViewModelCreatingHasFinished() throws Exception {
        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.VIEW_MODEL_OBJECT_CREATING_FINISHED));
    }

    @Test
    public void loggerContainsMessageAboutCalculationsHaveStarted() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.CALCULATING_STARTED));
    }

    @Test
    public void loggerContainsMessageAboutCalculationsHaveFinished() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.CALCULATING_FINISHED));
    }

    @Test
    public void loggerContainsMessageAboutTriangleParametersHaveBeenReceived() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.POINTS_COORDINATES_RECEIVED));
    }

    @Test
    public void loggerContainsMessageAboutAreaHasBeenCalculated() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.AREA_CALCULATED));
    }

    @Test
    public void loggerContainsMessageAboutPerimeterHasBeenCalculated() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.PERIMETER_CALCULATED));
    }

    @Test
    public void loggerContainsMessageAboutIncircleRadiusHasBeenCalculated() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.INCIRCLE_RADIUS_CALCULATED));
    }

    @Test
    public void loggerContainsMessageAboutIncircleCenterHasBeenCalculated() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.INCIRCLE_CENTER_CALCULATED));
    }

    @Test
    public void loggerContainsMessageAboutCircumcircleRadiusHasBeenCalculated() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.CIRCUMCIRCLE_RADIUS_CALCULATED));
    }

    @Test
    public void loggerContainsMessageAboutCircumcircleCenterHasBeenCalculated() throws Exception {
        setViewModelValidParameters();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.CIRCUMCIRCLE_CENTER_CALCULATED));
    }

    @Test
    public void loggerContainsMessageAboutCircumcircleCanNotBeCalculated() throws Exception {
        setViewModelParametersWhenCircumcircleUndefined();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.UNABLE_CALCULATE_CIRCUMCIRCLE_VALUES));
    }

    @Test
    public void loggerContainsMessageAboutStatusChecking() throws Exception {
        viewModel.axProperty().set("###");

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.CHECKING_INPUT_STATUS));
    }

    @Test
    public void loggerContainsMessageAboutStatusChanging() throws Exception {
        viewModel.axProperty().set("###");

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.INPUT_STATUS_SET));
    }

    protected void setViewModelValidParameters() {
        viewModel.axProperty().set("0.0");
        viewModel.bxProperty().set("1.0");
        viewModel.ayProperty().set("0.0");
        viewModel.cxProperty().set("0.0");
        viewModel.byProperty().set("0.0");
        viewModel.cyProperty().set("1.0");
    }

    protected void setViewModelParametersWhenCircumcircleUndefined() {
        viewModel.cyProperty().set("0.0");
        viewModel.cxProperty().set("0.0");
        viewModel.byProperty().set("0.0");
        viewModel.bxProperty().set("2.0");
        viewModel.ayProperty().set("0.0");
        viewModel.axProperty().set("2.0");
    }

}
