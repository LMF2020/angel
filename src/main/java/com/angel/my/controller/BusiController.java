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
import com.angel.my.util.CommonUtil;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.MySqlPagination;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 业绩和奖金核算
 * @author Jiang
 *
 */
@Controller
@RequestMapping("/busiController")
public class BusiController extends BaseController {

    private static Logger logger = Logger.getLogger(BusiController.class);

    @Autowired
    private ITAchieveService iAchieveService;
    @Autowired
    private ITBounService iBounService;
    @Autowired
    private ITPurchaserInfoService iPurchaserService;
    @Autowired
    private IBusiService iBusiService;

    /**
     * 计算当月奖金与业绩
     */
    @RequestMapping(value = "/beginCalculate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData startCount(
                            @RequestParam(required = false)String  startDate,
                            @RequestParam(required = false)String  endDate){

        System.out.println("=================启动核算进程==================");
        //上个月28号 - 本月28号
        String beginDateTime =  DateUtil.getLastMonDate(28);
        String endDateTime = DateUtil.getToday(); //今天是不是28号?
        try {
            //核算开始
            int topfloor = iBusiService.getMaxFloor();
            ////根据floor排序从底层往上层开始计算
            //计算前清空两表数据
            iBusiService.clear();
            for (int i = topfloor; i >=0; i--) {
                //获得第i层的所有会员
                List linesOnFloor = iBusiService.getLinesOnFloor(i);
                //遍历当前层的所有会员计算他们的业绩和奖金
                for (int j = 0,size=linesOnFloor.size(); j <size ; j++) {
                    //会员业绩
                    Map purchaserCodeMap = (Map)linesOnFloor.get(j);
                    String purchaserCode = (String)purchaserCodeMap.get("purchaser_code");
                    System.out.println("=======当前会员编号："+purchaserCode);
                    purchaserCodeMap = null;
                    double PPV = iBusiService.getPPV(purchaserCode,beginDateTime,endDateTime);
                    System.out.println("==="+purchaserCode+" 的 PPV  ："+PPV);
                    double PBV = iBusiService.getPBV(purchaserCode,beginDateTime,endDateTime);
                    System.out.println("==="+purchaserCode+" 的 PBV  ："+PBV);
                    double APPV = iBusiService.getAPPV(purchaserCode);
                    System.out.println("==="+purchaserCode+" 的 APPV ："+APPV);
                    double TNPV = iBusiService.getTNPV(purchaserCode,PPV,beginDateTime,endDateTime);
                    System.out.println("==="+purchaserCode+" 的 TNPV ："+TNPV);
                    double TNBV = iBusiService.getTNBV(purchaserCode,PBV,beginDateTime,endDateTime);
                    System.out.println("==="+purchaserCode+" 的 TNBV ："+TNBV);
                    double ATNPV = iBusiService.getATNPV(purchaserCode,APPV);
                    System.out.println("==="+purchaserCode+" 的 ATNPV ："+ATNPV);
                    String rankCode = iBusiService.getRANK(purchaserCode,PPV,APPV,ATNPV);
                    System.out.println("==="+purchaserCode+" 的 星级 ："+rankCode);
                    double GPV = iBusiService.getGPV(purchaserCode,rankCode,TNPV);
                    System.out.println("==="+purchaserCode+" 的 GPV ："+GPV);
                    //会员奖金
                    double DBV = iBusiService.getDBV(purchaserCode,rankCode,PBV);
                    System.out.println("==="+purchaserCode+" 的 DBV ："+DBV);
                    double IBV = iBusiService.getIBV(purchaserCode,rankCode);
                    System.out.println("==="+purchaserCode+" 的 IBV ："+IBV);
                    double LBV = iBusiService.getLBV(purchaserCode,rankCode,GPV);
                    System.out.println("==="+purchaserCode+" 的 LBV ："+LBV);
                    System.out.println("==="+purchaserCode+" ：计算结束.");
                    //存业绩表
                    TAchieve achieve = new TAchieve();
                    achieve.setPurchaserCode(purchaserCode);
                    achieve.setPpv(PPV);
                    achieve.setPbv(PBV);
                    achieve.setDbv(DBV);
                    achieve.setIbv(IBV);
                    achieve.setAppv(APPV);
                    achieve.setAtnpv(ATNPV);
                    achieve.setTnpv(TNPV);
                    achieve.setTnbv(TNBV);
                    achieve.setTier(i);
                    achieve.setRankCode(rankCode);
                    achieve.setGpv(GPV);
                    achieve.setAchieveDate(new Date());
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
                    //更新会员表(的星级)
                    TPurchaserInfo purchaser =  iPurchaserService.loadUser(purchaserCode);
                    purchaser.setRankCode(rankCode);
                    iPurchaserService.updateUser(purchaser);
                }
            }
            //核算完毕后将计算结果同步到历史表
            System.out.println("===开始同步数据到历史表====");
            iBusiService.createCopyToHisTable();
            System.out.println("===已成功同步数据到历史表===");
        } catch (Exception e) {
            e.printStackTrace();
            //核算失败回滚：清空业绩表，清空奖金表，重置会员表
            iBusiService.clear();
            return new ResponseData(true,"核算失败，请联系管理员确认计算问题.");
        }finally {

        }
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
            String purchaserCode,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order){
        if (!StringUtils.hasText(purchaserCode)){
            purchaserCode = CommonUtil.topUser; //默认查询顶级会员网络
        }
        MySqlPagination page = iBusiService.queryPageNetWork(startIndex,pageSize,purchaserCode,sort,order);
        return page;
    }

    /**
     * 查看当前用户的树状分支结构图
     * @param purchaserCode
     * @return
     */
    @RequestMapping(value = "/pageNetList/{purchaserCode}",method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getTreeGraph(@PathVariable String purchaserCode){
        return iBusiService.getTreeGraph(purchaserCode);
    }

}
