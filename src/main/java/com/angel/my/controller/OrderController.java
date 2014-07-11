package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.common.ResponseData;
import com.angel.my.model.TOrderList;
import com.angel.my.service.ITOrderListService;
import com.angel.my.service.ITProductInfoService;
import com.angel.my.util.IdGenerator;
import com.starit.common.dao.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;

/**
 * 订单管理
 * @author jiangzx@gmail.com
 *
 */
@Controller
@RequestMapping("/orderController")
public class OrderController extends BaseController {

    @Autowired
    private ITOrderListService ITOrderListService;
    @Autowired
    private ITProductInfoService itProductInfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 订单JSP|定位
     */
    @RequestMapping("/order")
    public String order() throws IOException {
        return "/order/order";
    }
    /**
     * 订单|添加
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addOrder(TOrderList order) throws Exception {
        int saleNumber = order.getSaleNumber();
        //生成订单编号
        order.setOrderCode(IdGenerator.getId());
        order.setBv(order.getBv()*saleNumber);
        order.setPv(order.getPv()*saleNumber);
        order.setSaleTime(new Date());

        /** //以下是前台带过来的数据
            order.setSaleNumber(saleNumber);
            order.setBook(order.getBook());
            order.setProductCode(order.getProductCode());
            order.setProductName(order.getProductName());
            order.setProductPrice(order.getProductPrice());
            order.setSumPrice(order.getSumPrice());
        */
        ITOrderListService.addOrder(order);
        return ResponseData.SUCCESS_NO_DATA;
    }
    /**
     * 订单|更新~
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateOrder(TOrderList order){
        int saleNumber = order.getSaleNumber();
        //提取保留信息
        TOrderList updateOrder = ITOrderListService.loadOrder(order.getOrderCode());
        //更新
        updateOrder.setProductCode(order.getProductCode());
        updateOrder.setProductName(order.getProductName());
        updateOrder.setProductPrice(order.getProductPrice());

        //查询pv,bv的单位值
//        TProductInfo productInfo =  itProductInfoService.loadProduct(order.getProductCode());
//        double pv = productInfo.getProductPv();
//        double bv = productInfo.getProductBv();
//        updateOrder.setPv(pv*saleNumber); //需要注意，这里的PV需要重新查询，而不是界面带入的值
//        updateOrder.setBv(bv*saleNumber); //需要注意，这里的BV需要重新查询，而不是界面带入的值
        updateOrder.setBv(order.getBv()*saleNumber);
        updateOrder.setPv(order.getPv()*saleNumber);
        updateOrder.setSumPrice(order.getSumPrice());

        updateOrder.setPurchaserCode(order.getPurchaserCode());
        updateOrder.setPurchaserName(order.getPurchaserName());
        updateOrder.setShopCode(order.getShopCode());
        updateOrder.setShopName(order.getShopName());

        updateOrder.setSaleNumber(saleNumber);
        updateOrder.setBook(order.getBook());
        updateOrder.setSaleTime(new Date());


        ITOrderListService.updateOrder(updateOrder);
        return ResponseData.SUCCESS_NO_DATA;
    }
    /**
     * 订单批量删除
     */
    @RequestMapping(value = "/destroyOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData destroyOrder(String[] codes){
        try {
            for (int i = 0; i < codes.length; i++) {
                ITOrderListService.destoryOrder(codes[i]);
            }
        } catch (Exception e) {
            return new ResponseData(true,"删除失败!");
        }
        return new ResponseData(true);
    }

    /**
     * 计算月销售额
     * @return
     */
    @RequestMapping(value = "/getSumMon",method = RequestMethod.GET)
    @ResponseBody
    public double getSumMon(){
        return ITOrderListService.getSumMon();
    }

    /**
     * 订单|查询
     */
    @RequestMapping("/pageOrderList")
    @ResponseBody
    public Pagination<Object> pageOrderList(
            @RequestParam("page") int startIndex,
            @RequestParam("rows") int pageSize, TOrderList orderEntity,
            @RequestParam(required = false)String startTime,
            @RequestParam(required = false)String  endTime,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order){
        //计算索引位置
        int offset = (startIndex-1)*pageSize;

        StringBuilder sb = new StringBuilder();
        sb.append(" 	from TOrderList ord  where 1=1	");

        //模糊查询

        if(!StringUtils.isEmpty(orderEntity.getOrderCode())){		/*订单编号*/
            sb.append(" and ord.orderCode like '%"+ orderEntity.getOrderCode()+"%'");
        }
        if(!StringUtils.isEmpty(orderEntity.getPurchaserCode())){ /*会员编号*/
            sb.append(" and ord.purchaserCode like '%"+ orderEntity.getPurchaserCode()+"%'");
        }
        if(!StringUtils.isEmpty(orderEntity.getProductCode())){   /*产品编号*/
            sb.append(" and ord.productCode like '%"+ orderEntity.getProductCode()+"%'");
        }
        if(!StringUtils.isEmpty(startTime)){			/*时间范围查询*/
            sb.append("	and ord.saleTime > '"+ startTime+"'");
        }
        if(!StringUtils.isEmpty(endTime)){
            sb.append("	and ord.saleTime < '"+ endTime+"'");
        }

        if(!StringUtils.isEmpty(orderEntity.getPurchaserName())){		/*会员姓名*/
            sb.append(" and ord.purchaserName like '%"+ orderEntity.getPurchaserName()+"%'");
        }
        if(!StringUtils.isEmpty(orderEntity.getShopName())){		/*商店名称*/
            sb.append(" and ord.shopName like '%"+ orderEntity.getShopName()+"%'");
        }
        if(!StringUtils.isEmpty(orderEntity.getProductName())){		/*产品名称*/
            sb.append(" and ord.productName like '%"+ orderEntity.getProductName()+"%'");
        }

        if(sort!=null && order!=null){		/*排序*/
            sb.append("  order by 	"+sort+" "+order);
        }else{
            sb.append("  order by ord.saleTime desc");
        }

        String rowSql = sb.toString();
        String countSql = "	select count(*) 	"+ rowSql;

        Pagination<Object> page = ITOrderListService.findPageByHQL(rowSql, countSql, offset, pageSize);

        return page;
    }

}
