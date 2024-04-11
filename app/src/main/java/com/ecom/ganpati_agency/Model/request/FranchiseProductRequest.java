package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.FranchiseProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseProductRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("franchise_res")
    @Expose
    private List<FranchiseProduct> franchiseRes;

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

    public List<FranchiseProduct> getFranchiseRes() {
        return franchiseRes;
    }

    public void setFranchiseRes(List<FranchiseProduct> franchiseRes) {
        this.franchiseRes = franchiseRes;
    }
}
