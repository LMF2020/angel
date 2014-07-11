package com.angle.test.mock;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-5-17
 * Time: 下午12:52
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
       String sql_network_information="SELECT " +
               "  t.floors AS TIER," +
               "  CONCAT(t.purchaser_code,'/',t.purchaser_name) AS PURCHASER_ID_NAME," +
               "  CONCAT(t.sponsor_code,'/',t.sponsor_name) AS SPONSOR_ID_NAME," +
               "  t.shop_code      AS SHOP_CODE," +
               "  t3.rank_name      AS RANK_NAME," +
               "  t1.ATNPV,"   +
               "  t1.APPV,"    +
               "  t1.TNPV,"    +
               "  t1.GPV,"     +
               "  t1.PPV "     +
               " FROM t_purchaser t " +
               "  LEFT JOIN t_achieve t1 " +
               "    ON t.purchaser_code = t1.purchaser_code " +
               "  LEFT JOIN t_bouns t2 " +
               "    ON t2.purchaser_code = t.purchaser_code " +
               "  LEFT JOIN t_rank t3 "  +
               "    ON t.rank_code = t3.rank_code " +
               "WHERE t.purchaser_code = '000001' " +
               "     OR t.upper_codes LIKE '%000001%' " +
               "ORDER BY t.floors ASC";

        System.out.println(sql_network_information);

    }
}
