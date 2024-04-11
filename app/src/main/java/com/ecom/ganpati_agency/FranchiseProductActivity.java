package com.ecom.ganpati_agency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Activity.DirectBuyActivity;
import com.ecom.ganpati_agency.Activity.LoginActivity;
import com.ecom.ganpati_agency.Activity.MyCartActivity;
import com.ecom.ganpati_agency.Adapter.FranchiseCatWiseProductAdapter;
import com.ecom.ganpati_agency.Adapter.FranchiseCategoryAdapter;
import com.ecom.ganpati_agency.Adapter.SliderAdapt;
import com.ecom.ganpati_agency.Adapter.SliderAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.BannerRequest;
import com.ecom.ganpati_agency.Model.request.CategoryModel;
import com.ecom.ganpati_agency.Model.request.FranchiseCatWiseProductRequest;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.Banner;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FranchiseProductActivity extends AppCompatActivity {

    ViewPager viewPagerOne;
    CircleIndicator circleIndicatorOne;
    MaterialButton btnGoHome;
    List<Banner> bannerList = new ArrayList<>();
    Timer timer;
    Handler handler;

    TextView txtShopName, totalProduct;
    RecyclerView franchiseCategoryRecycler, franchiseProductRecycler;
    ProgressBar franchiseProgress;
    RelativeLayout parentLayout, noDataLayout;
    public String shop_name, shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_product);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        franchiseProgress = findViewById(R.id.franchiseProgress);
        parentLayout = findViewById(R.id.parentLayout);
        txtShopName = findViewById(R.id.txtShopName);
        totalProduct = findViewById(R.id.totalProduct);
        noDataLayout = findViewById(R.id.noDataLayout);
        btnGoHome = findViewById(R.id.btnGoHome);

        noDataLayout.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shop_name = bundle.getString("shop_name");
            shop_id = bundle.getString("shop_id");
            Log.e("SHOP ID",shop_id);
            Log.e("SHOP NAME",shop_name);
            txtShopName.setText(shop_name);
        }

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FranchiseProductActivity.super.onBackPressed();
            }
        });

        franchiseProgress.setVisibility(View.GONE);
        getSlider();

    }

    public void getSlider() {
        franchiseProgress.setVisibility(View.VISIBLE);
        viewPagerOne = findViewById(R.id.viewPagerOne);
        circleIndicatorOne = findViewById(R.id.circleIndicatorOne);

        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<BannerRequest> call = api.getBannerList();
        call.enqueue(new Callback<BannerRequest>() {
            @Override
            public void onResponse(Call<BannerRequest> call, Response<BannerRequest> response) {
                franchiseProgress.setVisibility(View.GONE);
                if(response.body()!=null){
                    if(response.body().getStatus()){
                        bannerList = response.body().getBannerRes();
                        SliderAdapt adapter = new SliderAdapt(getApplicationContext(),null,FranchiseProductActivity.this,response.body().getBannerRes());
                        viewPagerOne.setAdapter(adapter);
                        circleIndicatorOne.setViewPager(viewPagerOne);
                        handler = new Handler();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int i = viewPagerOne.getCurrentItem();
                                        if (i == bannerList.size() - 1) {
                                            i = 0;
                                        } else {
                                            i++;
                                        }
                                        viewPagerOne.setCurrentItem(i, true);
                                    }
                                });
                            }
                        }, 4000, 4000);
                    }
                }
                getCategoryList();
            }

            @Override
            public void onFailure(Call<BannerRequest> call, Throwable t) {
                getCategoryList();
                franchiseProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION",t.getLocalizedMessage());
            }
        });
    }

    public void getCategoryList() {
        franchiseProgress.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
        franchiseCategoryRecycler = findViewById(R.id.franchiseCategoryRecycler);
        franchiseCategoryRecycler.setLayoutManager(new LinearLayoutManager(FranchiseProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CategoryModel> call = api.category();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                franchiseProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        FranchiseCategoryAdapter franchiseCategoryAdapter = new FranchiseCategoryAdapter(getApplicationContext(),
                                FranchiseProductActivity.this,
                                response.body().getCatRes());
                        franchiseCategoryRecycler.setAdapter(franchiseCategoryAdapter);
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        franchiseCategoryRecycler.setVisibility(View.GONE);
                        franchiseProductRecycler.setVisibility(View.GONE);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                getProductByCategory("", shop_id);
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                franchiseProgress.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
                franchiseCategoryRecycler.setVisibility(View.GONE);
                franchiseProductRecycler.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void getProductByCategory(String cat_id, String shop_id) {
        franchiseProgress.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
        franchiseProductRecycler = findViewById(R.id.franchiseProductRecycler);
        franchiseProductRecycler.setLayoutManager(new GridLayoutManager(FranchiseProductActivity.this, 2));
        franchiseProductRecycler.setVisibility(View.GONE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<FranchiseCatWiseProductRequest> call = api.getFranchiseCatByProducts(cat_id, shop_id);
        call.enqueue(new Callback<FranchiseCatWiseProductRequest>() {
            @Override
            public void onResponse(Call<FranchiseCatWiseProductRequest> call, Response<FranchiseCatWiseProductRequest> response) {
                franchiseProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        franchiseProductRecycler.setVisibility(View.VISIBLE);
                        totalProduct.setText(response.body().getShopcatprodsRes().size()+" Products");
                        FranchiseCatWiseProductAdapter franchiseCatWiseProductAdapter = new FranchiseCatWiseProductAdapter(getApplicationContext()
                                , FranchiseProductActivity.this
                                , response.body().getShopcatprodsRes());
                        franchiseProductRecycler.setAdapter(franchiseCatWiseProductAdapter);
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        franchiseProductRecycler.setVisibility(View.GONE);
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
            public void onFailure(Call<FranchiseCatWiseProductRequest> call, Throwable t) {
                franchiseProgress.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
                franchiseProductRecycler.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void addToCart(String token,String deviceid, String attrValFinal, String attrValNameFinal, String attrIdFinal, String shopId, String productId) {
        franchiseProgress.setVisibility(View.VISIBLE);
        if(token == null){
            Intent intent = new Intent(FranchiseProductActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<AddToCart> call = api.addToCart(token, deviceid, attrValFinal, attrValNameFinal, attrIdFinal, shopId, productId);
            call.enqueue(new Callback<AddToCart>() {
                @Override
                public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {
                    franchiseProgress.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(FranchiseProductActivity.this, MyCartActivity.class);
                                    startActivity(intent);
                                }
                            }, 500);
                        } else {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#EA2525"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                        }
                    } else {
                        Log.e("BODY", "Body is null");
                    }
                }

                @Override
                public void onFailure(Call<AddToCart> call, Throwable t) {
                    franchiseProgress.setVisibility(View.GONE);
                    Log.e("EXCEPTION", t.getLocalizedMessage());
                }
            });
        }

    }
}