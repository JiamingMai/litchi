package jm.app.algorithm;

import jm.app.algebra.Matrix;
import jm.app.algorithm.KMeansModel;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class KMeansModelTest {

    @Test
    public void testKMeansModel() throws Exception {
        String rootPath = this.getClass().getResource("").getPath() + "../../";
        String fileName = "cluster_test_data.csv";
        Scanner scanner = new Scanner(new File(rootPath, fileName));
        Matrix x = new Matrix(259, 2);
        int i = 0;
        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            double profit_amt = Double.parseDouble(fields[1]);
            double price = Double.parseDouble(fields[2]);
            x.setValue(i, 0, new BigDecimal(profit_amt));
            x.setValue(i, 1, new BigDecimal(price));
            i++;
        }
        KMeansModel kMeansModel = new KMeansModel();
        Map<Integer, List<Integer>> clusterToDataPointIndices = kMeansModel.cluster(x, 3, 100);
        System.out.println(clusterToDataPointIndices);
    }
}
