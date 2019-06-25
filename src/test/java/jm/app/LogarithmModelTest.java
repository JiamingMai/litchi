package jm.app;

import jm.app.algebra.Matrix;
import jm.app.algorithm.LogarithmModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LogarithmModelTest {

    @Test
    public void testLinearModel() {
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

        LogarithmModel logarithmModel = new LogarithmModel();
        Matrix params = logarithmModel.optimize(x, y);
        System.out.println(params);
        final BigDecimal error = new BigDecimal(0.00001);
        Assertions.assertTrue(params.getValue(0, 0).subtract(new BigDecimal(3.0)).abs().subtract(error).compareTo(new BigDecimal(0.0)) < 0);
        Assertions.assertTrue(params.getValue(1, 0).subtract(new BigDecimal(2.0)).abs().subtract(error).compareTo(new BigDecimal(0.0)) < 0);

        Matrix newX = new Matrix(1, 1);
        newX.setValue(0, 0, 15);
        BigDecimal res = logarithmModel.calcValue(newX, params).getValue(0, 0);
        System.out.println(res);
        Assertions.assertTrue(res.subtract(new BigDecimal(8.416100402204421).abs()).subtract(error).compareTo(new BigDecimal(0.0)) < 0);
    }

}
