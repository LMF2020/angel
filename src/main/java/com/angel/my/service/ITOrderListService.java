package com.angel.my.service;

import com.angel.my.dao.ITOrderListDao;
import com.angel.my.model.TOrderList;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ITOrderListService {
    @Autowired
    private ITOrderListDao ITOrderListDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //分页查询订单列表
    public Pagination<TOrderList> findAllForPage(PaginationRequest<TOrderList> pageRequest) {
        return ITOrderListDao.findPage(pageRequest);
    }

    //查询订单
    public TOrderList loadOrder(String orderCode){
        return ITOrderListDao.get(orderCode);
    }

    //新增订单
    public String addOrder(TOrderList Order){
        return ITOrderListDao.save(Order);
    }

    //编辑订单
    public void updateOrder(TOrderList Order){
        ITOrderListDao.update(Order);
    }

    //删除订单
    public void destoryOrder(String orderCode){
        ITOrderListDao.delete(orderCode);
    }

    /*根据Hql分页查询*/
    public Pagination<Object> findPageByHQL(String rowSql ,String countSql,int offset,int limit){
        return ITOrderListDao.findPageByHQL(rowSql, countSql, offset, limit);
    }

    /*计算月销售额*/
    public  double getSumMon(){
        String lastMonDate =  DateUtil.getLastMonDate(28);
        String thisMonDate = DateUtil.getToday();
        String sql = " SELECT SUM(t.PV) AS SUM" +
                     " FROM  t_order t " +
                     " WHERE t.sale_time > '"+lastMonDate+"' AND t.sale_time < '"+thisMonDate+"'";
        //double d =  jdbcTemplate.queryForObject(sql,Double.class);
        Map mm = (Map)jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("SUM");
        double d =  (dd == null?0:dd.doubleValue());
        return d;
    }
}
