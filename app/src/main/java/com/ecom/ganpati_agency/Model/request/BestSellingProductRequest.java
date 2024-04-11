package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.BestsellprodRe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BestSellingProductRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bestsellprod_res")
    @Expose
    private List<BestsellprodRe> bestsellprodRes;

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

    public List<BestsellprodRe> getBestsellprodRes() {
        return bestsellprodRes;
    }

    public void setBestsellprodRes(List<BestsellprodRe> bestsellprodRes) {
        this.bestsellprodRes = bestsellprodRes;
    }

}
