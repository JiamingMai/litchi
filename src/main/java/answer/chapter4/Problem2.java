package answer.chapter4;

import jm.app.algebra.AdvancedAlgebraUtil;
import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

/**
 * 设A=[5, 2, 0; 2, 8, 3; 4 9 7],
 * 尝试利用本章给出的矩阵特征值和特征向量函数来计算A的所有特征值及其对应的特征向量。
 */
public class Problem2 {

    public static void main(String[] args) {
        Matrix a = new Matrix(3, 3);
        a.setValue(0, 0, 5);
        a.setValue(0, 1, 2);
        a.setValue(0, 2, 0);
        a.setValue(1, 0, 2);
        a.setValue(1, 1, 8);
        a.setValue(1, 2, 3);
        a.setValue(2, 2, 4);
        a.setValue(2, 2, 9);
        a.setValue(2, 2, 7);
        Matrix[] valuesAndVectors = AdvancedAlgebraUtil.eigen(a);
        Matrix value = valuesAndVectors[0];
        Matrix vector = valuesAndVectors[1];
        System.out.println(value);
        System.out.println(vector);
    }

}
