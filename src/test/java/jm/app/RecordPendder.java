package jm.app;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.AutoEncoderModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecordPendder {

    private void pendRecords() throws Exception {
        String rootPath = this.getClass().getResource("").getPath() + "../../";
        String fileName = "cc_general.csv";
        Scanner scanner = new Scanner(new File(rootPath, fileName));
        // skip the first line (the header)
        StringBuilder sb = new StringBuilder();
        sb.append(scanner.nextLine());
        int number = 1;
        while (scanner.hasNextLine()) {
            System.out.println("Row #" + number + " Read");
            sb.append("\n");
            String row = scanner.nextLine();
            String[] field = row.split(",");
            // skip the first column
            for (int i = 0; i < field.length; i++) {
                if (null == field[i] || "".equals(field[i])) {
                    field[i] = "0.0";
                }
                if (i != field.length - 1) {
                    sb.append(field[i] + ",");
                } else {
                    sb.append(field[i]);
                }
            }
            number++;
        }
        PrintWriter printWriter = new PrintWriter(new File(rootPath, "test.csv"));
        printWriter.write(sb.toString());
        printWriter.close();
    }

    public static void main(String[] args) throws Exception {
        RecordPendder recordPendder = new RecordPendder();
        recordPendder.pendRecords();
    }

}
