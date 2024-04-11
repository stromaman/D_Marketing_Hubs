package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.RegisterShop;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterShopRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("reg_res")
    @Expose
    private RegisterShop regRes;

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

    public RegisterShop getRegRes() {
        return regRes;
    }

    public void setRegRes(RegisterShop regRes) {
        this.regRes = regRes;
    }

}
