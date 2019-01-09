package com.example.heady.headyassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductParameters {

    @SerializedName("id")
    @Expose
    private Long productIdReceived;

    @SerializedName("name")
    @Expose
    private String productname;

    @SerializedName("date_added")
    @Expose
    private String date_added;

    @SerializedName("variants")
    @Expose
    private List<VariantParameters> variantList = new ArrayList<>();

    @SerializedName("tax")
    @Expose
    private TaxParameters taxParameters;

    public Long getProductIdReceived() {
        return productIdReceived;
    }

    public void setProductIdReceived(Long productIdReceived) {
        this.productIdReceived = productIdReceived;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public List<VariantParameters> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<VariantParameters> variantList) {
        this.variantList = variantList;
    }

    public TaxParameters getTaxParameters() {
        return taxParameters;
    }

    public void setTaxParameters(TaxParameters taxParameters) {
        this.taxParameters = taxParameters;
    }
}
