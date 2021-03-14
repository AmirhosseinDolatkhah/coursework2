package model;

import java.util.ArrayList;

public class Column {
    private final String name;
    private final ArrayList<String> rows;

    public Column(String name) {
        this.name = name;
        rows = new ArrayList<>();
    }

    public int getSize() {
        return rows.size();
    }

    public String getRowValue(int row) {
        return rows.get(row);
    }

    public void setRowValue(int row, String newValue) {
        rows.set(row, newValue);
    }

    public void addRowValue(String newRowValue) {
        rows.add(newRowValue);
    }

    public String getName() {
        return name;
    }
}
