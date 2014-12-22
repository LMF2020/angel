package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.service.IExportService;
import com.angel.my.service.ITPurchaserInfoService;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.MySqlPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 工具箱
 * @author jiangzx@gmail.com
 *
 */
@Controller
@RequestMapping("/helperController")
public class HelperController extends BaseController {
	
	@Autowired
	private ITPurchaserInfoService userService;
    @Autowired
    private IExportService iExportService;
    @Autowired
    private ITPurchaserInfoService purchaserInfoService;

    /**
	 * 工具箱首页面
	 */
	@RequestMapping("/helper")
	public String home() throws IOException {
        return "/helper/helper";
	}
    @RequestMapping("/history")
    public String history() throws IOException {
        return "/history/history";
    }

    /**
	 * 查询每个星级的会员
     * 说明：三星级只是当月加入的会员
     *      四星级以上通过isCheck=1判断是否需要限制加入日期
	*/
	@RequestMapping("/pageUserList")
	@ResponseBody
	public MySqlPagination pageUserList(
			@RequestParam("page") int startIndex,
			@RequestParam("rows") int pageSize,
            @RequestParam("rankCode") String rankCode,
            @RequestParam(required = false,value ="isCheck") String isCheck,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String order){

        String lastMonth = DateUtil.getLastMonDate(1).substring(0,7);
        MySqlPagination page = userService.pageFilterListByRank(startIndex,pageSize,rankCode,isCheck,lastMonth);
		return page ;
	}

    /**
     * 导出每个星级的会员
     * 说明：三星级只是当月加入的会员
     *      四星级以上则不限制加入日期
     */
    @RequestMapping(value = "/exportRankUserList",method = RequestMethod.GET)
    public void exportRankUserList(
            @RequestParam("rankCode") String rankCode,
            @RequestParam(required = false,value ="isCheck") String isCheck,
            HttpServletRequest request,
            HttpServletResponse response){
        //Excel头部信息(标题)
        Map<String,String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("purchaser_code","DISTRIBUTOR ID");
        headerMap.put("purchaser_name","DISTRIBUTOR NAME");
        headerMap.put("shop_code","SHOP CODE");
        headerMap.put("rank_name","RANK");
        headerMap.put("PVBV","PERSONAL PV/BV");
        headerMap.put("APPV","APPV");
        headerMap.put("ATNPV","ATNPV");
        headerMap.put("create_time","JOIN TIME");
        iExportService.exportFilterExcelByRank(headerMap,rankCode,isCheck,request,response);
    }

    /**
     *  按（编号、结算月）查询会员历史业绩
     */
    @RequestMapping("/pageQueryUserGradeByMon")
    @ResponseBody
    public MySqlPagination pageQueryUserGradeByMon(
            @RequestParam("page") int startIndex,
            @RequestParam("rows") int limit,
            String purchaserCode,
            @RequestParam(required = false)String achieveDate){
        MySqlPagination page = purchaserInfoService.pageQueryUserGradeByMon(startIndex,limit,purchaserCode,achieveDate);
        return page ;
    }

}