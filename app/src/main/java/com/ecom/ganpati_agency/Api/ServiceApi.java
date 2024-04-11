package com.ecom.ganpati_agency.Api;

import com.ecom.ganpati_agency.Model.request.AddressRequest;
import com.ecom.ganpati_agency.Model.request.AllProductRequest;
import com.ecom.ganpati_agency.Model.request.BankRequest;
import com.ecom.ganpati_agency.Model.request.BannerModel;
import com.ecom.ganpati_agency.Model.request.BannerRequest;
import com.ecom.ganpati_agency.Model.request.BestSellingProductRequest;
import com.ecom.ganpati_agency.Model.request.BuyNowRequest;
import com.ecom.ganpati_agency.Model.request.CartRequest;
import com.ecom.ganpati_agency.Model.request.CategoryModel;
import com.ecom.ganpati_agency.Model.request.DashboardRequest;
import com.ecom.ganpati_agency.Model.request.DistrictRequest;
import com.ecom.ganpati_agency.Model.request.Final;
import com.ecom.ganpati_agency.Model.request.FranchiseCatWiseProductRequest;
import com.ecom.ganpati_agency.Model.request.FranchiseProductRequest;
import com.ecom.ganpati_agency.Model.request.FreeDeliveryAmountRequest;
import com.ecom.ganpati_agency.Model.request.ImageRequest;
import com.ecom.ganpati_agency.Model.request.OrderedAllProductRequest;
import com.ecom.ganpati_agency.Model.request.OrderedproductRequest;
import com.ecom.ganpati_agency.Model.request.PaymentMethodRequest;
import com.ecom.ganpati_agency.Model.request.PlaceOrderRequest;
import com.ecom.ganpati_agency.Model.request.ProductDetailsRequest;
import com.ecom.ganpati_agency.Model.request.ProductRequest;
import com.ecom.ganpati_agency.Model.request.RegisterShopRequest;
import com.ecom.ganpati_agency.Model.request.RelatedProductRequest;
import com.ecom.ganpati_agency.Model.request.ShopRequest;
import com.ecom.ganpati_agency.Model.request.StateRequest;
import com.ecom.ganpati_agency.Model.request.SubCategoryModel;
import com.ecom.ganpati_agency.Model.request.UpdateProfileRequest;
import com.ecom.ganpati_agency.Model.request.UserReviewRequest;
import com.ecom.ganpati_agency.Model.request.VerifyOtpRequest;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.CancelOrder;
import com.ecom.ganpati_agency.Model.response.DeleteCart;
import com.ecom.ganpati_agency.Model.response.OtpRequest;
import com.ecom.ganpati_agency.Model.response.SaveAadhaarDetails;
import com.ecom.ganpati_agency.Model.response.SaveBankDetails;
import com.ecom.ganpati_agency.Model.response.SaveFssaiDetails;
import com.ecom.ganpati_agency.Model.response.SaveGstDetails;
import com.ecom.ganpati_agency.Model.response.SavePANDetails;
import com.ecom.ganpati_agency.Model.response.SaveShopAddressDetails;
import com.ecom.ganpati_agency.Model.response.SendReview;
import com.ecom.ganpati_agency.Model.response.SignUp;
import com.ecom.ganpati_agency.Model.response.UpdateCart;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceApi {

    @FormUrlEncoded
    @POST("saveregister")
    Call<SignUp> registerUser(@Field("mobileno") String mobileno
            , @Field("name") String name
            , @Field("email") String email
            , @Field("password") String password
            , @Field("address") String address
            , @Field("state") String state
            , @Field("district") String district
            , @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("verifyotp")
    Call<VerifyOtpRequest> doVerifyOtp(@Field("mobile") String mobile
            , @Field("otp") String otp);

    @FormUrlEncoded
    @POST("loginwithotp")
    Call<OtpRequest> getLoginOtp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("customerlogin")
    Call<VerifyOtpRequest> loginUser(@Field("username") String username
            , @Field("password") String password);

    @POST("categorylist")
    Call<CategoryModel> category();

    @FormUrlEncoded
    @POST("subcategorylist")
    Call<SubCategoryModel> getSubCategory(@Field("cat_id") String cat_id);

    @FormUrlEncoded
    @POST("products_bysubcat")
    Call<ProductRequest> getProductSubCatBy(@Field("cat_id") String cat_id
            , @Field("subcat_id") String subcat_id
            , @Field("shop_id") String shop_id);

    @FormUrlEncoded
    @POST("productdetail")
    Call<ProductDetailsRequest> getProductDetails(@Field("prod_id") String prod_id);

    @POST("shoplist")
    Call<ShopRequest> getShopList();

    @FormUrlEncoded
    @POST("dashboard_prods")
    Call<DashboardRequest> getDashboardProduct(@Field("shop_id") String shop_id);

    @FormUrlEncoded
    @POST("addprodtocart")
    Call<AddToCart> addToCart(@Field("token") String token
                ,@Field("device_id")String device_id
            , @Field("attr_valuefinal") String attr_valuefinal
            , @Field("attr_value_namefinal") String attr_value_namefinal
            , @Field("attr_idfinal") String attr_idfinal
            , @Field("shop_id") String shop_id
            , @Field("prod_id") String prod_id);

    @FormUrlEncoded
    @POST("cartlist")
    Call<CartRequest> getCartList(@Field("token") String token);

    @FormUrlEncoded
    @POST("updatecartqty")
    Call<UpdateCart> updateCart(@Field("cartid") String cartid
            , @Field("qty") String qty
            , @Field("token") String token);

    @FormUrlEncoded
    @POST("deletecartproduct")
    Call<DeleteCart> deleteCart(@Field("token") String token
            , @Field("cartid") String cartid);

    @FormUrlEncoded
    @POST("prodsimg")
    Call<ImageRequest> getProductImage(@Field("prod_id") String prod_id);

    @FormUrlEncoded
    @POST("relatedproducts")
    Call<RelatedProductRequest> getRelatedProduct(@Field("token") String token
            , @Field("cat_id") String cat_id
            , @Field("subcat_id") String subcat_id
            , @Field("prod_id") String prod_id);

    @POST("area")
    Call<StateRequest> getStateList();

    @FormUrlEncoded
    @POST("area")
    Call<DistrictRequest> getDistrictList(@Field("state_id") String state_id);

    @FormUrlEncoded
    @POST("placeorderonselecteditem")
    Call<PlaceOrderRequest> placeOrder(@Field("token") String token
            , @Field("delivery_state") String delivery_state
            , @Field("delivery_district") String delivery_district
            , @Field("delivery_pincode") String delivery_pincode
            , @Field("delivery_mobileno") String delivery_mobileno
            , @Field("delivery_address") String delivery_address
            , @Field("payment_method") String payment_method
            , @Field("shop_id") String shop_id
            , @Field("selected_prods") String selected_prods);

    @POST("allproducts")
    Call<AllProductRequest> getAllProducts();

    @FormUrlEncoded
    @POST("orderlist")
    Call<OrderedproductRequest> getOrderedProductList(@Field("token") String token);

    @FormUrlEncoded
    @POST("orderdetailcust")
    Call<OrderedAllProductRequest> getOrderedProductDetails(@Field("token") String token
            , @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("customeraddress")
    Call<AddressRequest> getAddressList(@Field("token") String token);

    @Multipart
    @POST("updateprof_ile")
    Call<UpdateProfileRequest> updateProfile(@Part("token") RequestBody token,
                                             @Part("name") RequestBody name,
                                             @Part("address") RequestBody address,
                                             @Part("email") RequestBody email,
                                             @Part MultipartBody.Part image);

    @POST("franchiseall_prods")
    Call<FranchiseProductRequest> getAllFranchiseProducts();

    @FormUrlEncoded
    @POST("shopcatwise_prods")
    Call<FranchiseCatWiseProductRequest> getFranchiseCatByProducts(@Field("cat_id") String cat_id,
                                                                   @Field("shop_id") String shop_id);

    @FormUrlEncoded
    @POST("registor_shop")
    Call<RegisterShopRequest> registerShop(@Field("token") String token
            , @Field("partner_name") String partner_name
            , @Field("shop_name") String shop_name
            , @Field("password") String password
            , @Field("email") String email
            , @Field("mobile") String mobile);

    @Multipart
    @POST("pan_reg")
    Call<SavePANDetails> savePanDetails(@Part("token") RequestBody token,
                                        @Part("pan_card") RequestBody pan_card,
                                        @Part MultipartBody.Part pan_img);

    @Multipart
    @POST("shopaddhaar_reg")
    Call<SaveAadhaarDetails> saveAadhaarDetails(@Part("token") RequestBody token,
                                                @Part("aadhar_card") RequestBody aadhar_card,
                                                @Part MultipartBody.Part front_aimg,
                                                @Part MultipartBody.Part back_aimg);

    @POST("banks")
    Call<BankRequest> getBankList();

    @Multipart
    @POST("bankdetail_reg")
    Call<SaveBankDetails> saveBankDetails(@Part("token") RequestBody token,
                                          @Part("bank_id") RequestBody bank_id,
                                          @Part("acc_name") RequestBody acc_name,
                                          @Part("acc_no") RequestBody acc_no,
                                          @Part("ifsc") RequestBody ifsc,
                                          @Part MultipartBody.Part passbook_img);

    @Multipart
    @POST("gst_reg")
    Call<SaveGstDetails> saveGstDetails(@Part("token") RequestBody token,
                                        @Part("gst_no") RequestBody gst_no,
                                        @Part MultipartBody.Part gst_img);

    @Multipart
    @POST("fssaino_reg")
    Call<SaveFssaiDetails> saveFssaiDetails(@Part("token") RequestBody token,
                                            @Part("licence_no") RequestBody licence_no,
                                            @Part MultipartBody.Part food_licence_img);

    @FormUrlEncoded
    @POST("addressshop_reg")
    Call<SaveShopAddressDetails> saveShopAddressDetails(@Field("token") String token
            , @Field("shop_address") String shop_address
            , @Field("state_id") String state_id
            , @Field("district_id") String district_id
            , @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("buynow")
    Call<BuyNowRequest> buyNow(@Field("token") String token
            , @Field("attr_valuefinal") String attr_valuefinal
            , @Field("attr_value_namefinal") String attr_value_namefinal
            , @Field("attr_idfinal") String attr_idfinal
            , @Field("shop_id") String shop_id
            , @Field("prod_id") String prod_id
            , @Field("delivery_state") String delivery_state
            , @Field("delivery_district") String delivery_district
            , @Field("delivery_pincode") String delivery_pincode
            , @Field("delivery_mobileno") String delivery_mobileno
            , @Field("delivery_address") String delivery_address
            , @Field("payment_method") String payment_method
            , @Field("qty") String qty);

    @FormUrlEncoded
    @POST("bannerimg")
    Call<BannerModel> bannerimg(@Field("img") String img);

    @POST("bestsellingprod")
    Call<BestSellingProductRequest> getBestSellingProductList();

    @FormUrlEncoded
    @POST("rating_prodwise")
    Call<UserReviewRequest> getUserReviews(@Field("prod_id") String prod_id);

    @FormUrlEncoded
    @POST("save_rating")
    Call<SendReview> sendUserReview(@Field("token") String token,
                                    @Field("product_id") String product_id,
                                    @Field("comment") String comment,
                                    @Field("rate") String rate);

    @POST("paymentmethod")
    Call<PaymentMethodRequest> getPaymentMethods();
//
//    @FormUrlEncoded
//    @POST("getyourlogincredential")
//    Call<FranchiseLoginRequest> getFranchiseLoginRequest(@Field("token") String token);
//
    @POST("deliveryfree_on")
    Call<FreeDeliveryAmountRequest> getFreeDeliveryAmount();

    @FormUrlEncoded
    @POST("cancelorder")
    Call<CancelOrder> cancelOrder(@Field("token") String token,
                                  @Field("order_id") String order_id);

    @POST("bannerimg")
    Call<BannerRequest> getBannerList();

    @FormUrlEncoded
    @POST("getyourlogincredential")
    Call<Final> getsubmit(@Field("token") String token);

}
