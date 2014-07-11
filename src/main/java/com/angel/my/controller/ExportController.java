package com.angel.my.controller;

import com.angel.my.service.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 导入导出工具
 * @doc http://www.gojs.net/latest/samples/familyTree.html
 * @Jiang
 */
@Controller
@RequestMapping("/exportController")
public class ExportController {

    @Autowired
    private IExportService iExportService;

    /**
     * 导出会员网络图
     * @param purchaserCode
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportExcelNetwork",method = RequestMethod.GET)
    public void exportExcelNetwork(@RequestParam(required = false) String purchaserCode ,
                                    HttpServletRequest request, HttpServletResponse response){

        //Excel头部信息(标题)
        Map<String,String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("TIER","TIER");
        headerMap.put("PURCHASER_ID_NAME","The Distributor ID/NAME");
        headerMap.put("SPONSOR_ID_NAME","Sponsor's ID/NAME");
        headerMap.put("SHOP_CODE","Shop ID");
        headerMap.put("RANK_NAME","Rank");
        headerMap.put("ATNPV","Accumulate PV");
        headerMap.put("APPV","Personal APV");
        headerMap.put("TNPV","TNPV");
        headerMap.put("GPV","GPV");
        headerMap.put("PPV","Personal PV/BV");
        if (purchaserCode == null){  //文件打包导出
            iExportService.exportExcelNetworkZip(headerMap,request,response);
        }else{
            iExportService.exportExcelNetwork(headerMap,purchaserCode,request,response);
        }
    }

    /**
     * 导出店铺奖金发放表
     * @param shopCode
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportExcelShopBonus",method = RequestMethod.GET)
    public void exportExcelShopBonus(@RequestParam(required = false) String shopCode ,
                                   HttpServletRequest request, HttpServletResponse response){
        //Excel头部信息(标题)
        Map<String,String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("PURCHASER_CODE","Distributor ID");
        headerMap.put("PURCHASER_NAME","Distributor NAME");
        headerMap.put("TOTAL_BOUNS","Total Bonus");
        headerMap.put("COMPUTOR_FEE","Computer Fee");
        headerMap.put("TAXATION","Taxation");
        headerMap.put("REAL_WAGES","Real Wages");
        headerMap.put("SIGNATURE","Signature");
        if (shopCode == null){  //文件打包导出
            iExportService.exportExcelShopBonusZip(headerMap,request,response);
        }else{
            iExportService.exportExcelShopBonus(headerMap,shopCode,request,response);
        }
    }


}
