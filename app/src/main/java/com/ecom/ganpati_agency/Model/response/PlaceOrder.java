package com.ecom.ganpati_agency.Model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrder {
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("order_total")
    @Expose
    private Integer orderTotal;
    @SerializedName("orderrecipt_id")
    @Expose
    private String orderreciptId;
    @SerializedName("pay_url")
    @Expose
    private String payUrl;

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderreciptId() {
        return orderreciptId;
    }

    public void setOrderreciptId(String orderreciptId) {
        this.orderreciptId = orderreciptId;
    }
}
