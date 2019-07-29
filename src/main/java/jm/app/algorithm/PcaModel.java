package jm.app.algorithm;

import jm.app.algebra.Matrix;

import static jm.app.algebra.AdvancedAlgebraUtil.*;

public class PcaModel {

    // the mean vector u
    private Matrix u;

    // the scattering matrix S
    private Matrix s;

    // the mapping matrix A
    private Matrix a;

    public void train(Matrix[] x, int dimension) {
        int n = x.length;
        int m = x[0].getRowNum();
        // Step 1. Calculate the mean vector
        Matrix input = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                input.setValue(i, j, x[i].getValue(j, 0));
            }
        }
        u = transpose(mean(input, 0));

        // Step 2. Calculate the scattering matrix
        s = new Matrix(m, m);
        for (int i = 0; i < n; i++) {
            Matrix temp = multiply(subtract(x[i], u), transpose(subtract(x[i], u)));
            s = add(s, temp);
        }

        // Step 3. Calculate the mapping matrix A
        Matrix[] eigenvaluesAndEigenVectors = eigen(s);
        Matrix eigenValues = eigenvaluesAndEigenVectors[0];
        Matrix eigenVectors = eigenvaluesAndEigenVectors[1];
        a = new Matrix(m, dimension);
        for (int i = 0; i < dimension; i++) {
            Matrix eigenVector = getColumnVector(eigenVectors, i);
            Matrix unitizedEigenVector = unitize(eigenVector);
            a = setColumnVector(a, i, unitizedEigenVector);
        }
    }

    public Matrix encode(Matrix x) {
        Matrix z = multiply(transpose(a), x);
        return z;
    }

}
