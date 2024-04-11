package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.response.OtpRequest;
import com.ecom.ganpati_agency.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NumberLoginActivity extends AppCompatActivity {

    CardView numberLoginBack;
    ProgressBar numberProgress;
    RelativeLayout parentLayout;
    TextView txtLogin;
    EditText edtNumber;
    MaterialButton btnGetOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_login);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        numberLoginBack = findViewById(R.id.numberLoginBack);
        numberProgress = findViewById(R.id.numberProgress);
        parentLayout = findViewById(R.id.parentLayout);
        txtLogin = findViewById(R.id.txtLogin);
        edtNumber = findViewById(R.id.edtNumber);
        btnGetOtp = findViewById(R.id.btnGetOtp);

        numberProgress.setVisibility(View.GONE);
        numberLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberLoginActivity.super.onBackPressed();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberLoginActivity.super.onBackPressed();
            }
        });

        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNumber.length()==0){
                    Snackbar.make(parentLayout, "Please Enter Number", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                }else if(edtNumber.length()<10){
                    Snackbar.make(parentLayout, "Please Enter Valid Number", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                }else{
                    Log.e("NUMBER",edtNumber.getText().toString());
                    getLoginOtp(edtNumber.getText().toString());
                }
            }
        });

    }

    public void getLoginOtp(String number) {
        numberProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<OtpRequest> call = api.getLoginOtp(number);
        call.enqueue(new Callback<OtpRequest>() {
            @Override
            public void onResponse(Call<OtpRequest> call, Response<OtpRequest> response) {
                numberProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Intent intent = new Intent(NumberLoginActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("type", "number");
                        intent.putExtra("number", number);
                        intent.putExtra("otp", response.body().getOtpRes());
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
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
            public void onFailure(Call<OtpRequest> call, Throwable t) {
                numberProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}