package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ecom.ganpati_agency.Adapter.OrderedListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.OrderListModel;
import com.ecom.ganpati_agency.Model.request.OrderedproductRequest;
import com.ecom.ganpati_agency.Model.response.CancelOrder;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    CardView cardBack;
    LinearLayout startShoppingLayout, noOrderLayout, mainLayout;
    ProgressBar progress;
    RelativeLayout parentLayout;
    RecyclerView orderHistoryRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cardBack = findViewById(R.id.cardBack);
        startShoppingLayout = findViewById(R.id.startShoppingLayout);
        progress = findViewById(R.id.progress);
        parentLayout = findViewById(R.id.parentLayout);
        orderHistoryRecycler = findViewById(R.id.orderHistoryRecycler);
        noOrderLayout = findViewById(R.id.noOrderLayout);
        mainLayout = findViewById(R.id.mainLayout);

        orderHistoryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progress.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
        noOrderLayout.setVisibility(View.GONE);
        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderHistoryActivity.super.onBackPressed();
            }
        });

        startShoppingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderHistoryActivity.this, DashboardActivity.class));
                finishAffinity();
            }
        });

        if (InternetValidation.validation(OrderHistoryActivity.this)) {
            getOrderedList(MyPreferences.getInstance(getApplicationContext()).getUserToken());
        } else {
            InternetValidation.getNoConnectionDialog(OrderHistoryActivity.this);
        }

    }

    public void getOrderedList(String token) {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<OrderedproductRequest> call = api.getOrderedProductList(token);
        call.enqueue(new Callback<OrderedproductRequest>() {
            @Override
            public void onResponse(Call<OrderedproductRequest> call, Response<OrderedproductRequest> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        mainLayout.setVisibility(View.VISIBLE);
                        noOrderLayout.setVisibility(View.GONE);
                        OrderedListAdapter orderedListAdapter = new OrderedListAdapter(getApplicationContext()
                                , OrderHistoryActivity.this, response.body().getOrderlistResp());
                        orderHistoryRecycler.setAdapter(orderedListAdapter);
                    } else {
                        mainLayout.setVisibility(View.GONE);
                        noOrderLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<OrderedproductRequest> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void showActions(View view, String order_id) {
        PopupMenu popupMenu = new PopupMenu(OrderHistoryActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.cancel_order_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                switch (menuItem.getItemId()) {
                    case R.id.cancel:
                        cancelOrder(preferences.getUserToken(), order_id);
                        return true;
                    default:
                        Toast.makeText(OrderHistoryActivity.this, "Nothing...!!", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        popupMenu.show();
    }

    public void cancelOrder(String token, String order_id) {
        progress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CancelOrder> call = api.cancelOrder(token, order_id);
        call.enqueue(new Callback<CancelOrder>() {
            @Override
            public void onResponse(Call<CancelOrder> call, Response<CancelOrder> response) {
                progress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b"))
                                .setTextColor(Color.WHITE)
                                .show();
                        getOrderedList(token);
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
            public void onFailure(Call<CancelOrder> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}