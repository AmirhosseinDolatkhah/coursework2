package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public final class Model {
    private String pathOfFile;
    private DataFrame dataFrame;
    private boolean[] columnVisibility;

    public Model(String pathOfFile) {
        this.pathOfFile = pathOfFile;
        init();
    }

    public Model() {
        this(null);
    }

    private void init() {
        DataFrame dataFrame = new DataFrame();
        try {
            dataFrame = DataLoader.loadCSV(pathOfFile);
        } catch (FileNotFoundException ignore) {}
        this.dataFrame = dataFrame;
        columnVisibility = new boolean[dataFrame.getColumnCount()];
        for (int i = 0; i < dataFrame.getColumnCount(); i++)
            columnVisibility[i] = true;
    }

    public String[] getColumns() {
        return dataFrame.getColumnNames().toArray(new String[0]);
    }

    public String[] getColumnModel() {
        var res = new ArrayList<String>();
        for (int i = 0; i < dataFrame.getColumnCount(); i++)
            if (columnVisibility[i])
                res.add(dataFrame.getColumnNames().get(i));
        return res.toArray(new String[0]);
    }

    public String[][] getRowModel() {
        var res = new String[dataFrame.getRowCount()][];
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            var row = new ArrayList<String>();
            for (int j = 0; j < dataFrame.getColumnCount(); j++)
                if (columnVisibility[j])
                    row.add(dataFrame.getValueAt(i, j));
            res[i] = row.toArray(new String[0]);
        }
        return res;
    }

    public boolean[] getColumnVisibility() {
        return columnVisibility;
    }

    public void setColumnVisibility(boolean[] columnVisibility) {
        this.columnVisibility = columnVisibility;
    }

    public List<Integer> search(String column, String key) {
        var col = dataFrame.getColumnByName(column).values();
        key = key.toLowerCase();
        var res = new ArrayList<Integer>();
        for (int i = 0; i < col.length; i++)
            if (col[i].toLowerCase().contains(key))
                res.add(i+1);
        return res;
    }

    public HashMap<String, Integer> getStatisticOf(String columnName) {
        return dataFrame.getStatistical(columnName);
    }

    public String getPathOfFile() {
        return pathOfFile;
    }

    public void setPathOfFile(String pathOfFile) {
        this.pathOfFile = pathOfFile;
        init();
    }
}
