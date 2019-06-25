package jm.app.algorithm;

import jm.app.optimize.BasicModel;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;

public class LogarithmModel implements BasicModel {

    @Override
    public Matrix optimize(Matrix x, Matrix y) {
        Matrix lnx = new Matrix(x.getRowNum(), x.getColNum());
        for (int i = 0; i < lnx.getRowNum(); i++) {
            for (int j = 0; j < lnx.getColNum(); j++) {
                BigDecimal value = new BigDecimal(Math.log(x.getValue(i, j).doubleValue()));
                lnx.setValue(i, j, value);
            }
        }
        LinearModel linearModel = new LinearModel();
        Matrix params = linearModel.optimize(lnx, y);
        return params;
    }

    @Override
    public Matrix calcValue(Matrix x, Matrix params) {
        Matrix lnx = new Matrix(x.getRowNum(), x.getColNum());
        for (int i = 0; i < x.getRowNum(); i++) {
            for (int j = 0; j < x.getColNum(); j++) {
                BigDecimal value = new BigDecimal(Math.log(x.getValue(i, j).doubleValue()));
                lnx.setValue(i, j, value);
            }
        }
        LinearModel linearModel = new LinearModel();
        return linearModel.calcValue(lnx, params);
    }
}
