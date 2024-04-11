package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Dashboard;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dashboard_res")
    @Expose
    private List<Dashboard> dashboardRes;

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

    public List<Dashboard> getDashboardRes() {
        return dashboardRes;
    }

    public void setDashboardRes(List<Dashboard> dashboardRes) {
        this.dashboardRes = dashboardRes;
    }
}
