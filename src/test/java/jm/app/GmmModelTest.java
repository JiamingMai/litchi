package jm.app;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class GmmModelTest {

    @Test
    public void testGmmModel() {
        Matrix x = new Matrix(1, 2);
        x.setValue(0, 0, 0);
        x.setValue(0, 1, 0);
        Matrix u = new Matrix(1, 2);
        u.setValue(0, 0, 0);
        u.setValue(0, 1, 0);
        Matrix sigma = new Matrix(2, 2);
        sigma.setValue(0, 0, 3.0);
        sigma.setValue(0, 1, 0.0);
        sigma.setValue(1, 0, 0.0);
        sigma.setValue(1, 1, 3.5);

        GmmModel gmmModel = new GmmModel();
        BigDecimal p = gmmModel.gaussianFunction(AlgebraUtil.transpose(x), AlgebraUtil.transpose(u), sigma);
        System.out.println(p);
    }
}
