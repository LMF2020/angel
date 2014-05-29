package com.angel.my.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @author jiangzx@gmail.com
 */
@Entity
@Table(name="t_order_list")
public class TOrderList implements Serializable {
	private static final long serialVersionUID = 1L;

    /*订单编号*/
	@Id
	@Column(name="order_code")
	private String orderCode;

    /*未知字段*/
	private Integer book;
    /*层级*/
	private Integer floors;
    /*产品编码*/
    @Column(name="product_code")
    private String productCode;
    /*产品价格*/
    @Formula("(select t.product_price from t_product_info t where t.product_code = product_code)")
	@Column(name="product_price")
	private double productPrice;
    /*产品名称*/
    @Formula("(select t.product_name from t_product_info t where t.product_code = product_code)")
    @Column(name="product_name")
    private String productName;
    /*BV*/
    @Formula("(select t.product_bv from t_product_info t where t.product_code = product_code)")
    private Double bv;
    /*PV*/
    @Formula("(select t.product_pv from t_product_info t where t.product_code = product_code)")
    private Double pv;
    /*会员编码*/
	@Column(name="purchaser_code")
	private String purchaserCode;
    /*会员名称*/
    @Formula("(select t.purchaser_name from t_purchaser_info t where t.purchaser_code = purchaser_code)")
	@Column(name="purchaser_name")
	private String purchaserName;
    /*商店编码*/
    @Formula("(select t.shop_code from t_purchaser_info t where t.purchaser_code = purchaser_code)")
    @Column(name="shop_code")
    private String shopCode;
    /*商店名称*/
    @Formula("(select t.shop_name from t_shop_info t left join t_purchaser_info t2 on t.shop_code = t2.shop_code where t2.purchaser_code = purchaser_code)")
    @Column(name="shop_name")
    private String shopName;
    /*购买数量*/
	@Column(name="sale_number")
	private Integer saleNumber;
    /*购买时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sale_time")
	private Date saleTime;
    /*总价格*/
	@Column(name="sum_price")
	private Double sumPrice;
    /*构造函数*/
	public TOrderList() {
	}

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getBook() {
        return book;
    }

    public void setBook(Integer book) {
        this.book = book;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getBv() {
        return bv;
    }

    public void setBv(Double bv) {
        this.bv = bv;
    }

    public Double getPv() {
        return pv;
    }

    public void setPv(Double pv) {
        this.pv = pv;
    }

    public String getPurchaserCode() {
        return purchaserCode;
    }

    public void setPurchaserCode(String purchaserCode) {
        this.purchaserCode = purchaserCode;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }
}