package jm.app;

import java.math.BigDecimal;


public class Matrix {

    private BigDecimal[][] mat;

    private int rowNum;

    private int colNum;

    public Matrix(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        mat = new BigDecimal[rowNum][colNum];
    }

    private void initializeMatrix() {
        for (int i = 0; i < rowNum; i ++) {
            for (int j = 0; j < colNum; j++) {
                mat[i][j] = new BigDecimal(0.0);
            }
        }
    }

    public void setValue(int x1, int x2, double value) {
        mat[x1][x2] = new BigDecimal(value);
    }

    public void setValue(int x1, int x2, BigDecimal value) {
        mat[x1][x2] = value;
    }

    public BigDecimal getValue(int x1, int x2) {
        return mat[x1][x2];
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                sb.append(String.format("%15f", mat[i][j].doubleValue()));
            }
        }
        return sb.toString();
    }
}
