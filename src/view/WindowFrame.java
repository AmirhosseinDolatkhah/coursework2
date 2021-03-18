package view;

import view.state.NoTableState;
import view.state.StatisticsState;
import view.state.TableState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class WindowFrame extends MainFrame {
    private TableState tableState;

    public WindowFrame() {
        init();
    }

    private void init() {
        if (isVisible())
            return;
        handleTablesTab();
        handleNoTablePanel();
        setState("noTable");
    }

    private void handleTablesTab() {
        // table = key
        tableState = new TableState() {
            @Override
            protected void noTabAction() {
                setState("noTable");
            }

            @Override
            protected void addTableAction() {
                var path = JOptionPane.showInputDialog("Enter name of CSV file");
                addModel(path);
                addTable(path);
                repaint();
                revalidate();
            }

            @Override
            protected String[] getColumnModel(String name) {
                return WindowFrame.this.getColumnModel(name);
            }

            @Override
            protected void viewChangeAction(String name, boolean[] visibility) {
                var tabbedPane = tableState.getTabbedPane();
                tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
                setColVisibility(name, visibility);
                addTable(name);
                tabbedPane.repaint();
                tabbedPane.revalidate();
            }

            @Override
            protected boolean[] getColVisibility(String name) {
                return WindowFrame.this.getColVisibility(name);
            }

            @Override
            protected String[] getColumns(String name) {
                return WindowFrame.this.getColumns(name);
            }

            @Override
            protected void closeTabAction(String name) {
                removeModel(name);
            }

            @Override
            protected void statisticAction(String name) {
                setContentPane(new StatisticsState(name) {
                    @Override
                    protected String[] getColumns(String name) {
                        return WindowFrame.this.getColumns(name);
                    }

                    @Override
                    protected Map<String, Integer> getStatisticsOf(String tableName, String col) {
                        return WindowFrame.this.getStatisticsOf(tableName, col);
                    }

                    @Override
                    protected void backAction() {
                        setState("table");
                    }
                });

                repaint();
                revalidate();
            }
        };
        addState("table", tableState);
    }

    private void handleNoTablePanel() {
        // noTable = key
        JPanel noTableState = new NoTableState() {
            @Override
            protected void addTableAction() {
                var path = JOptionPane.showInputDialog("Enter name of CSV file");
                addModel(path);
                if (getRowModel(path).length == 0)
                    return;
                addTable(path);
                setState("table");
                repaint();
                revalidate();
            }
        };
        addState("noTable", noTableState);
    }

    private void addTable(String name) {
        if (getRowModel(name).length == 0)
            return;
        var majorCM = getColumnModel(name);
        var majorRM = getRowModel(name);
        var cm = new String[majorCM.length + 1];
        cm[0] = "No.";
        System.arraycopy(majorCM, 0, cm, 1, majorCM.length);
        var rm = new String[majorRM.length][majorRM[0].length + 1];
        for (int i = 0; i < majorRM.length; i++) {
            rm[i][0] = String.valueOf(i + 1);
            System.arraycopy(majorRM[i], 0, rm[i], 1, majorCM.length);
        }
        JTable dataTable = new JTable(new DefaultTableModel(rm, cm));
        dataTable.setRowHeight(40);
        var txtField = new JTextField();
        var columns = new JComboBox<String>();
        for (var col : getColumnModel(name))
            columns.addItem(col);
        var wrapper = new JPanel(new BorderLayout());
        wrapper.add(columns, BorderLayout.WEST);
        wrapper.add(txtField, BorderLayout.CENTER);
        var indexResult = new JComboBox<Integer>();
        txtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                indexResult.removeAllItems();
                if (txtField.getText().isEmpty())
                    return;
                var sr = getSearchResultIndexes(name, (String) columns.getSelectedItem(), txtField.getText());
                for (var i : sr)
                    indexResult.addItem(i);
            }
        });
        var innerWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        innerWrapper.add(new JLabel("Indexes: ", JLabel.RIGHT));
        innerWrapper.add(indexResult);
        wrapper.add(innerWrapper, BorderLayout.EAST);
        var outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        outerWrapper.add(wrapper, BorderLayout.NORTH);
        tableState.getTabbedPane().add(name, outerWrapper);
    }

    protected abstract String[] getColumnModel(String name);
    protected abstract String[][] getRowModel(String name);
    protected abstract void addModel(String fileName);
    protected abstract int getModelCount();
    protected abstract void removeModel(String name);
    protected abstract boolean[] getColVisibility(String name);
    protected abstract void setColVisibility(String name, boolean[] visibility);
    protected abstract String[] getColumns(String name);
    protected abstract List<Integer> getSearchResultIndexes(String tableName, String column, String key);
    protected abstract Map<String, Integer> getStatisticsOf(String tableName, String col);
}
