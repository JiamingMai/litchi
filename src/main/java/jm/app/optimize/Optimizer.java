package jm.app.optimize;

import jm.app.algebra.Matrix;

public interface Optimizer {

    /**
     * This method is used for common optimizing problems
     * @param fun the original target function
     * @param params the parameters to be optimized
     * @param args the arguments of the target function
     * @return
     */
    Matrix optimize(TargetFunction fun, Matrix params, Matrix args);

    /**
     * This method is used for supervised learning
     * @param fun the original target function
     * @param params the parameters to be optimized
     * @param trainInput the input training samples
     * @param truthOutput the labels of the training samples
     * @param toWrapRmseFunction if this parameter is true, then the memthod should wrap the target function with RMSE loss function
     * @return
     */
    Matrix optimize(TargetFunction fun, Matrix params, Matrix trainInput, Matrix truthOutput, boolean toWrapRmseFunction);

}
