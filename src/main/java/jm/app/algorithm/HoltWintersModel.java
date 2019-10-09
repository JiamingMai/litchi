package jm.app.algorithm;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.optimize.GeneticAlgorithmOptimizer;
import jm.app.optimize.Optimizer;
import jm.app.optimize.TargetFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HoltWintersModel {

    private final int DEFAULT_EPOCH_NUM = 20;

    public Matrix optimize(Matrix y, int cycleLen) {
        return optimize(y, cycleLen, DEFAULT_EPOCH_NUM);
    }

    public Matrix optimize(Matrix y, int cycleLen, int epochNum) {
        Matrix boundaries = new Matrix(3, 2);
        for (int i = 0; i < boundaries.getRowNum(); i++) {
            boundaries.setValue(i, 0, 0.0);
            boundaries.setValue(i, 1, 1.0);
        }
        Optimizer optimizer = new GeneticAlgorithmOptimizer(boundaries, epochNum);
        // initialize parameters that are going to be optimized
        Matrix params = new Matrix(1, 3);
        for (int i = 0; i < params.getRowNum(); i++) {
            params.setValue(i, 0, 0.001);
        }
        params = optimizer.optimize(new HoltWintersModelRmseTargetFunction(cycleLen), params, y, y, false);
        return params;
    }

    public Matrix fittedValue(Matrix y, Matrix params, int cycleLen, int predictionLen) {
        List<BigDecimal> yHat = estimateYHat(y, params, cycleLen, predictionLen);
        // wrap matrix
        yHat.remove(0);
        Matrix yHatMat = new Matrix(yHat.size(), 1);
        for (int i = 0; i < yHatMat.getRowNum(); i++) {
            yHatMat.setValue(i, 0, yHat.get(i));
        }
        return yHatMat;
    }

    private List<BigDecimal> estimateYHat(Matrix y, Matrix params, int cycleLen, int predictionLen) {
        BigDecimal alpha = params.getValue(0, 0);
        BigDecimal beta = params.getValue(0, 1);
        BigDecimal gama = params.getValue(0, 2);

        List<BigDecimal> s = new ArrayList<>();
        List<BigDecimal> b = new ArrayList<>();
        List<BigDecimal> c = new ArrayList<>();
        List<BigDecimal> yHat = new ArrayList<>();
        // Initialize s0
        BigDecimal sum = new BigDecimal(0.0);
        for (int i = 0; i < cycleLen; i++) {
            sum = sum.add(y.getValue(i, 0));
        }
        BigDecimal s0 = sum.multiply(new BigDecimal(1.0 / cycleLen));
        s.add(s0);
        // Initialize b0
        sum = new BigDecimal(0.0);
        for (int i = cycleLen; i < 2 * cycleLen; i++) {
            sum = sum.add(y.getValue(i, 0).subtract(y.getValue(i - cycleLen, 0)));
        }
        BigDecimal b0 = sum.multiply(new BigDecimal(1.0 / (cycleLen * cycleLen)));
        b.add(b0);
        // Initialize c
        for (int i = 0; i < cycleLen; i++) {
            BigDecimal ci = y.getValue(i, 0).subtract(s.get(0));
            c.add(ci);
        }
        // Initialize yHat
        BigDecimal y0 = s.get(0).add(b.get(0)).add(c.get(0));
        yHat.add(y0);
        // Estimate yHat
        for (int i = 0; i < y.getRowNum(); i++) {
            // estimate si
            BigDecimal sLeftTerm = alpha.multiply(y.getValue(i, 0).subtract(c.get(i)));
            BigDecimal sRightTerm = new BigDecimal(1.0 - alpha.doubleValue()).multiply(s.get(i).add(b.get(i)));
            s.add(sLeftTerm.add(sRightTerm));
            // estimate bi
            BigDecimal bLeftTerm = beta.multiply(s.get(i + 1).subtract(s.get(i)));
            BigDecimal bRightTerm = new BigDecimal(1.0 - beta.doubleValue()).multiply(b.get(i));
            b.add(bLeftTerm.add(bRightTerm));
            // estimate ci
            BigDecimal cLeftTerm = gama.multiply(y.getValue(i, 0).subtract(s.get(i)).subtract(b.get(i)));
            BigDecimal cRightTerm = new BigDecimal(1.0 - gama.doubleValue()).multiply(c.get(i));
            c.add(cLeftTerm.add(cRightTerm));
            // estimate yHat
            yHat.add(s.get(i + 1).add(b.get(i + 1)).add(c.get(i + 1)));
            // predict here
            if (i == y.getRowNum() - 1) {
                for (int j = 2; j <= predictionLen + 1; j++) {
                    BigDecimal lastTerm = new BigDecimal(j % predictionLen);
                    yHat.add(s.get(i + 1).add(b.get(i + 1).multiply(new BigDecimal(j))).add(c.get(i + 1)).add(lastTerm));
                }
            }
        }
        return yHat;
    }

    public class HoltWintersModelRmseTargetFunction implements TargetFunction {

        private int cycleLen;

        public HoltWintersModelRmseTargetFunction(int cycleLen) {
            this.cycleLen = cycleLen;
        }

        @Override
        public BigDecimal fun(Matrix params, Matrix args) {
            Matrix truthOutput = AlgebraUtil.getColumnVector(args, args.getColNum() - 1);
            return calcRmse(truthOutput, params);
        }

        private BigDecimal calcRmse(Matrix y, Matrix params) {
            List<BigDecimal> yHat = estimateYHat(y, params, cycleLen, 0);
            // Calculate RMSE
            BigDecimal sum = new BigDecimal(0.0);
            for (int i = 0; i < y.getRowNum(); i++) {
                sum = sum.add(y.getValue(i, 0).subtract(yHat.get(i)).pow(2));
            }
            BigDecimal rmse = new BigDecimal(Math.sqrt(sum.multiply(new BigDecimal(1.0 / y.getRowNum())).doubleValue()));
            return rmse;
        }

    }

}
