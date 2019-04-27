package jm.app;

import java.math.BigDecimal;
import java.util.*;

public class GmmModel {

    public Map<Integer, List<Integer>> cluster(Matrix featureMat, int k, int epochNum) {
        int dimension = featureMat.getColNum();
        // Step 1. Normalize the input
        Matrix x = AlgebraUtil.normalize(featureMat, 0);

        // Step 2. dispatch the data to random cluster
        Random random = new Random();
        List<Matrix>[] clusters = new List[k];
        for (int i = 0; i < x.getRowNum(); i++) {
            Matrix xi = AlgebraUtil.getRowVector(x, i);
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
            Matrix combinedCi = new Matrix(ci.size(), dimension);
            for (int r = 0; r < ci.size(); r++) {
                for (int c = 0; c < dimension; c++) {
                    combinedCi.setValue(r, c, ci.get(r).getValue(0, c));
                }
            }
            sigma[i] = AlgebraUtil.covariance(combinedCi);
        }
        for (int i = 0; i < k; i++) {
            List<Matrix> ci = clusters[i];
            for (Matrix xCi : ci) {
                Matrix xiSubtractUc = AlgebraUtil.subtract(xCi, u[i]);
                Matrix transXiSubtractUc = AlgebraUtil.transpose(xiSubtractUc);
                sigma[i] = AlgebraUtil.add(sigma[i], AlgebraUtil.multiply(transXiSubtractUc, xiSubtractUc));
            }
            u[i] = AlgebraUtil.multiply(u[i], new BigDecimal(1.0 / ci.size()));
            sigma[i] = AlgebraUtil.multiply(sigma[i], new BigDecimal(1.0 / ci.size()));
        }

        // Step 4. update the gaussian components
        for (int e = 0; e < epochNum; e++) {
            System.out.format("==== Epoch #%d ====\n", e);
            // initialize new u and sigma
            Matrix[] newU = new Matrix[k];
            Matrix[] newSigma = new Matrix[k];
            for (int i = 0; i < k; i++) {
                newU[i] = new Matrix(1, dimension);
                newSigma[i] = new Matrix(dimension, dimension);
            }
            for (int c = 0; c < k; c++) {
                // update u and sigma for the cth cluster with EM algorithm
                BigDecimal nc = new BigDecimal(0.0);
                for (int i = 0; i < x.getRowNum(); i++) {
                    BigDecimal pi = new BigDecimal(0.0);
                    for (int s = 0; s < k; s++) {
                        Matrix xi = AlgebraUtil.getRowVector(x, i);
                        pi = pi.add(gaussianFunction(xi, u[s], sigma[s]));
                    }
                    Matrix xi = AlgebraUtil.getRowVector(x, i);
                    BigDecimal pic = gaussianFunction(xi, u[c], sigma[c]).multiply(new BigDecimal(1.0 / pi.doubleValue()));
                    newU[c] = AlgebraUtil.add(newU[c], AlgebraUtil.multiply(xi, pic));
                    Matrix xiSubtractUc = AlgebraUtil.subtract(xi, u[c]);
                    Matrix transXiSubtractUc = AlgebraUtil.transpose(xiSubtractUc);
                    newSigma[c] = AlgebraUtil.add(newSigma[c], AlgebraUtil.multiply(AlgebraUtil.multiply(transXiSubtractUc, xiSubtractUc), pic));
                    nc = nc.add(pic);
                }
                System.out.format("cluster #%d have %d samples\n", c, (int) nc.doubleValue());
                newU[c] = AlgebraUtil.multiply(newU[c], new BigDecimal(1.0 / nc.doubleValue()));
                newSigma[c] = AlgebraUtil.multiply(newSigma[c], new BigDecimal(1.0 / nc.doubleValue()));
            }
            u = newU;
            sigma = newSigma;
            System.out.println("===================");
            System.out.println();
        }
        System.out.println(u);
        return cluster(x, u, sigma);
    }

    private Map<Integer, List<Integer>> cluster(Matrix x, Matrix[] u, Matrix[] sigma) {
        int k = u.length;
        Map<Integer, List<Integer>> clusteredResult = new HashMap<>();
        for (int i = 0; i < x.getRowNum(); i++) {
            Matrix xi = AlgebraUtil.getRowVector(x, i);
            BigDecimal maxP = new BigDecimal(Double.MIN_VALUE);
            int bestClazz = -1;
            for (int c = 0; c < k; c++) {
                BigDecimal pic = gaussianFunction(xi, u[c], sigma[c]);
                if (pic.compareTo(maxP) > 0) {
                    maxP = pic;
                    bestClazz = c;
                }
            }
            List<Integer> elementsInClazz = clusteredResult.get(bestClazz);
            if (null == elementsInClazz) {
                elementsInClazz = new ArrayList<>();
            }
            elementsInClazz.add(i);
            clusteredResult.put(bestClazz, elementsInClazz);
        }
        return clusteredResult;
    }

    public BigDecimal gaussianFunction(Matrix inputX, Matrix inputU, Matrix sigma) {
        Matrix x = inputX;
        Matrix u = inputU;
        if (sigma.getRowNum() != x.getRowNum()) {
            x = AlgebraUtil.transpose(x);
        }
        if (sigma.getRowNum() != u.getRowNum()) {
            u = AlgebraUtil.transpose(u);
        }
        int d = x.getRowNum();
        double leftTerm = 1.0 / (Math.pow(2 * Math.PI, d / 2.0) * Math.sqrt(AlgebraUtil.determinant(sigma).doubleValue()));
        double rightTerm = Math.exp(-0.5 * AlgebraUtil.multiply(AlgebraUtil.multiply(
                AlgebraUtil.transpose(AlgebraUtil.subtract(x, u)), AlgebraUtil.inverse(sigma)),
                AlgebraUtil.subtract(x, u)).getValue(0, 0).doubleValue());
        BigDecimal p = new BigDecimal(leftTerm * rightTerm);
        return p;
    }
}
