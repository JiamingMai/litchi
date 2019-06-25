package jm.app.algebra;

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

    public final static Matrix multiply(Matrix a, BigDecimal b) {
        if (a == null || b == null) {
            return null;
        }
        Matrix resultMat = new Matrix(a.getRowNum(), a.getColNum());
        for (int i = 0; i < resultMat.getRowNum(); i++) {
            for (int j = 0; j < resultMat.getColNum(); j++) {
                BigDecimal value = a.getValue(i, j).multiply(b);
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

            permutations.add(calcFullPermutation(permutations, permutation, startIndex + 1));

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

    public final static Matrix dot(Matrix matA, Matrix matB) {
        if (matA == null || matB == null ||
                matA.getRowNum() != matB.getRowNum() || matA.getColNum() != matB.getColNum()) {
            return null;
        }
        Matrix newMat = new Matrix(matA.getRowNum(), matA.getColNum());
        for (int i = 0; i < matA.getRowNum(); i++) {
            for (int j = 0; j < matA.getColNum(); j++) {
                BigDecimal value = matA.getValue(i, j).multiply(matB.getValue(i, j));
                newMat.setValue(i, j, value);
            }
        }
        return newMat;
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
                    continue;
                }
                BigDecimal value = mat.getValue(i, j);
                newMat.setValue(i - rowFlag, j - colFlag, value);
            }
        }
        BigDecimal cofactor = determinant(newMat);
        cofactor = new BigDecimal(Math.pow(-1, (row + col)) * cofactor.doubleValue());
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

    public final static Matrix getRow(Matrix x, int row) {
        Matrix rowVector = new Matrix(1, x.getColNum());
        for (int i = 0; i < x.getColNum(); i++) {
            BigDecimal value = x.getValue(row, i);
            rowVector.setValue(0, i, value);
        }
        return rowVector;
    }

    public final static Matrix max(Matrix x, int direction) {
        if (direction == 0) {
            Matrix maxMat = new Matrix(1, x.getColNum());
            for (int c = 0; c < x.getColNum(); c++) {
                BigDecimal maxValue = x.getValue(0, c);
                for (int r = 0; r < x.getRowNum(); r++) {
                    BigDecimal value = x.getValue(r, c);
                    if (value.compareTo(maxValue) > 0) {
                        maxValue = value;
                    }
                }
                maxMat.setValue(0, c, maxValue);
            }
            return maxMat;
        } else if (direction == 1) {
            Matrix maxMat = new Matrix(x.getRowNum(), 1);
            for (int r = 0; r < x.getRowNum(); r++) {
                BigDecimal maxValue = x.getValue(r, 0);
                for (int c = 0; c < x.getColNum(); c++) {
                    BigDecimal value = x.getValue(r, c);
                    if (value.compareTo(maxValue) > 0) {
                        maxValue = value;
                    }
                }
                maxMat.setValue(r, 0, maxValue);
            }
            return maxMat;
        } else {
            return null;
        }
    }

    public final static Matrix min(Matrix x, int direction) {
        if (direction == 0) {
            Matrix minMat = new Matrix(1, x.getColNum());
            for (int c = 0; c < x.getColNum(); c++) {
                BigDecimal minValue = x.getValue(0, c);
                for (int r = 0; r < x.getRowNum(); r++) {
                    BigDecimal value = x.getValue(r, c);
                    if (value.compareTo(minValue) < 0) {
                        minValue = value;
                    }
                }
                minMat.setValue(0, c, minValue);
            }
            return minMat;
        } else if (direction == 1) {
            Matrix minMat = new Matrix(x.getRowNum(), 1);
            for (int r = 0; r < x.getColNum(); r++) {
                BigDecimal minValue = x.getValue(r, 0);
                for (int c = 0; c < x.getColNum(); c++) {
                    BigDecimal value = x.getValue(r, c);
                    if (value.compareTo(minValue) < 0) {
                        minValue = value;
                    }
                }
                minMat.setValue(r, 0, minValue);
            }
            return minMat;
        } else {
            return null;
        }
    }

    public final static Matrix copy(Matrix x) {
        Matrix copiedMat = new Matrix(x.getRowNum(), x.getColNum());
        for (int i = 0; i < x.getRowNum(); i++) {
            for (int j = 0; j < x.getColNum(); j++) {
                BigDecimal value = x.getValue(i, j);
                copiedMat.setValue(i, j, value);
            }
        }
        return copiedMat;
    }

    public final static Matrix setColumnVector(Matrix x, int column, Matrix columnVector) {
        Matrix newMat = copy(x);
        for (int r = 0; r < x.getRowNum(); r++) {
            BigDecimal value = columnVector.getValue(r, 0);
            newMat.setValue(r, column, value);
        }
        return newMat;
    }

    public final static Matrix getColumnVector(Matrix x, int column) {
        Matrix columnVector = new Matrix(x.getRowNum(), 1);
        for (int r = 0; r < x.getRowNum(); r++) {
            BigDecimal value = x.getValue(r, column);
            columnVector.setValue(r, 0, value);
        }
        return columnVector;
    }

    public final static Matrix setRowVector(Matrix x, int row, Matrix rowVector) {
        Matrix newMat = copy(x);
        for (int c = 0; c < x.getColNum(); c++) {
            BigDecimal value = rowVector.getValue(0, c);
            newMat.setValue(row, c, value);
        }
        return newMat;
    }

    public final static Matrix getRowVector(Matrix x, int row) {
        Matrix rowVector = new Matrix(1, x.getColNum());
        for (int c = 0; c < x.getColNum(); c++) {
            BigDecimal value = x.getValue(row, c);
            rowVector.setValue(0, c, value);
        }
        return rowVector;
    }

    public final static Matrix normalize(Matrix x, int direction) {
        if (direction == 0) {
            Matrix maxMat = AlgebraUtil.max(x, 0);
            Matrix minMat = AlgebraUtil.min(x, 0);
            Matrix normalizedMat = new Matrix(x.getRowNum(), x.getColNum());
            for (int c = 0; c < x.getColNum(); c++) {
                Matrix columnVector = getColumnVector(normalizeColumn(x, c, maxMat.getValue(0, c), minMat.getValue(0, c)), c);
                normalizedMat = setColumnVector(normalizedMat, c, columnVector);
            }
            return normalizedMat;
        } else if (direction == 1) {
            Matrix maxMat = AlgebraUtil.max(x, 1);
            Matrix minMat = AlgebraUtil.min(x, 1);
            Matrix normalizedMat = new Matrix(x.getRowNum(), x.getColNum());
            for (int r = 0; r < x.getRowNum(); r++) {
                Matrix rowVector = getRowVector(normalizeRow(x, r, maxMat.getValue(r, 0), minMat.getValue(r, 0)), r);
                normalizedMat = setRowVector(normalizedMat, r, rowVector);
            }
            return normalizedMat;
        } else {
            return null;
        }
    }

    private final static Matrix normalizeRow(Matrix x, int row, BigDecimal maxValue, BigDecimal minValue) {
        Matrix newMat = new Matrix(x.getRowNum(), x.getColNum());
        BigDecimal width = maxValue.subtract(minValue);
        for (int c = 0; c < x.getColNum(); c++) {
            BigDecimal newValue = x.getValue(row, c).subtract(minValue).multiply(new BigDecimal(1.0 / width.doubleValue()));
            newMat.setValue(row, c, newValue);
        }
        return newMat;
    }

    private final static Matrix normalizeColumn(Matrix x, int column, BigDecimal maxValue, BigDecimal minValue) {
        Matrix newMat = new Matrix(x.getRowNum(), x.getColNum());
        BigDecimal width = maxValue.subtract(minValue);
        for (int r = 0; r < x.getRowNum(); r++) {
            BigDecimal newValue = x.getValue(r, column).subtract(minValue).multiply(new BigDecimal(1.0 / width.doubleValue()));
            newMat.setValue(r, column, newValue);
        }
        return newMat;
    }

    public final static BigDecimal calcEuclideanDistance(Matrix mat1, Matrix mat2) {
        BigDecimal sum = new BigDecimal(0.0);
        for (int c = 0; c < mat1.getColNum(); c++) {
            BigDecimal x = mat1.getValue(0, c);
            BigDecimal y = mat2.getValue(0, c);
            sum = sum.add(x.subtract(y).multiply(x.subtract(y)));
        }
        BigDecimal distance = new BigDecimal(Math.sqrt(sum.doubleValue()));
        return distance;
    }

    public final static Matrix getSubMatrix(Matrix mat, int rowStart, int rowEnd, int columnStart, int columnEnd) {
        Matrix subMat = new Matrix(rowEnd - rowStart + 1, columnEnd - columnStart + 1);
        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = columnStart; c <= columnEnd; c++) {
                BigDecimal value = mat.getValue(r, c);
                subMat.setValue(r - rowStart, c - columnStart, value);
            }
        }
        return subMat;
    }

    public final static Matrix mergeMatrix(Matrix mat1, Matrix mat2, int direction) {
        // TODO: do validation for the row number and the column number of mat1 and mat2 here
        if (direction == 0) {
            Matrix mergedMat = new Matrix(mat1.getRowNum() + mat2.getRowNum(), mat1.getColNum());
            // copy data from mat1
            for (int r = 0; r < mat1.getRowNum(); r++) {
                for (int c = 0; c < mat1.getColNum(); c++) {
                    BigDecimal value = mat1.getValue(r, c);
                    mergedMat.setValue(r, c, value);
                }
            }

            // copy data from mat2
            for (int r = 0; r < mat2.getRowNum(); r++) {
                for (int c = 0; c < mat2.getColNum(); c++) {
                    BigDecimal value = mat2.getValue(r, c);
                    mergedMat.setValue(r + mat1.getRowNum(), c, value);
                }
            }
            return mergedMat;
        } else if (direction == 1) {
            Matrix mergedMat = new Matrix(mat1.getRowNum(), mat1.getColNum() + mat2.getColNum());
            // copy data from mat1
            for (int r = 0; r < mat1.getRowNum(); r++) {
                for (int c = 0; c < mat1.getColNum(); c++) {
                    BigDecimal value = mat1.getValue(r, c);
                    mergedMat.setValue(r, c, value);
                }
            }

            // copy data from mat2
            for (int r = 0; r < mat2.getRowNum(); r++) {
                for (int c = 0; c < mat2.getColNum(); c++) {
                    BigDecimal value = mat2.getValue(r, c);
                    mergedMat.setValue(r, c + mat1.getColNum(), value);
                }
            }
            return mergedMat;
        } else {
            return null;
        }
    }

    public final static Matrix unitize(Matrix x) {
        if (x.getColNum() == 1) {
            Matrix unitizedVector = new Matrix(x.getRowNum(), x.getColNum());
            int elementNum = x.getRowNum();
            BigDecimal denominator = l2Norm(x);
            for (int i = 0; i < elementNum; i++) {
                BigDecimal value = x.getValue(i, 0);
                BigDecimal unitizedValue = value.multiply(new BigDecimal(1.0 / denominator.doubleValue()));
                unitizedVector.setValue(i, 0, unitizedValue);
            }
            return unitizedVector;
        } else if (x.getRowNum() == 1) {
            Matrix unitizedVector = new Matrix(x.getRowNum(), x.getColNum());
            int elementNum = x.getRowNum();
            BigDecimal denominator = l2Norm(x);
            for (int i = 0; i < elementNum; i++) {
                BigDecimal value = x.getValue(0, i);
                BigDecimal unitzedValue = value.multiply(new BigDecimal(1.0 / denominator.doubleValue()));
                unitizedVector.setValue(i, 0, unitzedValue);
            }
            return unitizedVector;
        } else {
            return null;
        }
    }

    public final static BigDecimal l2Norm(Matrix x) {
        if (x.getColNum() == 1) {
            int elementNum = x.getRowNum();
            BigDecimal sum = new BigDecimal(0.0);
            for (int i = 0; i < elementNum; i++) {
                BigDecimal value = x.getValue(i, 0);
                sum = sum.add(value.multiply(value));
            }
            BigDecimal l2Value = new BigDecimal(Math.sqrt(sum.doubleValue()));
            return l2Value;
        } else if (x.getRowNum() == 1) {
            int elementNum = x.getColNum();
            BigDecimal sum = new BigDecimal(0.0);
            for (int i = 0; i < elementNum; i++) {
                BigDecimal value = x.getValue(0, i);
                sum = sum.add(value.multiply(value));
            }
            BigDecimal l2Value = new BigDecimal(Math.sqrt(sum.doubleValue()));
            return l2Value;
        } else {
            return null;
        }
    }

    public final static BigDecimal inner(Matrix a, Matrix b) {
        if (a.getColNum() == 1 && b.getColNum() == 1) {
            return multiply(transpose(a), b).getValue(0, 0);
        } else if (a.getColNum() == 1 && b.getRowNum() == 1) {
            return multiply(transpose(a), transpose(b)).getValue(0, 0);
        } else if (a.getRowNum() == 1 && b.getColNum() == 1) {
            return multiply(a, b).getValue(0, 0);
        } else if (a.getRowNum() == 1 && b.getRowNum() == 1) {
            return multiply(a, transpose(b)).getValue(0, 0);
        } else {
            return null;
        }
    }

    public final static Matrix orthogonalize(Matrix a) {
        int dimension = a.getRowNum();
        int vectorNum = a.getColNum();
        Matrix b = new Matrix(dimension, vectorNum);
        for (int i = 0; i < vectorNum; i++) {
            Matrix ai = getColumnVector(a, i);
            Matrix bi = copy(ai);
            for (int j = 0; j < i; j++) {
                Matrix bj = getColumnVector(b, j);
                BigDecimal coefficient = inner(bj, ai).multiply(new BigDecimal(1.0 / inner(bj, bj).doubleValue()));
                bi = subtract(bi, dot(bj, coefficient));
            }
            b = setColumnVector(b, i, bi);
        }

        // unitization
        for (int i = 0; i < vectorNum; i++) {
            b = setColumnVector(b, i, unitize(getColumnVector(b, i)));
        }
        return b;
    }

    public final static Matrix[] qrFactorize(Matrix a) {
        Matrix q = orthogonalize(a);
        Matrix r = multiply(transpose(q), a);
        Matrix[] qr = new Matrix[2];
        qr[0] = q;
        qr[1] = r;
        return qr;
    }

    public final static Matrix[] eigen(Matrix a) {
        Matrix eigenvalues = copy(a);
        for (int i = 0; i < 20; i++) {
            Matrix[] qr = qrFactorize(eigenvalues);
            Matrix q = qr[0];
            Matrix r = qr[1];
            eigenvalues = multiply(r, q);
        }
        for (int i = 0; i < eigenvalues.getRowNum(); i++) {
            for (int j = 0; j < eigenvalues.getColNum(); j++) {
                if (i != j) {
                    eigenvalues.setValue(i, j, 0.0);
                }
            }
        }

        // TODO: the returned eigenvectors and eigenvalues are not corresponding yet
        Matrix eigenvectors = inverse(subtract(a, eigenvalues));
        Matrix[] valuesAndVectors = new Matrix[2];
        valuesAndVectors[0] = eigenvalues;
        valuesAndVectors[1] = eigenvectors;
        return valuesAndVectors;
    }
}
