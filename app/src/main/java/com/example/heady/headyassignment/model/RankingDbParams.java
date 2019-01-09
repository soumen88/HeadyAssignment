package com.example.heady.headyassignment.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Ranking")
public class RankingDbParams {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long rankingId;

    private String ranking_type;

    @Generated(hash = 742518712)
    public RankingDbParams(Long rankingId, String ranking_type) {
        this.rankingId = rankingId;
        this.ranking_type = ranking_type;
    }

    @Generated(hash = 1269801447)
    public RankingDbParams() {
    }

    public Long getRankingId() {
        return this.rankingId;
    }

    public void setRankingId(Long rankingId) {
        this.rankingId = rankingId;
    }

    public String getRanking_type() {
        return this.ranking_type;
    }

    public void setRanking_type(String ranking_type) {
        this.ranking_type = ranking_type;
    }


}
