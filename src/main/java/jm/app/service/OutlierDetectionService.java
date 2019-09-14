package jm.app.service;

import jm.app.algebra.AdvancedAlgebraUtil;
import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.GmmModel;
import jm.app.algorithm.PcaModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OutlierDetectionService {

    private GmmModel gmmModel = new GmmModel();

    private PcaModel pcaModel = new PcaModel();

    private final int MAX_FEATURE_NUM = 3;

    // the threshold of outlier
    private final double EPSILON = 0.01;

    public Map<String, Double[]> detectOutlier(Map<String, Double[]> records) {
        Map<String, Double[]> outliers = new HashMap<>();
        Map<Integer, String> indexToKey = new HashMap<>();
        // Step 1. Convert Map to Matrix
        Matrix x = null;
        Set<String> keys = records.keySet();
        if (null != keys && !keys.isEmpty()) {
            // get the number of features
            String firstKey = keys.iterator().next();
            Double[] firstFeatures = records.get(firstKey);
            int featureNum = firstFeatures.length;
            int recordNum = keys.size();
            x = new Matrix(recordNum, featureNum);
            // copy the data and record the index of each row
            int i = 0;
            for (String key : keys) {
                Double[] features = records.get(key);
                Matrix xi = new Matrix(1, featureNum);
                for (int c = 0; c < featureNum; c++) {
                    xi.setValue(0, c, features[c]);
                }
                x = AdvancedAlgebraUtil.setRowVector(x, i, xi);
                indexToKey.put(i, key);
                i++;
            }
            // reduce dimension with PCA model if there are too many features
            if (featureNum > MAX_FEATURE_NUM) {
                // convert the normalized matrix to array
                x = AlgebraUtil.normalize(x, 0);
                Matrix[] xiArray = new Matrix[x.getRowNum()];
                for (int j = 0; j < xiArray.length; j++) {
                    xiArray[j] = AlgebraUtil.transpose(AlgebraUtil.getRowVector(x, j));
                }
                pcaModel.train(xiArray, MAX_FEATURE_NUM);
                Matrix reducedX = new Matrix(xiArray.length, MAX_FEATURE_NUM);
                for (int j = 0; j < xiArray.length; j++) {
                    Matrix xj = xiArray[j];
                    reducedX = AlgebraUtil.setRowVector(reducedX, j, AlgebraUtil.transpose(pcaModel.encode(xj)));
                }
                x = reducedX;
            }
        } else {
            return null;
        }
        // Step 2. Calculate the mean vector u and the covariance matrix sigma
        x = AdvancedAlgebraUtil.normalize(x, 0);
        Matrix u = AlgebraUtil.transpose(AdvancedAlgebraUtil.mean(x, 0));
        Matrix sigma = AdvancedAlgebraUtil.covariance(x);

        // Step 3. Calculate the result of Gaussian Function and detect the outliers
        for (int i = 0; i < x.getRowNum(); i++) {
            Matrix xi = AdvancedAlgebraUtil.getRowVector(x, i);
            BigDecimal p = gmmModel.gaussianFunction(xi, u, sigma);
            if (p.doubleValue() < EPSILON) {
                String key = indexToKey.get(i);
                outliers.put(key, records.get(key));
            }
        }
        return outliers;
    }

}
