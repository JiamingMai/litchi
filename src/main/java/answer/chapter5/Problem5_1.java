package answer.chapter5;

import jm.app.algebra.Matrix;
import jm.app.optimize.Optimizer;
import jm.app.optimize.SteepestDescentOptimizer;
import jm.app.optimize.TargetFunction;

import java.math.BigDecimal;

/**
 * 利用SteepDescentOptimizer，分别计算出目标函数
 * F(X)=4*x1^3+2*x2^2+3*x3+x4在x1=7，x2=5，x3=-1，x4=16时
 * 关于x1，x2，x3，x4的偏导数。
 *
 * 思路：将F(X;A)转换为关于A的函数F(A;X)=x1^3*a1+x2^2*a2+x3*a3+x4*a4之后再计算,
 * 其中a1=4, a2=2, a3=3, a4=1，此时params是X, args是A
 */
public class Problem5_1 {

    static class MyTargetFunction implements TargetFunction {
        @Override
        public BigDecimal fun(Matrix params, Matrix args) {
            BigDecimal x1 = params.getValue(0, 0);
            BigDecimal x2 = params.getValue(0, 1);
            BigDecimal x3 = params.getValue(0, 2);
            BigDecimal x4 = params.getValue(0, 3);
            BigDecimal a1 = args.getValue(0, 0);
            BigDecimal a2 = args.getValue(0, 1);
            BigDecimal a3 = args.getValue(0, 2);
            BigDecimal a4 = args.getValue(0, 3);
            BigDecimal result = x1.pow(3).multiply(a1).add(x2.pow(2).multiply(a2)).add(x3.multiply(a3)).add(x4.multiply(a4));
            return result;
        }
    }

    public static void main(String[] args) {
        SteepestDescentOptimizer optimizer = new SteepestDescentOptimizer();
        Matrix params = new Matrix(1, 4);
        params.setValue(0, 0, 7);
        params.setValue(0, 1,  5);
        params.setValue(0, 2, -1);
        params.setValue(0, 3, 16);
        Matrix arguments = new Matrix(1, 4);
        arguments.setValue(0, 0, 4);
        arguments.setValue(0, 1, 2);
        arguments.setValue(0, 2, 3);
        arguments.setValue(0, 3, 1);
        MyTargetFunction myTargetFunction = new MyTargetFunction();
        BigDecimal partialDerivativeOfX1 = optimizer.calcPartialDerivative(myTargetFunction, params, 0, arguments);
        BigDecimal partialDerivativeOfX2 = optimizer.calcPartialDerivative(myTargetFunction, params, 1, arguments);
        BigDecimal partialDerivativeOfX3 = optimizer.calcPartialDerivative(myTargetFunction, params, 2, arguments);
        BigDecimal partialDerivativeOfX4 = optimizer.calcPartialDerivative(myTargetFunction, params, 3, arguments);
        System.out.println(partialDerivativeOfX1);
        System.out.println(partialDerivativeOfX2);
        System.out.println(partialDerivativeOfX3);
        System.out.println(partialDerivativeOfX4);
    }

}
