package com.angel.my.service;

import com.angel.my.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务计算服务
 */
@Service
public class IBusiService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 计算PPV - 个人业绩
     * 定义: 一个业绩核算月内，以本人编号购买的PV之和
     * @param purchaserCode
     * @return
     */
    public double getPPV(String purchaserCode,String lastMonDate , String thisMonDate){
        //String lastMonDate =  DateUtil.getLastMonDate(28);
        //String thisMonDate = DateUtil.getToday();
        //String purchaserCode = "000047";
        String sql = "SELECT SUM(t.PV) " +
                            " FROM  t_order t " +
                            " WHERE t.purchaser_code = '"+purchaserCode+"' " +
                            " AND   t.sale_time > '"+lastMonDate+"' AND t.sale_time < '"+thisMonDate+"'";
        String sqlnull = "SELECT IFNULL("+sql+",'0')  AS ppv";
        Map map = jdbcTemplate.queryForMap(sqlnull);
        return CommonUtil.getDoubleCeil(map.get("ppv").toString());
    }

    /**
     * 计算APPV - 个人累计业绩
     * 定义:直销商自加入公司，所有以本人编号购买产品PV之和
     * @param purchaserCode
     * @return
     */
    public double getAPPV(String purchaserCode){
        String sql = "SELECT SUM(t.PV)  FROM t_order t WHERE t.purchaser_code = '"+purchaserCode+"'";
        String sqlnull = "SELECT IFNULL("+sql+",'0')  AS appv";
        Map map = jdbcTemplate.queryForMap(sqlnull);
        return CommonUtil.getDoubleCeil(map.get("appv").toString());
    }

    /**
     * 计算整网业绩(TNPV)
     * 定义:一个业绩核算月内，本人及其所有下线直销商个人业绩（PPV）之和
     * @param purchaserCode
     * @param PPV   本人当月个人业绩
     * @return
     */
    public double getTNPV(String purchaserCode ,double PPV,String lastMonDate , String today){
        //计算本人所有下线从上个月到本月结算时的整网业绩
        String sql = " SELECT SUM(t1.PV) " +
                     " FROM t_order t1 LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                     " WHERE t2.upper_codes LIKE '%"+purchaserCode+"%' " +
                     " AND t1.sale_time>'"+lastMonDate+"' AND t1.sale_time<'"+today+"'";
        String sqlnull = "SELECT IFNULL("+sql+",'0')  AS tnpv";
        Map map = jdbcTemplate.queryForMap(sqlnull);
        double d = CommonUtil.getDoubleCeil(map.get("tnpv").toString());

        //加上自己在本月的个人业绩 == 本人的整网业绩
        double total = d+PPV;
        return total;
    }

    /**
     * 计算累计整网业绩(ATNPV)
     * 定义:直销商自加入公司，本人及其所有下线直销商的个人累计业绩(APPV)之和
     * @param purchaserCode
     * @param APPV   本人累计个人业绩
     * @return
     */
    public double getATNPV(String purchaserCode ,double APPV){
        //计算本人下线从上个月到本月结算时的累计整网业绩
        String sql = " SELECT SUM(t1.PV) " +
                " FROM t_order t1 LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                " WHERE t2.upper_codes LIKE '%"+purchaserCode+"%' ";
        String sqlnull = "SELECT IFNULL("+sql+",'0')  AS atnpv";
        Map map = jdbcTemplate.queryForMap(sqlnull);
        double d = CommonUtil.getDoubleCeil(map.get("atnpv").toString());

        //加上本人的个人累计业绩 == 本人的累计整网业绩
        double total = d+APPV;
        return total;
    }

    /**
     *  计算星级
     *  @param purchaserCode
     *  @param PPV  - 个人业绩
     *  @param APPV - 累计个人业绩
     *  @param ATNPV - 累计整网业绩
     */
    public String getRANK(String purchaserCode,double PPV,double APPV,double ATNPV){

        /*****************下面是基于购买PV情况的判定**********************/
        String rankCode = "102001";
        //一星判定
        if ((PPV<100 && PPV>=0)|| (ATNPV<100 && ATNPV>=0)) {
            rankCode = "102001";
            return rankCode;
        }
        //二星判定
        if ((PPV<200 && PPV>=100)||(APPV<200 && APPV>=100)|| (ATNPV<350 && ATNPV>=100)) {
            rankCode = "102002";
            return rankCode;
        }
        //三星判定
        if ((PPV<1000 && PPV>=200)||(APPV<1000 && APPV>=200)|| (ATNPV<1000 && ATNPV>=350)) {
            rankCode = "102003";
            return rankCode;
        }
        //四星判定
        if ((PPV<3800 && PPV>=1000)||(APPV<3800 && APPV>=1000)) {
            rankCode = "102004";
            return rankCode;
        }
        //五星判定
        if ((PPV<16000 && PPV>=3800)||(APPV<16000 && APPV>=3800)) {
            rankCode = "102005";
            return rankCode;
        }
        //六星判定
        if ((PPV<73000 && PPV>=16000)||(APPV<73000 && APPV>=16000)) {
            rankCode = "102006";
            return rankCode;
        }
        //七星判定
        if ((PPV<280000 && PPV>=73000)||(APPV<280000 && APPV>=73000)) {
            rankCode = "102007";
            return rankCode;
        }
        //九星判定
        if ((PPV>=400000)||(APPV>=400000)) {
            rankCode = "102009";
            return rankCode;
        }
        //八星判定
        if ((PPV<580000 && PPV>=280000)||(APPV<580000 && APPV>=280000)) {
            rankCode = "102008";
            return rankCode;
        }

        /************************对于无法满足以上购买情况的会员，继续可以通过判定其网络结构得出星级***************************/
        //求有几条直接网络下线
        String directLineSql = "SELECT t.rank_code,t.purchaser_code FROM t_purchaser t WHERE t.sponsor_code = '"+purchaserCode+"'";
        List list = jdbcTemplate.queryForList(directLineSql);
        int size = list.size(); //得到直接网络下线的个数
        //满足至少2个网络
        if(size>=2){
            //直接网络下线的结构（下线编号=下线保存的各星级个数映射表）
            Map directLine = new HashMap();
            for (int i = 0; i <size ; i++) {
                Map t = (Map)list.get(i);
                Object purchaser_code = t.get("purchaser_code");
                Object rank_code = t.get("rank_code"); //本会员的等级
                Map mm = getRankTableByPurchaserCode(purchaser_code.toString()); //他下线会员的等级映射表
                Object count = mm.get(rank_code); //查找本会员等级在下线表中的个数，因为这个等级属于这条网络，所以必须算入在内
                /*if(count!=null){ //下线有相同等级的话就加1
                    mm.put(rank_code, new Long(((Long)count).intValue()+1)); //可以优化，无需转换
                }else{ //下线没有相同等级的话,就放到下线映射表里供规则读取
                    mm.put(rank_code,new Long(1));
                }*/
                if(count == null){ //下线没有相同等级的话,就放到下线映射表里供规则读取
                    mm.put(rank_code,new Long(1));
                }
                t = null;
                //最后将该下线的编号与等级表放入规则映射表 -- 以方便待会儿读取
                directLine.put(purchaser_code,mm);
            }

            //计算直接网络里各分支3~8星直销商的个数
            int count3=0,count4=0,count5=0,count6=0,count7=0,count8=0;
            for (Object value : directLine.values()) {  //遍历每个直销商
                Map mm = (Map)value;
                if (mm.get("102003")!=null)
                    count3++;
                if (mm.get("102004")!=null)
                    count4++;
                if (mm.get("102005")!=null)
                    count5++;
                if (mm.get("102006")!=null)
                    count6++;
                if (mm.get("102007")!=null)
                    count7++;
                if (mm.get("102008")!=null)
                    count8++;
                mm = null;
            }

            //根据规则(网络结构)判断符合条件
            if((ATNPV>=400000 && count8 >=3) || (ATNPV>=780000 && count8 >=2)){
                rankCode = "102009";
                return rankCode;
            }
            if((ATNPV>=280000 && 400000>ATNPV && count7 >=3) || (ATNPV>=580000 && 780000>ATNPV && count7 >=2)){
                rankCode = "102008";
                return rankCode;
            }
            if((ATNPV>=73000 && 280000>ATNPV && count6 >=3) || (ATNPV>=145000 && 580000>ATNPV && count6 >=2)){
                rankCode = "102007";
                return rankCode;
            }
            if((ATNPV>=16000 && 73000>ATNPV && count5 >=3) || (ATNPV>=35000 && 145000>ATNPV && count5 >=2)){
                rankCode = "102006";
                return rankCode;
            }
            if((ATNPV>=3800 && 16000>ATNPV && count4 >=3) || (ATNPV>=7800 && 35000>ATNPV && count4 >=2)){
                rankCode = "102005";
                return rankCode;
            }
            if((ATNPV>=1000 && 3800>ATNPV && count3 >=3) || (ATNPV>=2200 && 7800>ATNPV && count3 >=2)){
                rankCode = "102004";
                return rankCode;
            }

        }
        //至少有三个网络中各有一名3*及3*以上级别直销商，且累计整网业绩（ATNPV）≥1000（即直销商3800＞ ATNPV≥1000），当月升为4*直销商
        return rankCode;
    }

    /**
     * 计算小组业绩(GPV)
     * 定义: 本人下线网络中除去同职级及以上职级网络以外的业绩(就是本人TNPV-同职级或比他级别高的下线的TNPV)
     * @param purchaserCode
     * @param TNPV - 本人整网业绩
     * @param rankCode - 星级
     * @return
     */
    public double getGPV(String purchaserCode,String rankCode,double TNPV){

        //小组业绩第一种算法：有错误
       /* String sql = " SELECT SUM(t1.TNPV) " +
                     " FROM t_achieve t1" +
                     " LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                     " WHERE  t2.upper_codes LIKE '%"+purchaserCode+"%' AND t2.rank_code >= '"+rankCode+"'";
        String sqlnull = "SELECT IFNULL("+sql+",'0')  AS gpv";
        Map map = jdbcTemplate.queryForMap(sqlnull);
        double d = CommonUtil.getDoubleCeil(map.get("gpv").toString());

        //本人TNPV - 下线的同职级或高职级的TNPV == 小组业绩
        double d2 = TNPV - d;
        return d2;*/
        //小组业绩第二种算法：待验证
       //获取同职级或同职级以上的所有下线会员(不包含在同一网络)
        List srcList  = getDownLineHigherRankPurchaser(purchaserCode,rankCode);
        //得到剔除后的scrList,计算小组业绩：把TNPV相加
        double sum = 0;
        for (int i = 0,len = srcList.size(); i <len; i++) {
            double d = (Double)((Map)srcList.get(i)).get("TNPV");
            sum += d;
        }
        //本人的整网业绩-下线整网业绩
        double d2 = TNPV - sum;

        return  d2;
    }


//////////////////////////////////////////下面是奖金的计算：直接奖、间接奖、领导奖/////////////////////////////////////////////////////////////////

    /**
     * 计算直接奖(DBV)
     * @param purchaserCode - 会员编号
     * @param rankCode - 会员星级
     * @return
     */
    public double getDBV(String purchaserCode,String rankCode,double PPV){
        double d = PPV * CommonUtil.directRateConstant.get(rankCode);
        return d;
    }

    /**
     * 计算间接奖(IBV)
     * @return
     */
    public double getIBV(String purchaserCode,String rankCode){
        //剔除直接下线中职级比他高的会员
        String sql = "SELECT t1.purchaser_code,t1.rank_code,t1.TNPV " +
                " FROM t_achieve t1 " +
                "    LEFT JOIN t_purchaser t2 " +
                "    ON t1.purchaser_code = t2.purchaser_code " +
                " WHERE t2.sponsor_code = '"+purchaserCode+"' " +
                "    AND t1.rank_code < '"+rankCode+"' ";

        List directDownList = jdbcTemplate.queryForList(sql);
        //直接下线的个数
        int size = directDownList.size();
        //间接奖
        double ibv = 0;
        if (size>0){
            for (int i = 0; i <size ; i++) {
                Map mm = (Map)directDownList.get(i);
                //获得直接下线的编号
                String  downChildCode = (String)mm.get("purchaser_code");
                //获得直接下线的等级
                String  downChildRankCode = (String)mm.get("rank_code");
                double downChildTNPV = (Double)mm.get("TNPV");
                //获得直接下线的下线网络职级比他上线高的会员
                List currentDownList = getDownLineHigherRankPurchaser(downChildCode,rankCode);
                double sum = 0;
                for (int j = 0,len = currentDownList.size(); j <len; i++) {
                    double d = (Double)((Map)currentDownList.get(j)).get("TNPV");
                    sum += d;
                }
                //其中一条间接网络的TNPV=直接下线的TNPV-直接下线那条网络符合条件的节点的TNPV
                double directTNPV = downChildTNPV - sum;
                double rate = CommonUtil.directRateConstant.get(rankCode)-CommonUtil.directRateConstant.get(downChildCode);
                //其中一条直接网络的间接奖
                double oneDownChildBouns = directTNPV * rate;
                ibv += oneDownChildBouns;
            }
        }
        return ibv;
    }

    /**
     * 计算领导奖(LBV)
     * @return
     */
    public double getLBV(String purchaserCode,String rankCode,double GPV){
        double gpv = 0;
        if (rankCode == "102005" && GPV>=600){//5星会员
            String sql = "SELECT " +
                    "  t2.GPV " +
                    "FROM t_purchaser t1 " +
                    "  LEFT JOIN t_achieve t2 " +
                    "    ON t1.purchaser_code = t2.purchaser_code " +
                    "WHERE t1.sponsor_code = '"+purchaserCode+"' " +
                    "    AND t1.rank_code >= '"+rankCode+"'";
            List gpvList = jdbcTemplate.queryForList(sql);
            for (int i = 0,len = gpvList.size(); i <len ; i++) {
                gpv +=(Double)gpvList.get(i)*0.01;
            }
            gpvList = null;
            return gpv;
        }
        if (rankCode == "102006" && GPV>=1000){ //6星会员

        }
        return  0;
    }

    ////////////////////////////////////////以下是业务逻辑调用/////////////////////////
    /**
     * 根据上级会员获取下线会员等级及其个数的映射表【星级，个数】
     * @param purchaserCode - 上级会员编号
     * @return
     */
    public Map getRankTableByPurchaserCode(String purchaserCode){

        String sql = " SELECT t.rank_code,COUNT(t.rank_code) AS count FROM t_purchaser t " +
                " WHERE t.upper_codes LIKE '%"+purchaserCode+"%' GROUP BY t.rank_code ORDER BY t.rank_code";
        List list = jdbcTemplate.queryForList(sql);
        Map map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            Map t =  (Map)list.get(i);
            map.put(t.get("rank_code"), t.get("count"));
            t = null;
        }
        return map;
    }

    /**
     * 获取同职级或同职级以上的所有下线会员(不包含在同一网络)
     * @param purchaserCode 查询的祖先会员编号
     * @param rankCode      祖先的等级
     * @return
     */
    public List getDownLineHigherRankPurchaser(String purchaserCode,String rankCode){
        String sql = " SELECT t2.floors,t2.purchaser_code,t2.upper_codes,t1.TNPV " +
                " FROM t_achieve t1" +
                " LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                " WHERE  t2.upper_codes LIKE '%"+purchaserCode+"%' AND t2.rank_code >= '"+rankCode+"'";
        //Map包含会员编号,星级，上线编号集合,整网业绩
        List srcList = jdbcTemplate.queryForList(sql);
        List compareList = new ArrayList(srcList.size());

        if(srcList.size()>0){
            //复制list到compareList中
            for (int i = 0,size = srcList.size(); i < size; i++) {
                compareList.add(srcList.get(i));
            }
            //剔除srcList中重复的网络下线
            for (int i = 0,size = compareList.size(); i < size; i++) {
                //主比较对象
                Map mm =  (Map)compareList.get(i);
                String upper_codes = (String)mm.get("upper_codes");
                int floors = ((Long)mm.get("floors")).intValue();
                //被比较对象
                for (int j = 0,len = srcList.size(); j <len ; j++) {
                    //主比较对象的upper_codes比较被比较对象的purchaser_code,且星级不能相同,则移除元素
                    Map mj = (Map)srcList.get(j);
                    int tier = ((Long)mj.get("floors")).intValue();
                    String purchaser_code = (String)mj.get("purchaser_code");
                    if(tier != floors && upper_codes.contains(purchaser_code)){
                        srcList.remove(j);
                        j = j-1;
                    }
                }
            }
        }
        return srcList;
    }

    /**
     * 计算网络总共有几层
     * @return
     */
    public int getMaxFloor(){
        String sql = "SELECT MAX(t.floors) FROM t_purchaser t ";
        int max = jdbcTemplate.queryForObject(sql,int.class);
        return max;
    }

    /**
     * 获得第N层的所有会员编号
     * @param floor
     * @return
     */
    public List<String> getAllByFloor(int floor){
        String sql = "SELECT t.purchaser_code FROM t_purchaser t WHERE t.floors = '"+floor+"'";
        List list = jdbcTemplate.queryForObject(sql,List.class);
        return  list;
    }

    /**
     * 清除 - 业绩表和奖金表
     */
    public void clearTableData(){
         String truncate_TAchieve_sql = "TRUNCATE TABLE t_achieve";
         String truncate_TBoun_sql = "TRUNCATE TABLE t_bouns";
         jdbcTemplate.batchUpdate(new String[]{truncate_TAchieve_sql,truncate_TBoun_sql});
    }

}

