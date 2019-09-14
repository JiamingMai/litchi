package jm.app.service;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;
import jm.app.algorithm.*;
import jm.app.optimize.BasicModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TrendLineService {

    public List<Double> estimateValue(List<Double> values, TrendLineEnum trendLineType) {
        BasicModel basicModel = null;
        Matrix[] input = wrapMatrices(values);
        Matrix x = input[0];
        Matrix y = input[1];
        switch(trendLineType) {
            case AUTO:
                basicModel = selectBestModel(x, y);
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
            default:
                // can't happen
                return null;
        }
        Matrix params = basicModel.optimize(x, y);
        Matrix yHat = basicModel.calcValue(x, params);
        List<Double> trendLineValues = unwrapMatrix(yHat);
        return trendLineValues;
    }

    private BasicModel selectBestModel(Matrix x, Matrix y) {
        List<BasicModel> models = new ArrayList<>();
        models.add(new LinearModel());
        models.add(new LogarithmModel());
        models.add(new ExponentialModel());
        models.add(new PolynomialModel());
        models.add(new PowerModel());
        int bestModelIndex = -1;
        double minRmse = Double.MAX_VALUE;
        for (int i = 0; i < models.size(); i++) {
            BasicModel model = models.get(i);
            double rmse = calcRmse(model, x, y).doubleValue();
            System.out.println(String.format("Model: %s, RMSE: %f", model, rmse));
            if (rmse < minRmse) {
                minRmse = rmse;
                bestModelIndex = i;
            }
        }
        BasicModel bestModel = models.get(bestModelIndex);
        System.out.println("Best Model: " + bestModel);
        return bestModel;
    }

    private BigDecimal calcRmse(BasicModel basicModel, Matrix x, Matrix y) {
        Matrix params = basicModel.optimize(x, y);
        Matrix yHat = basicModel.calcValue(x, params);
        BigDecimal rmse = AlgebraUtil.multiply(AlgebraUtil.transpose(AlgebraUtil.subtract(yHat, y)), AlgebraUtil.subtract(yHat, y)).getValue(0, 0);
        rmse = new BigDecimal(Math.sqrt(rmse.doubleValue() / y.getRowNum()));
        return rmse;
    }

    private Matrix[] wrapMatrices(List<Double> values) {
        Matrix x = new Matrix(values.size(), 1);
        for (int i = 0; i < x.getRowNum(); i++) {
            x.setValue(i, 0, i + 1);
        }
        Matrix y = new Matrix(values.size(), 1);
        for (int i = 0; i < y.getRowNum(); i++) {
            y.setValue(i, 0, values.get(i));
        }
        Matrix[] inputXAndY = new Matrix[2];
        inputXAndY[0] = x;
        inputXAndY[1] = y;
        return inputXAndY;
    }

    private List<Double> unwrapMatrix(Matrix yHat) {
        List<Double> trendLineValues = new ArrayList<>();
        for (int i = 0; i < yHat.getRowNum(); i++) {
            trendLineValues.add(yHat.getValue(i, 0).doubleValue());
        }
        return trendLineValues;
    }

}
