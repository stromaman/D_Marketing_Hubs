package com.ecom.ganpati_agency.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private static MyPreferences mInstance;
    private static Context mCtx;

    private static final String SHARED_PREFF_NAME = "mysharedpref12";
    public static final String id = "reg";
    public static final String Name = "name";
    public static final String Email = "email";
    public static final String Image = "image";
    public static final String Number = "number";
    public static final String Address = "address";
    public static final String StateId = "stateid";
    public static final String DistrictId = "districtid";
    public static final String StateName = "statename";
    public static final String DistrictName = "districtname";
    public static final String Pincode = "pincode";
    public static final String Token = "token";
    public static final String FirebaseToken = "firebasetoken";
    public static final String DeviceId = "deviceId";
    public static final String ShopId = "shopId";
    public static final String ShopName = "shopName";
    public static final String Login_url ="login_url";
    public static final String SellerPartnerName = "sellerpartnername";
    public static final String SellerShopName = "sellershopname";
    public static final String SellerMobile = "sellermobile";
    public static final String SellerEmail = "selleremail";




    public MyPreferences(Context context) {
        mCtx = context;
    }

    public static synchronized MyPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyPreferences(context);
        }
        return mInstance;
    }
    public boolean logout() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public void saveLoginCredentials(String name, String email, String image, String number, String token
            , String address, String stateId, String stateName, String districtId, String districtName, String pincode){
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, name);
        editor.putString(Email, email);
        editor.putString(Image, image);
        editor.putString(Number, number);
        editor.putString(Token, token);
        editor.putString(Address, address);
        editor.putString(StateId, stateId);
        editor.putString(StateName, stateName);
        editor.putString(DistrictId, districtId);
        editor.putString(DistrictName, districtName);
        editor.putString(Pincode, pincode);
        editor.apply();
    }
    public void saveUpdateDetails(String name, String address, String email, String image){
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Name, name);
        editor.putString(Address, address);
        editor.putString(Email, email);
        editor.putString(Image, image);
        editor.apply();
    }
    public void saveSellerDetails(String sellerPartnerName, String sellerShopName, String sellerMobile, String sellerEmail){
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SellerPartnerName, sellerPartnerName);
        editor.putString(SellerShopName, sellerShopName);
        editor.putString(SellerMobile, sellerMobile);
        editor.putString(SellerEmail, sellerEmail);
        editor.apply();
    }


    public String getid() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(id, null);
    }
    public String getUserName() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Name,null);
    }

    public String getUserEmail() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Email,null);
    }

    public String getUserImage() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Image,null);
    }

    public String getUserNumber() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Number,null);
    }

    public String getUserToken() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Token,null);
    }

    public String getUserFirebaseToken() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(FirebaseToken,null);
    }

    public String getUserDeviceId() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(DeviceId,null);
    }

    public String getShopId() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(ShopId,null);
    }

    public String getShopName() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(ShopName,null);
    }

    public String getAddress() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Address,null);
    }

    public String getSellerPartnerName() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(SellerPartnerName,null);
    }

    public String getSellerShopName() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(SellerShopName,null);
    }

    public String getSellerMobile() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(SellerMobile,null);
    }

    public String getSellerEmail() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(SellerEmail,null);
    }

    public String getStateId() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(StateId,null);
    }

    public String getStateName() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(StateName,null);
    }

    public String getDistrictId() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(DistrictId,null);
    }

    public String getDistrictName() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(DistrictName,null);
    }

    public String getPincode() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Pincode,null);
    }


    public String getLogin_url() {
        SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREFF_NAME, Context.MODE_PRIVATE);
        return settings.getString(Login_url,null);
    }


}
