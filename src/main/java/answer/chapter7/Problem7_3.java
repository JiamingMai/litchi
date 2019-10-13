package answer.chapter7;

import jm.app.algebra.Matrix;
import jm.app.algorithm.*;
import jm.app.optimize.BasicModel;

import java.math.BigDecimal;

// 4, 8, 9, 8, 7, 12, 6, 10, 6, 9
// 9, 20, 22, 15, 17, 23, 18, 25, 10, 20
public class Problem7_3 {

    public static void main(String[] args) {
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
        LinearModel model = new LinearModel();
        Matrix params = model.optimize(x, y);
        Matrix newX = new Matrix(1, 1);
        newX.setValue(0, 0, 5);
        BigDecimal res = model.calcValue(newX, params).getValue(0, 0);
        System.out.println(res);
    }

}
