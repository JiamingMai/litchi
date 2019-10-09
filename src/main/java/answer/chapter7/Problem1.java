package answer.chapter7;

import jm.app.algebra.Matrix;
import jm.app.algorithm.*;
import jm.app.optimize.BasicModel;

import java.math.BigDecimal;

public class Problem1 {

    private static Matrix[] init() {
        Matrix x = new Matrix(10, 1);
        x.setValue(0, 0, 1);
        x.setValue(1, 0, 2);
        x.setValue(2, 0, 3);
        x.setValue(3, 0, 4);
        x.setValue(4, 0, 5);
        x.setValue(5, 0, 6);
        x.setValue(6, 0, 7);
        x.setValue(7, 0, 8);
        x.setValue(8, 0, 9);
        x.setValue(9, 0, 10);

        Matrix y = new Matrix(10, 1);
        y.setValue(0, 0, 3.00);
        y.setValue(1, 0, 4.38629436119891);
        y.setValue(2, 0, 5.19722457733622);
        y.setValue(3, 0, 5.772588722239782);
        y.setValue(4, 0, 6.218875824868201);
        y.setValue(5, 0, 6.58351893845611);
        y.setValue(6, 0, 6.891820298110627);
        y.setValue(7, 0, 7.1588830833596715);
        y.setValue(8, 0, 7.394449154672439);
        y.setValue(9, 0, 7.605170185988092);

        Matrix[] xAndY = new Matrix[2];
        xAndY[0] = x;
        xAndY[1] = y;
        return xAndY;
    }

    public static void testOtherModels(Matrix x, Matrix y, BasicModel[] models) {
        for (BasicModel model : models) {
            Matrix params = model.optimize(x, y);
            System.out.println(params);
            Matrix newX = new Matrix(1, 1);
            newX.setValue(0, 0, 15);
            BigDecimal res = model.calcValue(newX, params).getValue(0, 0);
            System.out.println(res);
        }
    }

    public static void main(String[] args) {
        Matrix[] xAndY = init();
        BasicModel[] models = new BasicModel[5];
        models[0] = new LinearModel();
        models[1] = new LogarithmModel();
        models[2] = new ExponentialModel();
        models[3] = new PolynomialModel();
        models[4] = new PowerModel();
        testOtherModels(xAndY[0], xAndY[1], models);
    }

}
