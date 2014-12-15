package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.service.IExportService;
import com.angel.my.service.ITPurchaserInfoService;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.MySqlPagination;
import com.starit.common.dao.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
	 */
	@RequestMapping("/pageUserList")
	@ResponseBody
	public Pagination<Object> pageUserList(
			@RequestParam("page") int startIndex,
			@RequestParam("rows") int pageSize,
            @RequestParam("rankCode") String rankCode,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String order){

        //查询当月加入的会员
        String startTime = null;
        String endTime = null;
        if(rankCode.equals("102003")){
            startTime = DateUtil.getFirstDayOfMonth();
            endTime = DateUtil.getLastDayOfMonth();
        }
        //计算分页索引位置
        int offset = (startIndex-1)*pageSize;

        StringBuilder sb = new StringBuilder();
        sb.append(" 	from TPurchaserInfo user  where 1=1	");

        //查询条件
        if(StringUtils.hasText(startTime)){			/*会员加入时间起始范围*/
            sb.append("	and user.createTime >= '"+ startTime+" 00:00:00'");
        }
        if(StringUtils.hasText(endTime)){           /*会员加入时间结束范围*/
            sb.append("	and user.createTime <= '"+ endTime+" 23:59:59'");
        }
        //查询 N 星级的会员
        sb.append(" and user.rankCode ='"+rankCode+"'");

        //查询排序
        if(sort!=null && order!=null){
            sb.append("  order by 	user."+sort+" "+order);
        }

        //分页查询
        String rowSql = sb.toString();
        String countSql = "	select count(*) 	"+ rowSql;
        Pagination<Object> page = userService.findPageByHQL(rowSql, countSql, offset, pageSize);

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
            HttpServletRequest request,
            HttpServletResponse response){
        //Excel头部信息(标题)
        Map<String,String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("PURCHASER_CODE","DISTRIBUTOR ID");
        headerMap.put("PURCHASER_NAME","DISTRIBUTOR NAME");
        headerMap.put("SHOP_CODE","SHOP CODE");
        headerMap.put("RANK_NAME","RANK");
        headerMap.put("PPBV","PERSONAL PV/BV");
        headerMap.put("APPV","APPV");
        headerMap.put("ATNPV","ATNPV");
        headerMap.put("CREATE_TIME","JOIN TIME");
        iExportService.exportExcelRank(headerMap,rankCode,request,response);
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