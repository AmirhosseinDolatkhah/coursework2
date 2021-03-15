package controller;

import model.DataFrame;
import model.Model;
import view.WindowFrame;

import javax.swing.*;
import java.io.FileNotFoundException;

public final class Controller {
    private final static WindowFrame windowFrame;
    private final static Model model;

    static {
        model = new Model(".\\res\\patients100000.csv");
        windowFrame = new WindowFrame() {

            @Override
            protected String[] getColumnModel() {
                return model.getColumnModel();
            }

            @Override
            protected String[][] getRowModel() {
                return model.getRowModel();
            }
        };
    }

    public static void run() {
        SwingUtilities.invokeLater(windowFrame);
    }
}
