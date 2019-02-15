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

}
