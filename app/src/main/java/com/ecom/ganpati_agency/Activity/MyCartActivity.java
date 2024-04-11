package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Adapter.MyCartListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.CartRequest;
import com.ecom.ganpati_agency.Model.request.FreeDeliveryAmountRequest;
import com.ecom.ganpati_agency.Model.response.Cart;
import com.ecom.ganpati_agency.Model.response.DeleteCart;
import com.ecom.ganpati_agency.Model.response.UpdateCart;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartActivity extends AppCompatActivity {
    RecyclerView myCartRecycler;
    RelativeLayout parentLayout, noDataLayout;
    CardView cardBack;
    TextView total_amount, txtItemCount;
    LinearLayout checkOutLayout;
    ProgressBar cartProgress;
    MaterialButton btnGoHome;
    List<Cart> cartList = new ArrayList<>();
    public List<String> productIdList = new ArrayList<>();
    public double amount, sellingPrice, mrp ;
    public double deliveryCharge=0.0;
    public int totalProduct;
    int totalCount, freeDeliveryAmount;
    public double maxDeliveryCharge = 0.0;
    public double updateDeliveryCharge = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        getSupportActionBar().hide();


        cardBack = findViewById(R.id.cardBack);
        cartProgress = findViewById(R.id.cartProgress);
        parentLayout = findViewById(R.id.parentLayout);
        total_amount = findViewById(R.id.total_amount);
        noDataLayout = findViewById(R.id.noDataLayout);
        myCartRecycler = findViewById(R.id.myCartRecycler);
        btnGoHome = findViewById(R.id.btnGoHome);
        checkOutLayout = findViewById(R.id.checkOutLayout);
        txtItemCount = findViewById(R.id.txtItemCount);

        myCartRecycler.setLayoutManager(new LinearLayoutManager(MyCartActivity.this));
        cartProgress.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        myCartRecycler.setVisibility(View.GONE);
        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCartActivity.super.onBackPressed();
            }
        });

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCartActivity.super.onBackPressed();
            }
        });

//        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
//        if (InternetValidation.validation(MyCartActivity.this)) {
//            getCartList(preferences.getUserToken());
//        } else {
//            InternetValidation.getNoConnectionDialog(MyCartActivity.this);
//        }



        checkOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cartList.isEmpty()) {
                    if (!productIdList.isEmpty()) {
                        Log.e("PRODUCT ID'S", new Gson().toJson(productIdList));
                        Log.e("SELLING PRICE", sellingPrice + "");
                        Log.e("MRP", mrp + "");
                        Log.e("DELIVERY CHARGE2", deliveryCharge+ "");
                        Log.e("COUNT", totalProduct + "");
                        Intent intent = new Intent(MyCartActivity.this, CheckoutActivity.class);
                        intent.putExtra("coming_from", "MyCart");
                        intent.putExtra("productIdList", new Gson().toJson(productIdList));
                        intent.putExtra("discount", sellingPrice);
                        intent.putExtra("mrp", mrp);
                        intent.putExtra("deliveryCharge", deliveryCharge);
                        intent.putExtra("count", totalProduct);
                        if (InternetValidation.validation(MyCartActivity.this)) {
                            startActivity(intent);
                        } else {
                            InternetValidation.getNoConnectionDialog(MyCartActivity.this);
                        }
                    } else {
                        Snackbar.make(parentLayout, "Please Select Products To Buy...!!", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                    }
                } else {
                    Snackbar.make(parentLayout, "No Product In Your Cart...!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                }
            }
        });








    }


    MyPreferences preferences = MyPreferences.getInstance(MyCartActivity.this);
    @Override
    protected void onResume() {
        super.onResume();
        getCartList(preferences.getUserToken());

    }



//    public void getCartList(String token) {
//        productIdList.clear();
//        amount = 0.0;
//        sellingPrice = 0.0;
//        mrp = 0.0;
////        deliveryCharge = 0.0;
//        totalProduct = 0;
//        final double[] highestDeliveryCharge = {0.0}; // Variable to store the highest delivery charge
//
//        cartProgress.setVisibility(View.VISIBLE);
//        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
//        Call<CartRequest> call = api.getCartList(token);
//        call.enqueue(new Callback<CartRequest>() {
//            @Override
//            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
//                cartProgress.setVisibility(View.GONE);
//                if (response.body() != null) {
//                    if (response.body().getStatus()) {
//                        myCartRecycler.setVisibility(View.VISIBLE);
//                        MyCartListAdapter myCartListAdapter = new MyCartListAdapter(getApplicationContext(),
//                                MyCartActivity.this,
//                                response.body().getCartlistResp());
//                        myCartRecycler.setAdapter(myCartListAdapter);
//                        cartList = response.body().getCartlistResp();
//                        txtItemCount.setText("You have " + cartList.size() + " items in cart");
//
//                        for (Cart cart : cartList) {
//                            amount = amount + (Double.parseDouble(cart.getProductdata().getOnlinePrice()) * Integer.parseInt(cart.getQty()));
//
//                            // Update highestDeliveryCharge if the current product's delivery charge is higher
//                            double currentDeliveryCharge = Double.parseDouble(cart.getProductdata().getDeliveryCharge());
//                            if (currentDeliveryCharge > highestDeliveryCharge[0]) {
//                                highestDeliveryCharge[0] = currentDeliveryCharge;
//                            }
//                        }
//
//                        // Add highest delivery charge to the total amount
//                        total_amount.setText("₹" + (amount + highestDeliveryCharge[0]));
//
//                        // Update global variable for use in checkout
//                       deliveryCharge = highestDeliveryCharge[0];
//                        Log.d("TAG", "onResponse: deliveryCharge " +deliveryCharge);
//                    } else {
//                        total_amount.setText("₹0");
//                        myCartRecycler.setVisibility(View.GONE);
//                        noDataLayout.setVisibility(View.VISIBLE);
//
//                    }
//                } else {
//                    Log.e("BODY", "Body is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CartRequest> call, Throwable t) {
//                cartProgress.setVisibility(View.GONE);
//                Log.e("EXCEPTION", t.getLocalizedMessage());
//            }
//        });
//    }

    public void getCartList(String token) {
        productIdList.clear();
        amount = 0.0;
        sellingPrice = 0.0;
        mrp = 0.0;
        maxDeliveryCharge = 0.0;
        totalProduct = 0;
        cartProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CartRequest> call = api.getCartList(token);
        call.enqueue(new Callback<CartRequest>() {
            @Override
            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
                cartProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        myCartRecycler.setVisibility(View.VISIBLE);
                        MyCartListAdapter myCartListAdapter = new MyCartListAdapter(getApplicationContext()
                                , MyCartActivity.this
                                , response.body().getCartlistResp());
                        myCartRecycler.setAdapter(myCartListAdapter);
                        cartList = response.body().getCartlistResp();
                        txtItemCount.setText("You have " + cartList.size() + " items in cart");
                        for (Cart cart : cartList) {
                            amount = amount + (Double.parseDouble(cart.getProductdata().getOnlinePrice()) * Integer.parseInt(cart.getQty()));
                        }
                        total_amount.setText("₹" + amount);
                    } else {
                        total_amount.setText("₹0");
                        myCartRecycler.setVisibility(View.GONE);
                        noDataLayout.setVisibility(View.VISIBLE);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<CartRequest> call, Throwable t) {
                cartProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }






    public void updateCartQuantity(String cart_id, String quantity, String token) {
        cartProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<UpdateCart> call = api.updateCart(cart_id, quantity, token);
        call.enqueue(new Callback<UpdateCart>() {
            @Override
            public void onResponse(Call<UpdateCart> call, Response<UpdateCart> response) {
                cartProgress.setVisibility(View.GONE);
                try {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                        } else {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#EA2525"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                        }
                    } else {
                        Log.e("BODY", "Body is empty");
                    }
                } catch (Exception e) {
                    Log.e("EXCEPTION", e.getLocalizedMessage());
                }
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                if (InternetValidation.validation(MyCartActivity.this)) {
                    getCartList(preferences.getUserToken());
                } else {
                    InternetValidation.getNoConnectionDialog(MyCartActivity.this);
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call, Throwable t) {
                cartProgress.setVisibility(View.GONE);
                Log.e("FAILURE", t.getLocalizedMessage());
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                if (InternetValidation.validation(MyCartActivity.this)) {
                    getCartList(preferences.getUserToken());
                } else {
                    InternetValidation.getNoConnectionDialog(MyCartActivity.this);
                }
            }
        });
    }

    public void deleteCartItem(String cart_id) {
        cartProgress.setVisibility(View.VISIBLE);
        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<DeleteCart> call = api.deleteCart(preferences.getUserToken(), cart_id);
        call.enqueue(new Callback<DeleteCart>() {
            @Override
            public void onResponse(Call<DeleteCart> call, Response<DeleteCart> response) {
                cartProgress.setVisibility(View.GONE);
                try {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                        } else {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#EA2525"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                        }
                    } else {
                        Log.e("BODY", "Body is empty");
                    }
                } catch (Exception e) {
                    Log.e("EXCEPTION", e.getLocalizedMessage());
                }

                if (InternetValidation.validation(MyCartActivity.this)) {
                    getCartList(preferences.getUserToken());
                } else {
                    InternetValidation.getNoConnectionDialog(MyCartActivity.this);
                }
            }

            @Override
            public void onFailure(Call<DeleteCart> call, Throwable t) {
                cartProgress.setVisibility(View.GONE);
                Log.e("FAILURE", t.getLocalizedMessage());
                getCartList(preferences.getUserToken());
            }
        });

    }


}