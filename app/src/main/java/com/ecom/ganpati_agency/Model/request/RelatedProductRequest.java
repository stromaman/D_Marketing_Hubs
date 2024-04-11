package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RelatedProductRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products_resp")
    @Expose
    private List<Product> productsResp = null;

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

    public List<Product> getProductsResp() {
        return productsResp;
    }

    public void setProductsResp(List<Product> productsResp) {
        this.productsResp = productsResp;
    }
}
