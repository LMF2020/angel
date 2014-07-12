package com.angle.test.mock;

import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.00");
        double f = 179.99999999999991; //180.00.18
        System.out.println(Double.valueOf(df.format(f)));
    }
}
