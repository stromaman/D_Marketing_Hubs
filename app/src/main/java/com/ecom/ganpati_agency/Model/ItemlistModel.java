package com.ecom.ganpati_agency.Model;

public class ItemlistModel {

    private Integer image;
    private  String name;
    private  String about;

    public ItemlistModel(Integer image, String name, String price,String about){
        this.image = image;
        this.name = name;
        this.about=about;
    }

    public Integer getImage() {
        return image;}

    public void setImage(Integer image) {
        this.image = image;}

    public String getName() {
        return name;}

    public void  setName(String name){
        this.name = name;}



    public String getAbout(){
        return about;
    }

    public  void setAbout(String about){
        this.about=about;
    }
}
