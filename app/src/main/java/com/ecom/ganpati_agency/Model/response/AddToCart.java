package com.ecom.ganpati_agency.Model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddToCart {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("addtocart_resp")
    @Expose
    private List<Object> addtocartResp;

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

    public List<Object> getAddtocartResp() {
        return addtocartResp;
    }

    public void setAddtocartResp(List<Object> addtocartResp) {
        this.addtocartResp = addtocartResp;
    }
    }
