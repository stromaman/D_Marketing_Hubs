package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
//import com.google.android.gms.tasks.Task;
//import com.google.android.play.core.appupdate.AppUpdateInfo;
//import com.google.android.play.core.appupdate.AppUpdateManager;
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.core.install.model.AppUpdateType;
//import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private Uri uri;

   // private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


//        appUpdateManager = AppUpdateManagerFactory.create(SplashActivity.this);
//        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
//                try {
//                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, SplashActivity.this, 1);
//                } catch (IntentSender.SendIntentException e) {
//                    e.printStackTrace(); //'.google.android.play:core:1.10.3'
//                }
//            }
//        });


        if (InternetValidation.validation(SplashActivity.this)) {
            MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
            uri = getIntent().getData();

            if (uri != null) {
                List<String> parameters = uri.getPathSegments();
                String param = parameters.get(parameters.size() - 1);
                Log.e("ID", param);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (preferences.getUserToken() != null) {
                            Toast.makeText(SplashActivity.this, "Loading Product " + param, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                            intent.putExtra("product_id", param);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(SplashActivity.this, "Please Login...!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }
                }, 2000);

            } else {
               
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                        if (preferences.getUserToken() != null) {
                            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }
                }, 2000);
            }

        } else {
            InternetValidation.getNoConnectionDialog(SplashActivity.this);
        }
    }


}