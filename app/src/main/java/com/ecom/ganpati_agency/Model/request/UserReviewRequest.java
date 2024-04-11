package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.UserReview;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserReviewRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("review")
    @Expose
    private List<UserReview> review;

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

    public List<UserReview> getReview() {
        return review;
    }

    public void setReview(List<UserReview> review) {
        this.review = review;
    }
}
