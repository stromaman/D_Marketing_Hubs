package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.UpdateProfile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("prof_updateres")
    @Expose
    private UpdateProfile profUpdateres;

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

    public UpdateProfile getProfUpdateres() {
        return profUpdateres;
    }

    public void setProfUpdateres(UpdateProfile profUpdateres) {
        this.profUpdateres = profUpdateres;
    }
}
