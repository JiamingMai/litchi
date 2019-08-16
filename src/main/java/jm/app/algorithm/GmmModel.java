package jm.app.algorithm;

import jm.app.algebra.AdvancedAlgebraUtil;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;
import java.util.*;

public class GmmModel {

    public Map<Integer, List<Integer>> cluster(Matrix featureMat, int k, int epochNum) {
        int dimension = featureMat.getColNum();
        // Step 1. Normalize the input
        Matrix x = AdvancedAlgebraUtil.normalize(featureMat, 0);

        // Step 2. dispatch the data to random cluster
        Random random = new Random();
        List<Matrix>[] clusters = new List[k];
        for (int i = 0; i < x.getRowNum(); i++) {
            Matrix xi = AdvancedAlgebraUtil.getRowVector(x, i);
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
                u[i] = AdvancedAlgebraUtil.add(u[i], xCi);
            }
            u[i] = AdvancedAlgebraUtil.multiply(u[i], new BigDecimal(1.0 / ci.size()));
            Matrix combinedCi = new Matrix(ci.size(), dimension);
            for (int r = 0; r < ci.size(); r++) {
                for (int c = 0; c < dimension; c++) {
                    combinedCi.setValue(r, c, ci.get(r).getValue(0, c));
                }
            }
            sigma[i] = AdvancedAlgebraUtil.covariance(combinedCi);
        }
        for (int i = 0; i < k; i++) {
            List<Matrix> ci = clusters[i];
            for (Matrix xCi : ci) {
                Matrix xiSubtractUc = AdvancedAlgebraUtil.subtract(xCi, u[i]);
                Matrix transXiSubtractUc = AdvancedAlgebraUtil.transpose(xiSubtractUc);
                sigma[i] = AdvancedAlgebraUtil.add(sigma[i], AdvancedAlgebraUtil.multiply(transXiSubtractUc, xiSubtractUc));
            }
            u[i] = AdvancedAlgebraUtil.multiply(u[i], new BigDecimal(1.0 / ci.size()));
            sigma[i] = AdvancedAlgebraUtil.multiply(sigma[i], new BigDecimal(1.0 / ci.size()));
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
                        Matrix xi = AdvancedAlgebraUtil.getRowVector(x, i);
                        pi = pi.add(gaussianFunction(xi, u[s], sigma[s]));
                    }
                    Matrix xi = AdvancedAlgebraUtil.getRowVector(x, i);
                    BigDecimal pic = gaussianFunction(xi, u[c], sigma[c]).multiply(new BigDecimal(1.0 / pi.doubleValue()));
                    newU[c] = AdvancedAlgebraUtil.add(newU[c], AdvancedAlgebraUtil.multiply(xi, pic));
                    Matrix xiSubtractUc = AdvancedAlgebraUtil.subtract(xi, u[c]);
                    Matrix transXiSubtractUc = AdvancedAlgebraUtil.transpose(xiSubtractUc);
                    newSigma[c] = AdvancedAlgebraUtil.add(newSigma[c], AdvancedAlgebraUtil.multiply(AdvancedAlgebraUtil.multiply(transXiSubtractUc, xiSubtractUc), pic));
                    nc = nc.add(pic);
                }
                System.out.format("cluster #%d have %d samples\n", c, (int) nc.doubleValue());
                newU[c] = AdvancedAlgebraUtil.multiply(newU[c], new BigDecimal(1.0 / nc.doubleValue()));
                newSigma[c] = AdvancedAlgebraUtil.multiply(newSigma[c], new BigDecimal(1.0 / nc.doubleValue()));
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
            Matrix xi = AdvancedAlgebraUtil.getRowVector(x, i);
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
            x = AdvancedAlgebraUtil.transpose(x);
        }
        if (sigma.getRowNum() != u.getRowNum()) {
            u = AdvancedAlgebraUtil.transpose(u);
        }
        int d = x.getRowNum();
        double leftTerm = 1.0 / (Math.pow(2 * Math.PI, d / 2.0) * Math.sqrt(AdvancedAlgebraUtil.determinant(sigma).doubleValue()));
        double rightTerm = Math.exp(-0.5 * AdvancedAlgebraUtil.multiply(AdvancedAlgebraUtil.multiply(
                AdvancedAlgebraUtil.transpose(AdvancedAlgebraUtil.subtract(x, u)), AdvancedAlgebraUtil.inverse(sigma)),
                AdvancedAlgebraUtil.subtract(x, u)).getValue(0, 0).doubleValue());
        BigDecimal p = new BigDecimal(leftTerm * rightTerm);
        return p;
    }
}
