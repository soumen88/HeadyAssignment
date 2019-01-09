package com.example.heady.headyassignment.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryParameters implements Comparable<CategoryParameters>{

    @SerializedName("id")
    @Expose
    private Long categoryIdReceived;

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("products")
    @Expose
    private ArrayList<ProductParameters> productList =  new ArrayList<>();

    public Long getCategoryIdReceived() {
        return categoryIdReceived;
    }

    public void setCategoryIdReceived(Long categoryIdReceived) {
        this.categoryIdReceived = categoryIdReceived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProductParameters> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductParameters> productList) {
        this.productList = productList;
    }

    @Override
    public int compareTo(@NonNull CategoryParameters categoryParameters) {
        return (categoryIdReceived.compareTo(categoryParameters.getCategoryIdReceived()));
    }
}
