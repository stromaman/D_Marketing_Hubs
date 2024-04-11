package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Adapter.DistrictListAdapter;
import com.ecom.ganpati_agency.Adapter.StateListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.DistrictRequest;
import com.ecom.ganpati_agency.Model.request.StateRequest;
import com.ecom.ganpati_agency.Model.response.District;
import com.ecom.ganpati_agency.Model.response.SignUp;
import com.ecom.ganpati_agency.Model.response.State;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    public TextView txtLogin, txtState, txtDistrict;
    CardView signUpBack;
    EditText edtNumber, edtName, edtEmailAddress, edtPassword, edtPincode, edtAddress;
    MaterialButton btnSignUp;
    RelativeLayout parentLayout;
    boolean isAllFieldsChecked = false;
    ProgressBar signUpProgress;
    public String state_name, state_id, district_name, district_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        signUpBack= findViewById(R.id.signUpBack);
        txtLogin = findViewById(R.id.txtLogin);
        edtNumber = findViewById(R.id.edtNumber);
        edtName = findViewById(R.id.edtName);
        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        parentLayout = findViewById(R.id.parentLayout);
        signUpProgress = findViewById(R.id.signUpProgress);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        edtPincode = findViewById(R.id.edtPincode);
        edtAddress = findViewById(R.id.edtAddress);

        signUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.super.onBackPressed();
            }
        });

        signUpProgress.setVisibility(View.GONE);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.super.onBackPressed();
            }
        });

        txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectStatePopup();
            }
        });

        txtDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state_id != null) {
                    selectDistrictPopup(state_id);
                } else {
                    Snackbar.make(parentLayout, "Please Select State...!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                    Log.e("NUMBER", edtNumber.getText().toString().trim());
                    Log.e("NAME", edtName.getText().toString().trim());
                    Log.e("EMAIL", edtEmailAddress.getText().toString().trim());
                    Log.e("PASSWORD", edtPassword.getText().toString().trim());
                 //   Log.e("DEVICE ID", preferences.getUserDeviceId());
                    Log.e("STATE ID", state_id);
                    Log.e("DISTRICT ID", district_id);
                    Log.e("ADDRESS", edtAddress.getText().toString());
                    Log.e("PINCODE", edtPincode.getText().toString());

                    doUserRegistration(edtNumber.getText().toString()
                            , edtName.getText().toString().trim()
                            , edtEmailAddress.getText().toString().trim()
                            , edtPassword.getText().toString().trim()
                            , state_id
                            , district_id
                            , edtAddress.getText().toString().trim()
                            , edtPincode.getText().toString());

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
            Snackbar.make(parentLayout, "Please Enter Correct Number", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtName.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Name", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtEmailAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Email Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (!edtEmailAddress.getText().toString().trim().contains("@")) {
            Snackbar.make(parentLayout, "Please Enter Valid Email Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (state_id == null) {
            Snackbar.make(parentLayout, "Please Select Your State", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (district_id == null) {
            Snackbar.make(parentLayout, "Please Select Your District", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPincode.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Area Pincode", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtPincode.length() < 6) {
            Snackbar.make(parentLayout, "Please Enter Valid Area Pincode", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPassword.length() == 0) {
            Snackbar.make(parentLayout, "Please Create Your Password", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        return true;
    }

    public BottomSheetDialog selectStateDialog;
    TextView stateHeading;
    ProgressBar stateProgress;
    RecyclerView stateRecycler;
    StateListAdapter stateListAdapter;
    EditText searchState;
    List<State> stateList = new ArrayList<>();

    public void selectStatePopup() {
        selectStateDialog = new BottomSheetDialog(SignUpActivity.this);
        selectStateDialog.setContentView(R.layout.custom_select_area_layout);
        selectStateDialog.getWindow().findViewById(R.id.contantLayout).setBackgroundColor(Color.TRANSPARENT);
        selectStateDialog.getWindow().setTransitionBackgroundFadeDuration(10000);

        stateHeading = selectStateDialog.findViewById(R.id.txtAreaHeading);
        stateProgress = selectStateDialog.findViewById(R.id.areaProgress);
        stateRecycler = selectStateDialog.findViewById(R.id.areaRecycler);
        searchState = selectStateDialog.findViewById(R.id.edtSearchArea);

        stateRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        stateProgress.setVisibility(View.GONE);
        stateHeading.setText("Select Your State");
        getStateList();

        searchState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stateFilter(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        selectStateDialog.show();
        selectStateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void stateFilter(String statename) {
        List<State> newStateList = new ArrayList<>();
        for (State state : stateList) {
            if (state.getName().toLowerCase().trim().contains(statename.toLowerCase().trim())) {
                newStateList.add(state);
            }
        }
        stateListAdapter.filterList(newStateList);
    }

    public void getStateList() {
        stateProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<StateRequest> call = api.getStateList();
        call.enqueue(new Callback<StateRequest>() {
            @Override
            public void onResponse(Call<StateRequest> call, Response<StateRequest> response) {
                stateProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        stateList = response.body().getStates();
                        stateListAdapter = new StateListAdapter(getApplicationContext()
                                , null
                                , null
                                , null
                                ,SignUpActivity.this
                                , response.body().getStates());
                        stateRecycler.setAdapter(stateListAdapter);
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
            public void onFailure(Call<StateRequest> call, Throwable t) {
                stateProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public void doUserRegistration(String number, String name, String email, String password, String state_id, String district_id, String address, String pincode) {
        signUpProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<SignUp> call = api.registerUser(number, name, email, password, address, state_id, district_id, pincode);
        call.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                signUpProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Intent intent = new Intent(SignUpActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("type", "signup");
                        intent.putExtra("name", name);
                        intent.putExtra("number", number);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        intent.putExtra("otp", response.body().getOtpRes());
                        intent.putExtra("address", address);
                        intent.putExtra("state_id", state_id);
                        intent.putExtra("district_id", district_id);
                        intent.putExtra("pincode", pincode);
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#12d06b")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 1000);
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
                signUpProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public BottomSheetDialog selectDistrictDialog;
    TextView districtHeading;
    ProgressBar districtProgress;
    RecyclerView districtRecycler;
    EditText searchDistrict;
    DistrictListAdapter districtListAdapter;
    List<District> districtList = new ArrayList<>();

    public void selectDistrictPopup(String stateId) {
        selectDistrictDialog = new BottomSheetDialog(SignUpActivity.this);
        selectDistrictDialog.setContentView(R.layout.custom_select_area_layout);
        selectDistrictDialog.getWindow().findViewById(R.id.contantLayout).setBackgroundColor(Color.TRANSPARENT);
        selectDistrictDialog.getWindow().setTransitionBackgroundFadeDuration(10000);

        districtHeading = selectDistrictDialog.findViewById(R.id.txtAreaHeading);
        districtProgress = selectDistrictDialog.findViewById(R.id.areaProgress);
        districtRecycler = selectDistrictDialog.findViewById(R.id.areaRecycler);
        searchDistrict = selectDistrictDialog.findViewById(R.id.edtSearchArea);

        districtRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        districtProgress.setVisibility(View.GONE);
        districtHeading.setText("Select Your District");
        getDistrictList(stateId);

        searchDistrict.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                districtFilter(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        selectDistrictDialog.show();
        selectDistrictDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void districtFilter(String districtname) {
        List<District> newDistrictList = new ArrayList<>();
        for (District district : districtList) {
            if (district.getName().toLowerCase().trim().contains(districtname.toLowerCase().trim())) {
                newDistrictList.add(district);
            }
        }
        districtListAdapter.filterList(newDistrictList);
    }

    public void getDistrictList(String stateId) {
        districtProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<DistrictRequest> call = api.getDistrictList(stateId);
        call.enqueue(new Callback<DistrictRequest>() {
            @Override
            public void onResponse(Call<DistrictRequest> call, Response<DistrictRequest> response) {
                districtProgress.setVisibility(View.GONE);
                Log.d("99999",response.body().getDistricts().toString());
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        districtList = response.body().getDistricts();
                        districtListAdapter = new DistrictListAdapter(getApplicationContext()
                                , null
                                , null
                                , null
                                , SignUpActivity.this
                                , response.body().getDistricts());
                        districtRecycler.setAdapter(districtListAdapter);
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
            public void onFailure(Call<DistrictRequest> call, Throwable t) {
                districtProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}