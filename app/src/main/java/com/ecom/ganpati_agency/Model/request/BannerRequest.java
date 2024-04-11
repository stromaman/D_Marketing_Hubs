package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Banner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("banner_res")
    @Expose
    private List<Banner> bannerRes;

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

    public List<Banner> getBannerRes() {
        return bannerRes;
    }

    public void setBannerRes(List<Banner> bannerRes) {
        this.bannerRes = bannerRes;
    }
}
