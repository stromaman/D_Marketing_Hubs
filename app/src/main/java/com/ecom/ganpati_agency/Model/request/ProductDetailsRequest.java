package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.ProductDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("productdetail_resp")
    @Expose
    private ProductDetails productdetailResp;

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

    public ProductDetails getProductdetailResp() {
        return productdetailResp;
    }

    public void setProductdetailResp(ProductDetails productdetailResp) {
        this.productdetailResp = productdetailResp;
    }
}
