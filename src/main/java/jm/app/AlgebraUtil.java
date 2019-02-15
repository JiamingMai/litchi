package jm.app;

import java.math.BigDecimal;

public class AlgebraUtil {

    public final static Matrix identityMatrix(int dimension) {
        Matrix identityMatrix = new Matrix(dimension, dimension);
        for (int i = 0; i < identityMatrix.getRowNum(); i++) {
            identityMatrix.setValue(i, i, 1.0);
        }
        return identityMatrix;
    }

    public final static Matrix add(Matrix a, Matrix b) {
        if (a == null || b == null) {
            return null;
        }
        if (a.getRowNum() != b.getRowNum() || a.getColNum() != b.getColNum()) {
            // TODO: throw exception here
            return null;
        }
        Matrix resultMat = new Matrix(a.getRowNum(), a.getColNum());
        for (int i = 0; i < resultMat.getRowNum(); i++) {
            for (int j = 0; j < resultMat.getColNum(); j++) {
                BigDecimal value = a.getValue(i, j).add(b.getValue(i, j));
                resultMat.setValue(i, j, value);
            }
        }
        return resultMat;
    }



}
