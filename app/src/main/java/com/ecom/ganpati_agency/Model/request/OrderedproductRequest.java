package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.OrderedProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderedproductRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderlist_resp")
    @Expose
    private List<OrderedProduct> orderlistResp = null;

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

    public List<OrderedProduct> getOrderlistResp() {
        return orderlistResp;
    }

    public void setOrderlistResp(List<OrderedProduct> orderlistResp) {
        this.orderlistResp = orderlistResp;
    }
}
