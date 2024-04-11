package com.ecom.ganpati_agency.Model.request;

import com.ecom.ganpati_agency.Model.response.Cart;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequest {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cartlist_resp")
    @Expose
    private List<Cart> cartlistResp = null;

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

    public List<Cart> getCartlistResp() {
        return cartlistResp;
    }

    public void setCartlistResp(List<Cart> cartlistResp) {
        this.cartlistResp = cartlistResp;
    }

}
