package jm.app;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1.1);
        BigDecimal b = a;
        System.out.println(b);
        System.out.println(a);
    }
}
