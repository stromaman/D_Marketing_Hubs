package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Adapter.OrderedProductDetailAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.OrderedAllProductRequest;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTrackingActivity extends AppCompatActivity {
    ProgressBar progress;
    RelativeLayout parentLayout;
    RecyclerView orderedProductRecycler;
    ProgressBar deliveryProgress;
    TextView orderDate, orderId, txtAddress, txtShopName, txtOrderStat1, txtOrderStat2, orderStatusComment;
    CardView cardBack;
    String order_id, order_date, order_no, address, shop_name, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        progress = findViewById(R.id.progress);
        parentLayout = findViewById(R.id.parentLayout);
        orderedProductRecycler = findViewById(R.id.orderedProductRecycler);
        orderDate = findViewById(R.id.orderDate);
        orderId = findViewById(R.id.orderId);
        cardBack = findViewById(R.id.cardBack);
        txtAddress = findViewById(R.id.txtAddress);
        txtShopName = findViewById(R.id.txtShopName);
        deliveryProgress = findViewById(R.id.deliveryProgress);
        txtOrderStat1 = findViewById(R.id.txtOrderStat1);
        txtOrderStat2 = findViewById(R.id.txtOrderStat2);
        orderStatusComment = findViewById(R.id.orderStatusComment);

        orderedProductRecycler.setLayoutManager(new LinearLayoutManager(OrderTrackingActivity.this));
        progress.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            order_id = bundle.getString("order_id");
            order_date = bundle.getString("order_date");
            order_no = bundle.getString("order_no");
            address = bundle.getString("address");
            shop_name = bundle.getString("shop_name");
            status = bundle.getString("status");

            orderDate.setText(order_date);
            orderId.setText(order_no);
            txtAddress.setText(address);
            txtShopName.setText(shop_name);

            if(Integer.parseInt(status) == 1){
                txtOrderStat1.setText("order placed");
                txtOrderStat2.setText("order placed");
                orderStatusComment.setText("We have received your order.");
                deliveryProgress.setProgress(25);
            }else if(Integer.parseInt(status) == 2){
                txtOrderStat1.setText("order dispatched");
                txtOrderStat2.setText("order dispatched");
                orderStatusComment.setText("Your order has been dispatched.");
                deliveryProgress.setProgress(50);
            }else if(Integer.parseInt(status) == 3){
                txtOrderStat1.setText("out for delivery");
                txtOrderStat2.setText("out for delivery");
                orderStatusComment.setText("Order out for delivery.");
                deliveryProgress.setProgress(75);
            }else if(Integer.parseInt(status) == 4){
                txtOrderStat1.setText("order delivered");
                txtOrderStat2.setText("order delivered");
                orderStatusComment.setText("Delivered Successfully");
                deliveryProgress.setProgress(100);
            }

            if (InternetValidation.validation(OrderTrackingActivity.this)) {
                getOrderedProductDetails(MyPreferences.getInstance(getApplicationContext()).getUserToken(), order_id);
            } else {
                InternetValidation.getNoConnectionDialog(OrderTrackingActivity.this);
            }

        }

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderTrackingActivity.super.onBackPressed();
            }
        });
    }

    public void getOrderedProductDetails(String token, String order_id){
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<OrderedAllProductRequest> call = api.getOrderedProductDetails(token, order_id);
        call.enqueue(new Callback<OrderedAllProductRequest>() {
            @Override
            public void onResponse(Call<OrderedAllProductRequest> call, Response<OrderedAllProductRequest> response) {
                progress.setVisibility(View.GONE);
                if(response.body()!=null){
                    if(response.body().getStatus()){
                        OrderedProductDetailAdapter orderedProductDetailAdapter = new OrderedProductDetailAdapter(getApplicationContext()
                                ,OrderTrackingActivity.this
                                ,response.body().getOrderdetailResp());
                        orderedProductRecycler.setAdapter(orderedProductDetailAdapter);
                    }else{
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                    }
                }else{
                    Log.e("BODY","Body is null");
                }
            }

            @Override
            public void onFailure(Call<OrderedAllProductRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION",t.getLocalizedMessage());
            }
        });
    }
}