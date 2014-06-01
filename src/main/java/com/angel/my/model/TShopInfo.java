package com.angel.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 店铺
 * @author jiangzx@gmail.com
 */

@Entity
@Table(name="t_shop")
public class TShopInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="shop_code")
	private String shopCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="shop_city")
	private String shopCity;

	@Column(name="shop_country")
	private String shopCountry;

	@Column(name="shop_name")
	private String shopName;

	@Column(name="shop_owner")
	private String shopOwner;

	@Column(name="shop_street")
	private String shopStreet;

	public TShopInfo() {
	}

	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getShopCity() {
		return this.shopCity;
	}

	public void setShopCity(String shopCity) {
		this.shopCity = shopCity;
	}

	public String getShopCountry() {
		return this.shopCountry;
	}

	public void setShopCountry(String shopCountry) {
		this.shopCountry = shopCountry;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopOwner() {
		return this.shopOwner;
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public String getShopStreet() {
		return this.shopStreet;
	}

	public void setShopStreet(String shopStreet) {
		this.shopStreet = shopStreet;
	}

}