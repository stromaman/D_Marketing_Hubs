package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.SubcatRe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("subcat_res")
    @Expose
    private List<SubcatRe> subcatRes;

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

    public List<SubcatRe> getSubcatRes() {
        return subcatRes;
    }

    public void setSubcatRes(List<SubcatRe> subcatRes) {
        this.subcatRes = subcatRes;
    }
}
