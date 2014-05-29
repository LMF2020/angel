package com.angel.my.dao;

import com.angel.my.model.TShopInfo;
import com.starit.common.dao.hibernate4.HibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
public class ITShopInfoDao extends HibernateBaseDaoImpl<TShopInfo,String >{
	
	/**
	 * 店铺查询
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	public List<TShopInfo> getShopList(HttpServletRequest request, HttpServletResponse response){
		
		String hql = "from TShopInfo";
		List<TShopInfo> shopList =this.findByHQL(hql);
		
		return shopList;
	}
}
