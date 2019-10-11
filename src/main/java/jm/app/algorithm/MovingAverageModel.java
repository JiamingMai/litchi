package jm.app.algorithm;

import jm.app.algebra.Matrix;
import jm.app.optimize.BasicModel;

public class MovingAverageModel implements BasicModel {

    private int windowsSize = 3;

    public MovingAverageModel() {
    }

    public MovingAverageModel(int windowsSize) {
        this.windowsSize = windowsSize;
    }

    @Override
    public Matrix optimize(Matrix x, Matrix y) {
        // no need to implement this method
        return null;
    }

    @Override
    public Matrix calcValue(Matrix x, Matrix params) {
        int length = x.getRowNum();
        Matrix yHat = new Matrix(length, 1);
        for (int i = 0; i < length; i++) {
            if (i < windowsSize) {
                double sum = 0.0;
                for (int j = i; j >= 0; j--) {
                    sum += x.getValue(j, 0).doubleValue();
                }
                double val = sum / (i + 1);
                yHat.setValue(i, 0, val);
            } else {
                double sum = 0.0;
                for (int j = i; j >= i - windowsSize + 1; j--) {
                    sum += x.getValue(j, 0).doubleValue();
                }
                double val = sum / windowsSize;
                yHat.setValue(i, 0, val);
            }
        }
        return yHat;
    }

    public int getWindowsSize() {
        return windowsSize;
    }

    public void setWindowsSize(int windowsSize) {
        this.windowsSize = windowsSize;
    }
}
