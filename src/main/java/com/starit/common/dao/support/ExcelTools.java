package com.starit.common.dao.support;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Excel导入导出工具
 */
public class ExcelTools {

    private String sql;
    private int limit;
    private JdbcTemplate jdbcTemplate;
    private int nextIndex = 3; //内容行的索引，从第三行开始
    private int startPage = 1; //默认从第一页开始写入
    private SXSSFWorkbook wb;
    private Map<String,Object> tableInfo;

    public  ExcelTools(String sql,int limit, JdbcTemplate jdbcTemplate){
        this.sql = sql;
        this.jdbcTemplate = jdbcTemplate;
        this.limit = limit;
    }

    /**
     * 设置Excel的头部前缀信息
     * @param tableInfo
     */
    public void setTableInfo(Map<String,Object> tableInfo){
        this.tableInfo = tableInfo;
    }

    /**
     * 数据写入Excel表格
     * @param headerMap 表头名称映射表
     * @param outputStream   输出流
     */
    public void createExcel( Map<String,String> headerMap,OutputStream outputStream){
        //获得字段数组
        String[] fields = new String[headerMap.keySet().size()];
        Iterator iterator = headerMap.keySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            //调整列的大小
            Object key = iterator.next();
            fields[index] = key.toString();
            index ++ ;
        }
        //限制内存只保留多少条数据，其他数据写入硬盘
        wb = new SXSSFWorkbook(100);
        Sheet sh = wb.createSheet();

        //设置主题样式
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex()); //橘红色
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);   //线性填充
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);   //字体加粗
        style.setFont(font);
        //计算总页数
        int totalPages = getTotalPage();
        //写入表头前缀信息
        String tableType = tableInfo.get("tableType").toString();
        if (tableType.equals("NETWORK"))
        {
            writeHeaderPrefixWithNetworkTable(sh);

        }else if (tableType.equals("SHOPBONUS"))
        {
            writeHeaderPrefixWithShopBouns(sh);
        }
        //写入表头
        writeHeader(sh,headerMap,fields,style);

        //写入表数据
        if(totalPages>0){
            MySqlPagination nextPage = null;
            List<Map<String,Object>> nextDataList = null;
            for (int pagenum =this.startPage;pagenum<=totalPages;pagenum++){
                nextPage =new MySqlPagination(sql, pagenum ,limit, jdbcTemplate);
                nextDataList  = nextPage.getResult();
                writeContent(sh,nextDataList,fields);
                nextDataList = null;
                nextPage = null;
            }
        }
        //调整列宽
        for (int i = 0; i < fields.length; i++) {
            sh.autoSizeColumn(i);
        }
        //测试输出
        //http://blog.csdn.net/sunshinestation/article/details/4383953
        try {
            wb.write(outputStream);
            //手动关闭流
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络业绩表的表头前缀
     * @param sh
     */
    private  void writeHeaderPrefixWithNetworkTable(Sheet sh){

        //定义样式
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);   //字体加粗
        style.setFont(font);

        //第一行创建标题
        Row row0 = sh.createRow(0);
        Cell cell0 = row0.createCell((short) 0);
        cell0.setCellValue("NETWORK INFORMATION OF ANGEL DISTRIBUTOR");
        cell0.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(0,0,0,9));
        //标题居中对齐
        CellUtil.setAlignment(cell0, wb, CellStyle.ALIGN_CENTER);

        //第二行创建基本信息
        Row row1 = sh.createRow(1);
        Cell cell1 = row1.createCell((short) 0);
        cell1.setCellValue("DISTRIBOTOR ID: "+tableInfo.get("purchaserCode"));
        cell1.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(1,1,0,2));
        CellUtil.setAlignment(cell1, wb, CellStyle.ALIGN_CENTER);
        Cell cell2 = row1.createCell((short) 3);
        cell2.setCellValue("YEAR MONTH: "+tableInfo.get("yearMonth"));
        cell2.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(1,1,3,6));
        Cell cell3 = row1.createCell((short) 7);
        cell3.setCellValue("RECORDS: "+tableInfo.get("totalRecords"));
        cell3.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(1,1,7,9));
        CellUtil.setAlignment(cell3, wb, CellStyle.ALIGN_CENTER);

    }

    /**
     * 奖金发放表的表头前缀
     * @param sh
     */
    private void writeHeaderPrefixWithShopBouns(Sheet sh){
        //定义样式
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);   //字体加粗
        style.setFont(font);

        //第一行创建标题
        Row row0 = sh.createRow(0);
        Cell cell0 = row0.createCell((short) 0);
        cell0.setCellValue("BONUS LIST OF ANGEL");
        cell0.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(0,0,0,6));
        //标题居中对齐
        CellUtil.setAlignment(cell0, wb, CellStyle.ALIGN_CENTER);

        //第二行创建基本信息
        Row row1 = sh.createRow(1);
        Cell cell1 = row1.createCell((short) 0);
        cell1.setCellValue("Nation: CG");
        cell1.setCellStyle(style);

        Cell cell2 = row1.createCell((short) 1);
        cell2.setCellValue("Branch: ("+tableInfo.get("shopCode")+")");
        cell2.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(1,1,1,3));
        //CellUtil.setAlignment(cell2, wb, CellStyle.ALIGN_LEFT);

        Cell cell3 = row1.createCell((short) 4);
        cell3.setCellValue("Pay Period: "+tableInfo.get("yearMonth"));
        cell3.setCellStyle(style);
        sh.addMergedRegion(new CellRangeAddress(1,1,4,5));

        Cell cell4 = row1.createCell((short) 6);
        cell4.setCellValue("(BV Unit: USD)");
        cell4.setCellStyle(style);
    }


    /**
     * 写表头
     * @param sh
     * @param headerMap  - 表头名称
     * @param fields     - 字段数组
     */
    private void  writeHeader(Sheet sh ,Map<String,String> headerMap,String[] fields ,CellStyle style){
        //第三行创建表头
        Row row2 = sh.createRow(2);
        //遍历字段,创建Cell
        for (int cellnum=0,len = fields.length;cellnum<len;cellnum++){
            Cell cell = row2.createCell(cellnum);
            String headerName = headerMap.get(fields[cellnum]);
            cell.setCellValue(headerName);
            cell.setCellStyle(style);
        }
    }

    /**
     * 写表数据
     * @param sh
     * @param dataList  -数据集合(Map<字段,数据>)
     * @param fields    -字段数组
     */
    private void writeContent(Sheet sh , List<Map<String,Object>> dataList,String[] fields){
        //数据实际长度
        int rowLength = dataList.size();
        if (rowLength>0){
            //如果有数据,则创建表头
            for (int rownum = 0; rownum < rowLength; rownum++){
                //获取当前List中的一条记录
                Map<String,Object> record =   dataList.get(rownum);
                //从第四行开始创建行，每次创建下一行的时候都要迭代加一
                Row row = sh.createRow(this.nextIndex);
                //根据当前行号创建所有列
                for(int cellnum = 0,len = fields.length; cellnum < len; cellnum++){
                    Cell cell = row.createCell(cellnum);
                    //获取当前字段
                    String field = fields[cellnum];
                    //获取字段值
                    Object value =  record.get(field);
                    //转换字段值
                    cell.setCellValue(value.toString());
                }
                this.nextIndex++; //继续读取下一行
            }
        }
    }

    /**
     * 计算总页数
     * @return
     */
    private int getTotalPage(){
        int totalPages = 0;
        int totalRecords = (Integer)tableInfo.get("totalRecords");
        if(totalRecords % limit == 0){
            totalPages = totalRecords / limit;
        }else{
            totalPages = (totalRecords / limit) + 1;
        }
        return totalPages;
    }

}
