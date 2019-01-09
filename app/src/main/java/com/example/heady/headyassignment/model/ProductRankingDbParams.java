package com.example.heady.headyassignment.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "ProductRanking")
public class ProductRankingDbParams {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long Id;

    private Long rankingId;

    private Long productIdReceived;

    private Long viewCount;

    @Generated(hash = 704506907)
    public ProductRankingDbParams(Long Id, Long rankingId, Long productIdReceived,
            Long viewCount) {
        this.Id = Id;
        this.rankingId = rankingId;
        this.productIdReceived = productIdReceived;
        this.viewCount = viewCount;
    }

    @Generated(hash = 153727363)
    public ProductRankingDbParams() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getRankingId() {
        return this.rankingId;
    }

    public void setRankingId(Long rankingId) {
        this.rankingId = rankingId;
    }

    public Long getProductIdReceived() {
        return this.productIdReceived;
    }

    public void setProductIdReceived(Long productIdReceived) {
        this.productIdReceived = productIdReceived;
    }

    public Long getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    

}
