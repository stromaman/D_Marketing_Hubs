package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Adapter.BestSellingProductAdapter;
import com.ecom.ganpati_agency.Adapter.CategoryListAdapter;
import com.ecom.ganpati_agency.Adapter.CategoryMainListAdapter;
import com.ecom.ganpati_agency.Adapter.FranchiseProductAdapter;
import com.ecom.ganpati_agency.Adapter.ItemlistAdapter;
import com.ecom.ganpati_agency.Adapter.ShopListAdapter;
import com.ecom.ganpati_agency.Adapter.SliderAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.BuildConfig;
import com.ecom.ganpati_agency.Model.ItemlistModel;
import com.ecom.ganpati_agency.Model.request.BannerModel;
import com.ecom.ganpati_agency.Model.request.BestSellingProductRequest;
import com.ecom.ganpati_agency.Model.request.CartRequest;
import com.ecom.ganpati_agency.Model.request.CategoryModel;
import com.ecom.ganpati_agency.Model.request.DashboardRequest;
import com.ecom.ganpati_agency.Model.request.FranchiseProductRequest;
import com.ecom.ganpati_agency.Model.request.ShopRequest;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.Cart;
import com.ecom.ganpati_agency.Model.response.Dashboard;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.seller.JoinSellerActivity;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout menuLayout, viewCartLayout, franchiseShopLayout, categoryLayout, bestSellingLayout, franchiseLayout;
    RecyclerView categoryRecycler, bestSellingRecycler, offerRecycler, categoryMainRecycler, franchiseRecycler, shopRecyler;
    TextView txtSearch, txtShopCount, txtAmount, txtItem, cartCount;
    public RelativeLayout bottomCartLayout;
    String versionName = "";
    RelativeLayout myCartLayout, parentLayout, shopLayout, sliderLayout;

    ProgressBar dashProgress;
    ViewPager viewPagerOne;
    CircleIndicator circleIndicatorOne;
    Timer timer;
    Handler handler;
    List<Cart> cartList = new ArrayList<>();
    List<Dashboard> dashboard = new ArrayList<>();
    List<Dashboard> newDashboard = new ArrayList<>();

     SliderView slider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        slider=findViewById(R.id.slider);
        menuLayout = findViewById(R.id.menuLayout);
        txtSearch = findViewById(R.id.txtSearch);
        txtShopCount = findViewById(R.id.txtShopCount);
        txtAmount = findViewById(R.id.txtAmount);
        txtItem = findViewById(R.id.txtItem);
        cartCount = findViewById(R.id.cartCount);
        bottomCartLayout = findViewById(R.id.bottomCartLayout);
        myCartLayout = findViewById(R.id.myCartLayout);
        parentLayout = findViewById(R.id.parentLayout);
        shopLayout = findViewById(R.id.shopLayout);
        dashProgress = findViewById(R.id.dashProgress);
        viewCartLayout = findViewById(R.id.viewCartLayout);
        categoryLayout = findViewById(R.id.categoryLayout);
        bestSellingLayout = findViewById(R.id.bestSellingLayout);
        franchiseLayout = findViewById(R.id.franchiseLayout);
        franchiseShopLayout = findViewById(R.id.franchiseShopLayout);
        shopRecyler = findViewById(R.id.shopRecyler);
       // sliderLayout = findViewById(R.id.sliderLayout);

        versionName = BuildConfig.VERSION_NAME;
        bottomCartLayout.setVisibility(View.GONE);
        dashProgress.setVisibility(View.GONE);
        categoryLayout.setVisibility(View.GONE);
    //    franchiseShopLayout.setVisibility(View.GONE);
    //    bestSellingLayout.setVisibility(View.GONE);
     //   franchiseLayout.setVisibility(View.GONE);
     //   sliderLayout.setVisibility(View.GONE);
        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDrawerDialog();
            }
        });

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, SearchActivity.class));
            }
        });

        myCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });

        shopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, MyCartActivity.class));
            }
        });

        BannerApi();
        getCategoryList();
        getProductsList(MyPreferences.getInstance(getApplicationContext()).getShopId());
    }

    private void BannerApi() {
        try{
            MyPreferences preferences = new MyPreferences(DashboardActivity.this);
            ServiceApi serviceApi = ApiClient.getClient().create(ServiceApi.class);
            Call<BannerModel> call = serviceApi.bannerimg(preferences.getid());
            call.enqueue(new Callback<BannerModel>() {
                @Override
                public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {

                            SliderAdapter adapter = new SliderAdapter(DashboardActivity.this,response.body().getBannerRes());
                            slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                            slider.setSliderAdapter(adapter);
                            slider.setScrollTimeInSec(3);
                            slider.setAutoCycle(true);
                            slider.startAutoCycle();


                            Log.d("response",""+response.body());
                        } else {
                            Log.d("response",""+response.body());
                            Toast.makeText(DashboardActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BannerModel> call, Throwable t) {
                    //  Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error", t.getLocalizedMessage());
                }
            });


        }catch (Exception e){
            Log.e("Exception",e.getLocalizedMessage());}

    }



    Dialog drawerDialog;
    LinearLayout transLayer, drawerProfileLayout, drawerMyCartLayout,
            drawerOrderHistoryLayout, drawerAddressLayout, logoutLayout,
            drawerAboutUsLayout, drawerJoinSellerLayout, drawerPrivacyLayout, drawerTermsLayout, drawerReturnLayout, drawerQualityLayout,drawertrackOrderLayout, drawerContactLayout;
    TextView txtVersionName, userName, userNumber;
    CircleImageView userImage;

    public void getDrawerDialog() {
        drawerDialog = new Dialog(DashboardActivity.this);
        drawerDialog.setContentView(R.layout.custom_drawer_layout);
        drawerDialog.setCancelable(true);

        transLayer = drawerDialog.findViewById(R.id.transLayer);
        txtVersionName = drawerDialog.findViewById(R.id.txtVersionName);
        drawerProfileLayout = drawerDialog.findViewById(R.id.drawerProfileLayout);
        drawerMyCartLayout = drawerDialog.findViewById(R.id.drawerMyCartLayout);
        drawerOrderHistoryLayout = drawerDialog.findViewById(R.id.drawerOrderHistoryLayout);
        drawerAddressLayout = drawerDialog.findViewById(R.id.drawerAddressLayout);
        logoutLayout = drawerDialog.findViewById(R.id.logoutLayout);
        userName = drawerDialog.findViewById(R.id.userName);
        userNumber = drawerDialog.findViewById(R.id.userNumber);
        userImage = drawerDialog.findViewById(R.id.userImage);
        drawerAboutUsLayout = drawerDialog.findViewById(R.id.drawerAboutUsLayout);
        drawerJoinSellerLayout = drawerDialog.findViewById(R.id.drawerJoinSellerLayout);
        drawerPrivacyLayout = drawerDialog.findViewById(R.id.drawerPrivacyLayout);
        drawerTermsLayout = drawerDialog.findViewById(R.id.drawerTermsLayout);
        drawerReturnLayout = drawerDialog.findViewById(R.id.drawerReturnLayout);
        drawerQualityLayout = drawerDialog.findViewById(R.id.drawerQualityLayout);
        drawerContactLayout = drawerDialog.findViewById(R.id.drawerContactLayout);
        drawertrackOrderLayout = drawerDialog.findViewById(R.id.drawertrackOrderLayout);

        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
        userName.setText(preferences.getUserName());
        userNumber.setText("+91 " + preferences.getUserNumber());

        if (preferences.getUserImage()!=null) {
            Glide.with(getApplicationContext())
                    .load(Constant.IMAGE_URL + preferences.getUserImage())
                    .into(userImage);
        } else {
            Glide.with(getApplicationContext())
                    .load(R.drawable.noprofile)
                    .into(userImage);
        }

        drawerProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        drawerMyCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
                Intent intent = new Intent(DashboardActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });

        drawerOrderHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
                Intent intent = new Intent(DashboardActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        drawerAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
                Intent intent = new Intent(DashboardActivity.this, AddAddressActivity.class);
                intent.putExtra("flag", "drawer");
                startActivity(intent);
            }
        });
        drawertrackOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
                Intent intent = new Intent(DashboardActivity.this, OrderTrackingActivity.class);
                startActivity(intent);
            }
        });
        drawerJoinSellerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    drawerDialog.dismiss();
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                 String Token = preferences.getUserToken();
                if (Token == null) {
                    // If token is null, go to login page
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                } else {
                    // If token exists, go to JoinSellerActivity page
                    startActivity(new Intent(DashboardActivity.this, JoinSellerActivity.class));
                }


            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
                MyPreferences.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });

        txtVersionName.setText("Version (" + versionName + ")");
        transLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerDialog.dismiss();
            }
        });

        drawerDialog.show();
        drawerDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        drawerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        drawerDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationReport;
        drawerDialog.getWindow().setGravity(Gravity.TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawerDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            drawerDialog.getWindow().setStatusBarColor(getApplicationContext().getColor(R.color.white));
        }
    }

    public void getCategoryList() {
        dashProgress.setVisibility(View.VISIBLE);
        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CategoryModel> call = api.category();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                dashProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        categoryLayout.setVisibility(View.VISIBLE);
                        CategoryListAdapter categoryAdapter = new CategoryListAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , response.body().getCatRes());
                        categoryRecycler.setAdapter(categoryAdapter);
                    } else {
                  //      categoryLayout.setVisibility(View.GONE);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525"))
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getBestSellingProduct();
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                dashProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getBestSellingProduct();
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }
        });
    }

    public void getBestSellingProduct() {
        dashProgress.setVisibility(View.VISIBLE);
        bestSellingRecycler = findViewById(R.id.bestSellingRecycler);
        bestSellingRecycler.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<BestSellingProductRequest> call = api.getBestSellingProductList();
        call.enqueue(new Callback<BestSellingProductRequest>() {
            @Override
            public void onResponse(Call<BestSellingProductRequest> call, Response<BestSellingProductRequest> response) {
                dashProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        bestSellingLayout.setVisibility(View.VISIBLE);
                        BestSellingProductAdapter bestSellingProductAdapter = new BestSellingProductAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , response.body().getBestsellprodRes());
                        bestSellingRecycler.setAdapter(bestSellingProductAdapter);
                    } else {
                        bestSellingLayout.setVisibility(View.GONE);
                        BestSellingProductAdapter bestSellingProductAdapter = new BestSellingProductAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , response.body().getBestsellprodRes());
                        bestSellingRecycler.setAdapter(bestSellingProductAdapter);
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getShopList();
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }

            @Override
            public void onFailure(Call<BestSellingProductRequest> call, Throwable t) {
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getShopList();
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
                dashProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void getShopList() {
        dashProgress.setVisibility(View.VISIBLE);
        franchiseRecycler = findViewById(R.id.franchiseRecycler);
        franchiseRecycler.setLayoutManager(new GridLayoutManager(DashboardActivity.this, 2));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<ShopRequest> call = api.getShopList();
        call.enqueue(new Callback<ShopRequest>() {
            @Override
            public void onResponse(Call<ShopRequest> call, Response<ShopRequest> response) {
                dashProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        franchiseLayout.setVisibility(View.VISIBLE);
                        ShopListAdapter shopListAdapter = new ShopListAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , response.body().getShopRes());
                        franchiseRecycler.setAdapter(shopListAdapter);
                    } else {
                        franchiseLayout.setVisibility(View.GONE);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525"))
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                if (InternetValidation.validation(DashboardActivity.this)) {
                      getFranchiseProductsList();
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ShopRequest> call, Throwable t) {
                dashProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getFranchiseProductsList();
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }
        });
    }

    public void getFranchiseProductsList() {
        dashProgress.setVisibility(View.VISIBLE);
        shopRecyler.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<FranchiseProductRequest> call = api.getAllFranchiseProducts();
        call.enqueue(new Callback<FranchiseProductRequest>() {
            @Override
            public void onResponse(Call<FranchiseProductRequest> call, Response<FranchiseProductRequest> response) {
                dashProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        franchiseShopLayout.setVisibility(View.VISIBLE);
                        FranchiseProductAdapter franchiseProductAdapter = new FranchiseProductAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , response.body().getFranchiseRes());
                        shopRecyler.setAdapter(franchiseProductAdapter);
                    } else {
                        FranchiseProductAdapter franchiseProductAdapter = new FranchiseProductAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , response.body().getFranchiseRes());
                        shopRecyler.setAdapter(franchiseProductAdapter);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525"))
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getProductsList(MyPreferences.getInstance(getApplicationContext()).getShopId());
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }

            @Override
            public void onFailure(Call<FranchiseProductRequest> call, Throwable t) {
                dashProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
                if (InternetValidation.validation(DashboardActivity.this)) {
                    getProductsList(MyPreferences.getInstance(getApplicationContext()).getShopId());
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }
        });
    }

    public void getProductsList(String shop_id) {
        dashProgress.setVisibility(View.VISIBLE);
        categoryMainRecycler = findViewById(R.id.categoryMainRecycler);
        categoryMainRecycler.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<DashboardRequest> call = api.getDashboardProduct(shop_id);
        call.enqueue(new Callback<DashboardRequest>() {
            @Override
            public void onResponse(Call<DashboardRequest> call, Response<DashboardRequest> response) {
                dashProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        dashboard = response.body().getDashboardRes();
                        newDashboard.clear();
                        for (int i = 0; i < dashboard.size(); i++) {
                            if (!dashboard.get(i).getProducts().isEmpty()) {
                                newDashboard.add(dashboard.get(i));
                            }
                        }
                        CategoryMainListAdapter categoryMainListAdapter = new CategoryMainListAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , newDashboard);
                        categoryMainRecycler.setAdapter(categoryMainListAdapter);
                    } else {
                        CategoryMainListAdapter categoryMainListAdapter = new CategoryMainListAdapter(getApplicationContext()
                                , DashboardActivity.this
                                , newDashboard);
                        categoryMainRecycler.setAdapter(categoryMainListAdapter);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525"))
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                if (InternetValidation.validation(DashboardActivity.this)) {
                 //  getCartList(preferences.getUserToken());
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }

            }

            @Override
            public void onFailure(Call<DashboardRequest> call, Throwable t) {
                dashProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                if (InternetValidation.validation(DashboardActivity.this)) {
                  //  getCartList(preferences.getUserToken());
                } else {
                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
                }
            }
        });
    }

    public void addToCart(String token,String deviceid, String attrValFinal, String attrValNameFinal, String attrIdFinal, String shopId, String productId) {
        dashProgress.setVisibility(View.VISIBLE);
        if(token == null){
            Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<AddToCart> call = api.addToCart(token,deviceid,attrValFinal, attrValNameFinal, attrIdFinal, shopId, productId);
            call.enqueue(new Callback<AddToCart>() {
                @Override
                public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {
                    dashProgress.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                           getCartList(token);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(DashboardActivity.this, MyCartActivity.class);
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
                    dashProgress.setVisibility(View.GONE);
                    Log.e("EXCEPTION", t.getLocalizedMessage());
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MyPreferences preferences = new MyPreferences(getApplicationContext());
        String tk = preferences.getUserToken();
        getCartList(tk);
    }

    public void getCartList(String token) {
        dashProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CartRequest> call = api.getCartList(token);
        call.enqueue(new Callback<CartRequest>() {
            @Override
            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
                dashProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        cartList = response.body().getCartlistResp();
                        cartCount.setText(String.valueOf(cartList.size()));
                        double amount = 0.0;
                        int count = 0;
                        for (Cart cart : cartList) {
                            amount = amount + (Double.parseDouble(cart.getProductdata().getOnlinePrice()) * Integer.parseInt(cart.getQty()));
                            count = count + Integer.parseInt(cart.getQty());
                        }
                        txtAmount.setText("₹" + amount);
                        if (count > 0) {
                            bottomCartLayout.setVisibility(View.VISIBLE);
                            txtItem.setText(count + " ITEM");
                        }
                    } else {
                        cartCount.setText("0");
                        bottomCartLayout.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }

            }

            @Override
            public void onFailure(Call<CartRequest> call, Throwable t) {
                dashProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());

            }
        });
    }

//    public void getCartList(String token, String product_id) {
//        dashProgress.setVisibility(View.VISIBLE);
//        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
//        Call<CartRequest> call = api.getCartList(token);
//        call.enqueue(new Callback<CartRequest>() {
//            @Override
//            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
//                dashProgress.setVisibility(View.GONE);
//                if (response.body() != null) {
//                    if (response.body().getStatus()) {
//                        cartCount.setText(String.valueOf(response.body().getCartlistResp().size()));
//                    } else {
//                        cartCount.setText("0");
//                    }
//                } else {
//                    Log.e("BODY", "Body is null");
//                }
//
//                if (InternetValidation.validation(DashboardActivity.this)) {
//
//                } else {
//                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CartRequest> call, Throwable t) {
//                dashProgress.setVisibility(View.GONE);
//
//                if (InternetValidation.validation(DashboardActivity.this)) {
//                } else {
//                    InternetValidation.getNoConnectionDialog(DashboardActivity.this);
//                }
//                Log.e("EXCEPTION", t.getLocalizedMessage());
//            }
//        });
//    }



    public BottomSheetDialog selectShopDialog;
    RecyclerView usersRecycler;
    TextView txtAddMember;
    ProgressBar shopProgress;



}