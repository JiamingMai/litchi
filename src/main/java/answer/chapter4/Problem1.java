package answer.chapter4;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

/**
 * 设A=[6, 7, 1; 2, 2, 4]，
 * 尝试利用本章给出的最大值函数和最小值函数分别计算A每行的最大值和最小值。
 */
public class Problem1 {

    public static void main(String[] args) {
        Matrix a = new Matrix(2, 3);
        a.setValue(0, 0, 6);
        a.setValue(0, 1, 7);
        a.setValue(0, 2, 1);
        a.setValue(1, 0, 2);
        a.setValue(1, 1, 2);
        a.setValue(1, 2, 4);
        Matrix rowMax = AlgebraUtil.max(a, 1);
        Matrix rowMin = AlgebraUtil.min(a, 1);
        System.out.println(rowMax);
        System.out.println(rowMin);
    }

}
