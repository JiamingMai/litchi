package jm.app;

import java.math.BigDecimal;

public interface TargetFunction {

    BigDecimal fun(Matrix params, Matrix args);

}
