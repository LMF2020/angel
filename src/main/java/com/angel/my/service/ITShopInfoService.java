package com.angel.my.service;

import com.angel.my.dao.ITShopInfoDao;
import com.angel.my.model.TShopInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ITShopInfoService {
	
	@Autowired
	private ITShopInfoDao ITshopInfoDao;
	
	public List<TShopInfo> getShopList(HttpServletRequest request, HttpServletResponse response){
		return ITshopInfoDao.getShopList(request, response);
	}
}
