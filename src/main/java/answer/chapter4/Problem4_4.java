package answer.chapter4;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

/**
 * 设A=[5, 2, 0; 2, 8, 3; 4 9 7]，
 * 尝试利用本章给出的矩阵求逆的实现来求解出A的逆矩阵A-1。
 */
public class Problem4_4 {

    public static void main(String[] args) {
        Matrix a = new Matrix(3, 3);
        a.setValue(0, 0, 5);
        a.setValue(0, 1, 2);
        a.setValue(0, 2, 0);
        a.setValue(1, 0, 2);
        a.setValue(1, 1, 8);
        a.setValue(1, 2, 3);
        a.setValue(2, 0, 4);
        a.setValue(2, 1, 9);
        a.setValue(2, 2, 7);
        Matrix invA = AlgebraUtil.inverse(a);
        System.out.println(invA);
    }

}
