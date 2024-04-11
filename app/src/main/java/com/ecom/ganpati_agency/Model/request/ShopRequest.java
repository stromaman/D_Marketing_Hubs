package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Shop;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("shop_res")
    @Expose
    private List<Shop> shopRes = null;

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

    public List<Shop> getShopRes() {
        return shopRes;
    }

    public void setShopRes(List<Shop> shopRes) {
        this.shopRes = shopRes;
    }
}
