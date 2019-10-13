package answer.chapter5;

import jm.app.algebra.Matrix;
import jm.app.optimize.SteepestDescentOptimizer;
import jm.app.optimize.TargetFunction;

import java.math.BigDecimal;

/**
 * 利用SteepestDescentOpitmizer编写程序求出5x1-2x2+3x3-6x4=0的一个可行解。
 */
public class Problem5_4 {

    static class MyTargetFunction implements TargetFunction {
        @Override
        public BigDecimal fun(Matrix params, Matrix args) {
            double x1 = params.getValue(0, 0).doubleValue();
            double x2 = params.getValue(0, 1).doubleValue();
            double x3 = params.getValue(0, 2).doubleValue();
            double x4 = params.getValue(0, 3).doubleValue();
            double a1 = args.getValue(0, 0).doubleValue();
            double a2 = args.getValue(0, 1).doubleValue();
            double a3 = args.getValue(0, 2).doubleValue();
            double a4 = args.getValue(0, 3).doubleValue();
            double result = Math.pow(a1 * x1 + a2 * x2 + a3 * x3 + a4 * x4, 2);
            return new BigDecimal(result);
        }
    }

    public static void main(String[] args) {
        Matrix arguments = new Matrix(1, 4);
        arguments.setValue(0, 0, 5);
        arguments.setValue(0, 1, -2);
        arguments.setValue(0, 2, 3);
        arguments.setValue(0, 3, -6);
        Matrix params = new Matrix(1, 4);
        params.setValue(0, 0, 0.3);
        params.setValue(0, 1, 0.3);
        params.setValue(0, 2, 0.5);
        params.setValue(0, 3, 0.2);
        MyTargetFunction targetFunction = new MyTargetFunction();
        SteepestDescentOptimizer steepestDescentOptimizer = new SteepestDescentOptimizer();
        steepestDescentOptimizer.setLearningRate(new BigDecimal(0.0001));
        Matrix optimizedParams = steepestDescentOptimizer.optimize(targetFunction, params, arguments);
        System.out.println(optimizedParams);

    }

}
