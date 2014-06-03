package com.angel.my.model;

/**
 * 会员
 * @author jiangzx@gmail.com
 */

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="t_purchaser")
public class TPurchaserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//经销商编码(主键)
	@Id
	@Column(name="purchaser_code")
	private String purchaserCode;
	
	//街道地址
	private String address;
	
	//银行账户
	private Integer bankAcc;
	
	//会员注册时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

    //会员更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="update_time")
    private Date updateTime;

	//层级
	private Integer floors;
	
	//联系方式
	private String phone;
	
	//年龄
	@Column(name="purchaser_age")
	private Integer purchaserAge;
	
	//性别
	@Column(name="purchaser_gender")
	private String purchaserGender;
	
	//姓名
	@Column(name="purchaser_name")
	private String purchaserName;
	
	//登陆密码
	@Column(name="purchaser_pass")
	private String purchaserPass;
	
	//等级编码
	@Column(name="rank_code")
	private String rankCode;
	
	//等级名称
    @Formula("(select f.rank_name from t_rank f where f.rank_code = rank_code)")
	@Column(name="rank_name")
	private String rankName;
	
	//备注信息
	private String remark;
	
	//所在店铺编码
	@Column(name="shop_code")
	private String shopCode;

	//所在店铺名称(变化)
    @Formula("(select f.shop_name from t_shop f where f.shop_code = shop_code)")
	@Column(name="shop_name")
	private String shopName;
	
	//上级经销商编码
	@Column(name="sponsor_code")
	private String sponsorCode;
	
	//上级经销商名称
    @Formula("(select f.purchaser_name from t_purchaser f where f.purchaser_code = sponsor_code)")
	@Column(name="sponsor_name")
	private String sponsorName;

    //上级编号列表
    @Lob
    @Column(name="upper_codes")
    private String upperCodes;

	public Integer getBankAcc() {
		return bankAcc;
	}

    public String getUpperCodes() {
        return upperCodes;
    }

    public void setUpperCodes(String upperCodes) {
        this.upperCodes = upperCodes;
    }

    public void setBankAcc(Integer bankAcc) {
		this.bankAcc = bankAcc;
	}

	public Integer getFloors() {
		return floors;
	}

	public void setFloors(Integer floors) {
		this.floors = floors;
	}

	public Integer getPurchaserAge() {
		return purchaserAge;
	}

	public void setPurchaserAge(Integer purchaserAge) {
		this.purchaserAge = purchaserAge;
	}

	public TPurchaserInfo() {
	}

	public String getPurchaserCode() {
		return this.purchaserCode;
	}

	public void setPurchaserCode(String purchaserCode) {
		this.purchaserCode = purchaserCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getPurchaserGender() {
		return this.purchaserGender;
	}

	public void setPurchaserGender(String purchaserGender) {
		this.purchaserGender = purchaserGender;
	}

	public String getPurchaserName() {
		return this.purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserPass() {
		return this.purchaserPass;
	}

	public void setPurchaserPass(String purchaserPass) {
		this.purchaserPass = purchaserPass;
	}

	public String getRankCode() {
		return this.rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getRankName() {
		return this.rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSponsorCode() {
		return this.sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public String getSponsorName() {
		return this.sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

}