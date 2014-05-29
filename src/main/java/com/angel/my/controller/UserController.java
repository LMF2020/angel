package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.common.ResponseData;
import com.angel.my.model.TPurchaserInfo;
import com.angel.my.service.ITPurchaserInfoService;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * 会员管理
 * @author jiangzx@gmail.com
 *
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
	
	@Autowired
	private ITPurchaserInfoService userService;

    /**
	 * 首页JSP|定位
	 */
	@RequestMapping("/home") 
	public String home() throws IOException {
        return "/home";
	}
	
	/**
	 * 会员JSP|定位
	 */
	@RequestMapping("/userReg") 
	public String userReg() throws IOException {
		return "/user/userReg";
	}
	
	/**
	 * 登陆
	 */
	@RequestMapping("/login") 
	@ResponseBody
	public ResponseData login(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		TPurchaserInfo user = new TPurchaserInfo();
		String username = request.getParameter("usercode");
		String password = request.getParameter("password");
		
		boolean logsuccess = userService.getLoginModel(request,  response,  session, user,  username ,  password);
		if(logsuccess){
			return ResponseData.SUCCESS_NO_DATA;
		}
		return new ResponseData(true,"登陆失败!");
		
	}
	
	/**
	 * 注销
	 */
	@RequestMapping("/logout") 
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/logout");   
	}

    /**
     * 会员|注册
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addUser(TPurchaserInfo user) throws Exception {
        //判断会员编码是否重复
        TPurchaserInfo e = userService.loadUser(user.getPurchaserCode());
        if (e != null){
            return new ResponseData(true,"会员编码重复!");
        }
        //判断上级会员编码是否存在
        e = userService.loadUser(user.getSponsorCode());
        if (e == null) {
            return new ResponseData(true,"上级会员编码输入错误!");
        }
        //等级 ,初始化等级均为一星
        user.setRankCode("102001");
        //层级
        user.setFloors(e.getFloors()+1);
        //创建时间
        user.setCreateTime(new Date());
        //更新时间
        user.setUpdateTime(new Date());
        userService.addUser(user);
        return ResponseData.SUCCESS_NO_DATA;
    }

    /**
     * 会员|更新~
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateUser(TPurchaserInfo user){
        //提取保留信息
        TPurchaserInfo updateUser = userService.loadUser(user.getPurchaserCode());
        //更新时间
        updateUser.setUpdateTime(new Date());
        //更新姓名
        updateUser.setPurchaserName(user.getPurchaserName());
        userService.updateUser(updateUser);
        return ResponseData.SUCCESS_NO_DATA;
    }

    /**
     * 会员注销
     */
    @RequestMapping(value = "/destroyUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData destroyUser(String[] codes){
        try {
            for (int i = 0; i < codes.length; i++) {
                userService.destoryUser(codes[i]);
            }
        } catch (Exception e) {
            return new ResponseData(true,"删除失败!");
        }
        return new ResponseData(true);
    }

	/**
	 * 会员|查询
	 */
	@RequestMapping("/pageUserList")
	@ResponseBody
	public Pagination<TPurchaserInfo> pageUserList(
            @RequestParam(required = false) String q, //模糊查询字段
			@RequestParam("page") int startIndex,
			@RequestParam("rows") int pageSize, TPurchaserInfo user,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String order){

        //计算索引位置
        int offset = (startIndex-1)*pageSize;
        PaginationRequest <TPurchaserInfo > pageRequest = new PaginationRequest<TPurchaserInfo>(offset, pageSize);
		if(StringUtils.hasText(sort) && StringUtils.hasText(order)) pageRequest.addOrder(sort, order);
        // 姓名
		if(StringUtils.hasText(user.getPurchaserName())){
			pageRequest.addLikeCondition("purchaserName", user.getPurchaserName());
		}
		//编号（模糊查询）
		if(StringUtils.hasText(user.getPurchaserCode())){
			pageRequest.addLikeCondition("purchaserCode", user.getPurchaserCode());
		}
        //表单-编号,模糊查询
        if(StringUtils.hasText(q)){
            pageRequest.addLikeCondition("purchaserCode", q);
        }
		//上级编号
		if(StringUtils.hasText(user.getSponsorCode())){
			pageRequest.addLikeCondition("sponsorCode", user.getSponsorCode());
		}
		//商店
		if(StringUtils.hasText(user.getShopCode())){
			pageRequest.addCondition("shopCode", user.getShopCode());
		}
        //等级
        if(StringUtils.hasText(user.getRankCode())){
            pageRequest.addCondition("rankCode", user.getRankCode());
        }
		Pagination<TPurchaserInfo> page = userService.findAllForPage(pageRequest);
		
		return page ;
	}
	
}