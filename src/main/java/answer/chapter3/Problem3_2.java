package answer.chapter3;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

/**
 * 设A=[6, 7, 1; 2, 2, 4]，B=[8, 1, 3; 4, 4, 1]，
 * 尝试利用本章给出的矩阵点乘的实现来计算A和B点乘的结果。
 */
public class Problem3_2 {

    public static void main(String[] args) {
        Matrix a = new Matrix(2, 3);
        a.setValue(0, 0, 6);
        a.setValue(0, 1, 7);
        a.setValue(0, 2, 1);
        a.setValue(1, 0, 2);
        a.setValue(1, 1, 2);
        a.setValue(1, 2, 4);
        Matrix b = new Matrix(2, 3);
        b.setValue(0, 0, 8);
        b.setValue(0, 1, 1);
        b.setValue(0, 2, 3);
        b.setValue(1, 0, 4);
        b.setValue(1, 1, 4);
        b.setValue(1, 2, 1);
        Matrix aDotB = AlgebraUtil.dot(a, b);
        System.out.println(aDotB);
    }

}
