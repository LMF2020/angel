package com.angel.my.service;

import com.angel.my.dao.ITOrderListDao;
import com.angel.my.dao.ITPurchaserInfoDao;
import com.angel.my.model.TPurchaserInfo;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ITPurchaserInfoService {
	
	@Autowired
	private ITPurchaserInfoDao ITPurchaserInfoDao;
	
	//获取登陆会员信息session
	public boolean getLoginModel(HttpServletRequest request, HttpServletResponse response, HttpSession session, TPurchaserInfo user, String usercode , String password){
		return ITPurchaserInfoDao.getLoginModel(request, response, session, user, usercode, password);
	}
	
	//分页查询会员列表
	public Pagination<TPurchaserInfo> findAllForPage(PaginationRequest<TPurchaserInfo> pageRequest) {
		return ITPurchaserInfoDao.findPage(pageRequest);
	}

    /*根据Hql分页查询*/
    public Pagination<Object> findPageByHQL(String rowSql ,String countSql,int offset,int limit){
        return ITPurchaserInfoDao.findPageByHQL(rowSql, countSql, offset, limit);
    }


    //查询一个会员
    public TPurchaserInfo loadUser(String code){
        return ITPurchaserInfoDao.get(code);
    }

    //新增一个会员
    public String addUser(TPurchaserInfo user){
        return ITPurchaserInfoDao.save(user);
    }

    //编辑一个会员
    public void updateUser(TPurchaserInfo user){
        ITPurchaserInfoDao.update(user);
    }

    //删除指定的会员列表
    public void destoryUser(String code){
        ITPurchaserInfoDao.delete(code);
    }

    //查询所有经销商编码
    public List<String> findAllId(){
        return ITPurchaserInfoDao.findByHQL("SELECT T.purchaserCode FROM TPurchaserInfo T ");
    }

}