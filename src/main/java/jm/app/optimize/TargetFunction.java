package jm.app.optimize;

import jm.app.algebra.Matrix;

import java.math.BigDecimal;

public interface TargetFunction {

    BigDecimal fun(Matrix params, Matrix args);

}
