package jm.app.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OutlierDetectionServiceTest2 {

    private Map<String, Double[]> readRecords() throws Exception {
        String rootPath = this.getClass().getResource("").getPath() + "../../../";
        String fileName = "cluster_test_data.csv";
        Scanner scanner = new Scanner(new File(rootPath, fileName));
        Map<String, Double[]> data = new HashMap<>();
        int number = 1;
        while (scanner.hasNextLine()) {
            System.out.println("Row #" + number + " Read");
            String row = scanner.nextLine();
            String[] field = row.split(",");
            String rowKey = field[0];
            // skip the first column
            Double[] values = new Double[field.length - 1];
            for (int i = 1; i < field.length; i++) {
                if (null == field[i] || "".equals(field[i])) {
                    field[i] = "0.0";
                }
                double value = Double.parseDouble(field[i]);
                values[i-1] = value;
            }
            data.put(rowKey, values);
            number++;
        }
        return data;
    }

    @Test
    public void testDetectOutlier() throws Exception {
        Map<String, Double[]> data = readRecords();
        OutlierDetectionService outlierDetectionService = new OutlierDetectionService();
        Map<String, Double[]> outliers = outlierDetectionService.detectOutlier(data);
        System.out.println("Total Number of Outliers: " + outliers.size());
        for (Map.Entry<String, Double[]> entry : outliers.entrySet()) {
            System.out.println(entry);
        }
    }

}
