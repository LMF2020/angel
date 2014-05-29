package com.angel.my.dao;

import com.angel.my.model.TPurchaserInfo;
import com.starit.common.dao.hibernate4.HibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Repository
public class ITPurchaserInfoDao extends HibernateBaseDaoImpl<TPurchaserInfo,String>{
	
	/**
	 * 以会员编码登陆系统
	 * @param session
	 * @param user
	 * @param usercode
	 * @param password 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean getLoginModel(HttpServletRequest request,HttpServletResponse response, HttpSession session, TPurchaserInfo user, String usercode , String password){
		String hql = "from TPurchaserInfo t where t.purchaserCode = ?";
		List<TPurchaserInfo> userList = this.findByHQL(hql,usercode);
		if(userList.size() == 0) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		} else if(!password.equals(userList.get(0).getPurchaserPass())) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		} else {
			user.setPurchaserCode(usercode);
			user.setPurchaserPass(password);
			session.setAttribute("__SESSIONKEY__", user);
			return true;
		}
	}
	
	
	
}
