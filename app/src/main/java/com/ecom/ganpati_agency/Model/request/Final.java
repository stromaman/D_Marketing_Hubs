package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.LoginCredential;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Final {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("login_credential")
    @Expose
    private LoginCredential loginCredential;

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

    public LoginCredential getLoginCredential() {
        return loginCredential;
    }

    public void setLoginCredential(LoginCredential loginCredential) {
        this.loginCredential = loginCredential;
    }
}
