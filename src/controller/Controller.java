package controller;

import model.DataFrame;
import view.WindowFrame;

import javax.swing.*;

public final class Controller {
    private final static WindowFrame windowFrame;
    private final static DataFrame dataFrame;

    static {
        windowFrame = new WindowFrame();
        dataFrame = new DataFrame();
    }

    public static void run() {
        SwingUtilities.invokeLater(windowFrame);
    }
}
