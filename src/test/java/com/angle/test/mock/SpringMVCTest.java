package com.angle.test.mock;

import com.alibaba.fastjson.JSON;
import com.starit.common.dao.support.ExcelTools;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/****
 * 单元测试Service和Dao
 * @author 蒋兆欣
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/*-servlet.xml","classpath:context-core.xml"})
public class SpringMVCTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //打印网络图
    private String sql_network_information = null;
    //打印奖金发放表
    private String sql_specialty_shop_bonus_list = null;

	//@Before
    public void setup() {

		sql_network_information="SELECT " +
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

        sql_specialty_shop_bonus_list = "SELECT " +
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
                " WHERE t.shop_code = 'CG982000' " +    //商店编号
                " GROUP BY t.purchaser_code " +
                " ORDER BY t.purchaser_code asc";
	}

    @org.junit.Test
    public  void testSuccess(){

    }

	//@Test
	public void test_sql_network_information() {

        //测试输出
        String outpath = "D:\\poiout\\network_information.xlsx";
        Map<String,String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("TIER","TIER");
        headerMap.put("PURCHASER_ID_NAME","The Distributor ID/NAME");
        headerMap.put("SPONSOR_ID_NAME","Sponsor's ID/NAME");
        headerMap.put("SHOP_CODE","Shop ID");
        headerMap.put("RANK_NAME","Rank");
        headerMap.put("ATNPV","Accumulate PV");
        headerMap.put("APPV","Personal APV");
        headerMap.put("TNPV","TNPV");
        headerMap.put("GPV","GPV");
        headerMap.put("PPV","Personal PV/BV");

        //方法调用
        ExcelTools tools =  new ExcelTools(sql_network_information,10,jdbcTemplate);
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(outpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tools.createExcel(headerMap,fileOut);
    }

    //@Test
    public void test_specialty_shop_bonus_list() {
        System.out.println(sql_specialty_shop_bonus_list);
        //测试输出
        String outpath = "D:\\poiout\\specialty_shop_bonus_list.xlsx";
        Map<String,String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("PURCHASER_CODE","Distributor ID");
        headerMap.put("PURCHASER_NAME","Distributor NAME");
        headerMap.put("TOTAL_BOUNS","Total Bonus");
        headerMap.put("COMPUTOR_FEE","Computer Fee");
        headerMap.put("TAXATION","Taxation");
        headerMap.put("REAL_WAGES","Real Wages");
        headerMap.put("SIGNATURE","Signature");

        //方法调用
        ExcelTools tools =  new ExcelTools(sql_specialty_shop_bonus_list,10,jdbcTemplate);
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(outpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tools.createExcel(headerMap,fileOut);
    }

   // @Test
    public void test_jdbcSql(){
        String sql = "SELECT " +
                "  t.purchaser_code AS 'key', " +
                "  t.sponsor_code   AS 'parent', " +
                "  t1.rank_name     AS 'name' " +
                " FROM t_purchaser t " +
                "  LEFT JOIN t_rank t1 " +
                "    ON t1.rank_code = t.rank_code " +
                " WHERE t.purchaser_code = '000001' " +
                "     OR t.upper_codes LIKE '%000001%' ";

        List list =  jdbcTemplate.queryForList(sql);
        System.out.println("=============================");
        String json =  JSON.toJSONString(list);
        System.out.println(json);
    }

   // @Test
    public void test_jdbc_getResult(){
        String sql = "SELECT angel.queryChildrenInfo('000001') AS QUERY_SQL ";
        Map mm =  jdbcTemplate.queryForMap(sql);
        String query_sql = mm.get("QUERY_SQL").toString();
        System.out.println("=======================\n"+query_sql);

        List queryList = jdbcTemplate.queryForList(query_sql);
        System.out.println("queryList.size()==="+queryList.size());

    }

}