package jm.app.algorithm;

import jm.app.optimize.BasicModel;
import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;

public class LinearModel implements BasicModel {

    @Override
    public Matrix optimize(Matrix x, Matrix y) {
        Matrix newX = convertToMatrixWithX0(x);
        Matrix newXT = AlgebraUtil.transpose(newX);
        Matrix params = AlgebraUtil.multiply(AlgebraUtil.multiply(AlgebraUtil.inverse(AlgebraUtil.multiply(newXT, newX)), newXT), y);
        return params;
    }

    @Override
    public Matrix calcValue(Matrix x, Matrix params) {
        Matrix newX = convertToMatrixWithX0(x);
        Matrix yHat = AlgebraUtil.multiply(newX, params);
        return yHat;
    }

    private Matrix convertToMatrixWithX0(Matrix x) {
        Matrix newX = new Matrix(x.getRowNum(), x.getColNum() + 1);
        for (int i = 1; i < newX.getColNum(); i++) {
            for (int j = 0; j < newX.getRowNum(); j++) {
                BigDecimal value = new BigDecimal(x.getValue(j, i - 1).doubleValue());
                newX.setValue(j, i, value);
            }
        }
        for (int i = 0; i < newX.getRowNum(); i++) {
            newX.setValue(i, 0, 1);
        }
        return newX;
    }

    @Override
    public String toString() {
        return "Linear Model";
    }
}
