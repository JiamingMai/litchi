package jm.app.algorithm;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.GmmModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GmmModelTest {

    @Test
    public void testCluster() throws IOException {
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
        GmmModel gmmModel = new GmmModel();
        Map<Integer, List<Integer>> clusterToDataPointIndices = gmmModel.cluster(x, 3, 100);
        System.out.println(clusterToDataPointIndices);
    }

    @Test
    public void testGaussianFunction() {
        Matrix x = new Matrix(1, 2);
        x.setValue(0, 0, 0);
        x.setValue(0, 1, 0);
        Matrix u = new Matrix(1, 2);
        u.setValue(0, 0, 0);
        u.setValue(0, 1, 0);
        Matrix sigma = new Matrix(2, 2);
        sigma.setValue(0, 0, 3.0);
        sigma.setValue(0, 1, 0.0);
        sigma.setValue(1, 0, 0.0);
        sigma.setValue(1, 1, 3.5);

        GmmModel gmmModel = new GmmModel();
        BigDecimal p = gmmModel.gaussianFunction(AlgebraUtil.transpose(x), AlgebraUtil.transpose(u), sigma);
        System.out.println(p);
    }
}
