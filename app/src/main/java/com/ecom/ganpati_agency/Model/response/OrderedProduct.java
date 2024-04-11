package com.ecom.ganpati_agency.Model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderedProduct {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("delivery_rate")
    @Expose
    private String deliveryRate;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("delivery_pincode")
    @Expose
    private String deliveryPincode;
    @SerializedName("delivery_mobileno")
    @Expose
    private String deliveryMobileno;
    @SerializedName("order_total")
    @Expose
    private String orderTotal;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("added_on")
    @Expose
    private String addedOn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shopaddress")
    @Expose
    private String shopaddress;
    @SerializedName("shopstate_name")
    @Expose
    private String shopstateName;
    @SerializedName("shopdistrict_name")
    @Expose
    private String shopdistrictName;
    @SerializedName("shoppincode")
    @Expose
    private String shoppincode;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDeliveryRate() {
        return deliveryRate;
    }

    public void setDeliveryRate(String deliveryRate) {
        this.deliveryRate = deliveryRate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDeliveryPincode() {
        return deliveryPincode;
    }

    public void setDeliveryPincode(String deliveryPincode) {
        this.deliveryPincode = deliveryPincode;
    }

    public String getDeliveryMobileno() {
        return deliveryMobileno;
    }

    public void setDeliveryMobileno(String deliveryMobileno) {
        this.deliveryMobileno = deliveryMobileno;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getShopstateName() {
        return shopstateName;
    }

    public void setShopstateName(String shopstateName) {
        this.shopstateName = shopstateName;
    }

    public String getShopdistrictName() {
        return shopdistrictName;
    }

    public void setShopdistrictName(String shopdistrictName) {
        this.shopdistrictName = shopdistrictName;
    }

    public String getShoppincode() {
        return shoppincode;
    }

    public void setShoppincode(String shoppincode) {
        this.shoppincode = shoppincode;
    }
}
