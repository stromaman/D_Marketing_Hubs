package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ecom.ganpati_agency.Adapter.AllProductListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.AllProductRequest;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.Product;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    CardView cardBack;
    ProgressBar progress;
    LinearLayout mainLayout;
    RelativeLayout noDataLayout, parentLayout;
    RecyclerView searchProductRecycler;
    EditText edtSearchProduct;
    AllProductListAdapter allProductListAdapter;
    List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cardBack = findViewById(R.id.cardBack);
        progress = findViewById(R.id.progress);
        searchProductRecycler = findViewById(R.id.searchProductRecycler);
        mainLayout = findViewById(R.id.mainLayout);
        noDataLayout = findViewById(R.id.noDataLayout);
        edtSearchProduct = findViewById(R.id.edtSearchProduct);
        parentLayout = findViewById(R.id.parentLayout);

        progress.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        searchProductRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.super.onBackPressed();
            }
        });

        if (InternetValidation.validation(SearchActivity.this)) {
            getAllProductList();
        } else {
            InternetValidation.getNoConnectionDialog(SearchActivity.this);
        }

        edtSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterProduct(String.valueOf(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void filterProduct(String productName){
        List<Product> filteredList = new ArrayList<>();
        for (Product product: productList) {
            if(product.getProductName().toLowerCase().trim().contains(productName.toLowerCase().trim())){
                filteredList.add(product);
            }
        }
        allProductListAdapter.filterList(filteredList);
    }
    public void addToCart(String token,String deviceid, String attrValFinal, String attrValNameFinal, String attrIdFinal, String shopId, String productId){
        progress.setVisibility(View.VISIBLE);
        if(token == null){
            Intent intent = new Intent(SearchActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<AddToCart> call = api.addToCart(token,deviceid,attrValFinal, attrValNameFinal, attrIdFinal, shopId, productId);
            call.enqueue(new Callback<AddToCart>() {
                @Override
                public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {
                    progress.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b")) // 136afb
                                    .setTextColor(Color.WHITE)
                                    .show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SearchActivity.this, MyCartActivity.class);
                                    startActivity(intent);
                                }
                            }, 500);
                        } else {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                    .setTextColor(Color.WHITE)
                                    .show();
                        }
                    } else {
                        Log.e("BODY", "Body is null");
                    }
                }

                @Override
                public void onFailure(Call<AddToCart> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Log.e("EXCEPTION", t.getLocalizedMessage());
                }
            });
        }
    }

    public void getAllProductList() {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<AllProductRequest> call = api.getAllProducts();
        call.enqueue(new Callback<AllProductRequest>() {
            @Override
            public void onResponse(Call<AllProductRequest> call, Response<AllProductRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        productList = response.body().getAllproductsResp();
                        mainLayout.setVisibility(View.VISIBLE);
                        noDataLayout.setVisibility(View.GONE);
                        allProductListAdapter = new AllProductListAdapter(getApplicationContext()
                                , SearchActivity.this
                                , response.body().getAllproductsResp());
                        searchProductRecycler.setAdapter(allProductListAdapter);
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        mainLayout.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<AllProductRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}
