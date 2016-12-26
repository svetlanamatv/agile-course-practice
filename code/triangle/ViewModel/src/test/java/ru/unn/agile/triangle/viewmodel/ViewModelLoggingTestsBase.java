package ru.unn.agile.triangle.viewmodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.viewmodel.utils.LoggerWrapper;

public abstract class ViewModelLoggingTestsBase {
    private ViewModel viewModel;
    private LoggerWrapper logger;

    protected abstract Logger constructLogger() throws Exception;

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
    public void loggerContainsMessageAboutStatusChecking() throws Exception {
        viewModel.axProperty().set("###");

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.CHECKING_INPUT_STATUS));
    }

    @Test
    public void loggerContainsMessageAboutStatusChanging() throws Exception {
        viewModel.axProperty().set("10");

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.INPUT_STATUS_SET));
    }

    @Test
    public void loggerContainsMessageAboutCircumcircleCanNotBeCalculated() throws Exception {
        setViewModelParametersWhenCircumcircleUndefined();

        viewModel.calculate();

        Assert.assertTrue(logger.hasRecordWithMessage(
                ViewModel.LoggerMessages.UNABLE_CALCULATE_CIRCUMCIRCLE_VALUES));
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
