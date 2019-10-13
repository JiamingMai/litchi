package answer.chapter10;

import jm.app.algebra.Matrix;
import jm.app.algorithm.HoltWintersModel;
import jm.app.optimize.GeneticAlgorithmOptimizer;
import jm.app.optimize.Optimizer;
import jm.app.optimize.SteepestDescentOptimizer;

import java.math.BigDecimal;

public class Problem10_2 {

    class ExtendedHoltWintersModel extends HoltWintersModel {
        @Override
        public Matrix optimize(Matrix y, int cycleLen, int epochNum) {
            Matrix boundaries = new Matrix(3, 2);
            for (int i = 0; i < boundaries.getRowNum(); i++) {
                boundaries.setValue(i, 0, 0.0);
                boundaries.setValue(i, 1, 1.0);
            }
            Optimizer optimizer = new SteepestDescentOptimizer(epochNum, 0.0001);
            // initialize parameters that are going to be optimized
            Matrix params = new Matrix(1, 3);
            for (int i = 0; i < params.getRowNum(); i++) {
                params.setValue(i, 0, 0.001);
            }
            params = optimizer.optimize(new HoltWintersModelRmseTargetFunction(cycleLen), params, y, y, false);
            return params;
        }
    }
}
