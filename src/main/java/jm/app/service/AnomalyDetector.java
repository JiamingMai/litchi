package jm.app.service;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.HoltWintersModel;

import java.util.List;

public class AnomalyDetector {

    private HoltWintersModel holtWintersModel = new HoltWintersModel();

    private final int CYCLE_LEN = 7;

    public boolean detectAnomaly(List<Double> y, double yNext) {
        // wrap matrix
        Matrix yMat = new Matrix(y.size(), 1);
        for (int i = 0; i < yMat.getRowNum(); i++) {
            yMat.setValue(i, 0, y.get(i));
        }
        // predict the value of next timestamp
        Matrix optimizedParams = holtWintersModel.optimize(yMat, CYCLE_LEN);
        Matrix yHat = holtWintersModel.fittedValue(yMat, optimizedParams, CYCLE_LEN, 1);
        double predictedValue = yHat.getValue(yHat.getRowNum() - 1, 0).doubleValue();
        // calculate 3 sigma
        Matrix sigma = AlgebraUtil.covariance(yMat);
        double sigmaValue = sigma.getValue(0, 0).doubleValue();
        double upperBound = predictedValue + 3 * sigmaValue;
        double lowerBound = predictedValue - 3 * sigmaValue;
        if (yNext > upperBound || yNext < lowerBound) {
            return true;
        } else {
            return false;
        }
    }

}
