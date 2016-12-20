package ru.unn.agile.personalfinance.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import ru.unn.agile.PersonalFinance.ViewModel.*;
import ru.unn.agile.personalfinance.view.controllers.DataContextController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

public final class WindowsManager {
    private static final int HOME_SCREEN_WINDOW_WIDTH = 700;
    private static final int HOME_SCREEN_WINDOW_HEIGHT = 400;
    private static final int EDIT_ACCOUNT_WINDOW_WIDTH = 400;
    private static final int EDIT_ACCOUNT_WINDOW_HEIGHT = 200;
    private static final int EDIT_EXTERNAL_TRANSACTION_WINDOW_WIDTH = 500;
    private static final int EDIT_EXTERNAL_TRANSACTION_WINDOW_HEIGHT = 400;
    private static final int EDIT_TRANSFER_WINDOW_WIDTH = 500;
    private static final int EDIT_TRANSFER_WINDOW_HEIGHT = 250;
    private static final int EDIT_CATEGORIES_WINDOW_WIDTH = 300;
    private static final int EDIT_CATEGORIES_WINDOW_HEIGHT = 400;

    private static final Object LOCK = new Object();
    private static WindowsManager windowsManager;

    private final Stack<Stage> stagesHistory = new Stack<>();
    private final HashMap<URL, Pair<Scene, DataContextController<?>>> scenesCache = new HashMap<>();

    private WindowsManager() {
    }

    public static WindowsManager getInstance() {
        // Using synchronized block instead of
        // synchronized method to prevent PMD violation
        synchronized (LOCK) {
            if (windowsManager == null) {
                windowsManager = new WindowsManager();
            }
        }
        return windowsManager;
    }

    public void showHomeScreenView(final Stage rootStage, final LedgerViewModel ledger) {
        getView("home-screen.fxml")
                .title("My Wallet")
                .width(HOME_SCREEN_WINDOW_WIDTH)
                .height(HOME_SCREEN_WINDOW_HEIGHT)
                .userStage(rootStage)
                .data(ledger)
                .<LedgerViewModel>show();
    }

    public void showEditAccountView(final AccountViewModel account) {
        if (account == null) {
            return;
        }

        getView("edit-account.fxml")
                .title("Edit account")
                .width(EDIT_ACCOUNT_WINDOW_WIDTH)
                .height(EDIT_ACCOUNT_WINDOW_HEIGHT)
                .data(account)
                .<AccountViewModel>show();
    }

    public void showEditTransactionView(final TransactionViewModel transaction) {
        if (transaction == null) {
            return;
        }

        if (transaction instanceof ExternalTransactionViewModel) {
            showEditExternalTransactionView((ExternalTransactionViewModel) transaction);
        } else if (transaction instanceof TransferViewModel) {
            showEditTransferView((TransferViewModel) transaction);
        } else {
            throw new IllegalArgumentException("We don't know how to present transaction of type "
                    + transaction.getClass().getName());
        }
    }

    public void showEditCategoriesView() {
        getView("edit-categories.fxml")
                .title("Edit categories")
                .width(EDIT_CATEGORIES_WINDOW_WIDTH)
                .height(EDIT_CATEGORIES_WINDOW_HEIGHT)
                .show();
    }

    public void goBack() {
        Stage currentStage = stagesHistory.peek();
        currentStage.close();
    }

    private void showEditExternalTransactionView(final ExternalTransactionViewModel transaction) {
        getView("edit-external-transaction.fxml")
                .title("Edit transaction")
                .width(EDIT_EXTERNAL_TRANSACTION_WINDOW_WIDTH)
                .height(EDIT_EXTERNAL_TRANSACTION_WINDOW_HEIGHT)
                .data(transaction)
                .<ExternalTransactionViewModel>show();
    }

    private void showEditTransferView(final TransferViewModel transfer) {
        getView("edit-transfer.fxml")
                .title("Edit transfer")
                .width(EDIT_TRANSFER_WINDOW_WIDTH)
                .height(EDIT_TRANSFER_WINDOW_HEIGHT)
                .data(transfer)
                .<TransferViewModel>show();
    }

    private ViewBuilder getView(final String fxmlSource) {
        return new ViewBuilder(fxmlSource);
    }

    class ViewBuilder {
        private final URL rootUrl;
        private String title;
        private int width;
        private int height;
        private Stage userStage;
        private Object dataObject;

        ViewBuilder(final String fxmlSource) {
            rootUrl = getClass().getResource(fxmlSource);
        }

        ViewBuilder title(final String title) {
            this.title = title;
            return this;
        }

        ViewBuilder width(final int width) {
            this.width = width;
            return this;
        }

        ViewBuilder height(final int height) {
            this.height = height;
            return this;
        }

        ViewBuilder userStage(final Stage userStage) {
            this.userStage = userStage;
            return this;
        }

        ViewBuilder data(final Object dataObject) {
            this.dataObject = dataObject;
            return this;
        }

        <T> void show() {
            if (scenesCache.containsKey(rootUrl)) {
                Pair<Scene, DataContextController<?>> sceneAndController = scenesCache.get(rootUrl);
                this.<T>showScene(sceneAndController.getKey(), sceneAndController.getValue());
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(rootUrl);
                    loader.load();
                    Parent root = loader.getRoot();
                    DataContextController<?> controller = loader.getController();

                    Scene scene = new Scene(root);
                    scenesCache.put(rootUrl, new Pair<>(scene, controller));
                    this.<T>showScene(scene, controller);
                } catch (IOException ex) {
                    handleWindowCreatingException(ex);
                }
            }
        }

        private <T> void showScene(final Scene scene, final DataContextController<?> controller) {
            Stage stage = getStage();

            if (!stagesHistory.isEmpty()) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(stagesHistory.peek());
            }
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setWidth(width);
            stage.setHeight(height);

            stagesHistory.push(stage);

            stage.setOnHidden(event -> {
                stagesHistory.pop();
                controller.setDataContext(null);
            });
            stage.show();

            this.<T>setupDataContext(controller);
        }

        private <T> void setupDataContext(final DataContextController<?> controller) {
            try {
                // Typecast is save here, we assume each view defined within
                // FXML should be associated with DataContextController

                @SuppressWarnings("unchecked")
                DataContextController<T> typedController = (DataContextController<T>) controller;

                @SuppressWarnings("unchecked")
                T typedDataObject = (T) dataObject;

                typedController.setDataContext(typedDataObject);
            } catch (ClassCastException ex) {
                throw new RuntimeException("Can't associate given data object "
                        + "with loaded data context controller", ex);
            }
        }

        private Stage getStage() {
            return (userStage == null) ? new Stage() : userStage;
        }

        private void handleWindowCreatingException(final Exception ex) {
            ex.printStackTrace();
        }

    }
}
