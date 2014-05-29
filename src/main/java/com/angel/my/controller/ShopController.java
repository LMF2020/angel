package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.model.TShopInfo;
import com.angel.my.service.ITShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 * 商店管理
 * @author jiangzx@gmail.com
 *
 */
@Controller
@RequestMapping("/shopController")
public class ShopController extends BaseController {
	
	@Autowired
	private ITShopInfoService shopInfoService; 
	
	/**
	 * 店铺列表
	 */
	@RequestMapping("/getShopList") 
	public List<TShopInfo> getShopList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 return shopInfoService.getShopList(request, response);
	}
	
	
}