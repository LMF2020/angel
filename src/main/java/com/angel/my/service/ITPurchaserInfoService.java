package com.angel.my.service;

import com.angel.my.dao.ITPurchaserInfoDao;
import com.angel.my.model.TPurchaserInfo;
import com.angel.my.util.DateUtil;
import com.starit.common.dao.support.MySqlPagination;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ITPurchaserInfoService {
	
	@Autowired
	private ITPurchaserInfoDao ITPurchaserInfoDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	//获取登陆会员信息session
	public boolean getLoginModel(HttpServletRequest request, HttpServletResponse response, HttpSession session, TPurchaserInfo user, String usercode , String password){
		return ITPurchaserInfoDao.getLoginModel(request, response, session, user, usercode, password);
	}
	
	//分页查询会员列表
	public Pagination<TPurchaserInfo> findAllForPage(PaginationRequest<TPurchaserInfo> pageRequest) {
		return ITPurchaserInfoDao.findPage(pageRequest);
	}

    /*根据Hql分页查询*/
    public Pagination<Object> findPageByHQL(String rowSql ,String countSql,int offset,int limit){
        return ITPurchaserInfoDao.findPageByHQL(rowSql, countSql, offset, limit);
    }

    /*判断该经销商是否没有下线分支*/
    public boolean findNotExistChild(String parentCode){
        List children =  ITPurchaserInfoDao.findByHQL("FROM TPurchaserInfo T WHERE T.sponsorCode = ? ",parentCode);
        if (children.isEmpty()){
            return true;
        }
        return false;
    }

    //查询一个会员
    public TPurchaserInfo loadUser(String code){
        return ITPurchaserInfoDao.get(code);
    }

    //新增一个会员
    public String addUser(TPurchaserInfo user){
        return ITPurchaserInfoDao.save(user);
    }

    //编辑一个会员
    public void updateUser(TPurchaserInfo updateUser){
        ITPurchaserInfoDao.update(updateUser);
    }

    //编辑一个会员和他的所有下属节点
    public void updateUserCascade(TPurchaserInfo updateUser,String oldUpperCodes,String newUpperCodes){
        updateChildNodes(updateUser.getPurchaserCode(),oldUpperCodes,newUpperCodes);
        ITPurchaserInfoDao.update(updateUser);
    }

    //删除指定的会员列表
    public void destoryUser(String code){
        ITPurchaserInfoDao.delete(code);
    }

    //查询所有经销商编码
    public List<String> findAllId(){
        return ITPurchaserInfoDao.findByHQL("SELECT T.purchaserCode FROM TPurchaserInfo T ");
    }

    /**
     * 更新我的所有下属节点
     * @param myCode        我的编码
     * @param newUpperCodes 新的父节点UpperCodes编码
     * @param oldUpperCodes 老的父节点UpperCodes编码
     * @return
     */
    public boolean updateChildNodes(String myCode, String oldUpperCodes,String newUpperCodes) {
        //查询我的直接孩子节点
        String Hql = "FROM TPurchaserInfo T WHERE T.sponsorCode = ?";
        List<TPurchaserInfo> downLine = ITPurchaserInfoDao.findByHQL(Hql,myCode);
        for (TPurchaserInfo down : downLine){
           String downUpperCodes =  down.getUpperCodes();
           down.setUpperCodes(downUpperCodes.replaceFirst(oldUpperCodes,newUpperCodes));
           //执行递归更新down的所有下属节点
           updateChildNodes(down.getPurchaserCode(),oldUpperCodes,newUpperCodes);
        }
        return true;
    }

    /**
     * 查询每个星级的会员
     *      通过isCheck=1判断是否需要只显示当月星级新增的会员
     */
    public MySqlPagination pageFilterListByRank(int startIndex,int limit,String rankCode,String isCheck,String lastMonth){
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
        //过滤掉上个月星级相同的会员
        if(isCheck!=null && isCheck.equals("1")){
            sb.append(" AND NOT EXISTS(SELECT" +
                        " 1" +
                        " FROM t_achieve_his t2" +
                        " WHERE t1.purchaser_code = t2.purchaser_code" +
                        " AND SUBSTRING(t2.achieve_date,1,7) = '"+lastMonth+"'" +
                        " AND t1.rank_code = t2.rank_code)");
        }
        MySqlPagination page=new MySqlPagination(sb.toString(), startIndex, limit, jdbcTemplate);
        return page;
    }
    /**
     * 按（编号、结算月）查询会员历史业绩
     */
    public MySqlPagination pageQueryUserGradeByMon(int startIndex,int limit,String purchaserCode,String achieveDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                "  t2.achieve_date," +
                "  t2.purchaser_code," +
                "  t1.purchaser_name," +
                "  t1.create_time," +
                "  t3.rank_name," +
                "  t1.shop_code," +
                "  t1.sponsor_code," +
                "  t2.PPV," +
                "  t2.DBV," +
                "  t2.IBV," +
                "  t2.ATNPV," +
                "  t2.TNPV," +
                "  t2.GPV," +
                "  t2.APPV" +
                " FROM t_purchaser t1" +
                "  LEFT JOIN t_achieve_his t2" +
                "    ON t1.purchaser_code = t2.purchaser_code" +
                "  LEFT JOIN t_rank t3" +
                "    ON t3.rank_code = t2.rank_code" +
                " WHERE t1.purchaser_code = '"+purchaserCode+"' ");

        if(StringUtils.isNotEmpty(achieveDate)){
            String first = DateUtil.get1stDayOfDate(achieveDate);
            String last = DateUtil.getLastDayOfDate(achieveDate);
            sb.append(" AND t2.achieve_date BETWEEN '"+first+" 00:00:00'AND '"+last+" 23:59:59'");
        }
        MySqlPagination page=new MySqlPagination(sb.toString(), startIndex, limit, jdbcTemplate);
        return page;
    }

}