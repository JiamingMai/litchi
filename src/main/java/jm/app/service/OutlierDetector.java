package jm.app.service;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.GmmModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OutlierDetector {

    private GmmModel gmmModel = new GmmModel();

    // the threshold of outlier
    private final double EPSILON = 0.001;

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
                AlgebraUtil.setRowVector(x, i, xi);
                indexToKey.put(i, key);
                i++;
            }
        } else {
            return null;
        }
        // Step 2. Calculate the mean vector u and the covariance matrix sigma
        x = AlgebraUtil.normalize(x, 0);
        Matrix u = AlgebraUtil.mean(x, 0);
        Matrix sigma = AlgebraUtil.covariance(x);

        // Step 3. Calculate the result of Gaussian Function and detect the outliers
        for (int i = 0; i < x.getRowNum(); i++) {
            Matrix xi = AlgebraUtil.getRowVector(x, i);
            BigDecimal p = gmmModel.gaussianFunction(xi, u, sigma);
            if (p.doubleValue() < EPSILON) {
                String key = indexToKey.get(i);
                outliers.put(key, records.get(key));
            }
        }
        return outliers;
    }

}
