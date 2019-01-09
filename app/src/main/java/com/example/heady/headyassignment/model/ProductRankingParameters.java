package com.example.heady.headyassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRankingParameters {

    @SerializedName("id")
    @Expose
    private Long productRankingIdReceived;

    @SerializedName("view_count")
    @Expose
    private Long viewcount;

    @SerializedName("order_count")
    @Expose
    private Long ordercount;

    @SerializedName("shares")
    @Expose
    private Long shares;

    public Long getProductRankingIdReceived() {
        return productRankingIdReceived;
    }

    public void setProductRankingIdReceived(Long productRankingIdReceived) {
        this.productRankingIdReceived = productRankingIdReceived;
    }

    public Long getViewcount() {
        return viewcount;
    }

    public void setViewcount(Long viewcount) {
        this.viewcount = viewcount;
    }

    public Long getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(Long ordercount) {
        this.ordercount = ordercount;
    }

    public Long getShares() {
        return shares;
    }

    public void setShares(Long shares) {
        this.shares = shares;
    }
}
