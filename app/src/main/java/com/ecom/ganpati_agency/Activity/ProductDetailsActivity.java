package com.ecom.ganpati_agency.Activity;

import static android.text.Html.fromHtml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Adapter.ProductImageAdapter;
import com.ecom.ganpati_agency.Adapter.RelatedProductListAdapter;
import com.ecom.ganpati_agency.Adapter.UserReviewAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.CartRequest;
import com.ecom.ganpati_agency.Model.request.ImageRequest;
import com.ecom.ganpati_agency.Model.request.ProductDetailsRequest;
import com.ecom.ganpati_agency.Model.request.RelatedProductRequest;
import com.ecom.ganpati_agency.Model.request.UserReviewRequest;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.Image;
import com.ecom.ganpati_agency.Model.response.ProductDetails;
import com.ecom.ganpati_agency.Model.response.SendReview;
import com.ecom.ganpati_agency.Model.response.UserReview;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.jsibbold.zoomage.ZoomageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    RecyclerView relatedProductRecycler, reviewsRecycler;
    CardView cardBack, offCard, shareLayout;
    ViewPager productImageViewPager;
    MaterialButton btnSendReview;
    CircleIndicator productImageIndicator;
    Timer timer;
    Handler handler;
    ProgressBar progress;
    RelativeLayout parentLayout, myCartLayout;
    LinearLayout addToCartLayout, buyNowLayout, reviewLayout, relatedProductLayout;
    List<Image> imageList = new ArrayList<>();
    List<UserReview> reviews = new ArrayList<>();
    TextView productName, productWeight, txtDiscountAmount, txtAmount, productDescription, txtOff, amountMain, cartCount, txtRating, txtReview, freeDelivery, txtDeliveryDay;
    RatingBar ratingBar;
    ProductDetails productDetails;
    String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cardBack = findViewById(R.id.cardBack);
        progress = findViewById(R.id.progress);
        parentLayout = findViewById(R.id.parentLayout);
        productName = findViewById(R.id.productName);
        productWeight = findViewById(R.id.productWeight);
        txtDiscountAmount = findViewById(R.id.txtDiscountAmount);
        txtAmount = findViewById(R.id.txtAmount);
        productDescription = findViewById(R.id.productDescription);
        offCard = findViewById(R.id.offCard);
        txtOff = findViewById(R.id.txtOff);
        amountMain = findViewById(R.id.amountMain);
        addToCartLayout = findViewById(R.id.addToCartLayout);
        relatedProductRecycler = findViewById(R.id.relatedProductRecycler);
        myCartLayout = findViewById(R.id.myCartLayout);
        cartCount = findViewById(R.id.cartCount);
        buyNowLayout = findViewById(R.id.buyNowLayout);
        reviewsRecycler = findViewById(R.id.reviewsRecycler);
        reviewLayout = findViewById(R.id.reviewLayout);
        btnSendReview = findViewById(R.id.btnSendReview);
        txtRating = findViewById(R.id.txtRating);
        txtReview = findViewById(R.id.txtReview);
        ratingBar = findViewById(R.id.RatingBar);
        relatedProductLayout = findViewById(R.id.relatedProductLayout);
        shareLayout = findViewById(R.id.shareLayout);
        freeDelivery = findViewById(R.id.freeDelivery);
        txtDeliveryDay = findViewById(R.id.txtDeliveryDay);

        relatedProductRecycler.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
        txtDiscountAmount.setPaintFlags(txtDiscountAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        offCard.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        reviewLayout.setVisibility(View.GONE);
        reviewsRecycler.setVisibility(View.GONE);
        relatedProductLayout.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product_id = bundle.getString("product_id");
            Log.e("PRODUCT ID", product_id);
        }

        if (InternetValidation.validation(ProductDetailsActivity.this)) {
            getProductDetails(product_id);
        } else {
            InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
        }

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailsActivity.super.onBackPressed();
            }
        });

        addToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productDetails != null) {
                    MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                    if (InternetValidation.validation(ProductDetailsActivity.this)) {
                        addToCart(preferences.getUserToken()
                                ,preferences.getUserDeviceId()
                                , productDetails.getAttrValuefinal()
                                , productDetails.getAttrValueNamefinal()
                                , productDetails.getAttrIdfinal()
                                , productDetails.getShopId()
                                , productDetails.getId());
                    } else {
                        InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                    }
                }
            }
        });

        myCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });

        buyNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, DirectBuyActivity.class);
                intent.putExtra("product_name", productDetails.getProductName());
                intent.putExtra("product_image", productDetails.getPath());
                intent.putExtra("product_discount", productDetails.getAttrDiscount());
                intent.putExtra("attr_valuefinal", productDetails.getAttrValuefinal());
                intent.putExtra("quantity", productDetails.getAttrValueNamefinal());
                intent.putExtra("attr_idfinal", productDetails.getAttrIdfinal());
                intent.putExtra("shop_id", productDetails.getShopId());
                intent.putExtra("shop_name", productDetails.getShopName());
                intent.putExtra("product_id", productDetails.getId());
                intent.putExtra("mrp", productDetails.getMrp());
                intent.putExtra("discount_amount", productDetails.getOnlinePrice());
                intent.putExtra("delivery_charge", productDetails.getDeliveryCharge());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (InternetValidation.validation(ProductDetailsActivity.this)) {
                    startActivity(intent);
                } else {
                    InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                }
            }
        });

        btnSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetValidation.validation(ProductDetailsActivity.this)) {
                    sendReviewDialog();
                } else {
                    InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                }
            }
        });



    }

    Dialog reviewDialog;
    RatingBar userRatingBar;
    EditText edtComment;
    LinearLayout notNowLayout, submitReviewLayout;
    TextView reviewUser, txtTime;
    RelativeLayout reviewParentLayout;
    ProgressBar reviewProgress;
    boolean isreviewFieldChecked = false;

    public void sendReviewDialog() {
        reviewDialog = new Dialog(ProductDetailsActivity.this);
        reviewDialog.setContentView(R.layout.custom_write_review_layout);
        reviewDialog.setCancelable(true);

        userRatingBar = reviewDialog.findViewById(R.id.RatingBar);
        edtComment = reviewDialog.findViewById(R.id.edtComment);
        notNowLayout = reviewDialog.findViewById(R.id.notNowLayout);
        submitReviewLayout = reviewDialog.findViewById(R.id.submitReviewLayout);
        reviewUser = reviewDialog.findViewById(R.id.reviewUser);
        txtTime = reviewDialog.findViewById(R.id.txtTime);
        reviewParentLayout = reviewDialog.findViewById(R.id.parentLayout);
        reviewProgress = reviewDialog.findViewById(R.id.reviewProgress);

        reviewProgress.setVisibility(View.GONE);
        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String[] timeArr = Constant.getFormattedTime(dateFormat.format(cal.getTime()));
        System.out.println(dateFormat.format(cal.getTime()));
        txtTime.setText(timeArr[0] + ", " + timeArr[1]);
        reviewUser.setText(preferences.getUserName());

        notNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewDialog.dismiss();
            }
        });

        submitReviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isreviewFieldChecked = checkReviewField();
                if (isreviewFieldChecked) {
                    Log.e("RATING", userRatingBar.getRating() + "");
                    Log.e("COMMENT", edtComment.getText().toString().trim());
                    Log.e("TOKEN", preferences.getUserToken());
                    Log.e("PRODUCT ID", productDetails.getId());
                    if (InternetValidation.validation(ProductDetailsActivity.this)) {
                        sendReview(preferences.getUserToken()
                                , productDetails.getId()
                                , edtComment.getText().toString().trim()
                                , userRatingBar.getRating() + "");
                    } else {
                        InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                    }
                }
            }
        });

        reviewDialog.show();
        reviewDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        reviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        reviewDialog.getWindow().setGravity(Gravity.CENTER);

    }

    public boolean checkReviewField() {

        if (userRatingBar.getRating() == 0.0f) {
            Snackbar.make(reviewParentLayout, "Please add your rating...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtComment.length() == 0) {
            Snackbar.make(reviewParentLayout, "Please write your review...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }

        return true;

    }

    public void getSlider(String product_id) {
        progress.setVisibility(View.VISIBLE);
        productImageViewPager = findViewById(R.id.productImageViewPager);
        productImageIndicator = findViewById(R.id.productImageIndicator);

        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<ImageRequest> call = api.getProductImage(product_id);
        call.enqueue(new Callback<ImageRequest>() {
            @Override
            public void onResponse(Call<ImageRequest> call, Response<ImageRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        imageList = response.body().getProductimgsResp();
                        ProductImageAdapter adapter = new ProductImageAdapter(response.body().getProductimgsResp(), ProductDetailsActivity.this);
                        productImageViewPager.setAdapter(adapter);
                        productImageIndicator.setViewPager(productImageViewPager);

                        handler = new Handler();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int i = productImageViewPager.getCurrentItem();
                                        if (i == imageList.size() - 1) {
                                            i = 0;
                                        } else {
                                            i++;
                                        }
                                        productImageViewPager.setCurrentItem(i, true);
                                    }
                                });
                            }
                        }, 4000, 4000);
                    } else {
                        try {
                            imageList = response.body().getProductimgsResp();
                            ProductImageAdapter adapter = new ProductImageAdapter(response.body().getProductimgsResp(), ProductDetailsActivity.this);
                            productImageViewPager.setAdapter(adapter);
                            productImageIndicator.setViewPager(productImageViewPager);
                        } catch (Exception e) {
                            Log.e("EXCEPTION", e.getLocalizedMessage());
                        }
                        handler = new Handler();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int i = productImageViewPager.getCurrentItem();
                                        if (i == imageList.size() - 1) {
                                            i = 0;
                                        } else {
                                            i++;
                                        }
                                        productImageViewPager.setCurrentItem(i, true);
                                    }
                                });
                            }
                        }, 6000, 4000);
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                getCartList(MyPreferences.getInstance(getApplicationContext()).getUserToken(), product_id);
            }

            @Override
            public void onFailure(Call<ImageRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
                getCartList(MyPreferences.getInstance(getApplicationContext()).getUserToken(), product_id);
            }
        });
    }

    public void getProductDetails(String product_id) {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<ProductDetailsRequest> call = api.getProductDetails(product_id);
        call.enqueue(new Callback<ProductDetailsRequest>() {
            @Override
            public void onResponse(Call<ProductDetailsRequest> call, Response<ProductDetailsRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        productDetails = response.body().getProductdetailResp();
                        productName.setText(productDetails.getProductName());
                        txtDiscountAmount.setText("₹" + productDetails.getMrp());
                        txtAmount.setText("Offer Price: ₹" + productDetails.getOnlinePrice());
                        amountMain.setText("₹" + productDetails.getOnlinePrice());
                        freeDelivery.setText("@ ₹" + productDetails.getDeliveryfreeon());
                        txtDeliveryDay.setText(productDetails.getDeliverywithin());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            productDescription.setText(fromHtml(productDetails.getDesp(), HtmlCompat.FROM_HTML_MODE_COMPACT));
                        }
                        productWeight.setText(productDetails.getAttrValueNamefinal());
                        if (!productDetails.getAttrDiscount().equals("")) {
                            offCard.setVisibility(View.VISIBLE);
                            txtOff.setText((int) Double.parseDouble(productDetails.getAttrDiscount()) + "% OFF");
                        }
                    } else {
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                getRelatedProduct(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                        , response.body().getProductdetailResp().getSubcatId()
                        , response.body().getProductdetailResp().getLowcatId()
                        , product_id);
            }

            @Override
            public void onFailure(Call<ProductDetailsRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    ZoomageView image;
    CardView back;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @SuppressLint("ClickableViewAccessibility")
    public void imageDialog(String imageProduct) {
        Dialog dialog = new Dialog(ProductDetailsActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_image_zoom_dialog);

        image = dialog.findViewById(R.id.image);
        back = dialog.findViewById(R.id.back);

        Glide.with(getApplicationContext())
                .load(Constant.IMAGE_URL + imageProduct)
                .into(image);
        scaleGestureDetector = new ScaleGestureDetector(ProductDetailsActivity.this, new ScaleListener());
        image.setOnTouchListener((view, motionEvent) -> {
            scaleGestureDetector.onTouchEvent(motionEvent);
            return true;
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            image.setScaleX(mScaleFactor);
            image.setScaleY(mScaleFactor);
            return true;
        }
    }

    public void getRelatedProduct(String token, String cat_id, String subcat_id, String prod_id) {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<RelatedProductRequest> call = api.getRelatedProduct(token, cat_id, subcat_id, prod_id);
        call.enqueue(new Callback<RelatedProductRequest>() {
            @Override
            public void onResponse(Call<RelatedProductRequest> call, Response<RelatedProductRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        relatedProductLayout.setVisibility(View.VISIBLE);
                        RelatedProductListAdapter relatedProductListAdapter = new RelatedProductListAdapter(getApplicationContext()
                                , ProductDetailsActivity.this
                                , response.body().getProductsResp());
                        relatedProductRecycler.setAdapter(relatedProductListAdapter);
                    } else {
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
                getSlider(prod_id);
            }

            @Override
            public void onFailure(Call<RelatedProductRequest> call, Throwable t) {
                getSlider(prod_id);
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void addToCart(String token,String deviceid, String attrValFinal, String attrValNameFinal, String attrIdFinal, String shopId, String productId) {
        if(token == null){
            Intent intent = new Intent(ProductDetailsActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<AddToCart> call = api.addToCart(token,deviceid, attrValFinal, attrValNameFinal, attrIdFinal, shopId, productId);
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

                            if (InternetValidation.validation(ProductDetailsActivity.this)) {
                                getCartList(MyPreferences.getInstance(getApplicationContext()).getUserToken(), productId);
                            } else {
                                InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                            }
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

    public void getCartList(String token, String product_id) {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CartRequest> call = api.getCartList(token);
        call.enqueue(new Callback<CartRequest>() {
            @Override
            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        cartCount.setText(String.valueOf(response.body().getCartlistResp().size()));
                    } else {
                        cartCount.setText("0");
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }

                if (InternetValidation.validation(ProductDetailsActivity.this)) {
                    getUserReviews(product_id);
                } else {
                    InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                }
            }

            @Override
            public void onFailure(Call<CartRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);

                if (InternetValidation.validation(ProductDetailsActivity.this)) {
                    getUserReviews(product_id);
                } else {
                    InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                }
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void getUserReviews(String product_id) {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<UserReviewRequest> call = api.getUserReviews(product_id);
        call.enqueue(new Callback<UserReviewRequest>() {
            @Override
            public void onResponse(Call<UserReviewRequest> call, Response<UserReviewRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        reviewLayout.setVisibility(View.VISIBLE);
                        reviewsRecycler.setVisibility(View.VISIBLE);
                        reviews.clear();
                        reviews = response.body().getReview();
                        txtReview.setText(reviews.size() + " reviews");
                        int totalRating = 0;
                        for (UserReview review : reviews) {
                            totalRating += Integer.parseInt(review.getRate());
                        }
                        txtRating.setText(Float.parseFloat(String.valueOf(totalRating / reviews.size())) + "");
                        ratingBar.setRating(Float.parseFloat(String.valueOf(totalRating / reviews.size())));
                        UserReviewAdapter userReviewAdapter = new UserReviewAdapter(getApplicationContext()
                                , ProductDetailsActivity.this
                                , response.body().getReview());
                        reviewsRecycler.setAdapter(userReviewAdapter);
                    } else {
                        txtRating.setText("0.0");
                        ratingBar.setRating(0.0f);
                        txtReview.setText("0 reviews");
                        reviewLayout.setVisibility(View.VISIBLE);
                        reviewsRecycler.setVisibility(View.GONE);
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
            public void onFailure(Call<UserReviewRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void sendReview(String token, String product_id, String comment, String rate) {
        reviewProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<SendReview> call = api.sendUserReview(token, product_id, comment, rate);
        call.enqueue(new Callback<SendReview>() {
            @Override
            public void onResponse(Call<SendReview> call, Response<SendReview> response) {
                reviewProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                        reviewDialog.dismiss();

                        if (InternetValidation.validation(ProductDetailsActivity.this)) {
                            getUserReviews(product_id);
                        } else {
                            InternetValidation.getNoConnectionDialog(ProductDetailsActivity.this);
                        }
                    } else {
                        reviewDialog.dismiss();
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
            public void onFailure(Call<SendReview> call, Throwable t) {
                reviewProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}