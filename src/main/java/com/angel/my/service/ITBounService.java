package com.angel.my.service;

import com.angel.my.dao.IBounDao;
import com.angel.my.model.TBoun;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITBounService {
    @Autowired
    private IBounDao IBounDao;

    //分页查询奖金
    public Pagination<TBoun> indAllForPage(PaginationRequest<TBoun> pageRequest) {
        return IBounDao.findPage(pageRequest);
    }
    //根据经销商编码查询奖金
    public TBoun loadBoun(String purchaserCode){
        return IBounDao.get(purchaserCode);
    }

    //保存奖金
    public String addBoun(TBoun boun){
        return IBounDao.save(boun);
    }

    //更新奖金表
    public void updateBoun(TBoun boun){
        IBounDao.update(boun);
    }

    //删除奖金
    public void destoryBoun(String purchaserCode){
        IBounDao.delete(purchaserCode);
    }

}
