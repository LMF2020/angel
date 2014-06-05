package com.angel.my.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务常用工具类
 */
public class CommonUtil {

    /**
     * 1星到9星的直接奖比列
     */
    public static final Map<String,Double> directRateConstant = new HashMap<String,Double>(){
        {
            put("102001",0.05);
            put("102002",0.10);
            put("102003",0.22);
            put("102004",0.26);
            put("102005",0.30);
            put("102006",0.34);
            put("102007",0.40);
            put("102008",0.43);
            put("102009",0.45);
        }
    };


  /*
    public static final double one_star_direct_rate = 0.05;
    */

    //5星到9星的领导奖比例

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
