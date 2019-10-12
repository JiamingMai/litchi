package answer.chapter12;

import jm.app.algebra.Matrix;
import jm.app.algorithm.HoltWintersModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Problem4 {

    class ImprovedHoltWinterModel extends HoltWintersModel {

        private final int DEFAULT_WINDOWS_SIZE = 20;

        protected List<BigDecimal> estimateYHat(Matrix inputY, Matrix params, int cycleLen, int predictionLen) {
            // pruning
            Matrix y;
            if (inputY.getRowNum() > DEFAULT_WINDOWS_SIZE) {
                y = new Matrix(DEFAULT_WINDOWS_SIZE, 1);
                for (int i = inputY.getRowNum() - DEFAULT_WINDOWS_SIZE, j = 0; i < inputY.getRowNum(); i++, j++) {
                    y.setValue(j, 0, inputY.getValue(i, 0));
                }
            } else {
                y = inputY;
            }

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

    }

}
