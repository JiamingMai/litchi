package jm.app.optimize;

import jm.app.algebra.Matrix;
import jm.app.optimize.GeneticAlgorithmOptimizer;
import jm.app.optimize.Optimizer;
import jm.app.optimize.TargetFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class GeneticAlgorithmOptimizerTest {

    private Optimizer optimizer;

    @BeforeEach
    public void initOptimizer() {
        Matrix boundaries = new Matrix(2, 2);
        boundaries.setValue(0, 0, -5);
        boundaries.setValue(0, 1, 5);
        boundaries.setValue(1, 0, -5);
        boundaries.setValue(1, 1, 5);
        optimizer = new GeneticAlgorithmOptimizer(boundaries);
    }

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
        }, params, trainInput, truthOutput);
        System.out.println(optimizedParams);
        final BigDecimal error = new BigDecimal(0.01);
        Assertions.assertTrue(optimizedParams.getValue(0, 0).subtract(new BigDecimal(2.0)).abs().
                subtract(error).compareTo(new BigDecimal(0.0)) < 0);
        Assertions.assertTrue(optimizedParams.getValue(0, 1).subtract(new BigDecimal(1.0)).abs().
                subtract(error).compareTo(new BigDecimal(0.0)) < 0);
    }
}
