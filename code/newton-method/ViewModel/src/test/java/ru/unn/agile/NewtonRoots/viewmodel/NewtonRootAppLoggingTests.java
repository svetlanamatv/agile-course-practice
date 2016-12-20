package ru.unn.agile.NewtonRoots.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class NewtonRootAppLoggingTests {
    @Test
    public void canCreateViewModelWithLogger() {
        NewtonRootAppViewModel viewModel = new NewtonRootAppViewModel(new FakeLogger());

        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void canSetLoggerOnAlreadyCreatedViewModel() {
        NewtonRootAppViewModel viewModel = new NewtonRootAppViewModel();

        viewModel.setLogger(new FakeLogger());

        assertNotNull(viewModel.getLogger());
    }
}
