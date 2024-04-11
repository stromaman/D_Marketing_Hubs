package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.PlaceOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order_id")
    @Expose
    private PlaceOrder orderId;

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

    public PlaceOrder getOrderId() {
        return orderId;
    }

    public void setOrderId(PlaceOrder orderId) {
        this.orderId = orderId;
    }
}
