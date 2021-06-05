package client;

import javax.swing.table.DefaultTableModel;

public class UneditableTableModel extends DefaultTableModel {

    public UneditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
