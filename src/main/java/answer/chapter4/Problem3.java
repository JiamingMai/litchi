package answer.chapter4;

import jm.app.algebra.AdvancedAlgebraUtil;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;

/**
 * 设A=[5, 2, 0; 2, 8, 3; 4 9 7]，
 * 尝试利用本章给出的行列式函数求解出A的行列式。
 */
public class Problem3 {

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
        BigDecimal det = AdvancedAlgebraUtil.determinant(a);
        System.out.println(det);
    }

}
