package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.common.ResponseData;
import com.angel.my.model.TAchieve;
import com.angel.my.model.TBoun;
import com.angel.my.model.TPurchaserInfo;
import com.angel.my.service.IBusiService;
import com.angel.my.service.ITAchieveService;
import com.angel.my.service.ITBounService;
import com.angel.my.service.ITPurchaserInfoService;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.MySqlPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 业务核算
 * @author jiangzx@gmail.com
 *
 */
@Controller
@RequestMapping("/busiController")
public class BusiController extends BaseController {

    @Autowired
    private IBusiService iBusiService;
    @Autowired
    private ITAchieveService iAchieveService;
    @Autowired
    private ITBounService iBounService;
    @Autowired
    private ITPurchaserInfoService iPurchaserService;

    /**
     * 计算当月奖金与业绩
     */
    @RequestMapping(value = "/beginCalculate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData startCount(){
        //上个月28号 - 本月28号
        String lastMonDate =  DateUtil.getLastMonDate(28);
        String thisMonDate = DateUtil.getToday(); //今天是不是28号?
        int maxfloor = iBusiService.getMaxFloor();
        ////根据floor排序从底层往上层开始计算
        try {
            //计算前清空两表数据
            iBusiService.clearTableData();
            for (int i = maxfloor; i >=0; i--) {
                //获得第i层的所有会员
                List<String> allInThisFloor = iBusiService.getAllByFloor(i);
                //遍历当前层的所有会员计算他们的业绩和奖金
                for (int j = 0,size=allInThisFloor.size(); j <size ; j++) {
                    //会员业绩
                    String purchaserCode = allInThisFloor.get(i);
                    double PPV = iBusiService.getPPV(purchaserCode,lastMonDate,thisMonDate);
                    double APPV = iBusiService.getAPPV(purchaserCode);
                    double TNPV = iBusiService.getTNPV(purchaserCode,PPV,lastMonDate,thisMonDate);
                    double ATNPV = iBusiService.getATNPV(purchaserCode,APPV);
                    String rankCode = iBusiService.getRANK(purchaserCode,PPV,APPV,ATNPV);
                    double GPV = iBusiService.getGPV(purchaserCode,rankCode,TNPV);
                    //会员奖金
                    double DBV = iBusiService.getDBV(purchaserCode,rankCode,PPV);
                    double IBV = iBusiService.getIBV(purchaserCode,rankCode);
                    double LBV = iBusiService.getLBV(purchaserCode,rankCode,GPV);
                    //保存入库
                    //存业绩表
                    TAchieve achieve = new TAchieve();
                    achieve.setPurchaserCode(purchaserCode);
                    achieve.setPpv(PPV);
                    achieve.setAppv(APPV);
                    achieve.setTnpv(TNPV);
                    achieve.setRankCode(rankCode);
                    achieve.setGpv(GPV);
                    iAchieveService.addAchieve(achieve);
                    //存奖金表
                    TBoun boun = new TBoun();
                    boun.setPurchaserCode(purchaserCode);
                    boun.setRankCode(rankCode);
                    boun.setBounsDate(new Date());
                    boun.setDirectBouns(DBV);
                    boun.setIndirectBouns(IBV);
                    boun.setLeaderBouns(LBV);
                    iBounService.addBoun(boun);
                    //更新会员表(等级)
                    TPurchaserInfo purchaser =  iPurchaserService.loadUser(purchaserCode);
                    purchaser.setRankCode(rankCode);
                    iPurchaserService.updateUser(purchaser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //回滚：清空业绩表，清空奖金表，重置会员表
            iBusiService.clearTableData();
            return new ResponseData(true,"核算失败，请联系管理员确认系统故障!");
        }

        //计算完毕且无误后分别批量导入业绩历史表和奖金历史表

        return ResponseData.SUCCESS_NO_DATA;
    }

    /**
     * 网络图JSP|定位
     */
    @RequestMapping("/net")
    public String net() throws IOException {
        return "/net/net";
    }

    /**
     * 网络表格查询
     * @param purchaserCode 会员编号
     * @return
     */
    @RequestMapping("/pageNetList")
    @ResponseBody
    public MySqlPagination pageOrderList(
            @RequestParam("page") int startIndex,
            @RequestParam("rows") int pageSize,
            String purchaserCode){
        if (!StringUtils.hasText(purchaserCode)){
            purchaserCode = "000000"; //默认查询顶级会员网络
        }
        MySqlPagination page = iBusiService.queryPageNetWork(startIndex,pageSize,purchaserCode);
        return page;
    }

}
