package com.example.heady.headyassignment.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Variants")
public class VariantDbParams {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long variantid;

    private Long variantIdReceived;

    private Long productid;

    private String color;

    private Long size;

    private Double price;

    @Generated(hash = 6764621)
    public VariantDbParams(Long variantid, Long variantIdReceived, Long productid,
            String color, Long size, Double price) {
        this.variantid = variantid;
        this.variantIdReceived = variantIdReceived;
        this.productid = productid;
        this.color = color;
        this.size = size;
        this.price = price;
    }

    @Generated(hash = 1636961122)
    public VariantDbParams() {
    }

    public Long getVariantid() {
        return this.variantid;
    }

    public void setVariantid(Long variantid) {
        this.variantid = variantid;
    }

    public Long getVariantIdReceived() {
        return this.variantIdReceived;
    }

    public void setVariantIdReceived(Long variantIdReceived) {
        this.variantIdReceived = variantIdReceived;
    }

    public Long getProductid() {
        return this.productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
