package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.PaymentMethod;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentMethodRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pmethod_res")
    @Expose
    private List<PaymentMethod> pmethodRes;

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

    public List<PaymentMethod> getPmethodRes() {
        return pmethodRes;
    }

    public void setPmethodRes(List<PaymentMethod> pmethodRes) {
        this.pmethodRes = pmethodRes;
    }
}