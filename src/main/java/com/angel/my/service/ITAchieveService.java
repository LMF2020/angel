package com.angel.my.service;

import com.angel.my.dao.IAchieveDao;
import com.angel.my.model.TAchieve;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITAchieveService {
    @Autowired
    private IAchieveDao IAchieveDao;

    //分页查询当月业绩
    public Pagination<TAchieve> findAllForPage(PaginationRequest<TAchieve> pageRequest) {
        return IAchieveDao.findPage(pageRequest);
    }
    //根据经销商编码查询业绩
    public TAchieve loadAchieve(String purchaserCode){
        return IAchieveDao.get(purchaserCode);
    }

    //保存业绩
    public String addAchieve(TAchieve achieve){
        return IAchieveDao.save(achieve);
    }

    //更新业绩表
    public void updateAchieve(TAchieve achieve){
        IAchieveDao.update(achieve);
    }

    //删除业绩
    public void destoryAchieve(String purchaserCode){
        IAchieveDao.delete(purchaserCode);
    }

}
