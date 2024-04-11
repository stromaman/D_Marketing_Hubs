package com.ecom.ganpati_agency.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.VerifyOtpRequest;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout loginOtpLayout;
    MaterialButton btnLogin;
    String DEVICE_ID, TOKEN;
    RelativeLayout parentLayout;
    TextView txtSignUp;
    EditText edtNumber, edtPassword;
    ProgressBar loginProgress;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        loginOtpLayout = findViewById(R.id.loginOtpLayout);
        txtSignUp = findViewById(R.id.txtSignUp);
        edtNumber = findViewById(R.id.edtNumber);
        edtPassword = findViewById(R.id.edtPassword);
        parentLayout = findViewById(R.id.parentLayout);
        btnLogin = findViewById(R.id.btnLogin);
        loginProgress = findViewById(R.id.loginProgress);

        loginProgress.setVisibility(View.GONE);
        DEVICE_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

//        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
//        preferences.saveShopDetails("1", "Admin");

        // Generating Firebase token
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.e("Exception", "Fetching FCM registration token failed " + task.getException());
//                        } else {
//                            TOKEN = task.getResult();
//                            Log.e("FIREBASE TOKEN", TOKEN);
//                            Log.e("DEVICE ID", DEVICE_ID);
//                            if (InternetValidation.validation(getApplicationContext())) {
//                                MyPreferences.getInstance(LoginActivity.this).saveCredentials(TOKEN, DEVICE_ID);
//                                Log.e("FIREBASE TOKEN PREFERENCES", MyPreferences.getInstance(LoginActivity.this).getUserFirebaseToken());
//                                Log.e("DEVICE ID PREFERENCES", MyPreferences.getInstance(LoginActivity.this).getUserDeviceId());
//                            } else {
//                                Toasty.error(LoginActivity.this, "No Internet Connection...!!\nPlease Check Your Internet", Toasty.LENGTH_SHORT, false).show();
//                            }
//                        }
//                    }
//                });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        loginOtpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, NumberLoginActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    Log.e("USERNAME", edtNumber.getText().toString().trim());
                    Log.e("Password", edtPassword.getText().toString().trim());
                    doLogin(edtNumber.getText().toString().trim(),
                            edtPassword.getText().toString().trim());
                }
            }
        });

    }

    public boolean checkAllFields() {
        if (edtNumber.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Number", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtNumber.length() < 10) {
            Snackbar.make(parentLayout, "Please Enter valid Number", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPassword.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Password", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        return true;
    }

    public void doLogin(String number, String password) {
        loginProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<VerifyOtpRequest> call = api.loginUser(number, password);
        call.enqueue(new Callback<VerifyOtpRequest>() {
            @Override
            public void onResponse(Call<VerifyOtpRequest> call, Response<VerifyOtpRequest> response) {
                loginProgress.setVisibility(View.GONE);
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
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
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
                loginProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}