package ru.unn.agile.Statistics.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.*;

import ru.unn.agile.Statistics.viewmodel.ViewModel;


abstract class PossibilityTable implements ChangeListener {
    private static final int
            COLUMN_WIDTH = 50;

    private static final char
            RANDOM_VARIABLE_LABEL = 'x',
            POSSIBILITY_LABEL = 'P';


    private final JTable table;
    private final JTableHeader header;


    JTable getTable() {
        return table;
    }

    PossibilityTable(final ViewModel viewModel) {
        table = new JTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
            @Override
            public void editingStopped(final ChangeEvent e) {
                super.editingStopped(e);
                PossibilityTable.this.stateChanged(e);
            }
        };


        header = table.getTableHeader();
        table.setTableHeader(null);
        table.setModel(new TableBinding(viewModel));

        update();
    }


    final void update() {
        table.getColumnModel().getColumn(0).setCellRenderer(new RowNumberRenderer(header));
        table.getColumnModel().getColumn(0).setMaxWidth(COLUMN_WIDTH);

        for (int i = 0; i < table.getColumnCount(); ++i) {
            table.getColumnModel().getColumn(i).setMinWidth(COLUMN_WIDTH);
        }

        table.setPreferredSize(
                new Dimension(
                        COLUMN_WIDTH * table.getColumnCount(),
                        table.getPreferredSize().height
                )
        );
    }



    private static final class TableBinding extends AbstractTableModel {
        private final ViewModel viewModel;

        TableBinding(final ViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public int getRowCount() {
            return 2;
        }

        @Override
        public int getColumnCount() {
            return 1 + viewModel.getTableSize();
        }

        @Override
        public Object getValueAt(final int i, final int j) {
            if (j == 0) {
                return i == 0 ? RANDOM_VARIABLE_LABEL : POSSIBILITY_LABEL;
            }
            return (i == 0 ? viewModel.getValue(j - 1) : viewModel.getPossibility(j - 1));
        }

        @Override
        public boolean isCellEditable(final int i, final int j) {
            return j != 0;
        }

        @Override
        public void setValueAt(final Object aValue, final int i, final int j) {
            double value;
            try {
                value = Double.valueOf((String) aValue);
            } catch (NumberFormatException e) {
                return;
            }
            if (i == 0) {
                viewModel.setValue(j - 1, value);
            } else {
                viewModel.setPossibility(j - 1, value);
            };
        }
    }


    private static final class RowNumberRenderer extends DefaultTableCellRenderer {
        RowNumberRenderer(final JTableHeader example) {
            setForeground(example.getForeground());
            setBackground(example.getBackground());
            setFont(example.getFont());
            setHorizontalAlignment(JLabel.CENTER);
            setFont(getFont().deriveFont(Font.BOLD));
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        }

        @Override
        public Component getTableCellRendererComponent(
                final JTable table, final Object value, final boolean isSelected,
                final boolean hasFocus, final int row, final int column
        ) {
            setText(value.toString());
            return this;
        }
    }
}
