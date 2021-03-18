package controller;

import model.DataFrame;
import model.Model;
import view.WindowFrame;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Controller {
    private final static WindowFrame windowFrame;
    private final static Map<String, Model> models;

    static {
        models = new HashMap<>();
        windowFrame = new WindowFrame() {

            @Override
            protected String[] getColumnModel(String name) {
                return models.get(name).getColumnModel();
            }

            @Override
            protected String[][] getRowModel(String name) {
                return models.get(name).getRowModel();
            }

            @Override
            protected void addModel(String fileName) {
                models.put(fileName, new Model(".\\res\\" + fileName + ".csv"));
            }

            @Override
            protected int getModelCount() {
                return models.size();
            }

            @Override
            protected void removeModel(String name) {
                models.remove(name);
            }

            @Override
            protected boolean[] getColVisibility(String name) {
                return models.get(name).getColumnVisibility();
            }

            @Override
            protected void setColVisibility(String name, boolean[] visibility) {
                models.get(name).setColumnVisibility(visibility);
            }

            @Override
            protected String[] getColumns(String name) {
                return models.get(name).getColumns();
            }

            @Override
            protected List<Integer> getSearchResultIndexes(String tableName, String column, String key) {
                return models.get(tableName).search(column, key);
            }

            @Override
            protected Map<String, Integer> getStatisticsOf(String tableName, String col) {
                return models.get(tableName).getStatisticOf(col);
            }
        };
    }

    public static void run() {
        SwingUtilities.invokeLater(windowFrame);
    }
}
