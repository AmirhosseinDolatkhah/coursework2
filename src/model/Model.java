package model;

import java.io.FileNotFoundException;

public final class Model {
    private final String pathOfFile;
    private final DataFrame dataFrame;

    public Model(String pathOfFile) throws FileNotFoundException {
        this.pathOfFile = pathOfFile;
        dataFrame = DataLoader.loadCSV(pathOfFile);
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
