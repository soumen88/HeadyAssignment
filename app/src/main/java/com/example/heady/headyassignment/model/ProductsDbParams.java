package com.example.heady.headyassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Products")
public class ProductsDbParams {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long productId;

    private Long productIdReceived;

    private Long categoryId;

    private String name;

    private String date_added;

    private String dbtaxname;

    private Double dbtaxvalue;

    @Generated(hash = 1425656559)
    public ProductsDbParams(Long productId, Long productIdReceived, Long categoryId,
            String name, String date_added, String dbtaxname, Double dbtaxvalue) {
        this.productId = productId;
        this.productIdReceived = productIdReceived;
        this.categoryId = categoryId;
        this.name = name;
        this.date_added = date_added;
        this.dbtaxname = dbtaxname;
        this.dbtaxvalue = dbtaxvalue;
    }

    @Generated(hash = 1016863179)
    public ProductsDbParams() {
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductIdReceived() {
        return this.productIdReceived;
    }

    public void setProductIdReceived(Long productIdReceived) {
        this.productIdReceived = productIdReceived;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_added() {
        return this.date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDbtaxname() {
        return this.dbtaxname;
    }

    public void setDbtaxname(String dbtaxname) {
        this.dbtaxname = dbtaxname;
    }

    public Double getDbtaxvalue() {
        return this.dbtaxvalue;
    }

    public void setDbtaxvalue(Double dbtaxvalue) {
        this.dbtaxvalue = dbtaxvalue;
    }

}
