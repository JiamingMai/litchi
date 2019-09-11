package jm.app.service;

import jm.app.algebra.Matrix;
import jm.app.algorithm.HoltWintersModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnomalyDetectorTest {

    @Test
    public void testAnomalyDetection() {
        Double[] y = new Double[]{400.0, 401.68, 399.395, 401.22, 407.21, 410.25, 414.31, 414.63, 414.84, 414.56,
                415.62, 409.79, 407.86, 407.53, 409.67, 405.5};
        List<Double> yAsList = Arrays.asList(y);
        AnomalyDetector anomalyDetector = new AnomalyDetector();
        boolean anomaly = anomalyDetector.detectAnomaly(yAsList, 275.05);
        System.out.println(anomaly);
    }

}
