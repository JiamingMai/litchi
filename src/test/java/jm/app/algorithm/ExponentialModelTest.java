package jm.app.algorithm;

import jm.app.algebra.Matrix;
import jm.app.algorithm.ExponentialModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ExponentialModelTest {

    @Test
    public void testExponentialModel() {
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
        y.setValue(0, 0, 22.16716829679195);
        y.setValue(1, 0, 163.7944500994327);
        y.setValue(2, 0, 1210.2863804782053);
        y.setValue(3, 0, 8942.873961125184);
        y.setValue(4, 0, 66079.39738442015);
        y.setValue(5, 0, 488264.37425701175);
        y.setValue(6, 0, 3607812.85249433);
        y.setValue(7, 0, 26658331.561523616);
        y.setValue(8, 0, 196979907.41199154);
        y.setValue(9, 0, 1455495586.2293708);

        ExponentialModel exponentialModel = new ExponentialModel();
        Matrix params = exponentialModel.optimize(x, y);
        System.out.println(params);
        final BigDecimal error = new BigDecimal(0.00001);
        Assertions.assertTrue(params.getValue(0, 0).subtract(new BigDecimal(3.0)).abs().subtract(error).compareTo(new BigDecimal(0.0)) < 0);
        Assertions.assertTrue(params.getValue(1, 0).subtract(new BigDecimal(2.0)).abs().subtract(error).compareTo(new BigDecimal(0.0)) < 0);
    }

}
