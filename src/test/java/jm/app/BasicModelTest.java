package jm.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

public class BasicModelTest {

    private Matrix x;

    private Matrix y;

    @BeforeEach
    public void init() {
        x = new Matrix(10, 1);
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

        y = new Matrix(10, 1);
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
    }

    private BigDecimal calcRmse(BasicModel basicModel) {
        Matrix params = basicModel.optimize(x, y);
        Matrix yHat = basicModel.calcValue(x, params);
        BigDecimal rmse = AlgebraUtil.multiply(AlgebraUtil.transpose(AlgebraUtil.subtract(yHat, y)), AlgebraUtil.subtract(yHat, y)).getValue(0, 0);
        rmse = new BigDecimal(Math.sqrt(rmse.doubleValue() / y.getRowNum()));
        return rmse;
    }

    @Test
    public void testBasicModel() {
        BasicModel linearModel = new LinearModel();
        System.out.println(String.format("Linear Model RMSE: %.2f", calcRmse(linearModel)));
        BasicModel logarithmModel = new LogarithmModel();
        System.out.println(String.format("Logarithm Model RMSE: %.2f", calcRmse(logarithmModel)));
        BasicModel powerModel = new PowerModel();
        System.out.println(String.format("Power Model RMSE: %.2f", calcRmse(powerModel)));
        BasicModel exponentialModel = new ExponentialModel();
        System.out.println(String.format("Exponential Model RMSE: %.2f", calcRmse(exponentialModel)));
        BasicModel polynomialModel = new PolynomialModel();
        System.out.println(String.format("Polynomial Model RMSE: %.2f", calcRmse(polynomialModel)));
    }
}
