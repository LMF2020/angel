package com.angel.my.service;

import com.angel.my.util.CommonUtil;
import com.starit.common.dao.support.MySqlPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 业务计算服务
 * @author Jiang
 */
@Service
public class IBusiService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DecimalFormat decimalFormat =  CommonUtil.getFormatInstance();
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
        String sql = "SELECT SUM(t.PV) AS  PPV " +
                            " FROM  t_order t " +
                            " WHERE t.purchaser_code = '"+purchaserCode+"' " +
                            " AND   t.sale_time > '"+lastMonDate+"' AND t.sale_time < '"+thisMonDate+"'";
        Map mm = jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("PPV");
        return dd==null?0:(dd.doubleValue());
    }

    /**
     * 计算PBV - 个人当月奖金
     * 定义: 一个业绩核算月内，以本人编号购买的PV之和
     * @param purchaserCode
     * @return
     */
    public double getPBV(String purchaserCode,String lastMonDate , String thisMonDate){
        String sql = "SELECT SUM(t.BV) AS  PBV " +
                " FROM  t_order t " +
                " WHERE t.purchaser_code = '"+purchaserCode+"' " +
                " AND   t.sale_time > '"+lastMonDate+"' AND t.sale_time < '"+thisMonDate+"'";
        Map mm = jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("PBV");
        return dd==null?0:(dd.doubleValue());
    }

    /**
     * 计算APPV - 个人累计业绩
     * 定义:直销商自加入公司，所有以本人编号购买产品PV之和
     * @param purchaserCode
     * @return
     */
    public double getAPPV(String purchaserCode){
        String sql = "SELECT SUM(t.PV) AS APPV FROM t_order t WHERE t.purchaser_code = '"+purchaserCode+"'";
        Map mm = jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("APPV");
        return dd==null?0:dd.doubleValue();
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
        String sql = " SELECT SUM(t1.PV) AS TNPV " +
                     " FROM t_order t1 LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                     " WHERE t2.upper_codes LIKE '%"+purchaserCode+"%' " +
                     " AND t1.sale_time>'"+lastMonDate+"' AND t1.sale_time<'"+today+"'";
        Map mm = jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("TNPV");
        double d = (dd==null?0:dd.doubleValue());

        //加上自己在本月的个人业绩 == 本人的整网业绩
        double total = d+PPV;
        return total;
    }

    /**
     * 计算整网奖金(TNBV)
     * 定义:一个业绩核算月内，本人及其所有下线直销商个人奖金（PBV）之和
     * @param purchaserCode
     * @param PBV   本人当月个人BV
     * @return
     */
    public double getTNBV(String purchaserCode ,double PBV,String lastMonDate , String today){
        //计算本人所有下线从上个月到本月结算时的整网业绩
        String sql = " SELECT SUM(t1.BV) AS TNBV " +
                " FROM t_order t1 LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                " WHERE t2.upper_codes LIKE '%"+purchaserCode+"%' " +
                " AND t1.sale_time>'"+lastMonDate+"' AND t1.sale_time<'"+today+"'";
        Map mm = jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("TNBV");
        double d = (dd==null?0:dd.doubleValue());

        //加上自己在本月的BV和== 本人的整网奖金
        double total = d+PBV;
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
        String sql = " SELECT SUM(t1.PV) AS ATNPV " +
                " FROM t_order t1 LEFT JOIN t_purchaser t2 ON t1.purchaser_code = t2.purchaser_code " +
                " WHERE t2.upper_codes LIKE '%"+purchaserCode+"%' ";
        Map mm = jdbcTemplate.queryForMap(sql);
        Double dd = (Double)mm.get("ATNPV");
        double d = (dd==null?0:dd.doubleValue());
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
        //$$计算结构 -- TNPV==========================================================================================
        String rankCode = "102001";

        //二星判定
        if (ATNPV<200 && ATNPV>=100) {
            rankCode = "102002";
            return rankCode;
        }
        //一星判定
        if (ATNPV<100 && ATNPV>=0) {
            rankCode = "102001";
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
                t = null;
                //最后将该下线的编号与等级表放入规则映射表 -- 以方便待会儿读取
                directLine.put(purchaser_code,mm);
            }

            //计算直接网络里3星及3星以上(4星及4星以上......)
            int count3=0,count4=0,count5=0,count6=0,count7=0,count8=0;
            //遍历下线网络各等级的分支数目
            //每一个循环都看做一条分支
            for (Object value : directLine.values()) {
                Map m = (Map)value;
                Iterator it = m.keySet().iterator();
                while(it.hasNext()){  //如果发现有大于102003的情况就停止第一条分支的循环且count3计数器加1
                    String  rankcode = (String)it.next();
                    if (Integer.parseInt(rankcode)>=102003){
                        count3++;
                        break;
                    }
                }
            }
            //几条4星以上
            for (Object value : directLine.values()) {
                Map m = (Map)value;
                Iterator it = m.keySet().iterator();
                while(it.hasNext()){  //如果发现有大于102003的情况就停止第一条分支的循环且count4计数器加1
                    String  rankcode = (String)it.next();
                    if (Integer.parseInt(rankcode)>=102004){
                        count4++;
                        break;
                    }
                }
            }
            //几条5星以上
            for (Object value : directLine.values()) {
                Map m = (Map)value;
                Iterator it = m.keySet().iterator();
                while(it.hasNext()){  //如果发现有大于102005的情况就停止第一条分支的循环且count3计数器加1
                    String  rankcode = (String)it.next();
                    if (Integer.parseInt(rankcode)>=102005){
                        count5++;
                        break;
                    }
                }
            }
            //几条6星以上
            for (Object value : directLine.values()) {
                Map m = (Map)value;
                Iterator it = m.keySet().iterator();
                while(it.hasNext()){  //如果发现有大于102006的情况就停止第一条分支的循环且count3计数器加1
                    String  rankcode = (String)it.next();
                    if (Integer.parseInt(rankcode)>=102006){
                        count6++;
                        break;
                    }
                }
            }
            //几条七星以上
            for (Object value : directLine.values()) {
                Map m = (Map)value;
                Iterator it = m.keySet().iterator();
                while(it.hasNext()){  //如果发现有大于102007的情况就停止第一条分支的循环且count3计数器加1
                    String  rankcode = (String)it.next();
                    if (Integer.parseInt(rankcode)>=102007){
                        count7++;
                        break;
                    }
                }
            }
            //几条8星
            for (Object value : directLine.values()) {
                Map m = (Map)value;
                Iterator it = m.keySet().iterator();
                while(it.hasNext()){  //如果发现有大于102008的情况就停止第一条分支的循环且count3计数器加1
                    String  rankcode = (String)it.next();
                    if (Integer.parseInt(rankcode)>=102008){
                        count8++;
                        break;
                    }
                }
            }

            //根据网络结构)判断符合条件
            if((ATNPV>=400000 && count8 >=3) || (ATNPV>=780000 && count8 >=2)){
                rankCode = "102009";
                return rankCode;
            }
            if((ATNPV>=280000 && count7 >=3) || (ATNPV>=580000 && count7 >=2)){
                rankCode = "102008";
                return rankCode;
            }
            if((ATNPV>=73000 && count6 >=3) || (ATNPV>=145000  && count6 >=2)){
                rankCode = "102007";
                return rankCode;
            }
            if((ATNPV>=16000 && count5 >=3) || (ATNPV>=35000 && count5 >=2)){
                rankCode = "102006";
                return rankCode;
            }
            if((ATNPV>=3800 && count4 >=3) || (ATNPV>=7800 && count4 >=2)){
                rankCode = "102005";
                return rankCode;
            }
            if((ATNPV>=1000  && count3 >=3) || (ATNPV>=2200 && count3 >=2)){
                rankCode = "102004";
                return rankCode;
            }

        }
        //$$计算结构 -- APPV==========================================================================================

        //九星判定
        if (APPV>=400000) {
            rankCode = "102009";
            return rankCode;
        }
        //八星判定
        if (APPV<400000 && APPV>=280000) {
            rankCode = "102008";
            return rankCode;
        }
        //七星判定
        if (APPV<280000 && APPV>=73000) {
            rankCode = "102007";
            return rankCode;
        }
        //六星判定
        if (APPV<73000 && APPV>=16000) {
            rankCode = "102006";
            return rankCode;
        }
        //五星判定
        if (APPV<16000 && APPV>=3800) {
            rankCode = "102005";
            return rankCode;
        }
        //四星判定
        if (APPV<3800 && APPV>=1000) {
            rankCode = "102004";
            return rankCode;
        }
        //三星ATNPV判定
        if (ATNPV>=200) {
            rankCode = "102003";
            return rankCode;
        }
        //三星判定
        if (APPV<1000 && APPV>=200) {
            rankCode = "102003";
            return rankCode;
        }
        //二星判定
        if (APPV<200 && APPV>=100) {
            rankCode = "102002";
            return rankCode;
        }
        //一星判定
        if (0<APPV && APPV<100) {
            rankCode = "102001";
            return rankCode;
        }

        //$$计算结构 -- PPV==========================================================================================
        //九星判定
        if (PPV>=400000) {
            rankCode = "102009";
            return rankCode;
        }
        //八星判定
        if (PPV<400000 && PPV>=280000) {
            rankCode = "102008";
            return rankCode;
        }
        //七星判定
        if (PPV<280000 && PPV>=73000) {
            rankCode = "102007";
            return rankCode;
        }
        //六星判定
        if (PPV<73000 && PPV>=16000) {
            rankCode = "102006";
            return rankCode;
        }
        //五星判定
        if (PPV<16000 && PPV>=3800) {
            rankCode = "102005";
            return rankCode;
        }
        //四星判定
        if (PPV<3800 && PPV>=1000) {
            rankCode = "102004";
            return rankCode;
        }
        //三星判定
        if (PPV<1000 && PPV>=200) {
            rankCode = "102003";
            return rankCode;
        }
        //二星判定
        if (PPV<200 && PPV>=100) {
            rankCode = "102002";
            return rankCode;
        }
        //一星判定
        if (PPV<100 && PPV>=0) {
            rankCode = "102001";
            return rankCode;
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
//        if(purchaserCode.equals("000001")){
//            System.out.println("--====");
//        }
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
    public double getDBV(String purchaserCode,String rankCode,double PBV){
        double d = PBV * CommonUtil.directRateConstant.get(rankCode);
        String s = decimalFormat.format(d);
        return Double.valueOf(s);
    }

    /**
     * 计算间接奖(IBV)
     * @return
     */
    public double getIBV(String purchaserCode,String rankCode){
        //剔除直接下线中职级比他高的会员
        String sql = "SELECT t1.purchaser_code,t1.rank_code,t1.TNBV " +
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
                //获得直接下线的TNPV
                double downChildTNBV = (Double)mm.get("TNBV");
                //获得直接下线的下线网络职级比他上线高的会员
                List currentDownList = getDownLineHigherRankPurchaser(downChildCode,rankCode);
                double sum = 0;
                for (int j = 0,len = currentDownList.size(); j <len; j++) {
                    double d = (Double)((Map)currentDownList.get(j)).get("TNBV");
                    sum += d;
                }
                //其中一条间接网络的TNPV=直接下线的TNPV-直接下线那条网络符合条件的节点的TNPV
                double directTNBV = downChildTNBV - sum;
                double rate = CommonUtil.directRateConstant.get(rankCode)-CommonUtil.directRateConstant.get(downChildRankCode);
                //其中一条直接网络的间接奖
                double oneDownChildBouns = directTNBV * rate;
                ibv += oneDownChildBouns;
            }
        }

        String s = decimalFormat.format(ibv);
//        if (purchaserCode.equals("000003")){
//            System.out.println();
//        }
        return Double.valueOf(s);
    }

    /**
     * 计算领导奖(LBV)
     * @return
     */
    public double getLBV(String purchaserCode,String rankCode,double GPV){

//        if (purchaserCode.equals("000001")){
//            System.out.println("====");
//        }

        double LB = 0;
        //五星会员
        if (rankCode == "102005" && GPV>=600){

            //找出同职级或以上职级中网络靠前的所有下线会员
            List headChildList = getDownLineHigherRankPurchaser(purchaserCode,rankCode);
            for (int i = 0,len=headChildList.size(); i <len ; i++) {
                Map mm = (Map)headChildList.get(i);
                LB +=(Double)mm.get("GPV")*0.01;
            }
            String s = decimalFormat.format(LB);
            return Double.valueOf(s);
        }
        //六星会员
        if (rankCode == "102006" && GPV>=1000){
            //查询当前会员的直接下线
            String childrenSql = "SELECT t.purchaser_code FROM t_purchaser t WHERE t.sponsor_code = '"+purchaserCode+"'";
            List childList = jdbcTemplate.queryForList(childrenSql);
            //查询当前会员有几条合格的N
            int N = getN(childList);
            //最多可以提取几代领导奖   
            int maxN = (N+1)>3?3:(N+1);
            //遍历每一个直接下线网络,然后汇总//purchaserCode,floor,GPV
            //找出同职级或以上职级中网络靠前的所有下线会员
            List headChildList = getDownLineHigherRankPurchaser(purchaserCode,rankCode);
            for (int i = 0,len=headChildList.size(); i <len ; i++) {
                Map mm = (Map)headChildList.get(i);
                String childCode = (String)mm.get("purchaser_code");
                //当前有效会员的代数（以这个为计算条件）
                Integer floor = (Integer)mm.get("floors");
                int limit = floor+maxN;
                String sql = "SELECT t2.GPV,t1.floors " +
                        "FROM t_purchaser t1 " +
                        "  LEFT JOIN t_achieve t2 " +
                        "    ON t1.purchaser_code = t2.purchaser_code " +
                        "WHERE (t1.upper_codes LIKE '%"+childCode+"%' OR t1.purchaser_code='"+childCode+"') " +
                        "    AND t1.floors < "+limit+" " +
                        "    AND t1.rank_code >= '"+rankCode+"'";
               //获取该同职级会员的N代内的下线
               List resultList =  jdbcTemplate.queryForList(sql);
               for (int j = 0,size=resultList.size(); j <size ; j++) {
                   Map nn = (Map)resultList.get(j);
                   int nnFloor = (Integer)nn.get("floors");
                   double nnGPV = (Double)nn.get("GPV");
                   if (nnFloor == floor){ //第一代提取0.1
                        LB+=nnGPV*0.01;
                        continue;
                   }else if (nnFloor == floor+1 || nnFloor == floor+2){ //第二代或者第三代提取0.05
                        LB+=nnGPV*0.005;
                   }

               }
            }
            String s = decimalFormat.format(LB);
            return Double.valueOf(s);
        }

        //七星会员
        if (rankCode == "102007" && GPV>=2000){
            //查询当前会员的直接下线
            String childrenSql = "SELECT t.purchaser_code FROM t_purchaser t WHERE t.sponsor_code = '"+purchaserCode+"'";
            List childList = jdbcTemplate.queryForList(childrenSql);
            //查询当前会员有几条合格的N
            int N = getN(childList);
            //最多可以提取几代领导奖
            int maxN = (N+1)>5?5:(N+1);
            //遍历每一个直接下线网络,然后汇总//purchaserCode,floor,GPV
            //找出同职级或以上职级中网络靠前的所有下线会员
            List headChildList = getDownLineHigherRankPurchaser(purchaserCode,rankCode);
            for (int i = 0,len=headChildList.size(); i <len ; i++) {
                Map mm = (Map)headChildList.get(i);
                String childCode = (String)mm.get("purchaser_code");
                //当前有效会员的代数（以这个为计算条件）
                Integer floor = (Integer)mm.get("floors");
                int limit = floor+maxN;
                String sql = "SELECT t2.GPV,t1.floors " +
                        "FROM t_purchaser t1 " +
                        "  LEFT JOIN t_achieve t2 " +
                        "    ON t1.purchaser_code = t2.purchaser_code " +
                        "WHERE (t1.upper_codes LIKE '%"+childCode+"%' OR t1.purchaser_code='"+childCode+"') " +
                        "    AND t1.floors < "+limit+" " +
                        "    AND t1.rank_code >= '"+rankCode+"'";
                //获取该同职级会员的N代内的下线
                List resultList =  jdbcTemplate.queryForList(sql);
                for (int j = 0,size=resultList.size(); j <size ; j++) {
                    Map nn = (Map)resultList.get(j);
                    int nnFloor = (Integer)nn.get("floors");
                    double nnGPV = (Double)nn.get("GPV");  //???
                    if (nnFloor == floor){ //第一代提取0.1
                        LB+=nnGPV*0.01;
                        continue;
                    }else if (nnFloor == floor+1 || nnFloor == floor+2 ||
                               nnFloor == floor+3 || nnFloor == floor+4){ //第二代,三代,四代,五代提取0.05
                        LB+=nnGPV*0.005;
                    }

                }
            }
            String s = decimalFormat.format(LB);
            return Double.valueOf(s);
        }
        //八星会员
        if (rankCode == "102008" && GPV>=3000){
            //查询当前会员的直接下线
            String childrenSql = "SELECT t.purchaser_code FROM t_purchaser t WHERE t.sponsor_code = '"+purchaserCode+"'";
            List childList = jdbcTemplate.queryForList(childrenSql);
            //查询当前会员有几条合格的N
            int N = getN(childList);
            //最多可以提取几代领导奖
            int maxN = (N+1)>8?8:(N+1);
            //遍历每一个直接下线网络,然后汇总//purchaserCode,floor,GPV
            //找出同职级或以上职级中网络靠前的所有下线会员
            List headChildList = getDownLineHigherRankPurchaser(purchaserCode,rankCode);
            for (int i = 0,len=headChildList.size(); i <len ; i++) {
                Map mm = (Map)headChildList.get(i);
                String childCode = (String)mm.get("purchaser_code");
                //当前有效会员的代数（以这个为计算条件）
                Integer floor = (Integer)mm.get("floors");
                int limit = floor+maxN;
                String sql = "SELECT t2.GPV,t1.floors " +
                        "FROM t_purchaser t1 " +
                        "  LEFT JOIN t_achieve t2 " +
                        "    ON t1.purchaser_code = t2.purchaser_code " +
                        "WHERE (t1.upper_codes LIKE '%"+childCode+"%' OR t1.purchaser_code='"+childCode+"') " +
                        "    AND t1.floors < "+limit+" " +
                        "    AND t1.rank_code >= '"+rankCode+"'";
                //获取该同职级会员的N代内的下线
                List resultList =  jdbcTemplate.queryForList(sql);
                for (int j = 0,size=resultList.size(); j <size ; j++) {
                    Map nn = (Map)resultList.get(j);
                    int nnFloor = (Integer)nn.get("floors");
                    double nnGPV = (Double)nn.get("GPV");  //???
                    if (nnFloor == floor){ //第一代提取0.1
                        LB+=nnGPV*0.02;
                        continue;
                    }else  if (nnFloor == floor+1){ //第二代提取0.1
                        LB+=nnGPV*0.01;
                        continue;
                    }else if (nnFloor == floor+2 || nnFloor == floor+3 ){ //第三代,四代提取0.05
                        LB+=nnGPV*0.005;
                        continue;
                    }else if (nnFloor == floor+4 || nnFloor == floor+5 ){ //第五代,六代提取0.03
                        LB+=nnGPV*0.003;
                        continue;
                    }else if (nnFloor == floor+6 || nnFloor == floor+7 ){ //第七代,八代提取0.02
                        LB+=nnGPV*0.002;
                    }

                }
            }
            String s = decimalFormat.format(LB);
            return Double.valueOf(s);
        }
        //九星会员
        if (rankCode == "102009" && GPV>=3000){
            //查询当前会员的直接下线
            String childrenSql = "SELECT t.purchaser_code FROM t_purchaser t WHERE t.sponsor_code = '"+purchaserCode+"'";
            List childList = jdbcTemplate.queryForList(childrenSql);
            //查询当前会员有几条合格的N
            int N = getN(childList);
            //最多可以提取几代领导奖
            int maxN = (N+1)>8?8:(N+1);
            //遍历每一个直接下线网络,然后汇总//purchaserCode,floor,GPV
            //找出同职级或以上职级中网络靠前的所有下线会员
            List headChildList = getDownLineHigherRankPurchaser(purchaserCode,rankCode);
            for (int i = 0,len=headChildList.size(); i <len ; i++) {
                Map mm = (Map)headChildList.get(i);
                String childCode = (String)mm.get("purchaser_code");
                //当前有效会员的代数（以这个为计算条件）
                Integer floor = (Integer)mm.get("floors");
                int limit = floor+maxN;
                String sql = "SELECT t2.GPV,t1.floors " +
                        "FROM t_purchaser t1 " +
                        "  LEFT JOIN t_achieve t2 " +
                        "    ON t1.purchaser_code = t2.purchaser_code " +
                        "WHERE (t1.upper_codes LIKE '%"+childCode+"%' OR t1.purchaser_code='"+childCode+"') " +
                        "    AND t1.floors < "+limit+" " +
                        "    AND t1.rank_code >= '"+rankCode+"'";
                //获取该同职级会员的N代内的下线
                List resultList =  jdbcTemplate.queryForList(sql);
                for (int j = 0,size=resultList.size(); j <size ; j++) {
                    Map nn = (Map)resultList.get(j);
                    int nnFloor = (Integer)nn.get("floors");
                    double nnGPV = (Double)nn.get("GPV");
                    if (nnFloor == floor){ //第一代提取0.1
                        LB+=nnGPV*0.02;
                        continue;
                    }else  if (nnFloor == floor+1){ //第二代提取0.1
                        LB+=nnGPV*0.01;
                        continue;
                    }else if (nnFloor == floor+2 || nnFloor == floor+3 ){ //第三代,四代提取0.05
                        LB+=nnGPV*0.005;
                        continue;
                    }else if (nnFloor == floor+4 || nnFloor == floor+5 ){ //第五代,六代提取0.03
                        LB+=nnGPV*0.003;
                        continue;
                    }else if (nnFloor == floor+6 || nnFloor == floor+7 ){ //第七代,八代提取0.02
                        LB+=nnGPV*0.002;
                    }
                }
            }
            String s = decimalFormat.format(LB);
            return Double.valueOf(s);
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
//
//        String sql = " SELECT t.rank_code,COUNT(t.rank_code) AS count FROM t_purchaser t " +
//                " WHERE t.upper_codes LIKE '%"+purchaserCode+"%' GROUP BY t.rank_code ORDER BY t.rank_code";

        String sql =  " SELECT t.rank_code,COUNT(t.rank_code) AS COUNT " +
                      " FROM t_purchaser t " +
                      " WHERE t.upper_codes LIKE '%"+purchaserCode+"%' OR t.purchaser_code = '"+purchaserCode+"' " +
                      " GROUP BY t.rank_code " +
                      " ORDER BY t.rank_code ";

        List list = jdbcTemplate.queryForList(sql);
        Map map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            Map t =  (Map)list.get(i);
            map.put(t.get("rank_code"), t.get("COUNT"));
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
        String sql = " SELECT t2.floors,t2.purchaser_code,t2.upper_codes,t1.TNPV,t1.TNBV,t1.GPV " +
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
                int floors = (Integer)mm.get("floors");
                String purchaser_code = (String)mm.get("purchaser_code");
                //被比较对象
                Iterator it= srcList.iterator();
                while(it.hasNext()){
                    Map mj =  (Map)it.next();
                    int tier = (Integer)mj.get("floors");
                    String upper_codes = (String)mj.get("upper_codes");
                    if(tier != floors && upper_codes.contains(purchaser_code)){
                        it.remove();
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
        int max = jdbcTemplate.queryForInt(sql);
        return max;
    }

    /**
     * 获得第N层的所有会员编号
     * @param floor
     * @return
     */
    public List<String> getAllByFloor(int floor){
        String sql = "SELECT t.purchaser_code FROM t_purchaser t WHERE t.floors = '"+floor+"'";
        List list = jdbcTemplate.queryForList(sql);
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

    /**
     * 查询有几条合格的N
     * @param childList
     * @return
     */
    private int getN(List childList){
        int N = 0; //初始化合格的N
        for (int i = 0,len = childList.size(); i <len; i++) {
            Map mm = (Map)childList.get(i);
            String directDownLineCode = (String)mm.get("purchaser_code");
            String NSql = "SELECT COUNT(1) " +
                    "FROM t_purchaser  t2 " +
                    "  LEFT JOIN t_achieve t1 " +
                    "    ON t1.purchaser_code = t2.purchaser_code " +
                    "WHERE t2.upper_codes LIKE '%"+directDownLineCode+"%' " +
                    "     OR t2.purchaser_code = '"+directDownLineCode+"' " +
                    "    AND t2.rank_code >= '102003' " +
                    "    AND t1.TNPV >= 2000";
            int result =  jdbcTemplate.queryForInt(NSql);
            if (result>0){
                N++;
            }
        }
        return N;
    }


    /**
     * 分页查询网络结构图
     * @param startIndex  当前页
     * @param limit   每页记录数
     * @return
     */
    public MySqlPagination queryPageNetWork(int startIndex,int limit,String purchaserCode,String sort,String order) {
        //按层次搜索
       /* String sql="SELECT " +
                "  t.floors AS TIER," +
                "  CONCAT(t.purchaser_code,'/',t.purchaser_name) AS PURCHASER_ID_NAME," +
                "  CONCAT(t.sponsor_code,'/',t.sponsor_name) AS SPONSOR_ID_NAME," +
                "  t3.rank_name      AS RANK_NAME," +
                "  t.shop_code      AS SHOP_CODE," +
                "  t1.ATNPV,"   +
                "  t1.APPV,"    +
                "  t1.TNPV,"    +
                "  t1.GPV,"     +
                "  CONCAT(t1.PPV,'/',t1.PBV)   AS PPV,"     +
                "  t2.direct_bouns   AS DB,"    +
                "  t2.indirect_bouns AS IB,"    +
                "  t2.leader_bouns   AS LB "    +
                "FROM t_purchaser t " +
                "  LEFT JOIN t_achieve t1 " +
                "    ON t.purchaser_code = t1.purchaser_code " +
                "  LEFT JOIN t_bouns t2 " +
                "    ON t2.purchaser_code = t.purchaser_code " +
                "  LEFT JOIN t_rank t3 "  +
                "    ON t.rank_code = t3.rank_code " +
                "WHERE t.purchaser_code = '"+purchaserCode+"' " +
                "     OR t.upper_codes LIKE '%"+purchaserCode+"%' " +
                "ORDER BY t.floors ASC";*/

        //首先调用存储过程,创建临时表(树的结构以及深度)
        jdbcTemplate.execute("CALL angel.showTreeNodes('"+purchaserCode+"');");
        //然后按深度查询
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT " +
                "  IF(ISNULL(B.nLevel),0,B.nLevel) AS TIER, " +
                "  CONCAT(t.purchaser_code,'/',t.purchaser_name) AS PURCHASER_ID_NAME, " +
                "  CONCAT(t.sponsor_code,'/',t.sponsor_name) AS SPONSOR_ID_NAME, " +
                "  t3.rank_name      AS RANK_NAME, " +
                "  t.shop_code       AS SHOP_CODE, " +
                "  t1.ATNPV, " +
                "  t1.APPV, " +
                "  t1.TNPV, " +
                "  t1.GPV, " +
                "  CONCAT(t1.PPV,'/',t1.PBV) AS PPV, " +
                "  t2.direct_bouns   AS DB, " +
                "  t2.indirect_bouns AS IB, " +
                "  t2.leader_bouns   AS LB " +
                " FROM t_purchaser t " +
                "  LEFT JOIN tmpLst B " +
                "    ON t.purchaser_code = B.ID " +
                "  LEFT JOIN t_achieve t1 " +
                "    ON t.purchaser_code = t1.purchaser_code " +
                "  LEFT JOIN t_bouns t2 " +
                "    ON t2.purchaser_code = t.purchaser_code " +
                "  LEFT JOIN t_rank t3 " +
                "    ON t.rank_code = t3.rank_code " +
                " WHERE t.purchaser_code = '"+purchaserCode+"' " +
                "     OR t.upper_codes LIKE '%"+purchaserCode+"%' " +
                " ORDER BY B.sCort asc");

        if(sort!=null && order!=null){
           sb.append(" t1."+sort+" "+order);
        }
        MySqlPagination page=new MySqlPagination(sb.toString(), startIndex, limit, jdbcTemplate);
        return page;
    }

    /**
     * 获取当前用户的树状分支结构
     * @param purchaserCode
     * @return
     */
    public List<Object> getTreeGraph(String purchaserCode){
        String sql = "SELECT " +
                "  t.purchaser_code AS 'key', " +
                "  t.sponsor_code   AS 'parent', " +
                "  t1.rank_name     AS 'name' " +
                " FROM t_purchaser t " +
                "  LEFT JOIN t_rank t1 " +
                "    ON t1.rank_code = t.rank_code " +
                " WHERE t.purchaser_code = '"+purchaserCode+"' " +
                "     OR t.upper_codes LIKE '%"+purchaserCode+"%' ";

        List list =  jdbcTemplate.queryForList(sql);
        return  list;
    }

}

