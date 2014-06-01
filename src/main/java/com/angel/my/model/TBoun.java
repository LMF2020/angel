package com.angel.my.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 当月奖金
 * @author jiangzx@gmail.com
 */
@Entity
@Table(name="t_bouns")
public class TBoun implements Serializable {
	private static final long serialVersionUID = 1L;

    //会员编号
	@Id
	@Column(name="purchaser_code")
	private String purchaserCode;
    //计算日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="bouns_date")
	private Date bounsDate;
    //直接奖
	@Column(name="direct_bouns")
	private Double directBouns;
    //间接奖
	@Column(name="indirect_bouns")
	private Double indirectBouns;
    //领导奖
	@Column(name="leader_bouns")
	private Double leaderBouns;
    //会员名称
	@Column(name="purchaser_name")
	private String purchaserName;
    //星级编号
	@Column(name="rank_code")
	private String rankCode;
    //星级名称
	@Column(name="rank_name")
	private String rankName;
    //备注
	private String remark;

	public TBoun() {
	}

    public String getPurchaserCode() {
        return purchaserCode;
    }
    public void setPurchaserCode(String purchaserCode) {
        this.purchaserCode = purchaserCode;
    }

    public Date getBounsDate() {
        return bounsDate;
    }
    public void setBounsDate(Date bounsDate) {
        this.bounsDate = bounsDate;
    }

    public Double getDirectBouns() {
        return directBouns;
    }
    public void setDirectBouns(Double directBouns) {
        this.directBouns = directBouns;
    }

    public Double getIndirectBouns() {
        return indirectBouns;
    }
    public void setIndirectBouns(Double indirectBouns) {
        this.indirectBouns = indirectBouns;
    }

    public Double getLeaderBouns() {
        return leaderBouns;
    }
    public void setLeaderBouns(Double leaderBouns) {
        this.leaderBouns = leaderBouns;
    }

    public String getPurchaserName() {
        return purchaserName;
    }
    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
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

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}