package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class DataLoader {
    public static DataFrame loadCSV(String path) throws FileNotFoundException {
        var dataFrame = new DataFrame();
        var file = new File(path);
        var scanner = new Scanner(file);
        while (scanner.hasNext()) {

        }
    }
}
