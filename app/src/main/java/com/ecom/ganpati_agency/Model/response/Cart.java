package com.ecom.ganpati_agency.Model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("attr_id")
    @Expose
    private String attrId;
    @SerializedName("attr_value")
    @Expose
    private String attrValue;
    @SerializedName("attr_value_name")
    @Expose
    private String attrValueName;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("added_on")
    @Expose
    private String addedOn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("wishlist_status")
    @Expose
    private String wishlistStatus;
    @SerializedName("productdata")
    @Expose

    private Product productdata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrValueName() {
        return attrValueName;
    }

    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWishlistStatus() {
        return wishlistStatus;
    }

    public void setWishlistStatus(String wishlistStatus) {
        this.wishlistStatus = wishlistStatus;
    }

    public Product getProductdata() {
        return productdata;
    }

    public void setProductdata(Product productdata) {
        this.productdata = productdata;
    }


}
