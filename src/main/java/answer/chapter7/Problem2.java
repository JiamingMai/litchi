package answer.chapter7;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.optimize.BasicModel;
import jm.app.optimize.GeneticAlgorithmOptimizer;
import jm.app.optimize.SteepestDescentOptimizer;
import jm.app.optimize.TargetFunction;

import java.math.BigDecimal;

public class Problem2 {

    class SteepestDecentLinearModel implements BasicModel {

        SteepestDescentOptimizer steepestDescentOptimizer = new SteepestDescentOptimizer();

        @Override
        public Matrix optimize(Matrix x, Matrix y) {
            Matrix params = new Matrix(1, x.getRowNum());
            for (int i = 0; i < params.getColNum(); i++) {
                params.setValue(0, i, Math.random());
            }
            Matrix optimizedParams = steepestDescentOptimizer.optimize(new TargetFunction() {
                @Override
                public BigDecimal fun(Matrix params, Matrix args) {
                    Matrix x = AlgebraUtil.getColumnVector(args, 0);
                    BigDecimal result = AlgebraUtil.multiply(params, x).getValue(0, 0);
                    return result;
                }
            }, params, x, y, true);
            return optimizedParams;
        }

        @Override
        public Matrix calcValue(Matrix x, Matrix params) {
            Matrix newX = convertToMatrixWithX0(x);
            Matrix yHat = AlgebraUtil.multiply(newX, params);
            return yHat;
        }

        private Matrix convertToMatrixWithX0(Matrix x) {
            Matrix newX = new Matrix(x.getRowNum(), x.getColNum() + 1);
            for (int i = 1; i < newX.getColNum(); i++) {
                for (int j = 0; j < newX.getRowNum(); j++) {
                    BigDecimal value = new BigDecimal(x.getValue(j, i - 1).doubleValue());
                    newX.setValue(j, i, value);
                }
            }
            for (int i = 0; i < newX.getRowNum(); i++) {
                newX.setValue(i, 0, 1);
            }
            return newX;
        }
    }

}
