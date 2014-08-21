package com.angel.my.service;

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

    /*判断该经销商是否没有下线分支*/
    public boolean findNotExistChild(String parentCode){
        List children =  ITPurchaserInfoDao.findByHQL("FROM TPurchaserInfo T WHERE T.sponsorCode = ? ",parentCode);
        if (children.isEmpty()){
            return true;
        }
        return false;
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
    public void updateUser(TPurchaserInfo updateUser){
        ITPurchaserInfoDao.update(updateUser);
    }

    //编辑一个会员和他的所有下属节点
    public void updateUserCascade(TPurchaserInfo updateUser,String oldUpperCodes,String newUpperCodes){
        updateChildNodes(updateUser.getPurchaserCode(),oldUpperCodes,newUpperCodes);
        ITPurchaserInfoDao.update(updateUser);
    }

    //删除指定的会员列表
    public void destoryUser(String code){
        ITPurchaserInfoDao.delete(code);
    }

    //查询所有经销商编码
    public List<String> findAllId(){
        return ITPurchaserInfoDao.findByHQL("SELECT T.purchaserCode FROM TPurchaserInfo T ");
    }

    /**
     * 更新我的所有下属节点
     * @param myCode        我的编码
     * @param newUpperCodes 新的父节点UpperCodes编码
     * @param oldUpperCodes 老的父节点UpperCodes编码
     * @return
     */
    public boolean updateChildNodes(String myCode, String oldUpperCodes,String newUpperCodes) {
        //查询我的直接孩子节点
        String Hql = "FROM TPurchaserInfo T WHERE T.sponsorCode = ?";
        List<TPurchaserInfo> downLine = ITPurchaserInfoDao.findByHQL(Hql,myCode);
        for (TPurchaserInfo down : downLine){
           String downUpperCodes =  down.getUpperCodes();
           down.setUpperCodes(downUpperCodes.replaceFirst(oldUpperCodes,newUpperCodes));
           //执行递归更新down的所有下属节点
           updateChildNodes(down.getPurchaserCode(),oldUpperCodes,newUpperCodes);
        }
        return true;
    }
}