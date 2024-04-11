package com.ecom.ganpati_agency.Model;

public class OrderListModel {

    private String orderid;
    private String shopname;
    private String orderdate;
    private String Totalamount;
    private String deliverycharge;
    private String paymentmethod;
    private String paystatus;

    public OrderListModel(String orderid,String shopname,String orderdate,String totalamount, String deliverycharge, String paymentmethod,String paystatus){

        this.orderid=orderid;
        this.shopname=shopname;
        this.orderdate=orderdate;
        this.Totalamount=totalamount;
        this.deliverycharge=deliverycharge;
        this.paymentmethod=paymentmethod;
        this.paystatus=paystatus;
    }




    public String getShopname(){
        return  shopname;}
   public  void setShopname(String shopname){
        this.shopname= shopname;
   }

   public String getOrderid(){return orderid;}
    public void setOrderid(String orderid){this.orderid=orderid;}

    public String getOrderdate(){return orderdate;}
    public void setOrderdate(String orderdate){this.orderdate=orderdate;}

    public String getTotalamount(){return Totalamount;}
    public void setTotalamounte(String totalamount){this.Totalamount=totalamount;}

    public String getDeliverycharge(){return deliverycharge;}
    public void setDeliverycharge(String deliverycharge){this.deliverycharge=deliverycharge;}

    public String getPaymentmethod(){return paymentmethod;}
    public void setPaymentmethod(String paymentmethod){this.paymentmethod=paymentmethod;}

    public String getPaystatus(){return paystatus;}
    public void setPaystatus(String paystatus){this.paystatus=paystatus;}
}
