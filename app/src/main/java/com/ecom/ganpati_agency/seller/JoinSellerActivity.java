package com.ecom.ganpati_agency.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecom.ganpati_agency.Activity.DirectBuyActivity;
import com.ecom.ganpati_agency.Adapter.DistrictListAdapter;
import com.ecom.ganpati_agency.Adapter.StateListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.DistrictRequest;
import com.ecom.ganpati_agency.Model.request.Final;
import com.ecom.ganpati_agency.Model.request.RegisterShopRequest;
import com.ecom.ganpati_agency.Model.request.StateRequest;
import com.ecom.ganpati_agency.Model.response.District;
import com.ecom.ganpati_agency.Model.response.SaveShopAddressDetails;
import com.ecom.ganpati_agency.Model.response.State;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinSellerActivity extends AppCompatActivity {


    LinearLayout contactDetailsLayout, contactDataLayout, documentLayout, documentDataLayout, aadhaarLayout, panLayout, bankLayout,
            fssaiLayout, gstLayout, addressDetailsLayout, addressDataLayout;
    RelativeLayout parentLayout;
    ImageView contactArrow, documentArrow, addressArrow;
    EditText edtName, edtShopName, edtEmail, edtNumber, edtPassword;
    MaterialButton btnSubmit, btnSubmitAddress,btnSubmitFinal;
    ProgressBar sellerProgress;
    String regex = "^(.+)@(.+)$";
    Pattern pattern;
    Matcher matcher;

    public TextView txtState, txtDistrict;
    EditText edtPincode, edtShopAddress;
    public String state_id, district_id;
    boolean isAddressChecked = false;
    boolean isAllFieldChecked = false;
    boolean ischekedallField = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_seller);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        contactDetailsLayout = findViewById(R.id.contactDetailsLayout);
        documentLayout = findViewById(R.id.documentLayout);
        contactArrow = findViewById(R.id.contactArrow);
        documentArrow = findViewById(R.id.documentArrow);
        contactDataLayout = findViewById(R.id.contactDataLayout);
        documentDataLayout = findViewById(R.id.documentDataLayout);
        aadhaarLayout = findViewById(R.id.aadhaarLayout);
        panLayout = findViewById(R.id.panLayout);
        bankLayout = findViewById(R.id.bankLayout);
        fssaiLayout = findViewById(R.id.fssaiLayout);
        gstLayout = findViewById(R.id.gstLayout);
        edtName = findViewById(R.id.edtName);
        edtShopName = findViewById(R.id.edtShopName);
        edtEmail = findViewById(R.id.edtEmail);
        edtNumber = findViewById(R.id.edtNumber);
        edtPassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        parentLayout = findViewById(R.id.parentLayout);
        sellerProgress = findViewById(R.id.sellerProgress);
        addressDetailsLayout = findViewById(R.id.addressDetailsLayout);
        addressArrow = findViewById(R.id.addressArrow);
        addressDataLayout = findViewById(R.id.addressDataLayout);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        btnSubmitAddress = findViewById(R.id.btnSubmitAddress);
        btnSubmitFinal = findViewById(R.id.btnSubmitFinal);
        edtPincode = findViewById(R.id.edtPincode);
        edtShopAddress = findViewById(R.id.edtShopAddress);

        documentDataLayout.setVisibility(View.GONE);
        contactDataLayout.setVisibility(View.GONE);
        sellerProgress.setVisibility(View.GONE);
        addressDataLayout.setVisibility(View.GONE);

        contactDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactDataLayout.getVisibility() == View.GONE) {
                    contactDataLayout.setVisibility(View.VISIBLE);
                    contactArrow.setImageResource(R.drawable.downarrow);
                } else {
                    contactDataLayout.setVisibility(View.GONE);
                    contactArrow.setImageResource(R.drawable.rightarrow);
                }
            }
        });

        documentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (documentDataLayout.getVisibility() == View.GONE) {
                    documentDataLayout.setVisibility(View.VISIBLE);
                    documentArrow.setImageResource(R.drawable.downarrow);
                } else {
                    documentDataLayout.setVisibility(View.GONE);
                    documentArrow.setImageResource(R.drawable.rightarrow);
                }
            }
        });

        addressDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressDataLayout.getVisibility() == View.GONE) {
                    addressDataLayout.setVisibility(View.VISIBLE);
                    addressArrow.setImageResource(R.drawable.downarrow);
                } else {
                    addressDataLayout.setVisibility(View.GONE);
                    addressArrow.setImageResource(R.drawable.rightarrow);
                }
            }
        });

        aadhaarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JoinSellerActivity.this, AadharActivity.class));
            }
        });

        panLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JoinSellerActivity.this, PANActivity.class));
            }
        });

        bankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JoinSellerActivity.this, BankActivity.class));
            }
        });

        fssaiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JoinSellerActivity.this, FssaiActivity.class));
            }
        });

        gstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JoinSellerActivity.this, GSTActivity.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldChecked = checkAllField();
                if (isAllFieldChecked) {
                    Log.e("TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                    Log.e("PARTNER NAME", edtName.getText().toString().trim());
                    Log.e("SHOP NAME", edtShopName.getText().toString().trim());
                    Log.e("EMAIL ADDRESS", edtEmail.getText().toString().trim());
                    Log.e("NUMBER", edtNumber.getText().toString().trim());
                    Log.e("PASSWORD", edtPassword.getText().toString().trim());
                    registerShop(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                            , edtName.getText().toString().trim()
                            , edtShopName.getText().toString().trim()
                            , edtEmail.getText().toString().trim()
                            , edtNumber.getText().toString().trim()
                            , edtPassword.getText().toString().trim());
                }
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

        btnSubmitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAddressChecked = checkAddressFields();
                if (isAddressChecked) {
                    Log.e("STATE ID", state_id);
                    Log.e("DISTRICT ID", district_id);
                    Log.e("PINCODE", edtPincode.getText().toString());
                    Log.e("ADDRESS", edtShopAddress.getText().toString());
                    saveShopAddress(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                            , edtShopAddress.getText().toString()
                            , state_id
                            , district_id
                            , edtPincode.getText().toString());
                }
            }
        });
        btnSubmitFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischekedallField = chekedallField();
                if (ischekedallField) {
                    getFinalSubmit(MyPreferences.getInstance(getApplicationContext()).getUserToken());
                }
            }
        });


    }

    public boolean checkAllField() {
        if (edtName.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Name...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtShopName.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Shop Name...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtEmail.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Email Address...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtEmail.length() > 0) {
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(edtEmail.getText().toString().trim());
            if (!matcher.matches()) {
                Snackbar.make(parentLayout, "Please Enter Valid Email Address...!!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.parseColor("#EA2525"))
                        .setTextColor(Color.WHITE)
                        .show();
                return false;
            }
        }
        if (edtNumber.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtNumber.length() < 10) {
            Snackbar.make(parentLayout, "Please Enter Valid Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPassword.length() == 0) {
            Snackbar.make(parentLayout, "Please Create Your Password...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        return true;
    }

    private boolean checkAddressFields() {
        if (state_id == null) {
            Snackbar.make(parentLayout, "Please Select Your State...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (district_id == null) {
            Snackbar.make(parentLayout, "Please Select Your District...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPincode.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Area Pincode...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtPincode.length() < 6) {
            Snackbar.make(parentLayout, "Please Enter Valid Area Pincode...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtShopAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Shop Address...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }

        return true;
    }

    public boolean chekedallField(){
        if (documentDataLayout == null) {
            Snackbar.make(parentLayout, "Please update your Pancard Detail!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }

        return  true;
    }

    public void registerShop(String token, String name, String shopname, String email, String number, String password) {
        sellerProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<RegisterShopRequest> call = api.registerShop(token, name, shopname, password, email, number);
        call.enqueue(new Callback<RegisterShopRequest>() {
            @Override
            public void onResponse(Call<RegisterShopRequest> call, Response<RegisterShopRequest> response) {
                sellerProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.parseColor("#12d06b"))
                                .setTextColor(Color.WHITE)
                                .show();
                        MyPreferences.getInstance(getApplicationContext())
                                .saveSellerDetails(response.body().getRegRes().getPartnerName()
                                        , response.body().getRegRes().getShopName()
                                        , response.body().getRegRes().getMobile()
                                        , response.body().getRegRes().getEmail());
                        Log.e("TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                        Log.e("PARTNER NAME", MyPreferences.getInstance(getApplicationContext()).getSellerPartnerName());
                        Log.e("SHOP NAME", MyPreferences.getInstance(getApplicationContext()).getSellerShopName());
                        Log.e("EMAIL ADDRESS", MyPreferences.getInstance(getApplicationContext()).getSellerEmail());
                        Log.e("NUMBER", MyPreferences.getInstance(getApplicationContext()).getSellerMobile());
                        edtName.setText("");
                        edtShopName.setText("");
                        edtEmail.setText("");
                        edtNumber.setText("");
                        edtPassword.setText("");
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
            public void onFailure(Call<RegisterShopRequest> call, Throwable t) {
                sellerProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    public BottomSheetDialog selectStateDialog;
    TextView stateHeading;
    ProgressBar stateProgress;
    RecyclerView stateRecycler;
    StateListAdapter stateListAdapter;
    EditText searchState;
    List<State> stateList = new ArrayList<>();

    public void selectStatePopup() {
        selectStateDialog = new BottomSheetDialog(JoinSellerActivity.this);
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
                                , JoinSellerActivity.this
                                , null
                                , null
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

    public BottomSheetDialog selectDistrictDialog;
    TextView districtHeading;
    ProgressBar districtProgress;
    RecyclerView districtRecycler;
    EditText searchDistrict;
    DistrictListAdapter districtListAdapter;
    List<District> districtList = new ArrayList<>();

    public void selectDistrictPopup(String stateId) {
        selectDistrictDialog = new BottomSheetDialog(JoinSellerActivity.this);
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
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        districtList = response.body().getDistricts();
                        districtListAdapter = new DistrictListAdapter(getApplicationContext()
                                , null
                                , JoinSellerActivity.this
                                , null
                                , null
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

    public void saveShopAddress(String token, String shopaddress, String state_id, String district_id, String pincode) {
        sellerProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<SaveShopAddressDetails> call = api.saveShopAddressDetails(token, shopaddress, state_id, district_id, pincode);
        call.enqueue(new Callback<SaveShopAddressDetails>() {
            @Override
            public void onResponse(Call<SaveShopAddressDetails> call, Response<SaveShopAddressDetails> response) {
                sellerProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.parseColor("#12d06b"))
                                .setTextColor(Color.WHITE)
                                .show();
                        txtState.setText("");
                        txtDistrict.setText("");
                        edtPincode.setText("");
                        edtShopAddress.setText("");
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
            public void onFailure(Call<SaveShopAddressDetails> call, Throwable t) {
                sellerProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    private void getFinalSubmit(String token) {
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<Final> call = api.getsubmit(token);
        call.enqueue(new Callback<Final>() {
            @Override
            public void onResponse(Call<Final> call, Response<Final> response) {
             if(response.body()!= null){
                 if(response.body().getStatus()){
                     Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_LONG)
                             .setBackgroundTint(Color.parseColor("#12d06b"))
                             .setTextColor(Color.WHITE)
                             .show();
                 }
             }else {
                 Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                         .setBackgroundTint(Color.parseColor("#EA2525"))
                         .setTextColor(Color.WHITE)
                         .show();
             }

            }

            @Override
            public void onFailure(Call<Final> call, Throwable t) {
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}