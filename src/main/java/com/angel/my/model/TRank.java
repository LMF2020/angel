package com.angel.my.model;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 星级和奖金比例配置
 * @author jiangzx@gmail.com
 */
@Entity
@Table(name="t_rank")
public class TRank implements Serializable {
	private static final long serialVersionUID = 1L;

    //星级编号
	@Id
	@Column(name="rank_code")
	private String rankCode;
    //星级名称
    @Column(name="rank_name")
    private String rankName;
    //直接奖比例
	@Column(name="direct_bouns_rate")
	private Double directBounsRate;
    //间接奖比例
	@Column(name="indirect_bouns_rate")
	private Double indirectBounsRate;
    //领导奖比例
	@Column(name="leader_bouns_rate")
	private Double leaderBounsRate;

	public TRank() {
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

    public Double getDirectBounsRate() {
        return directBounsRate;
    }
    public void setDirectBounsRate(Double directBounsRate) {
        this.directBounsRate = directBounsRate;
    }

    public Double getIndirectBounsRate() {
        return indirectBounsRate;
    }
    public void setIndirectBounsRate(Double indirectBounsRate) {
        this.indirectBounsRate = indirectBounsRate;
    }

    public Double getLeaderBounsRate() {
        return leaderBounsRate;
    }
    public void setLeaderBounsRate(Double leaderBounsRate) {
        this.leaderBounsRate = leaderBounsRate;
    }
}