package com.example.heady.headyassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RankingParameters {

    @SerializedName("ranking")
    @Expose
    private String  ranking_type;

    @SerializedName("products")
    @Expose
    private List<ProductRankingParameters> productRankingParametersList = new ArrayList<>();

    public String getRanking_type() {
        return ranking_type;
    }

    public void setRanking_type(String ranking_type) {
        this.ranking_type = ranking_type;
    }

    public List<ProductRankingParameters> getProductRankingParametersList() {
        return productRankingParametersList;
    }

    public void setProductRankingParametersList(List<ProductRankingParameters> productRankingParametersList) {
        this.productRankingParametersList = productRankingParametersList;
    }
}
