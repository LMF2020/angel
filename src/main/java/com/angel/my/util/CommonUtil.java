package com.angel.my.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务常用工具类
 */
public class CommonUtil {

    /**
     * 一星到九星的直接奖比列配置
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

    //导出网络图表
    public static final String sql_network_information=" SELECT " +
            "  t.floors AS TIER," +
            "  CONCAT(t.purchaser_code,'/',t.purchaser_name) AS PURCHASER_ID_NAME," +
            "  CONCAT(t.sponsor_code,'/',t.sponsor_name) AS SPONSOR_ID_NAME," +
            "  t.shop_code      AS SHOP_CODE," +
            "  t3.rank_name      AS RANK_NAME," +
            "  t1.ATNPV,"   +
            "  t1.APPV,"    +
            "  t1.TNPV,"    +
            "  t1.GPV,"     +
            "  CONCAT(t1.PPV,'/',t1.PBV)   AS PPV " +
            "  FROM t_purchaser t " +
            "  LEFT JOIN t_achieve t1 " +
            "    ON t.purchaser_code = t1.purchaser_code " +
            "  LEFT JOIN t_bouns t2 " +
            "    ON t2.purchaser_code = t.purchaser_code " +
            "  LEFT JOIN t_rank t3 "  +
            "    ON t.rank_code = t3.rank_code " +
            " WHERE t.purchaser_code = '?' " +
            "     OR t.upper_codes LIKE '%?%' " +
            " ORDER BY t.floors ASC";

    //导出奖金发放表
    public static final String sql_specialty_shop_bonus_list = " SELECT " +
            "  t.purchaser_code AS PURCHASER_CODE, " +
            "  t.purchaser_name AS PURCHASER_NAME, " +
            "  SUM(t1.direct_bouns+t1.indirect_bouns+t1.leader_bouns) AS TOTAL_BOUNS, " +
            "  2                AS COMPUTOR_FEE, " +
            "  0.0              AS TAXATION, " +
            "  SUM(t1.direct_bouns+t1.indirect_bouns+t1.leader_bouns-2)  AS REAL_WAGES, " +
            "  \"\"               AS SIGNATURE " +
            " FROM t_purchaser t " +
            "  LEFT JOIN t_bouns t1 " +
            "    ON t.purchaser_code = t1.purchaser_code " +
            " WHERE t.shop_code = '?' " +    //商店编号
            " GROUP BY t.purchaser_code " +
            " ORDER BY t.purchaser_code asc";

}
