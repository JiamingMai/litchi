package jm.app;

import java.math.BigDecimal;

public class ExponentialModel implements BasicModel {

    @Override
    public Matrix optimize(Matrix x, Matrix y) {
        Matrix lny = new Matrix(y.getRowNum(), y.getColNum());
        for (int i = 0; i < lny.getRowNum(); i++) {
            for (int j = 0; j < lny.getColNum(); j++) {
                BigDecimal value = new BigDecimal(Math.log(y.getValue(i, j).doubleValue()));
                lny.setValue(i, j, value);
            }
        }

        LinearModel linearModel = new LinearModel();
        Matrix params = linearModel.optimize(x, lny);
        BigDecimal lna = params.getValue(0, 0);
        BigDecimal a = new BigDecimal(Math.exp(lna.doubleValue()));
        BigDecimal b = params.getValue(1, 0);
        params.setValue(0, 0, a);
        params.setValue(1, 0, b);
        return params;
    }

    @Override
    public Matrix calcValue(Matrix x, Matrix params) {
        BigDecimal a = params.getValue(0, 0);
        BigDecimal b = params.getValue(1, 0);
        Matrix linearParams = new Matrix(2, 1);
        BigDecimal lna = new BigDecimal(Math.log(a.doubleValue()));
        linearParams.setValue(0, 0, lna);
        linearParams.setValue(1, 0, b);
        LinearModel linearModel = new LinearModel();
        Matrix lny = linearModel.calcValue(x, linearParams);
        Matrix y = new Matrix(lny.getRowNum(), lny.getColNum());
        for (int i = 0; i < lny.getRowNum(); i++) {
            BigDecimal yi = new BigDecimal(Math.exp(lny.getValue(i, 0).doubleValue()));
            y.setValue(i, 0, yi);
        }
        return y;
    }
}
