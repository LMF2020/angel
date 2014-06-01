package com.angel.my.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 历史月业绩
 * @author jiangzx@gmail.com
 */
@Entity
@Table(name="t_achieve_his")
public class TAchieveHis implements Serializable {
	private static final long serialVersionUID = 1L;

    //主键
    @Id
    @GeneratedValue(generator = "tableGenerator")
    @GenericGenerator(name = "tableGenerator", strategy="increment")
    private Integer id;

    //会员编号
	@Id
	@Column(name="purchaser_code")
	private String purchaserCode;

    //计算日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="achieve_date")
	private Date achieveDate;

    //累计个人业绩
	private Double appv;
    //累计整网业绩
	private Double atnpv;
    //整网业绩
    private Double tnpv;
    //直接业绩
	private Double dbv;
    //小组业绩
	private Double gpv;
    //间接业绩
	private Double ibv;
    //小组业绩
	private Double ppv;
    //星级
	@Column(name="rank_code")
	private String rankCode;
    //星级名称
	@Column(name="rank_name")
	private String rankName;
    //层次
	private Integer tier;
    //上级编号列表
    @Lob
    @Column(name="upper_codes")
    private String upperCodes;
    //构造函数
	public TAchieveHis() {
	}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaserCode() {
        return purchaserCode;
    }
    public void setPurchaserCode(String purchaserCode) {
        this.purchaserCode = purchaserCode;
    }

    public Date getAchieveDate() {
        return achieveDate;
    }
    public void setAchieveDate(Date achieveDate) {
        this.achieveDate = achieveDate;
    }

    public Double getAppv() {
        return appv;
    }
    public void setAppv(Double appv) {
        this.appv = appv;
    }

    public Double getAtnpv() {
        return atnpv;
    }
    public void setAtnpv(Double atnpv) {
        this.atnpv = atnpv;
    }

    public Double getTnpv() {
        return tnpv;
    }
    public void setTnpv(Double tnpv) {
        this.tnpv = tnpv;
    }

    public Double getDbv() {
        return dbv;
    }

    public void setDbv(Double dbv) {
        this.dbv = dbv;
    }
    public Double getGpv() {
        return gpv;
    }

    public void setGpv(Double gpv) {
        this.gpv = gpv;
    }
    public Double getIbv() {
        return ibv;
    }

    public void setIbv(Double ibv) {
        this.ibv = ibv;
    }

    public Double getPpv() {
        return ppv;
    }
    public void setPpv(Double ppv) {
        this.ppv = ppv;
    }

    public String getRankCode() {
        return rankCode;
    }
    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getRankName() {
        return rankName;
    }
    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Integer getTier() {
        return tier;
    }
    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public String getUpperCodes() {
        return this.upperCodes;
    }
    public void setUpperCodes(String upperCodes) {
        this.upperCodes = upperCodes;
    }
}