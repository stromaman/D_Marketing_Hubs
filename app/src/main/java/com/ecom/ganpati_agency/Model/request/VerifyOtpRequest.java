package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("login_res")
    @Expose
    private Login loginRes;

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

    public Login getLoginRes() {
        return loginRes;
    }

    public void setLoginRes(Login loginRes) {
        this.loginRes = loginRes;
    }
}
