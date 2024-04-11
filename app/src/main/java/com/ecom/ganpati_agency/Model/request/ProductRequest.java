package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_res")
    @Expose
    private List<Product> productRes;

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

    public List<Product> getProductRes() {
        return productRes;
    }

    public void setProductRes(List<Product> productRes) {
        this.productRes = productRes;
    }

}
