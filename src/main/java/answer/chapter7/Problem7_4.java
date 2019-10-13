package answer.chapter7;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.*;
import jm.app.optimize.BasicModel;

import java.math.BigDecimal;

// 4, 8, 9, 8, 7, 12, 6, 10, 6, 9
// 9, 20, 22, 15, 17, 23, 18, 25, 10, 20
public class Problem7_4 {

    private static Matrix[] init() {
        Matrix x = new Matrix(10, 1);
        x.setValue(0, 0, 4);
        x.setValue(1, 0, 8);
        x.setValue(2, 0, 9);
        x.setValue(3, 0, 8);
        x.setValue(4, 0, 7);
        x.setValue(5, 0, 12);
        x.setValue(6, 0, 6);
        x.setValue(7, 0, 10);
        x.setValue(8, 0, 6);
        x.setValue(9, 0, 9);

        Matrix y = new Matrix(10, 1);
        y.setValue(0, 0, 9);
        y.setValue(1, 0, 20);
        y.setValue(2, 0, 22);
        y.setValue(3, 0, 15);
        y.setValue(4, 0, 17);
        y.setValue(5, 0, 23);
        y.setValue(6, 0, 18);
        y.setValue(7, 0, 25);
        y.setValue(8, 0, 10);
        y.setValue(9, 0, 20);

        Matrix[] xAndY = new Matrix[2];
        xAndY[0] = x;
        xAndY[1] = y;
        return xAndY;
    }

    public static void testOtherModels(Matrix x, Matrix y, BasicModel[] models) {
        for (BasicModel model : models) {
            Matrix params = model.optimize(x, y);
            Matrix newX = new Matrix(1, 1);
            newX.setValue(0, 0, 5);
            BigDecimal res = model.calcValue(newX, params).getValue(0, 0);
            BigDecimal rmse = calcRmse(model, x, y, params);
            System.out.println(model);
            System.out.println(String.format("Predicted Result: %.2f", res));
            System.out.println(String.format("RMSE: %.2f", rmse.doubleValue()));
            System.out.println();
        }
    }

    public static BigDecimal calcRmse(BasicModel model, Matrix x, Matrix y, Matrix params) {
        Matrix yHat = model.calcValue(x, params);
        BigDecimal rmse = AlgebraUtil.l2Norm(AlgebraUtil.subtract(yHat, y));
        rmse = new BigDecimal(Math.sqrt(rmse.pow(2).doubleValue() / yHat.getRowNum()));
        return rmse;
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
