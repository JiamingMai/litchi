package jm.app;

public class AlgebraUtil {

    public final static Matrix identityMatrix(int dimension) {
        Matrix identityMatrix = new Matrix(dimension, dimension);
        for (int i = 0; i < identityMatrix.getRowNum(); i++) {
            identityMatrix.setValue(i, i, 1.0);
        }
        return identityMatrix;
    }



}
