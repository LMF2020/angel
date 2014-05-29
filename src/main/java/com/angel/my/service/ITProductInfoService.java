package com.angel.my.service;

import com.angel.my.dao.ITProductInfoDao;
import com.angel.my.model.TProductInfo;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITProductInfoService {
    @Autowired
    private ITProductInfoDao ITProductInfoDao;

    //分页查询产品列表
    public Pagination<TProductInfo> findAllForPage(PaginationRequest<TProductInfo> pageRequest) {
        return ITProductInfoDao.findPage(pageRequest);
    }
    //查询产品
    public TProductInfo loadProduct(String code){
        return ITProductInfoDao.get(code);
    }

    //新增产品
    public String addProduct(TProductInfo product){
        return ITProductInfoDao.save(product);
    }

    //编辑产品
    public void updateProduct(TProductInfo product){
        ITProductInfoDao.update(product);
    }

    //删除产品
    public void destoryProduct(String product){
        ITProductInfoDao.delete(product);
    }

}
