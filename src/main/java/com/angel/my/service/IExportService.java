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
        try {
            fOut = response.getOutputStream();
            // 进行转码，使其支持中文文件名
            encodedFileName = java.net.URLEncoder.encode(printDate+"-network-"+purchaserCode, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + encodedFileName  + ".xlsx");
            String sql_network_information = CommonUtil.sql_network_information.replace("?",purchaserCode);
            ExcelTools tools =  new ExcelTools(sql_network_information,10,jdbcTemplate);
            tools.createExcel(headerMap,fOut);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        try {
            fOut = response.getOutputStream();
            zos = new ZipOutputStream(fOut);
            //将excel文件都保存到临时文件目录 /tmp/excel/
            List<String> purchaserCodeList = itPurchaserInfoService.findAllId();
            for (int i = 0,len=purchaserCodeList.size(); i <len; i++) {
                String purchaserCode = purchaserCodeList.get(i);
                fileOutputStream = new FileOutputStream(dir+ printDate + "-network-"+ purchaserCode + ".xlsx");
                String sql_network_information = CommonUtil.sql_network_information.replace("?",purchaserCode);
                tools =  new ExcelTools(sql_network_information,10,jdbcTemplate);
                tools.createExcel(headerMap,fileOutputStream);
            }
            //文件写入完毕，此时打包处理
            zipFile(file, "/", zos);
            zos.flush();
            zos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        try {
            fOut = response.getOutputStream();
            String sql_shop_bonus = CommonUtil.sql_specialty_shop_bonus_list.replace("?",shopCode);
            ExcelTools tools =  new ExcelTools(sql_shop_bonus,10,jdbcTemplate);
            tools.createExcel(headerMap,fOut);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        try {
            fOut = response.getOutputStream();
            zos = new ZipOutputStream(fOut);
            //将excel文件都保存到临时文件目录 /tmp/excel/shop/
            List<String> shopCodeList = itShopInfoService.findAllId();
            for (int i = 0,len=shopCodeList.size(); i <len; i++) {
                String shopCode = shopCodeList.get(i);
                fileOutputStream = new FileOutputStream(dir+ printDate + "-shop-"+ shopCode + ".xlsx");
                String sql_network_information = CommonUtil.sql_specialty_shop_bonus_list.replace("?",shopCode);
                tools =  new ExcelTools(sql_network_information,10,jdbcTemplate);
                tools.createExcel(headerMap,fileOutputStream);
            }
            //文件写入完毕，此时打包处理
            zipFile(file, "/", zos);
            zos.flush();
            zos.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
}
