package com.ecom.ganpati_agency.Model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FranchiseProduct {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pcode")
    @Expose
    private String pcode;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("subcat_id")
    @Expose
    private String subcatId;
    @SerializedName("lowcat_id")
    @Expose
    private String lowcatId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("prodtype")
    @Expose
    private String prodtype;
    @SerializedName("hsn")
    @Expose
    private String hsn;
    @SerializedName("attr_id")
    @Expose
    private String attrId;
    @SerializedName("attr_value")
    @Expose
    private String attrValue;
    @SerializedName("price_attr")
    @Expose
    private String priceAttr;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("desp")
    @Expose
    private String desp;
    @SerializedName("gst_rate")
    @Expose
    private String gstRate;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("added_on")
    @Expose
    private String addedOn;
    @SerializedName("publish_status")
    @Expose
    private String publishStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("attrval")
    @Expose
    private String attrval;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("online_price")
    @Expose
    private String onlinePrice;
    @SerializedName("attr_discount")
    @Expose
    private String attrDiscount;
    @SerializedName("attr_stock_qty")
    @Expose
    private String attrStockQty;
    @SerializedName("subcatname")
    @Expose
    private String subcatname;
    @SerializedName("lowcatname")
    @Expose
    private String lowcatname;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("rv")
    @Expose
    private String rv;
    @SerializedName("profit")
    @Expose
    private String profit;
    @SerializedName("pv")
    @Expose
    private String pv;
    @SerializedName("stock_shop")
    @Expose
    private String stockShop;
    @SerializedName("stock_entry")
    @Expose
    private String stockEntry;
    @SerializedName("stock_gst")
    @Expose
    private String stockGst;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("stock_qty")
    @Expose
    private String stockQty;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("attr_valuefinal")
    @Expose
    private String attrValuefinal;
    @SerializedName("attr_value_namefinal")
    @Expose
    private String attrValueNamefinal;
    @SerializedName("attr_idfinal")
    @Expose
    private String attrIdfinal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(String subcatId) {
        this.subcatId = subcatId;
    }

    public String getLowcatId() {
        return lowcatId;
    }

    public void setLowcatId(String lowcatId) {
        this.lowcatId = lowcatId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProdtype() {
        return prodtype;
    }

    public void setProdtype(String prodtype) {
        this.prodtype = prodtype;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
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

    public String getPriceAttr() {
        return priceAttr;
    }

    public void setPriceAttr(String priceAttr) {
        this.priceAttr = priceAttr;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getGstRate() {
        return gstRate;
    }

    public void setGstRate(String gstRate) {
        this.gstRate = gstRate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttrval() {
        return attrval;
    }

    public void setAttrval(String attrval) {
        this.attrval = attrval;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(String onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public String getAttrDiscount() {
        return attrDiscount;
    }

    public void setAttrDiscount(String attrDiscount) {
        this.attrDiscount = attrDiscount;
    }

    public String getAttrStockQty() {
        return attrStockQty;
    }

    public void setAttrStockQty(String attrStockQty) {
        this.attrStockQty = attrStockQty;
    }

    public String getSubcatname() {
        return subcatname;
    }

    public void setSubcatname(String subcatname) {
        this.subcatname = subcatname;
    }

    public String getLowcatname() {
        return lowcatname;
    }

    public void setLowcatname(String lowcatname) {
        this.lowcatname = lowcatname;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRv() {
        return rv;
    }

    public void setRv(String rv) {
        this.rv = rv;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getStockShop() {
        return stockShop;
    }

    public void setStockShop(String stockShop) {
        this.stockShop = stockShop;
    }

    public String getStockEntry() {
        return stockEntry;
    }

    public void setStockEntry(String stockEntry) {
        this.stockEntry = stockEntry;
    }

    public String getStockGst() {
        return stockGst;
    }

    public void setStockGst(String stockGst) {
        this.stockGst = stockGst;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getStockQty() {
        return stockQty;
    }

    public void setStockQty(String stockQty) {
        this.stockQty = stockQty;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAttrValuefinal() {
        return attrValuefinal;
    }

    public void setAttrValuefinal(String attrValuefinal) {
        this.attrValuefinal = attrValuefinal;
    }

    public String getAttrValueNamefinal() {
        return attrValueNamefinal;
    }

    public void setAttrValueNamefinal(String attrValueNamefinal) {
        this.attrValueNamefinal = attrValueNamefinal;
    }

    public String getAttrIdfinal() {
        return attrIdfinal;
    }

    public void setAttrIdfinal(String attrIdfinal) {
        this.attrIdfinal = attrIdfinal;
    }

}
