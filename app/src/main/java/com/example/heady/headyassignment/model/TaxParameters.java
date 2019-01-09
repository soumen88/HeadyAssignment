package com.example.heady.headyassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxParameters {
    @SerializedName("name")
    @Expose
    private String taxname;

    @SerializedName("value")
    @Expose
    private Double taxvalue;

    public String getTaxname() {
        return taxname;
    }

    public void setTaxname(String taxname) {
        this.taxname = taxname;
    }

    public Double getTaxvalue() {
        return taxvalue;
    }

    public void setTaxvalue(Double taxvalue) {
        this.taxvalue = taxvalue;
    }
}
