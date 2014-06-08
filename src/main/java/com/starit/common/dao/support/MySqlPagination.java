package com.starit.common.dao.support;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Mysql分页封装
 * @author Jiang
 */
public class MySqlPagination implements Serializable,Iterable{

    public static final int NUMBERS_PER_PAGE = 10;
    //一页显示的记录数
    private int numPerPage;
    //记录总数
    private int totalRecords;
    //总页数
    private int totalPages;
    //当前页码
    private int currPage;
    //起始行数
    private int startIndex;
    //结束行数
    private int lastIndex;
    //结果集存放List
    private List rows;
    //JdbcTemplate jTemplate
    private JdbcTemplate jTemplate;

    /**
     * 每页显示10条记录的构造函数,使用该函数必须先给Pagination设置currentPage，jTemplate初值
     * @param sql oracle语句
     */
    public MySqlPagination(String sql){
        if(jTemplate == null){
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
        }else if(sql.equals("")){
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
        }
        new MySqlPagination(sql,currPage,NUMBERS_PER_PAGE ,jTemplate);
    }

    /**分页构造函数
     * @param sql 根据传入的sql语句得到一些基本分页信息
     * @param currentPage 当前页
     * @param numPerPage 每页记录数
     * @param jTemplate JdbcTemplate实例
     */
    public MySqlPagination(String sql,int currentPage,int numPerPage,JdbcTemplate jTemplate){
        if(jTemplate == null){
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
        }else if(sql == null || sql.equals("")){
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
        }
        //设置每页显示记录数
        setNumPerPage(numPerPage);
        //设置要显示的页数
        setCurrentPage(currentPage);
        //计算总记录数
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sql);
        totalSQL.append(" ) totalTable ");
        //给JdbcTemplate赋值
        //setJdbcTemplate(jTemplate);
        //总记录数
        setTotalRows(jTemplate.queryForInt(totalSQL.toString()));
        //计算总页数
        setTotalPages();
        //计算起始行数
        setStartIndex();
        //计算结束行数
        setLastIndex();

        //构造oracle数据库的分页语句
        /** StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
         paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
         paginationSQL.append(sql);
         paginationSQL.append(" ) temp where ROWNUM <= " + lastIndex);
         paginationSQL.append(" ) WHERE num > " + startIndex);
         */


        //装入结果集
        setResult(jTemplate.queryForList(getMySQLPageSQL(sql, startIndex, numPerPage)));
    }



    /**
     * 构造MySQL数据分页SQL 
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @return
     */
    @JSONField(serialize=false)
    public String getMySQLPageSQL(String queryString, Integer startIndex, Integer pageSize)
    {
        String result = "";
        if (null != startIndex && null != pageSize)
        {
            result = queryString + " limit " + startIndex + "," + pageSize;
        } else if (null != startIndex && null == pageSize)
        {
            result = queryString + " limit " + startIndex;
        } else
        {
            result = queryString;
        }
        return result;
    }



    public int getCurrentPage() {
        return currPage;
    }

    public void setCurrentPage(int currPage) {
        this.currPage = currPage;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    @JSONField(name="rows")
    public List getResult() {
        return rows;
    }

    public void setResult(List resultList) {
        this.rows = resultList;
    }

    public int getTotalPages() {
        return totalPages;
    }
    //计算总页数
    public void setTotalPages() {
        if(totalRecords % numPerPage == 0){
            this.totalPages = totalRecords / numPerPage;
        }else{
            this.totalPages = (totalRecords / numPerPage) + 1;
        }
    }

    @JSONField(name="total")
    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRows(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @JSONField(serialize=false)
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex() {
        this.startIndex = (currPage - 1) * numPerPage;
    }

    @JSONField(serialize=false)
    public int getLastIndex() {
        return lastIndex;
    }

    @JSONField(serialize=false)
    public JdbcTemplate getJTemplate() {
        return jTemplate;
    }

    public void setJTemplate(JdbcTemplate template) {
        jTemplate = template;
    }

    //计算结束时候的索引
    public void setLastIndex() {
        //System.out.println("totalRecords="+totalRecords);///////////
        //System.out.println("numPerPage="+numPerPage);///////////
        if( totalRecords < numPerPage){
            this.lastIndex = totalRecords;
        }else if((totalRecords % numPerPage == 0) || (totalRecords % numPerPage != 0 && currPage < totalPages)){
            this.lastIndex = currPage * numPerPage;
        }else if(totalRecords % numPerPage != 0 && currPage == totalPages){//最后一页
            this.lastIndex = totalRecords ;
        }
    }

    public Iterator iterator() {
        return (Iterator) (rows == null ? Collections.emptyList().iterator() : rows.iterator());
    }

}