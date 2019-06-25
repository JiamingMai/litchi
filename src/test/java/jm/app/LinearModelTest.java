package jm.app;

import jm.app.algebra.Matrix;
import jm.app.algorithm.LinearModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LinearModelTest {

    @Test
    public void testLinearModel() {
        Matrix x = new Matrix(10, 1);
        x.setValue(0, 0, -1);
        x.setValue(1, 0, 0);
        x.setValue(2, 0, 1);
        x.setValue(3, 0, 2);
        x.setValue(4, 0, 3);
        x.setValue(5, 0, 4);
        x.setValue(6, 0, 5);
        x.setValue(7, 0, 6);
        x.setValue(8, 0, 7);
        x.setValue(9, 0, 8);

        Matrix y = new Matrix(10, 1);
        y.setValue(0, 0, -1);
        y.setValue(1, 0, 1);
        y.setValue(2, 0, 3);
        y.setValue(3, 0, 5);
        y.setValue(4, 0, 7);
        y.setValue(5, 0, 9);
        y.setValue(6, 0, 11);
        y.setValue(7, 0, 13);
        y.setValue(8, 0, 15);
        y.setValue(9, 0, 17);

        LinearModel linearModel = new LinearModel();
        Matrix params = linearModel.optimize(x, y);
        System.out.println(params);
        final BigDecimal error = new BigDecimal(0.00001);
        Assertions.assertTrue(params.getValue(0, 0).subtract(new BigDecimal(1.0)).abs().subtract(error).compareTo(new BigDecimal(0.0)) < 0);
        Assertions.assertTrue(params.getValue(1, 0).subtract(new BigDecimal(2.0)).abs().subtract(error).compareTo(new BigDecimal(0.0)) < 0);

        Matrix newX = new Matrix(1, 1);
        newX.setValue(0, 0, 15);
        BigDecimal res = linearModel.calcValue(newX, params).getValue(0, 0);
        System.out.println(res);
        Assertions.assertTrue(res.subtract(new BigDecimal(31).abs()).subtract(error).compareTo(new BigDecimal(0.0)) < 0);
    }

}
