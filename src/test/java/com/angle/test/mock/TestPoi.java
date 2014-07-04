package com.angle.test.mock;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * POI常规方法测试
 */
public class TestPoi {
    public static void main(String[] args) {
        String path = "D:\\poiout\\workbook.xls";
        TestPoi testPoi =  new  TestPoi();
        testPoi.exportFile(path);
    }

    public void exportFile(String path ){

        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        for(int rownum = 0; rownum < 1000; rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum < 10; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }

        }

        // Rows with rownum < 900 are flushed and not accessible
        for(int rownum = 0; rownum < 900; rownum++){
            //Assert.assertNull(sh.getRow(rownum));
        }

        // ther last 100 rows are still in memory
        for(int rownum = 900; rownum < 1000; rownum++){
            //Assert.assertNotNull(sh.getRow(rownum));
        }

        try {
            FileOutputStream out = new FileOutputStream(path);
            wb.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }


    public void buildExceleFromTable(){
        //获取sql查询的数据列表List<Map<字段,Value>>
        //List.size = Row && Map.size = Col
        //列头(Header)名称 : Map<字段,HeaderName>  = 应该是遍历对象




    }

    //创建版本
    private Sheet getVersion(){
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        return sh;
    }

    /**
     * 创建Excel
     * @param headerMap 表头名称
     * @param dataList  数据集合(分页数据)
     */
    private void CreateAll(Map<String,String> headerMap , List<Map<String,Object>> dataList,String outpath){

        //获得所有字段
        String[] fields = (String[])headerMap.keySet().toArray();

        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        int rowLength = dataList.size();
        if (rowLength>0){
            //如果有数据,则创建表头
            CreateHeader(sh,headerMap,fields);
            for (int rownum = 0; rownum < rowLength; rownum++){
                //获取当前List中的一条记录
                Map<String,Object> record =   dataList.get(rownum);
                //从第二行开始创建Row，所以要 rownum+1
                Row row = sh.createRow(rownum+1);
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
            }
            //测试输出
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(outpath);
                wb.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建表头
     * @param sh
     * @param headerMap - 表头
     * @param fields    - 字段
     */
    private void  CreateHeader(Sheet sh ,Map<String,String> headerMap,String[] fields ){
        //第一行创建表头
        Row row = sh.createRow(0);
        //遍历字段,创建Cell
        for (int cellnum=0,len = fields.length;cellnum<len;cellnum++){
            Cell cell = row.createCell(cellnum);
            String headerName = headerMap.get(fields[cellnum]);
            cell.setCellValue(headerName);
        }
    }
    //创建行
    private void CreateRow(){

    }
    //创建列
    private void CreateColumn(){

    }

}

