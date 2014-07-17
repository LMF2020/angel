package com.angel.my.controller;

import com.angel.my.common.BaseController;
import com.angel.my.common.ResponseData;
import com.angel.my.model.TProductInfo;
import com.angel.my.service.ITProductInfoService;
import com.starit.common.dao.support.Pagination;
import com.starit.common.dao.support.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;

/**
 * 产品货架
 * @author jiangzx@gmail.com
 *
 */
@Controller
@RequestMapping("/productController")
public class ProductController extends BaseController {

    @Autowired
    private ITProductInfoService productInfoService;

    /**
     * 会员JSP|定位
     */
    @RequestMapping("/product")
    public String product() throws IOException {
        return "/product/product";
    }

    /**
      * 产品|添加
     */
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addProduct(TProductInfo product) throws Exception {
        //判断产品编码是否重复
        TProductInfo p = productInfoService.loadProduct(product.getProductCode());
        if (p != null){
            return new ResponseData(true,"产品编码重复!");
        }
        //创建时间
        product.setCreateTime(new Date());
        productInfoService.addProduct(product);
        return ResponseData.SUCCESS_NO_DATA;
    }

    /**
     * 产品|更新~
     */
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateProduct(TProductInfo product){
        //提取保留信息
        TProductInfo updateProduct = productInfoService.loadProduct(product.getProductCode());
        //更新
        updateProduct.setProductName(product.getProductName());
        updateProduct.setProductPrice(product.getProductPrice());
        updateProduct.setProductBv(product.getProductBv());
        updateProduct.setProductPv(product.getProductPv());
        updateProduct.setCreateTime(new Date());
        productInfoService.updateProduct(updateProduct);
        return ResponseData.SUCCESS_NO_DATA;
    }

    /**
     * 产品批量删除
     */
    @RequestMapping(value = "/destroyProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData destroyProduct(String[] codes){
        try {
            for (int i = 0; i < codes.length; i++) {
                productInfoService.destoryProduct(codes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(true,"删除失败!");
        }
        return new ResponseData(true);
    }

    /**
     * 产品|查询
     */
    @RequestMapping("/pageProductList")
    @ResponseBody
    public Pagination<TProductInfo> pageProductList(
            @RequestParam(required = false) String q, //模糊查询字段
            @RequestParam("page") int startIndex,
            @RequestParam("rows") int pageSize, TProductInfo prod,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order){
        //计算索引位置
        int offset = (startIndex-1)*pageSize;
        PaginationRequest<TProductInfo > pageRequest = new PaginationRequest<TProductInfo>(offset, pageSize);
        if(StringUtils.hasText(sort) && StringUtils.hasText(order)) pageRequest.addOrder(sort, order);

        // 产品名称（模糊查询）
        if(StringUtils.hasText(prod.getProductName())){
            pageRequest.addLikeCondition("productName", prod.getProductName());
        }
        //产品编号（模糊查询）
        if(StringUtils.hasText(prod.getProductCode())){
            pageRequest.addLikeCondition("productCode", prod.getProductCode());
        }

        if(StringUtils.hasText(q)){
            pageRequest.addLikeCondition("productCode", q);
        }

        Pagination<TProductInfo> page = productInfoService.findAllForPage(pageRequest);

        return page;
    }
}
