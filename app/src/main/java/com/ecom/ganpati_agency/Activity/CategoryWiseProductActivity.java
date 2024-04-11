package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Adapter.ProductBySubCatAdapter;
import com.ecom.ganpati_agency.Adapter.SubcategoryAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.ProductRequest;
import com.ecom.ganpati_agency.Model.request.SubCategoryModel;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.SubcatRe;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryWiseProductActivity extends AppCompatActivity {
    RecyclerView subCategoryRecycler, productRecycler;
    CardView cardBack;
    MaterialButton btnGoHome;
    ProgressBar progress;
    RelativeLayout parentLayout, noDataLayout;
    public TextView txtCategoryName, txtSubCategoryName;
    public String cat_name, cat_id, subcat_id, shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product);
        getSupportActionBar().hide();

        cardBack = findViewById(R.id.cardBack);
        txtCategoryName = findViewById(R.id.txtCategoryName);
        subCategoryRecycler = findViewById(R.id.subCategoryRecycler);
        productRecycler = findViewById(R.id.productRecycler);
        txtSubCategoryName = findViewById(R.id.txtSubCategoryName);
        parentLayout = findViewById(R.id.parentLayout);
        noDataLayout = findViewById(R.id.noDataLayout);
        btnGoHome = findViewById(R.id.btnGoHome);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        subCategoryRecycler.setVisibility(View.GONE);
        productRecycler.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cat_id = bundle.getString("cat_id");
            cat_name = bundle.getString("cat_name");

            Log.e("CAT ID", cat_id);
            Log.e("CAT NAME", cat_id);

            txtCategoryName.setText(cat_name);
        } else {
            Log.e("abcd", "abcd");
        }


        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryWiseProductActivity.super.onBackPressed();
            }
        });

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryWiseProductActivity.super.onBackPressed();
            }
        });

        if (InternetValidation.validation(CategoryWiseProductActivity.this)) {
            getSubCategoryList(cat_id);
        } else {
            InternetValidation.getNoConnectionDialog(CategoryWiseProductActivity.this);
        }
    }

    public void getSubCategoryList(String cat_id) {
        try {
            progress.setVisibility(View.VISIBLE);
            subCategoryRecycler.setLayoutManager(new LinearLayoutManager(CategoryWiseProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<SubCategoryModel> call = api.getSubCategory(cat_id);
            call.enqueue(new Callback<SubCategoryModel>() {
                @Override
                public void onResponse(Call<SubCategoryModel> call, Response<SubCategoryModel> response) {
                    progress.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            subCategoryRecycler.setVisibility(View.VISIBLE);
                            SubcatRe subCategory = response.body().getSubcatRes().get(0);
                            txtSubCategoryName.setText(subCategory.getName());
                            subcat_id = subCategory.getSubcatId();
                            Log.e("SUBCAT ID", subcat_id);
                            SubcategoryAdapter subcategoryAdapter = new SubcategoryAdapter(getApplicationContext()
                                    , CategoryWiseProductActivity.this
                                    , response.body().getSubcatRes());
                            subCategoryRecycler.setAdapter(subcategoryAdapter);
                             getCategoryRelatedProduct(cat_id, subcat_id, shop_id);
                        } else {
                            subCategoryRecycler.setVisibility(View.GONE);
                            productRecycler.setVisibility(View.GONE);
                            noDataLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.e("BODY", "Body is null");
                    }
                }

                @Override
                public void onFailure(Call<SubCategoryModel> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Log.e("EXCEPTION", t.getLocalizedMessage());

                    if (InternetValidation.validation(CategoryWiseProductActivity.this)) {
                        getCategoryRelatedProduct(cat_id, subcat_id, shop_id);
                    } else {
                        InternetValidation.getNoConnectionDialog(CategoryWiseProductActivity.this);
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Exception", e.getLocalizedMessage());
        }
    }

    public void getCategoryRelatedProduct(String cat_id, String subcat_id, String shop_id) {
        progress.setVisibility(View.VISIBLE);
        productRecycler.setLayoutManager(new LinearLayoutManager(CategoryWiseProductActivity.this));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<ProductRequest> call = api.getProductSubCatBy(cat_id, subcat_id, shop_id);
        call.enqueue(new Callback<ProductRequest>() {
            @Override
            public void onResponse(Call<ProductRequest> call, Response<ProductRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        noDataLayout.setVisibility(View.GONE);
                        productRecycler.setVisibility(View.VISIBLE);
                        ProductBySubCatAdapter productBySubCatAdapter = new ProductBySubCatAdapter(getApplicationContext()
                                , CategoryWiseProductActivity.this
                                , response.body().getProductRes());
                        productRecycler.setAdapter(productBySubCatAdapter);
                    } else {
                        productRecycler.setVisibility(View.GONE);
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
            public void onFailure(Call<ProductRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void addToCart(String token,String deviceid, String attrValFinal, String attrValNameFinal, String attrIdFinal, String shopId, String productId) {
        progress.setVisibility(View.VISIBLE);
        if(token == null){
            Intent intent = new Intent(CategoryWiseProductActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<AddToCart> call = api.addToCart(token,deviceid,  attrValFinal, attrValNameFinal, attrIdFinal, shopId, productId);
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
                                    Intent intent = new Intent(CategoryWiseProductActivity.this, MyCartActivity.class);
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



}