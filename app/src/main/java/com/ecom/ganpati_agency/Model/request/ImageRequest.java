package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("productimgs_resp")
    @Expose
    private List<Image> productimgsResp = null;

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

    public List<Image> getProductimgsResp() {
        return productimgsResp;
    }

    public void setProductimgsResp(List<Image> productimgsResp) {
        this.productimgsResp = productimgsResp;
    }
}
