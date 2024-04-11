package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.VerifyOtpRequest;
import com.ecom.ganpati_agency.Model.response.SignUp;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity {


    String name, number, email, password, otp, type, address, state_id, district_id, pincode;
    TextView txtOtp, txtNumber, txtResend;
    PinView otpView;
    LinearLayout resendLayout;
    MaterialButton btnVerify;
    RelativeLayout parentLayout;
    ProgressBar verifyProgress;
    CardView verifyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        txtOtp = findViewById(R.id.txtOtp);
        resendLayout = findViewById(R.id.resendLayout);
        btnVerify = findViewById(R.id.btnVerify);
        otpView = findViewById(R.id.otpView);
        parentLayout = findViewById(R.id.parentLayout);
        verifyProgress = findViewById(R.id.verifyProgress);
        txtNumber = findViewById(R.id.txtNumber);
        txtResend = findViewById(R.id.txtResend);
        verifyBack = findViewById(R.id.verifyBack);

        resendLayout.setVisibility(View.GONE);
        verifyProgress.setVisibility(View.GONE);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getString("type");
            if (type.equals("signup")) {
                resendLayout.setVisibility(View.VISIBLE);
                name = extra.getString("name");
                number = extra.getString("number");
                otp = extra.getString("otp");
                email = extra.getString("email");
                password = extra.getString("password");
                address = extra.getString("address");
                state_id = extra.getString("state_id");
                district_id = extra.getString("district_id");
                pincode = extra.getString("pincode");
                Log.e("NAME", name);
                Log.e("NUMBER", number);
                Log.e("EMAIL", email);
                Log.e("PASSWORD", password);
                Log.e("OTP", otp);
                Log.e("ADDRESS", address);
                Log.e("STATE ID", state_id);
                Log.e("DISTRICT ID", district_id);
                Log.e("PINCODE", pincode);
            } else {
                number = extra.getString("number");
                otp = extra.getString("otp");
                Log.e("NUMBER", number);
                Log.e("OTP", otp);
            }
            txtOtp.setText(otp);
            txtNumber.setText("+91 " + number);
        }

        verifyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyOtpActivity.super.onBackPressed();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpView.length() == 0) {
                    Snackbar.make(parentLayout, "Please Enter OTP", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                } else if (otpView.length() < 6) {
                    Snackbar.make(parentLayout, "Please Enter Valid OTP", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                } else {
                    verifyOtp(number, otpView.getText().toString().trim());
                }
            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUserRegistration(number, name, email, password,state_id, district_id, address, pincode);
            }
        });
    }

    public void verifyOtp(String number, String otp) {
        verifyProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<VerifyOtpRequest> call = api.doVerifyOtp(number, otp);
        call.enqueue(new Callback<VerifyOtpRequest>() {
            @Override
            public void onResponse(Call<VerifyOtpRequest> call, Response<VerifyOtpRequest> response) {
                verifyProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        MyPreferences.getInstance(getApplicationContext())
                                .saveLoginCredentials(response.body().getLoginRes().getName()
                                        , response.body().getLoginRes().getEmail()
                                        , response.body().getLoginRes().getImagePath()
                                        , response.body().getLoginRes().getMobileNo()
                                        , response.body().getLoginRes().getToken()
                                        , response.body().getLoginRes().getAddress()
                                        , response.body().getLoginRes().getStateId()
                                        , response.body().getLoginRes().getStateName()
                                        , response.body().getLoginRes().getDistrictId()
                                        , response.body().getLoginRes().getDistrictName()
                                        , response.body().getLoginRes().getPincode());

                        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                        Log.e("NAME", preferences.getUserName());
                        Log.e("EMAIL", preferences.getUserEmail());
                        Log.e("IMAGE", preferences.getUserImage());
                        Log.e("NUMBER", preferences.getUserNumber());
                        Log.e("TOKEN", preferences.getUserToken());
                        Log.e("ADDRESS", preferences.getAddress());
                        Log.e("STATE ID", preferences.getStateId());
                        Log.e("STATE NAME", preferences.getStateName());
                        Log.e("DISTRICT ID ", preferences.getDistrictId());
                        Log.e("DISTRICT NAME ", preferences.getDistrictName());
                        Log.e("PINCODE", preferences.getPincode());
                        startActivity(new Intent(VerifyOtpActivity.this, DashboardActivity.class));
                        finishAffinity();
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
            public void onFailure(Call<VerifyOtpRequest> call, Throwable t) {
                verifyProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void doUserRegistration(String number, String name, String email, String password, String state_id, String district_id, String address, String pincode) {
        verifyProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<SignUp> call = api.registerUser(number, name, email, password, address, state_id, district_id, pincode);
        call.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                verifyProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        txtOtp.setText(response.body().getOtpRes());
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
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
            public void onFailure(Call<SignUp> call, Throwable t) {
                verifyProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}