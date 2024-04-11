package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.FreeDeliveryAmount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeDeliveryAmountRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("amount_detail")
    @Expose
    private FreeDeliveryAmount amountDetail;

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

    public FreeDeliveryAmount getAmountDetail() {
        return amountDetail;
    }

    public void setAmountDetail(FreeDeliveryAmount amountDetail) {
        this.amountDetail = amountDetail;
    }
}
