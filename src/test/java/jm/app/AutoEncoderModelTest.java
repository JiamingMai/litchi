package jm.app;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.AutoEncoderModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutoEncoderModelTest {

    private Matrix[] readRecords() throws Exception {
        String rootPath = this.getClass().getResource("").getPath() + "../../";
        String fileName = "cc_general.csv";
        Scanner scanner = new Scanner(new File(rootPath, fileName));
        // skip the first line (the header)
        scanner.nextLine();
        List<Matrix> inputList = new ArrayList<>();
        int number = 1;
        while (scanner.hasNextLine()) {
            System.out.println("Row #" + number + " Read");
            String row = scanner.nextLine();
            String[] field = row.split(",");
            Matrix input = new Matrix(field.length - 1, 1);
            // skip the first column
            for (int i = 1; i < field.length; i++) {
                if (null == field[i] || "".equals(field[i])) {
                    field[i] = "0.0";
                }
                double value = Double.parseDouble(field[i]);
                input.setValue(i-1, 0, value);
            }
            inputList.add(input);
            number++;
        }
        // convert the data to a single matrix
        Matrix mat = new Matrix(inputList.size(), inputList.get(0).getRowNum());
        for (int r = 0; r < inputList.size(); r++) {
            for (int c = 0; c < inputList.get(0).getRowNum(); c++) {
                mat.setValue(r, c, inputList.get(r).getValue(c, 0));
            }
        }
        // normalize the matrix
        mat = AlgebraUtil.normalize(mat, 0);
        // convert the normalized matrix to array
        Matrix[] inputs = new Matrix[mat.getRowNum()];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = AlgebraUtil.getRowVector(mat, i);
        }
        return inputs;
    }

    @Test
    public void testAutoEncoderModel() throws Exception {
        Matrix[] inputs = readRecords();
        AutoEncoderModel autoEncoder = new AutoEncoderModel();
        autoEncoder.train(inputs);
        Matrix[] encoded_inputs = new Matrix[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            Matrix encoded_input = autoEncoder.encode(inputs[i]);
            encoded_inputs[i] = encoded_input;
            System.out.println(encoded_input);
        }
    }

}
