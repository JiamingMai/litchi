package jm.app.optimize;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;

public class RmseFunction implements TargetFunction {

    private TargetFunction originalFunction;

    public RmseFunction(TargetFunction originalFunction) {
        this.originalFunction = originalFunction;
    }

    /**
     * Calculate the RMSE of the original function with training samples
     * @param params parameters of the original function
     * @param args arguments include the training input and the truth output, the last column is the truth output
     * @return RMSE of the original function
     */
    @Override
    public BigDecimal fun(Matrix params, Matrix args) {
        Matrix truthOutput = AlgebraUtil.getColumnVector(args, args.getColNum() - 1);
        Matrix trainInput = AlgebraUtil.getSubMatrix(
                args,
                0,
                args.getRowNum() - 1,
                0,
                args.getColNum() - 1 - 1);
        BigDecimal rmse = new BigDecimal(0.0);
        for (int i = 0; i < trainInput.getRowNum(); i++) {
            Matrix input = AlgebraUtil.getRowVector(trainInput, i);
            BigDecimal res = originalFunction.fun(params, input);
            BigDecimal truth = truthOutput.getValue(i, 0);
            rmse = rmse.add(res.subtract(truth).multiply(res.subtract(truth)));
        }
        rmse = rmse.multiply(new BigDecimal(1.0 / trainInput.getRowNum()));
        rmse = new BigDecimal(Math.sqrt(rmse.doubleValue()));
        return rmse;
    }

    public TargetFunction getOriginalFunction() {
        return originalFunction;
    }

    public void setOriginalFunction(TargetFunction originalFunction) {
        this.originalFunction = originalFunction;
    }
}
