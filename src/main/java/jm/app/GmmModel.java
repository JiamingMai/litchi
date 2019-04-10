package jm.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class GmmModel {

    public Map<Integer, List<Integer>> cluster(Matrix featureMat, int k, int epochNum) {
        // Step 1. Normalize the input
        Matrix dataPoints = AlgebraUtil.normalize(featureMat, 0);

        // Step 2. dispatch the data to random cluster
        Matrix[] clusters = new Matrix[k];
        // TODO:
        for (int e = 0; e < epochNum; e++) {

        }
        return null;
    }

    private BigDecimal gaussianFunction(Matrix x, Matrix u, Matrix sigma) {
        // TODO:
        return null;
    }
}
