package jm.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GmmModel {

    public Map<Integer, List<Integer>> cluster(Matrix featureMat, int k, int epochNum) {
        int dimension = featureMat.getColNum();
        // Step 1. Normalize the input
        Matrix dataPoints = AlgebraUtil.normalize(featureMat, 0);

        // Step 2. dispatch the data to random cluster
        Random random = new Random();
        List<Matrix>[] clusters = new List[k];
        for (int i = 0; i < featureMat.getRowNum(); i++) {
            Matrix xi = AlgebraUtil.getRowVector(featureMat, i);
            int clazzIndex = random.nextInt(k);
            if (null == clusters[clazzIndex]) {
                clusters[clazzIndex] = new ArrayList<>();
            }
            clusters[clazzIndex].add(xi);
        }

        // Step 3. initialize u and sigma of the gaussian components
        Matrix[] u = new Matrix[k];
        Matrix[] sigma = new Matrix[k];
        for (int i = 0; i < k; i++) {
            u[i] = new Matrix(1, dimension);
            sigma[i] = new Matrix(dimension, dimension);
        }
        for (int i = 0; i < k; i++) {
            List<Matrix> ci = clusters[i];
            for (Matrix xCi : ci) {
                u[i] = AlgebraUtil.add(u[i], xCi);
            }
            u[i] = AlgebraUtil.multiply(u[i], new BigDecimal(1.0 / ci.size()));
        }

        // Step 4. update the gaussian components
        for (int e = 0; e < epochNum; e++) {

            for (int i = 0; i < k; i++) {


            }
        }
        return null;
    }

    public BigDecimal gaussianFunction(Matrix x, Matrix u, Matrix sigma) {
        int d = x.getRowNum();
        double leftTerm = 1.0 / (Math.pow(2 * Math.PI, d / 2.0) * Math.sqrt(AlgebraUtil.determinant(sigma).doubleValue()));
        double rightTerm = Math.exp(-0.5 * AlgebraUtil.multiply(AlgebraUtil.multiply(
                AlgebraUtil.transpose(AlgebraUtil.subtract(x, u)), AlgebraUtil.inverse(sigma)),
                AlgebraUtil.subtract(x, u)).getValue(0, 0).doubleValue());
        BigDecimal p = new BigDecimal(leftTerm * rightTerm);
        return p;
    }
}
