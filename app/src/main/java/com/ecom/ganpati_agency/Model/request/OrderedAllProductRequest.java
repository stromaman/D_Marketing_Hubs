package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.OrderedAllProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderedAllProductRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderdetail_resp")
    @Expose
    private List<OrderedAllProduct> orderdetailResp = null;

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

    public List<OrderedAllProduct> getOrderdetailResp() {
        return orderdetailResp;
    }

    public void setOrderdetailResp(List<OrderedAllProduct> orderdetailResp) {
        this.orderdetailResp = orderdetailResp;
    }
}
