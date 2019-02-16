package jm.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgebraUtil {

    public final static Matrix identityMatrix(int dimension) {
        Matrix identityMatrix = new Matrix(dimension, dimension);
        for (int i = 0; i < identityMatrix.getRowNum(); i++) {
            identityMatrix.setValue(i, i, 1.0);
        }
        return identityMatrix;
    }

    public final static Matrix multiply(Matrix a, Matrix b) {
        if (a == null || b == null) {
            return null;
        }
        if (a.getColNum() != b.getRowNum()) {
            // TODO: throw an exception here
            return null;
        }
        Matrix resultMat = new Matrix(a.getRowNum(), b.getColNum());
        for (int i = 0; i < resultMat.getRowNum(); i++) {
            for (int j = 0; j < resultMat.getColNum(); j++) {
                BigDecimal value = new BigDecimal(0.0);
                for (int c = 0; c < a.getColNum(); c++) {
                    value = value.add(a.getValue(i, c).multiply(b.getValue(c, j)));
                }
                resultMat.setValue(i, j, value);
            }
        }
        return resultMat;
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

    public final static Matrix subtract(Matrix a, Matrix b) {
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
                BigDecimal value = a.getValue(i, j).subtract(b.getValue(i, j));
                resultMat.setValue(i, j, value);
            }
        }
        return resultMat;
    }

    public final static BigDecimal determinant(Matrix mat) {
        if (mat.getColNum() != mat.getRowNum()) {
            // TODO: throw an exception here
            return null;
        }
        if (mat.getRowNum() > 10) {
            // TODO: throw an exception here
            return null;
        }
        if (mat.getRowNum() == 1) {
            return new BigDecimal(mat.getValue(0, 0).doubleValue());
        }
        int n = mat.getRowNum();
        List<Integer[]> permutations = calcFullPermutation(n);
        BigDecimal det = new BigDecimal(0.0);
        for (Integer[] permutation : permutations) {
            int t = calcInversionNumber(permutation);
            BigDecimal temp = new BigDecimal(Math.pow(-1, t));
            for (int i = 0, p = 0; p < permutation.length; p++, i++) {
                int j = permutation[p] - 1;
                temp = temp.multiply(mat.getValue(i, j));
            }
            det = det.add(temp);
        }
        return det;
    }

    private final static List<Integer[]> calcFullPermutation(int n) {
        List<Integer[]> permutations = new ArrayList<>();
        Integer[] initPermutation = new Integer[n];
        for (int i = 1; i <= n; i++) {
            initPermutation[i - 1] = i;
        }
        calcFullPermutation(permutations, initPermutation, 0);
        List<Integer[]> newPermutations = new ArrayList<>();
        for (Integer[] permutation : permutations) {
            if (permutation != null) {
                newPermutations.add(permutation);
            }
        }
        return newPermutations;
    }

    private final static Integer[] calcFullPermutation(
            List<Integer[]> permutations,
            Integer[] permutation,
            int startIndex) {
        if (startIndex >= permutation.length - 1) {
            Integer[] newPermutation = new Integer[permutation.length];
            for (int i = 0; i < permutation.length; i++) {
                newPermutation[i] = permutation[i];
            }
            return newPermutation;
        }

        for (int i = startIndex; i < permutation.length; i++) {
            int tempValue = permutation[startIndex];
            permutation[startIndex] = permutation[i];
            permutation[i] = tempValue;

            permutations.add(calcFullPermutation(permutations, permutation, startIndex));

            tempValue = permutation[startIndex];
            permutation[startIndex] = permutation[i];
            permutation[i] = tempValue;
        }
        return null;
    }

    private final static int calcInversionNumber(Integer[] permutation) {
        int sum = 0;
        for (int i = 0; i < permutation.length; i++) {
            int invValueOfIth = 0;
            for (int j = 0; j < i; j++) {
                if (permutation[j] > permutation[i]) {
                    invValueOfIth++;
                }
            }
            sum += invValueOfIth;
        }
        return sum;
    }

    public final static Matrix createRandomMatrix(int rowNum, int colNum) {
        Matrix mat = new Matrix(rowNum, colNum);
        Random random = new Random();
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                mat.setValue(i, j, random.nextDouble());
            }
        }
        return mat;
    }

    public final static Matrix transpose(Matrix mat) {
        if (mat == null) {
            return null;
        }
        Matrix transposedMat = new Matrix(mat.getColNum(), mat.getRowNum());
        for (int i = 0; i < mat.getRowNum(); i++) {
            for (int j = 0; j < mat.getColNum(); j++) {
                BigDecimal value = mat.getValue(i, j);
                transposedMat.setValue(j, i, value);
            }
        }
        return transposedMat;
    }

    public final static Matrix inverse(Matrix mat) {
        if (mat == null || mat.getRowNum() != mat.getColNum()) {
            return null;
        }
        if (mat.getRowNum() == 1) {
            Matrix invMat = new Matrix(1, 1);
            BigDecimal value = new BigDecimal(1.0 / mat.getValue(0, 0).doubleValue());
            invMat.setValue(0, 0, value);
            return invMat;
        }

        BigDecimal det = determinant(mat);
        if (det.doubleValue() == 0.0) {
            // TODO: throw an exception here
            return null;
        }
        Matrix ajointMatrix = adjointMatrix(mat);
        BigDecimal reciprocalDet = new BigDecimal(1.0 / det.doubleValue());
        Matrix invMat = dot(ajointMatrix, reciprocalDet);
        return invMat;
    }

    public final static Matrix dot(Matrix mat, BigDecimal element) {
        if (mat == null) {
            return null;
        }
        if (element == null) {
            return mat;
        }
        Matrix newMat = new Matrix(mat.getRowNum(), mat.getColNum());
        for (int i = 0; i < mat.getRowNum(); i++) {
            for (int j = 0; j < mat.getColNum(); j++) {
                BigDecimal value = mat.getValue(i, j).multiply(element);
                newMat.setValue(i, j, value);
            }
        }
        return newMat;
    }

    public final static Matrix adjointMatrix(Matrix mat) {
        if (mat == null) {
            return null;
        }
        Matrix adjointMatrix = new Matrix(mat.getRowNum(), mat.getRowNum());
        for (int i = 0; i < adjointMatrix.getRowNum(); i++) {
            for (int j = 0; j < adjointMatrix.getColNum(); j++) {
                BigDecimal value = algebraicCofactor(mat, j + 1, i + 1);
                adjointMatrix.setValue(i, j, value);
            }
        }
        return adjointMatrix;
    }

    public final static BigDecimal algebraicCofactor(Matrix mat, int row, int col) {
        if (mat == null || mat.getRowNum() != mat.getColNum() || mat.getRowNum() <= 1) {
            return null;
        }
        Matrix newMat = new Matrix(mat.getRowNum() - 1, mat.getColNum() - 1);
        int rowFlag = 0;
        int colFlag = 0; // check this line, is it necessary?
        for (int i = 0; i < mat.getRowNum(); i++) {
            colFlag = 0;
            for (int j = 0; j < mat.getColNum(); j++) {
                if (i == row - 1) {
                    rowFlag++;
                    break;
                }
                if (j == col - 1) {
                    colFlag++;
                    break;
                }
                BigDecimal value = mat.getValue(i, j);
                newMat.setValue(i - rowFlag, j - colFlag, value);
            }
        }
        BigDecimal cofactor = determinant(newMat);
        cofactor = new BigDecimal(Math.pow(-1, (row + col) * cofactor.doubleValue()));
        return cofactor;
    }

    public final static Matrix covariance(Matrix mat) {
        // TODO: implement covariance method here
        if (mat == null) {
            // TODO: throw an exception here
            return null;
        }
        int n = mat.getColNum();
        Matrix[] cols = new Matrix[n];
        for (int i = 0; i < n; i++) {
            cols[i] = new Matrix(mat.getRowNum(), 1);
            for (int j = 0; j < mat.getRowNum(); j++) {
                cols[i].setValue(j, 0, mat.getValue(j, i));
            }
        }

        Matrix covMatrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                BigDecimal value = covariance(cols[i], cols[j]);
                covMatrix.setValue(i, j, value);
            }
        }
        return covMatrix;
    }

    public final static BigDecimal covariance(Matrix x, Matrix y) {
        if (x == null || y == null || x.getRowNum() != y.getRowNum()) {
            // TODO: throw an exception here
            return null;
        }
        // Step 1. calculate the means of X and Y
        int n = x.getRowNum();
        BigDecimal xMean = new BigDecimal(0.0);
        for (int i = 0; i < n; i++) {
            xMean = xMean.add(x.getValue(i, 0));
        }
        xMean = xMean.multiply(new BigDecimal(1.0 / n));

        BigDecimal yMean = new BigDecimal(0.0);
        for (int i = 0; i < n; i++) {
            yMean = yMean.add(y.getValue(i, 0));
        }
        yMean = yMean.multiply(new BigDecimal(1.0 / n));

        // Step 2. calculate the covariance matrix
        BigDecimal sum = new BigDecimal(0.0);
        for (int i = 0; i < n; i++) {
            sum = sum.add(x.getValue(i, 0).subtract(xMean).multiply(y.getValue(i, 0).subtract(yMean)));
        }
        BigDecimal value = sum.multiply(new BigDecimal(1.0 / (n - 1)));
        return value;
    }

    public final static Matrix mean(Matrix x, int direction) {
        if (x == null) {
            return null;
        }
        if (direction == 0) {
            Matrix newMat = new Matrix(1, x.getColNum());
            for (int c = 0; c < x.getColNum(); c++) {
                BigDecimal mean = new BigDecimal(0.0);
                for (int i = 0; i < x.getRowNum(); i++) {
                    mean = mean.add(x.getValue(i, c));
                }
                mean = mean.multiply(new BigDecimal(1.0 / x.getRowNum()));
                newMat.setValue(0, c, mean);
            }
            return newMat;
        } else if (direction == 1) {
            Matrix newMat = new Matrix(x.getRowNum(), 1);
            for (int r = 0; r < x.getRowNum(); r++) {
                BigDecimal mean = new BigDecimal(0.0);
                for (int i = 0; i < x.getColNum(); i++) {
                    mean = mean.add(x.getValue(r, i));
                }
                mean = mean.multiply(new BigDecimal(1.0 / x.getColNum()));
                newMat.setValue(r, 0, mean);
            }
            return newMat;
        } else {
            return null;
        }
    }
}
