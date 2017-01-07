package ru.unn.agile.triangle.view.controls;

import javafx.scene.control.ListCell;
import ru.unn.agile.triangle.viewmodel.LoggerRecordViewModel;

public class LoggerRecordListCell extends ListCell<LoggerRecordViewModel> {
    @Override
    protected void updateItem(final LoggerRecordViewModel item, final boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else {
            setText(item.getMessage());
        }
    }
}
