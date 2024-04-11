package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.FranchiseProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseCatWiseProductRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("shopcatprods_res")
    @Expose
    private List<FranchiseProduct> shopcatprodsRes;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FranchiseProduct> getShopcatprodsRes() {
        return shopcatprodsRes;
    }

    public void setShopcatprodsRes(List<FranchiseProduct> shopcatprodsRes) {
        this.shopcatprodsRes = shopcatprodsRes;
    }
}
