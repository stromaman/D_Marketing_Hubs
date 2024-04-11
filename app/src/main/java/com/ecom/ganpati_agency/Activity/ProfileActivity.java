package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.MyPreferences;

public class ProfileActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtNumber, edtAddress;
    ImageView imgUser;
    LinearLayout editProfileLayout;
    CardView cardBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtNumber = findViewById(R.id.edtNumber);
        edtAddress = findViewById(R.id.edtAddress);
        imgUser = findViewById(R.id.imgUser);
        editProfileLayout = findViewById(R.id.editProfileLayout);
        cardBack = findViewById(R.id.cardBack);



        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
        Log.e("NAME",preferences.getUserName());
        Log.e("NUMBER",preferences.getUserNumber());
        Log.e("EMAIL",preferences.getUserEmail());
        Log.e("ADDRESS",preferences.getAddress());
        Log.e("IMAGE URL",preferences.getUserImage());
        edtName.setText(preferences.getUserName());
        edtEmail.setText(preferences.getUserEmail());
        edtNumber.setText("+91 "+preferences.getUserNumber());
        edtAddress.setText(preferences.getAddress());
        if(!preferences.getUserImage().equals("")){
            Glide.with(getApplicationContext())
                    .load(Constant.IMAGE_URL+preferences.getUserImage())
                    .into(imgUser);
        }else{
            Glide.with(getApplicationContext())
                    .load(R.drawable.noprofile)
                    .into(imgUser);
        }

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.super.onBackPressed();
            }
        });

        editProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
            }
        });

    }
}