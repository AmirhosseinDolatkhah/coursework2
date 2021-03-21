package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class JSONReader {
    public static DataFrame readFromJSON(String filePath) throws FileNotFoundException {
        var res = new DataFrame();
        var scanner = new Scanner(new File(filePath));
        boolean isColSet = false;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.trim().startsWith("[") || line.trim().startsWith("{") || line.trim().startsWith("]"))
                continue;
            if (line.trim().startsWith("}") || line.trim().startsWith("},")) {
                isColSet = true;
                continue;
            }
            var kv = line.trim().replace("\"", "").split(": ");
            if (!isColSet)
                res.addColumn(new Column(kv[0]));
            try {
                res.addValue(kv[0], kv[1]);
            } catch (Exception ignore) {}
        }
        scanner.close();
        return res;
    }
}
