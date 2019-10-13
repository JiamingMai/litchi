package answer.chapter14;

import jm.app.algebra.Matrix;
import jm.app.algorithm.*;
import jm.app.optimize.BasicModel;
import jm.app.service.TrendLineEnum;
import jm.app.service.TrendLineService;

import java.util.List;

public class Problem14_2 {

    class ExtendedTrendLineService extends TrendLineService {

        private final int PREDICTION_LEN = 3;

        @Override
        public List<Double> estimateValue(List<Double> values, TrendLineEnum trendLineType) {
            BasicModel basicModel = null;
            Matrix[] input = wrapMatrices(values);
            Matrix trainX = input[0];
            Matrix trainY = input[1];
            switch(trendLineType) {
                case AUTO:
                    basicModel = selectBestModel(trainX, trainY);
                    break;
                case LINEAR:
                    basicModel = new LinearModel();
                    break;
                case LOGARITHM:
                    basicModel = new LogarithmModel();
                    break;
                case EXPONENTIAL:
                    basicModel = new ExponentialModel();
                    break;
                case POLYNOMIAL:
                    basicModel = new PolynomialModel();
                    break;
                case POWER:
                    basicModel = new PowerModel();
                    break;
                case MOVING_AVERAGE:
                    basicModel = new MovingAverageModel();
                    break;
                default:
                    // can't happen
                    return null;
            }
            Matrix params = basicModel.optimize(trainX, trainY);
            Matrix testX = new Matrix(values.size(), 1);
            for (int i = 0; i < testX.getRowNum() + PREDICTION_LEN; i++) {
                testX.setValue(i, 0, i + 1);
            }
            Matrix yHat = basicModel.calcValue(testX, params);
            List<Double> trendLineValues = unwrapMatrix(yHat);
            return trendLineValues;
        }
    }

}
