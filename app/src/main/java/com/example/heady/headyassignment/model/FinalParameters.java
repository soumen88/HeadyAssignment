package com.example.heady.headyassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FinalParameters {
    @SerializedName("categories")
    @Expose
    private List<CategoryParameters> categoryParametersList ;

    @SerializedName("rankings")
    @Expose
    private List<RankingParameters> rankingParametersList;

    public List<CategoryParameters> getCategoryParametersList() {
        return categoryParametersList;
    }

    public void setCategoryParametersList(List<CategoryParameters> categoryParametersList) {
        this.categoryParametersList = categoryParametersList;
    }

    public List<RankingParameters> getRankingParametersList() {
        return rankingParametersList;
    }

    public void setRankingParametersList(List<RankingParameters> rankingParametersList) {
        this.rankingParametersList = rankingParametersList;
    }
}
