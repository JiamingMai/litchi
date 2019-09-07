package jm.app.algorithm;

import jm.app.algebra.Matrix;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class HoltWintersModelTest {

    @Test
    public void testOptimizeParameter() {
        int cycleLen = 7;
        Matrix y = new Matrix(17, 1);
        y.setValue(0, 0, 1.0);
        y.setValue(1, 0, 2.0);
        y.setValue(2, 0, 4.0);
        y.setValue(3, 0, 8.0);
        y.setValue(4, 0, 16.0);
        y.setValue(5, 0, 32.0);
        y.setValue(6, 0, 64.0);
        y.setValue(7, 0, 128.0);
        y.setValue(8, 0, 256.0);
        y.setValue(9, 0, 512.0);
        y.setValue(10, 0, 1024.0);
        y.setValue(11, 0, 2048.0);
        y.setValue(12, 0, 4096.0);
        y.setValue(13, 0, 8192.0);
        y.setValue(14, 0, 16384.0);
        y.setValue(15, 0, 32768.0);
        y.setValue(16, 0, 65536.0);

        long startTimestamp = System.currentTimeMillis();
        HoltWintersModel holtWintersModel = new HoltWintersModel();
        Matrix bestParams = holtWintersModel.optimize(y, cycleLen, 20);
        System.out.println(bestParams);
        long endTimestamp = System.currentTimeMillis();
        System.out.println((endTimestamp - startTimestamp) + " ms");

        Matrix forecastValues = holtWintersModel.fittedValue(y, bestParams, cycleLen, 3);
        System.out.println(forecastValues);
    }

}
