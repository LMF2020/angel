package com.angel.my.service;

import com.angel.my.dao.ITOrderListDao;
import com.angel.my.model.TOrderList;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITOrderListService {
    @Autowired
    private ITOrderListDao ITOrderListDao;

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
}
