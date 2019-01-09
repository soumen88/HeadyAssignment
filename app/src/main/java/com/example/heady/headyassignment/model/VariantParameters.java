package com.example.heady.headyassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VariantParameters {
    @SerializedName("id")
    @Expose
    private Long variantIdReceived;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("size")
    @Expose
    private Long size;

    @SerializedName("price")
    @Expose
    private Double price;

    public Long getVariantIdReceived() {
        return variantIdReceived;
    }

    public void setVariantIdReceived(Long variantIdReceived) {
        this.variantIdReceived = variantIdReceived;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
