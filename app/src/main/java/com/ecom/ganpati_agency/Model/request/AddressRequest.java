package com.ecom.ganpati_agency.Model.request;

import android.location.Address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressRequest {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cust_address_resp")
    @Expose
    private List<Address> custAddressResp = null;

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

    public List<Address> getCustAddressResp() {
        return custAddressResp;
    }

    public void setCustAddressResp(List<Address> custAddressResp) {
        this.custAddressResp = custAddressResp;
    }
}
