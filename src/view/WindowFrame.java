package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class WindowFrame extends MainFrame {
    private JTable dataTable;

    public WindowFrame() {
        init();
    }

    private void init() {
        handleTable();
    }

    private void handleTable() {
        dataTable = new JTable(new DefaultTableModel(getRowModel(), getColumnModel()));

        add(new JScrollPane(dataTable));
    }

    protected abstract String[] getColumnModel();
    protected abstract String[][] getRowModel();

}
