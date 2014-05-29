package com.angel.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**

 * 产品货架
 * @author jiangzx@gmail.com
 */
@Entity
@Table(name="t_product_info")
public class TProductInfo implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 产品编码
     */
    @Id
    @Column(name="product_code")
	private String productCode;

    /**
     * BV
     */
	@Column(name="product_bv")
	private Double productBv;

    /**
     * 产品名称
     */
	@Column(name="product_name")
	private String productName;

    /**
     * 产品价格
     */
	@Column(name="product_price")
	private Double productPrice;

    /**
     * PV
     */
	@Column(name="product_pv")
	private Double productPv;

    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time")
    private Date createTime;

    /**
     * 产品状态:在售,下架
     */
    private String status;

    /**
     * 备注
     */
	private String remark;

	public TProductInfo() {
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public Double getProductBv() {
        return productBv;
    }

    public void setProductBv(Double productBv) {
        this.productBv = productBv;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductPv() {
        return productPv;
    }

    public void setProductPv(Double productPv) {
        this.productPv = productPv;
    }
}