package jm.app;

import java.math.BigDecimal;

public class SteepestDescentOptimizer implements Optimizer {

    private int epochNum = 10000;

    private BigDecimal learningRate = new BigDecimal(0.0001);

    public SteepestDescentOptimizer() {
    }

    public SteepestDescentOptimizer(int epochNum, double learningRate) {
        this.epochNum = epochNum;
        this.learningRate = new BigDecimal(learningRate);
    }

    @Override
    public Matrix optimize(TargetFunction fun, Matrix params, Matrix args) {
        return null;
    }

    @Override
    public Matrix optimize(TargetFunction targetFunction, Matrix params, Matrix trainInput, Matrix truthOutput) {
        RmseFunction rmseFunction = new RmseFunction(targetFunction);
        Matrix args = AlgebraUtil.mergeMatrix(trainInput, truthOutput, 1);
        Matrix lastParams = AlgebraUtil.copy(params);
        Matrix newParams = AlgebraUtil.copy(params);
        for (int e = 0; e < epochNum; e++) {
            for (int i = 0; i < newParams.getColNum(); i++) {
                BigDecimal partialDerivative = calcPartialDerivative(rmseFunction, lastParams, i, args);
                BigDecimal param = newParams.getValue(0, i).subtract(learningRate.multiply(partialDerivative));
                newParams.setValue(0, i, param);
            }
            lastParams = AlgebraUtil.copy(newParams);
        }
        return newParams;
    }

    /**
     * Calculate the partial derivative with the target function, its corresponding parameters, and its arguments
     * @param targetFunction the target function that is guaranteed to be derivable
     * @param params the parameters that is represented by a matrix with only one row
     * @param partialVariableIndex the position index of the parameter that is to be calculated the partial derivative
     * @param args the arguments of the target function
     * @return the partial derivative
     */
    public BigDecimal calcPartialDerivative(TargetFunction targetFunction, Matrix params, int partialVariableIndex, Matrix args) {
        final BigDecimal epsilon = new BigDecimal(0.00000001);

        BigDecimal param = params.getValue(0, partialVariableIndex);
        BigDecimal leftOffset = param.subtract(epsilon);
        BigDecimal rightOffset = param.add(epsilon);
        Matrix leftOffsetParams = AlgebraUtil.copy(params);
        leftOffsetParams.setValue(0, partialVariableIndex, leftOffset);
        Matrix rightOffsetParams = AlgebraUtil.copy(params);
        rightOffsetParams.setValue(0, partialVariableIndex, rightOffset);

        BigDecimal fx = targetFunction.fun(params, args);
        BigDecimal leftDerivative = targetFunction.fun(leftOffsetParams, args).subtract(fx).multiply(new BigDecimal(1.0 / -epsilon.doubleValue()));
        BigDecimal rightDerivative = targetFunction.fun(leftOffsetParams, args).subtract(fx).multiply(new BigDecimal(1.0 / epsilon.doubleValue()));
        BigDecimal partialDerivative = leftDerivative.add(rightDerivative).multiply(new BigDecimal(0.5));
        return partialDerivative;
    }
}
