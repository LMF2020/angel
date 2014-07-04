package com.starit.common.dao.support;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
    private int nextIndex = 1; //下一行的索引
    private int startPage = 1; //默认从第一页开始写入

    public  ExcelTools(String sql,int limit, JdbcTemplate jdbcTemplate){
        this.sql = sql;
        this.jdbcTemplate = jdbcTemplate;
        this.limit = limit;
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
            Object key = iterator.next();
            fields[index] = key.toString();
            index ++ ;
        }
        //限制内存只保留多少条数据，其他数据写入硬盘
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sh = wb.createSheet();
        //写入表头
        writeHeader(sh,headerMap,fields);
        //计算总页数
        int totalPages = getTotalPage();
        //写入表数据
        if(totalPages>0){
            MySqlPagination nextPage = null;
            List<Map<String,Object>> nextDataList = null;
            for (int pagenum =this.startPage;pagenum<=totalPages;pagenum++){
                nextPage =new MySqlPagination(sql, pagenum ,limit, jdbcTemplate);
                nextDataList  = nextPage.getResult();
                writeContent(sh,nextDataList,fields,this.nextIndex);
                nextDataList = null;
                nextPage = null;
            }
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
     * 写表头
     * @param sh
     * @param headerMap  - 表头名称
     * @param fields     - 字段数组
     */
    private void  writeHeader(Sheet sh ,Map<String,String> headerMap,String[] fields ){
        //第一行创建表头
        Row row = sh.createRow(0);
        //遍历字段,创建Cell
        for (int cellnum=0,len = fields.length;cellnum<len;cellnum++){
            Cell cell = row.createCell(cellnum);
            String headerName = headerMap.get(fields[cellnum]);
            cell.setCellValue(headerName);
        }
    }

    /**
     * 写表数据
     * @param sh
     * @param dataList  -数据集合(Map<字段,数据>)
     * @param fields    -字段数组
     */
    private void writeContent(Sheet sh , List<Map<String,Object>> dataList,String[] fields ,int nextIndex){
        //数据实际长度
        int rowLength = dataList.size();
        if (rowLength>0){
            //如果有数据,则创建表头
            for (int rownum = 0; rownum < rowLength; rownum++){
                //获取当前List中的一条记录
                Map<String,Object> record =   dataList.get(rownum);
                //从第二行开始创建Row，所以要 rownum+1
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
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(this.sql);
        totalSQL.append(" ) totalTable ");
        int totalRecords = jdbcTemplate.queryForInt(totalSQL.toString());
        if(totalRecords % limit == 0){
            totalPages = totalRecords / limit;
        }else{
            totalPages = (totalRecords / limit) + 1;
        }
        return totalPages;
    }

}
