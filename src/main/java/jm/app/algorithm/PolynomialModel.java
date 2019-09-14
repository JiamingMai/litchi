package jm.app.algorithm;

import jm.app.optimize.BasicModel;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;

public class PolynomialModel implements BasicModel {

    private Matrix wrapInputMatrixX(Matrix x) {
        Matrix x2 = new Matrix(x.getRowNum(), x.getColNum());
        Matrix x3 = new Matrix(x.getRowNum(), x.getColNum());
        for (int i = 0; i < x.getRowNum(); i++) {
            for (int j = 0; j < x.getColNum(); j++) {
                x2.setValue(i, j, new BigDecimal(Math.pow(x.getValue(i, j).doubleValue(), 2)));
                x3.setValue(i, j, new BigDecimal(Math.pow(x.getValue(i, j).doubleValue(), 3)));
            }
        }

        Matrix[] xMat = new Matrix[]{x, x2, x3};
        Matrix newX = new Matrix(x.getRowNum(), 3);
        for (int i = 0; i < newX.getColNum(); i++) {
            for (int j = 0; j < newX.getRowNum(); j++) {
                BigDecimal value = xMat[i].getValue(j, 0);
                newX.setValue(j, i, value);
            }
        }
        return newX;
    }

    @Override
    public Matrix optimize(Matrix x, Matrix y) {
        Matrix newX = wrapInputMatrixX(x);
        LinearModel linearModel = new LinearModel();
        Matrix params = linearModel.optimize(newX, y);
        return params;
    }

    @Override
    public Matrix calcValue(Matrix x, Matrix params) {
        Matrix newX = wrapInputMatrixX(x);
        LinearModel linearModel  = new LinearModel();
        Matrix y = linearModel.calcValue(newX, params);
        return y;
    }

    @Override
    public String toString() {
        return "Polynomial Model";
    }
}
