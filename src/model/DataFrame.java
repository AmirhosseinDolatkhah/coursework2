package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataFrame {
    private final ArrayList<Column> columns;

    public DataFrame() {
        columns = new ArrayList<>();
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public List<String> getColumnNames() {
        return columns.stream().map(Column::getName).collect(Collectors.toList());
    }

    public int getRowCount() {
        return columns.isEmpty() ? 0 : columns.get(0).getSize();
    }

    public int getColumnCount() {
        return columns.size();
    }

    public String getValue(String columnName, int row) {
        return getColumnByName(columnName).getRowValue(row);
    }

    public void putValue(String columnName, int row, String value) {
        getColumnByName(columnName).setRowValue(row, value);
    }

    public void addValue(String columnName, String newValue) {
        getColumnByName(columnName).addRowValue(newValue);
    }

    public String getValueAt(int row, int col) {
        return columns.get(col).getRowValue(row);
    }

    public String[][] getAllCells() {
        var rowCount = getRowCount();
        var colCount = getColumnCount();
        var cells = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < colCount; j++)
                cells[i][j] = getValueAt(i, j);
        return cells;
    }

    private Column getColumnByName(String columnName) {
        for (var column : columns)
            if (column.getName().equals(columnName))
                return column;
        throw new RuntimeException("Invalid Column Name.");
    }
}
