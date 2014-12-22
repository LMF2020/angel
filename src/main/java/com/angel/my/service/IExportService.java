package com.angel.my.service;

import com.angel.my.util.CommonUtil;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.ExcelTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 导入导出业务
 */
@Service
public class IExportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ITPurchaserInfoService itPurchaserInfoService;
    @Autowired
    private ITShopInfoService itShopInfoService;

    /**
     * 导出指定会员的网络图
     *
     * @param headerMap
     * @param purchaserCode 会员编号
     * @param request
     * @param response
     */
    public void exportExcelNetwork(Map<String,String> headerMap,String purchaserCode ,
                                   HttpServletRequest request, HttpServletResponse response){
        String printDate = DateUtil.getPrintDate();
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        // 生成提示信息
        response.setContentType("application/vnd.ms-excel");
        String encodedFileName = null;
        OutputStream fOut = null;
        Map<String,Object> tableInfo = new HashMap<String, Object>();
        tableInfo.put("tableType","NETWORK");
        try {
            fOut = response.getOutputStream();
            // 进行转码，使其支持中文文件名
            encodedFileName = java.net.URLEncoder.encode(printDate+"-network-"+purchaserCode, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + encodedFileName  + ".xlsx");
            String sql_network_information = CommonUtil.sql_network_information.replace("?",purchaserCode);
            //执行存储过程，改变临时表的存储结构
            jdbcTemplate.execute("CALL angel.showTreeNodes('"+purchaserCode+"');");
            //执行导出函数
            ExcelTools tools =  new ExcelTools(sql_network_information,10,jdbcTemplate);
            tableInfo.put("totalRecords",getTotalRecords(sql_network_information));
            tableInfo.put("purchaserCode",purchaserCode);
            tableInfo.put("yearMonth",DateUtil.getYearMonth());
            tools.setTableInfo(tableInfo);
            tools.createExcel(headerMap,fOut);
            tableInfo.clear();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tableInfo = null;
            try
            {
                fOut.flush();
                fOut.close();
            }
            catch (IOException e)
            {}
            session.setAttribute("state", "open");
        }
    }
    /**
     * 打包所有会员的网络图并导出
     *
     * @param headerMap
     * @param request
     * @param response
     */
    public void exportExcelNetworkZip(Map<String,String> headerMap,HttpServletRequest request, HttpServletResponse response){
        String dir = "/tmp/excel/network/";
        String printDate = DateUtil.getPrintDate();
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        // 设置header信息
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + printDate+"-network-all"  + ".zip");

        ExcelTools tools = null;
        OutputStream fOut = null;
        ZipOutputStream zos = null;
        FileOutputStream fileOutputStream = null;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String,Object> tableInfo = new HashMap<String, Object>();
        tableInfo.put("tableType","NETWORK");
        try {
            fOut = response.getOutputStream();
            zos = new ZipOutputStream(fOut);
            //将excel文件都保存到临时文件目录 /tmp/excel/
            List<String> purchaserCodeList = itPurchaserInfoService.findAllId();
            for (int i = 0,len=purchaserCodeList.size(); i <len; i++) {
                String purchaserCode = purchaserCodeList.get(i);
                fileOutputStream = new FileOutputStream(dir+ printDate + "-network-"+ purchaserCode + ".xlsx");
                String sql_network_information = CommonUtil.sql_network_information.replace("?",purchaserCode);
                //执行存储过程，改变临时表的存储结构
                jdbcTemplate.execute("CALL angel.showTreeNodes('"+purchaserCode+"');");
                tools =  new ExcelTools(sql_network_information,10,jdbcTemplate);
                tableInfo.put("totalRecords",getTotalRecords(sql_network_information));
                tableInfo.put("purchaserCode",purchaserCode);
                tableInfo.put("yearMonth",DateUtil.getYearMonth());
                tools.setTableInfo(tableInfo);
                tools.createExcel(headerMap,fileOutputStream);
            }
            tableInfo.clear();
            //文件写入完毕，此时打包处理
            zipFile(file, "/", zos);
            zos.flush();
            zos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tableInfo = null;
            try
            {
                fOut.flush();
                fOut.close();
            }
            catch (IOException e)
            {}
            //删除目录/tmp/excel/network/下所有文件
            FileSystemUtils.deleteRecursively(file);
            session.setAttribute("state", "open");
        }
    }

    /**
     * 导出指定店铺奖金发放表
     *
     * @param headerMap
     * @param shopCode  店铺编号
     * @param request
     * @param response
     */
    public void exportExcelShopBonus(Map<String,String> headerMap,String shopCode ,
                                   HttpServletRequest request, HttpServletResponse response){
        String printDate = DateUtil.getPrintDate();
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        // 生成提示信息
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment;filename=" + printDate+"-"+shopCode  + ".xlsx");

        OutputStream fOut = null;
        Map<String,Object> tableInfo = new HashMap<String, Object>();
        tableInfo.put("tableType","SHOPBONUS");
        try {
            fOut = response.getOutputStream();
            String sql_shop_bonus = CommonUtil.sql_specialty_shop_bonus_list.replace("?",shopCode);
            ExcelTools tools =  new ExcelTools(sql_shop_bonus,10,jdbcTemplate);
            tableInfo.put("totalRecords",getTotalRecords(sql_shop_bonus));
            tableInfo.put("shopCode",shopCode);
            tableInfo.put("yearMonth",DateUtil.getYearMonth());
            tools.setTableInfo(tableInfo);
            tools.createExcel(headerMap,fOut);
            tableInfo.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tableInfo = null;
            try
            {
                fOut.flush();
                fOut.close();
            }
            catch (IOException e)
            {}
            session.setAttribute("state", "open");
        }
    }

    /**
     * 打包所有店铺奖金发放表并导出
     *
     * @param headerMap
     * @param request
     * @param response
     */
    public void exportExcelShopBonusZip(Map<String,String> headerMap,HttpServletRequest request, HttpServletResponse response){
        String dir = "/tmp/excel/shop/";
        String printDate = DateUtil.getPrintDate();
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        // 设置header信息
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + printDate+"-shop-all"  + ".zip");

        ExcelTools tools = null;
        OutputStream fOut = null;
        ZipOutputStream zos = null;
        FileOutputStream fileOutputStream = null;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        Map<String,Object> tableInfo = new HashMap<String, Object>();
        tableInfo.put("tableType","SHOPBONUS");
        try {
            fOut = response.getOutputStream();
            zos = new ZipOutputStream(fOut);
            //将excel文件都保存到临时文件目录 /tmp/excel/shop/
            List<String> shopCodeList = itShopInfoService.findAllId();
            for (int i = 0,len=shopCodeList.size(); i <len; i++) {
                String shopCode = shopCodeList.get(i);
                fileOutputStream = new FileOutputStream(dir+ printDate + "-shop-"+ shopCode + ".xlsx");
                String sql_shop_bonus = CommonUtil.sql_specialty_shop_bonus_list.replace("?",shopCode);
                tools =  new ExcelTools(sql_shop_bonus,10,jdbcTemplate);
                tableInfo.put("totalRecords",getTotalRecords(sql_shop_bonus));
                tableInfo.put("shopCode",shopCode);
                tableInfo.put("yearMonth",DateUtil.getYearMonth());
                tools.setTableInfo(tableInfo);
                tools.createExcel(headerMap,fileOutputStream);
            }
            tableInfo.clear();
            //文件写入完毕，此时打包处理
            zipFile(file, "/", zos);
            zos.flush();
            zos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tableInfo = null;
            try
            {
                fOut.flush();
                fOut.close();
            }
            catch (IOException e)
            {}
            //删除目录/tmp/excel/shop/下所有文件
            FileSystemUtils.deleteRecursively(file);
            session.setAttribute("state", "open");
        }
    }

    /**
     * 文件打包(私有方法)
     * @param f  文件或者目录
     * @param baseName  根目录
     * @param zos   zip输出流
     * @throws IOException
     */
    private void zipFile(File f, String baseName, ZipOutputStream zos)
            throws IOException {
        if (f.exists() && f.isFile()) {
            zos.putNextEntry(new ZipEntry(f.getName()));

            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int r = 0;
            while ((r = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, r);
            }
            fis.close();
        } else {
            String dirName = baseName + f.getName() + "/";
            //zos.putNextEntry(new ZipEntry(f.getName())); //目录名称
            File[] subs = f.listFiles();
            for (File file : subs) {
                zipFile(file, dirName, zos);
            }
        }

    }


    /**
     * 导出指定星级的会员
     *
     * @param headerMap
     * @param rankCode  星级编号
     * @param isCheck   是否只导出当月
     * @param request
     * @param response
     */
    public void exportFilterExcelByRank(Map<String,String> headerMap,String rankCode ,String isCheck,
                                     HttpServletRequest request, HttpServletResponse response){
        String printDate = DateUtil.getPrintDate();
        HttpSession session = request.getSession();
        session.setAttribute("state", null);
        String rankName = rankCode.substring(rankCode.length()-1);
        // 生成提示信息
        response.setContentType("application/vnd.ms-excel");
        String flag = isCheck.equals("1")?"-add":"-all";
        response.setHeader("content-disposition", "attachment;filename=" + printDate+"-star"+rankName+ flag +".xlsx");

        OutputStream fOut = null;
        Map<String,Object> tableInfo = new HashMap<String, Object>();
        tableInfo.put("tableType","DIST_OF_RANK");  //表的类型
        try {
            fOut = response.getOutputStream();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT" +
                    "  t1.purchaser_code," +
                    "  t3.purchaser_name," +
                    "  t1.rank_code," +
                    "  t3.shop_code," +
                    "  t4.rank_name," +
                    "  t3.create_time," +
                    "  CONCAT(t1.PPV,'/',t1.PBV) AS PVBV," +
                    "  t1.APPV," +
                    "  t1.ATNPV" +
                    " FROM t_achieve t1" +
                    "  LEFT JOIN t_purchaser t3" +
                    "    ON t3.purchaser_code = t1.purchaser_code" +
                    "  LEFT JOIN t_rank t4" +
                    "    ON t1.rank_code = t4.rank_code" +
                    " WHERE t1.rank_code = '"+rankCode+"'");
            if(isCheck!=null && isCheck.equals("1")){
                String lastMonth = DateUtil.getLastMonDate(1).substring(0,7);
                sb.append(" AND NOT EXISTS(SELECT" +
                        " 1" +
                        " FROM t_achieve_his t2" +
                        " WHERE t1.purchaser_code = t2.purchaser_code" +
                        " AND SUBSTRING(t2.achieve_date,1,7) = '"+lastMonth+"'" +
                        " AND t1.rank_code = t2.rank_code)");
            }
            ExcelTools tools =  new ExcelTools(sb.toString(),10,jdbcTemplate);
            tableInfo.put("totalRecords",getTotalRecords(sb.toString()));
            tableInfo.put("rank",rankName);
            tableInfo.put("yearMonth",DateUtil.getYearMonth());
            tools.setTableInfo(tableInfo);
            tools.createExcel(headerMap,fOut);
            tableInfo.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tableInfo = null;
            try
            {
                fOut.flush();
                fOut.close();
            }
            catch (IOException e)
            {}
            session.setAttribute("state", "open");
        }
    }

    /**
     * 根据查询sql语句获取总记录数
     * @param sql
     * @return
     */
    private int getTotalRecords(String sql){
        int totalPages = 0;
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sql);
        totalSQL.append(" ) totalTable ");
        int totalRecords = jdbcTemplate.queryForInt(totalSQL.toString());
        return totalRecords;
    }
}
