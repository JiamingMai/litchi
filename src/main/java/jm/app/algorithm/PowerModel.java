package jm.app.algorithm;

import jm.app.optimize.BasicModel;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;

public class PowerModel implements BasicModel {

    @Override
    public Matrix optimize(Matrix x, Matrix y) {
        Matrix lnx = new Matrix(x.getRowNum(), x.getColNum());
        for (int i = 0; i < lnx.getRowNum(); i++) {
            for (int j = 0; j < lnx.getColNum(); j++) {
                BigDecimal value = new BigDecimal(Math.log(x.getValue(i, j).doubleValue()));
                lnx.setValue(i, j, value);
            }
        }

        Matrix lny = new Matrix(y.getRowNum(), y.getColNum());
        for (int i = 0; i < lny.getRowNum(); i++) {
            for (int j = 0; j < lny.getColNum(); j++) {
                BigDecimal value = new BigDecimal(Math.log(y.getValue(i, j).doubleValue()));
                lny.setValue(i, j, value);
            }
        }

        LinearModel linearModel = new LinearModel();
        Matrix params = linearModel.optimize(lnx, lny);
        BigDecimal lna = params.getValue(0, 0);
        BigDecimal b = params.getValue(1, 0);
        BigDecimal a = new BigDecimal(Math.exp(lna.doubleValue()));
        params.setValue(0, 0, a);
        return params;
    }

    @Override
    public Matrix calcValue(Matrix x, Matrix params) {
        Matrix lnx = new Matrix(x.getRowNum(), x.getColNum());
        for (int i = 0; i < lnx.getRowNum(); i++) {
            for (int j = 0; j < lnx.getColNum(); j++) {
                BigDecimal value = new BigDecimal(Math.log(x.getValue(i, j).doubleValue()));
                lnx.setValue(i, j, value);
            }
        }
        BigDecimal a = params.getValue(0, 0);
        BigDecimal b = params.getValue(1, 0);
        BigDecimal lna = new BigDecimal(Math.log(a.doubleValue()));
        Matrix linearParams = new Matrix(2, 1);
        linearParams.setValue(0, 0, lna);
        linearParams.setValue(1, 0, b);
        LinearModel linearModel = new LinearModel();
        Matrix lny = linearModel.calcValue(lnx, linearParams);
        Matrix y = new Matrix(lny.getRowNum(), lny.getColNum());
        for (int i = 0; i < lny.getRowNum(); i++) {
            BigDecimal yi = new BigDecimal(Math.exp(lny.getValue(i, 0).doubleValue()));
            y.setValue(i, 0, yi);
        }
        return y;
    }
}
