package com.angel.my.util;

import java.math.BigDecimal;

/**
 * 常用工具类
 */
public class CommonUtil {
    /**
     * 保留4位小数
     * @param d
     * @return
     */
    public static double getDoubleCeil(double d){
        BigDecimal bg = new BigDecimal(d);
        double f1 = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
    public static double getDoubleCeil(String s){
        BigDecimal bg = new BigDecimal(s);
        double f1 = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }



}
