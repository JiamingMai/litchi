package jm.app.optimize;

import jm.app.algebra.Matrix;
import jm.app.optimize.SteepestDescentOptimizer;
import jm.app.optimize.TargetFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class SteepestDescentOptimizerTest {

    private SteepestDescentOptimizer optimizer = new SteepestDescentOptimizer();

    @Test
    public void testOptimize() {
        Matrix params = new Matrix(1, 2);
        params.setValue(0, 0, 0.01);
        params.setValue(0, 1, 0.01);

        Matrix trainInput = new Matrix(3, 1);
        trainInput.setValue(0, 0, -1);
        trainInput.setValue(1, 0, 0);
        trainInput.setValue(2, 0, 1);

        Matrix truthOutput = new Matrix(3, 1);
        truthOutput.setValue(0, 0, -1);
        truthOutput.setValue(1, 0, 1);
        truthOutput.setValue(2, 0, 3);

        Matrix optimizedParams = optimizer.optimize(new TargetFunction() {
            @Override
            public BigDecimal fun(Matrix params, Matrix args) {
                BigDecimal x = args.getValue(0, 0);
                BigDecimal a = params.getValue(0, 0);
                BigDecimal b = params.getValue(0, 1);
                BigDecimal res = a.multiply(x).add(b);
                return res;
            }
        }, params, trainInput, truthOutput, true);
        final BigDecimal error = new BigDecimal(0.001);
        Assertions.assertTrue(optimizedParams.getValue(0, 0).subtract(new BigDecimal(2.0)).abs().
                subtract(error).compareTo(new BigDecimal(0.0)) < 0);
        Assertions.assertTrue(optimizedParams.getValue(0, 1).subtract(new BigDecimal(1.0)).abs().
                subtract(error).compareTo(new BigDecimal(0.0)) < 0);
    }

    /**
     * F(X) = a1*x1^2 + a2*x2, 其中a = 1, b = 2.
     * 计算x1 = 4，x2 = 3时F(X)关于a的偏导数
     */
    @Test
    public void testCalcPartialDerivative() {
        Matrix params = new Matrix(1, 2);
        params.setValue(0, 0, 1);
        params.setValue(0, 1, 2);

        Matrix args = new Matrix(1, 2);
        args.setValue(0, 0, 4);
        args.setValue(0, 1, 3);
        BigDecimal partialDerivative = optimizer.calcPartialDerivative(new TargetFunction() {
            @Override
            public BigDecimal fun(Matrix params, Matrix args) {
                BigDecimal x1 = args.getValue(0, 0);
                BigDecimal x2 = args.getValue(0, 1);
                BigDecimal res = params.getValue(0, 0).multiply(x1.multiply(x1)).add(params.getValue(0, 1).multiply(x2));
                return res;
            }
        }, params, 0, args);

        final BigDecimal error = new BigDecimal(0.00001);
        Assertions.assertTrue(partialDerivative.subtract(new BigDecimal(16.0)).abs().subtract(error).
                compareTo(new BigDecimal(0.0)) < 0);
    }


}
