package model;

import java.io.FileNotFoundException;

public final class Model {
    private final String pathOfFile;
    private final DataFrame dataFrame;

    public Model(String pathOfFile) {
        this.pathOfFile = pathOfFile;
        DataFrame dataFrame = new DataFrame();
        try {
            dataFrame = DataLoader.loadCSV(pathOfFile);
        } catch (FileNotFoundException ignore) {}
        this.dataFrame = dataFrame;
    }

    public Model() {
        this(null);
    }

    public String[] getColumnModel() {
        return dataFrame.getColumnNames().toArray(new String[0]);
    }

    public String[][] getRowModel() {
        return dataFrame.getAllCells();
    }

    public String getPathOfFile() {
        return pathOfFile;
    }
}
