package answer.chapter9;

import jm.app.algebra.AdvancedAlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.GmmModel;
import sun.tools.jstat.Literal;

import java.math.BigDecimal;
import java.util.*;

/**
 * 尝试修改代码9-5，在GmmModel.java中增加一个方法
 * Map<Integer, List<Double>> clusterWithProbability(Matrix x, int k, int epochNum)
 * 利用GMM模型输出每个数据属于每个类别的概率，返回类型表示一个map，map的key是数据的索引，
 * value是一个List，List的大小等于k，List的第i元素是这个数据属于每i类别的概率。
 */
public class Problem2 {

    class ExtendedGmmModel extends GmmModel {

        public Map<Integer, List<Double>> clusterWithProbability(Matrix featureMat, int k, int epochNum) {
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
            return clusterWithProbability(x, u, sigma);
        }


        private Map<Integer, List<Double>> clusterWithProbability(Matrix x, Matrix[] u, Matrix[] sigma) {
            int k = u.length;
            Map<Integer, List<Double>> clusteredResult = new HashMap<>();
            for (int i = 0; i < x.getRowNum(); i++) {
                Matrix xi = AdvancedAlgebraUtil.getRowVector(x, i);
                List<Double> probabilities = new ArrayList<>();
                double sum = 0.0;
                for (int c = 0; c < k; c++) {
                    BigDecimal pic = gaussianFunction(xi, u[c], sigma[c]);
                    probabilities.add(pic.doubleValue());
                    sum += pic.doubleValue();
                }
                // normalize
                List<Double> normalizedProbabilities = new ArrayList<>();
                for (int c = 0; c < k; c++) {
                    double pic = probabilities.get(i);
                    pic /= sum;
                    normalizedProbabilities.add(pic);
                }
                clusteredResult.put(i, normalizedProbabilities);
            }
            return clusteredResult;
        }
    }

}
