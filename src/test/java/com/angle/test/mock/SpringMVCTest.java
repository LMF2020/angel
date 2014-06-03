package com.angle.test.mock;

import com.angel.my.service.IBusiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * 		      单元测试Service和Dao
		 * @author 蒋兆欣
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/*-servlet.xml","classpath:context-core.xml"})
public class SpringMVCTest {
	
	@Autowired
    private IBusiService iBusiService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Before
    public void setup() {
		
	}
	
	@Test
	public void test() {
        /**
         * 根据上级会员获取下线会员等级及其个数的映射表【星级，个数】
         * @param purchaserCode - 上级会员编号
         * @return
         */
        /*Map m = getRankTableByPurchaserCode("000000");
        int count = ((Long)m.get("102001")).intValue();
        System.out.println(count);*/

    }
    /**
     * 根据上级会员获取下线会员等级及其个数的映射表【星级，个数】
     * @param purchaserCode - 上级会员编号
     * @return
     */
    public Map getRankTableByPurchaserCode(String purchaserCode){
            Map map = new HashMap();
            String sql = " SELECT t.rank_code,COUNT(t.rank_code) AS count FROM t_purchaser t " +
                    " WHERE t.upper_codes LIKE '%"+purchaserCode+"%' GROUP BY t.rank_code ORDER BY t.rank_code";
            List list = jdbcTemplate.queryForList(sql);
            for (int i = 0; i < list.size(); i++) {
                Map t =  (Map)list.get(i);
                map.put(t.get("rank_code"),t.get("count"));
                t = null;
            }
            return map;
    }

}