package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.CatRe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cat_res")
    @Expose
    private List<CatRe> catRes;

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

    public List<CatRe> getCatRes() {
        return catRes;
    }

    public void setCatRes(List<CatRe> catRes) {
        this.catRes = catRes;
    }
}
