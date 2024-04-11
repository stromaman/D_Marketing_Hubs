package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecom.ganpati_agency.Adapter.DistrictListAdapter;
import com.ecom.ganpati_agency.Adapter.StateListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.DistrictRequest;
import com.ecom.ganpati_agency.Model.request.FreeDeliveryAmountRequest;
import com.ecom.ganpati_agency.Model.request.PaymentMethodRequest;
import com.ecom.ganpati_agency.Model.request.PlaceOrderRequest;
import com.ecom.ganpati_agency.Model.request.StateRequest;
import com.ecom.ganpati_agency.Model.response.District;
import com.ecom.ganpati_agency.Model.response.PaymentMethod;
import com.ecom.ganpati_agency.Model.response.Product;
import com.ecom.ganpati_agency.Model.response.State;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.skydoves.elasticviews.ElasticLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    ElasticLayout continuePaymentLayout;
    LinearLayout goToCartLayout, oldAddressLayout, newAddressLayout, addressLayout;
    CardView cardBack;
    RelativeLayout parentLayout;
    public TextView txtState, txtDistrict;
    public String stateId, districtId, payment_code;
    TextView itemCount, totalMrp, discountPrice, deliveryCharge, totalAmount;
    EditText edtName, edtNumber, edtEmailAddress, edtPincode, edtAddress;
    boolean isAllFieldsChecked = false;
    LinearProgressIndicator linearProgress;
    String coming_from, productIdList, addressType;
    double mrp, sellingPrice, deliverycharge;
    int totalCount, freeDeliveryAmount;
    double deliveryChargeAmount;
    private List<Product> cartProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        continuePaymentLayout = findViewById(R.id.continuePaymentLayout);
        goToCartLayout = findViewById(R.id.goToCartLayout);
        cardBack = findViewById(R.id.cardBack);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        parentLayout = findViewById(R.id.parentLayout);
        edtName = findViewById(R.id.edtName);
        edtNumber = findViewById(R.id.edtNumber);
        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        edtPincode = findViewById(R.id.edtPincode);
        edtAddress = findViewById(R.id.edtAddress);
        linearProgress = findViewById(R.id.linearProgress);
        itemCount = findViewById(R.id.itemCount);
        totalMrp = findViewById(R.id.totalPrice);
        discountPrice = findViewById(R.id.discountPrice);
        deliveryCharge = findViewById(R.id.deliveryCharge);
        totalAmount = findViewById(R.id.totalAmount);
        oldAddressLayout = findViewById(R.id.oldAddressLayout);
        newAddressLayout = findViewById(R.id.newAddressLayout);
        addressLayout = findViewById(R.id.addressLayout);

       // cartProducts = getCartProducts();

        addressLayout.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
        if (bundle != null) {
            coming_from = bundle.getString("coming_from");
            if (coming_from.equals("MyCart")) {
                productIdList = bundle.getString("productIdList");
                mrp = bundle.getDouble("mrp");
                sellingPrice = bundle.getDouble("discount");
                deliverycharge = bundle.getDouble("deliveryCharge");
                totalCount = bundle.getInt("count");
             //   deliveryChargeAmount = bundle.getDouble("deliveryCharge");
                System.out.println("============================ Preference Part =============================");
                Log.e("COMING FROM", coming_from);
                Log.e("PRODUCT ID LIST", productIdList);
                Log.e("NAME", preferences.getUserName());
                Log.e("EMAIL", preferences.getUserEmail());
                Log.e("NUMBER", preferences.getUserNumber());
                Log.e("TOKEN", preferences.getUserToken());
                Log.e("STATE ID", preferences.getStateId());
                Log.e("DISTRICT ID", preferences.getDistrictId());
                Log.e("PINCODE", preferences.getPincode());
                Log.e("ADDRESS", preferences.getAddress());
              //  Log.e("SHOP ID", preferences.getShopId());
                Log.e("SELLING PRICE", sellingPrice + "");
                Log.e("MRP", mrp + "");
                Log.e("DELIVERY CHARGE", deliverycharge + "");
                Log.e("COUNT", totalCount + "");

                edtName.setText(preferences.getUserName());
                edtEmailAddress.setText(preferences.getUserEmail());
                edtNumber.setText(preferences.getUserNumber());
                txtState.setText(preferences.getStateName());
                txtDistrict.setText(preferences.getDistrictName());
                stateId = preferences.getStateId();
                districtId = preferences.getDistrictId();
                edtPincode.setText(preferences.getPincode());
                edtAddress.setText(preferences.getAddress());

                itemCount.setText(totalCount + " item");
                totalMrp.setPaintFlags(totalMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                totalMrp.setText("₹ " + mrp);
                discountPrice.setText("₹ " + sellingPrice);

            }

            if (InternetValidation.validation(CheckoutActivity.this)) {
               getFreeAmount();
            } else {
                InternetValidation.getNoConnectionDialog(CheckoutActivity.this);
            }


        }
        linearProgress.setVisibility(View.INVISIBLE);
        goToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckoutActivity.super.onBackPressed();
            }
        });

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckoutActivity.super.onBackPressed();
            }
        });

        txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetValidation.validation(CheckoutActivity.this)) {
                    selectStatePopup();
                } else {
                    InternetValidation.getNoConnectionDialog(CheckoutActivity.this);
                }

            }
        });

        txtDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateId != null) {

                    if (InternetValidation.validation(CheckoutActivity.this)) {
                        selectDistrictPopup(stateId);
                    } else {
                        InternetValidation.getNoConnectionDialog(CheckoutActivity.this);
                    }

                } else {
                    Snackbar.make(parentLayout, "Please Select State...!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                }
            }
        });

        continuePaymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                    Log.e("NAME", edtName.getText().toString());
                    Log.e("EMAIL", edtEmailAddress.getText().toString());
                    Log.e("NUMBER", edtNumber.getText().toString());
                    Log.e("TOKEN", preferences.getUserToken());
                    Log.e("STATE ID", stateId);
                    Log.e("DISTRICT ID", districtId);
                    Log.e("PINCODE", edtPincode.getText().toString());
                    Log.e("ADDRESS", edtAddress.getText().toString());
                 //   Log.e("SHOP ID", preferences.getShopId());

                    if (InternetValidation.validation(CheckoutActivity.this)) {
                        getPaymentMethodDialog();
                    } else {
                        InternetValidation.getNoConnectionDialog(CheckoutActivity.this);
                    }

                }
            }
        });

        oldAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addressType = "old";

                oldAddressLayout.setBackgroundResource(R.drawable.payment_method_bg);
                newAddressLayout.setBackgroundResource(R.drawable.payment_method_borderless);

                addressLayout.setVisibility(View.VISIBLE);
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                edtEmailAddress.setText(preferences.getUserEmail());
                edtName.setText(preferences.getUserName());
                edtNumber.setText(preferences.getUserNumber());
                txtState.setText(preferences.getStateName());
                txtDistrict.setText(preferences.getDistrictName());
                stateId = preferences.getStateId();
                districtId = preferences.getDistrictId();
                edtPincode.setText(preferences.getPincode());
                edtAddress.setText(preferences.getAddress());
            }
        });

        newAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addressType = "new";

                newAddressLayout.setBackgroundResource(R.drawable.payment_method_bg);
                oldAddressLayout.setBackgroundResource(R.drawable.payment_method_borderless);

                addressLayout.setVisibility(View.VISIBLE);
                edtEmailAddress.setText("");
                edtName.setText("");
                edtNumber.setText("");
                txtState.setText("");
                txtDistrict.setText("");
                stateId = null;
                districtId = null;
                edtPincode.setText("");
                edtAddress.setText("");
            }
        });



    }

    public void getFreeAmount() {
        linearProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<FreeDeliveryAmountRequest> call = api.getFreeDeliveryAmount();
        call.enqueue(new Callback<FreeDeliveryAmountRequest>() {
            @Override
            public void onResponse(Call<FreeDeliveryAmountRequest> call, Response<FreeDeliveryAmountRequest> response) {
                linearProgress.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        freeDeliveryAmount = (int) Double.parseDouble(response.body().getAmountDetail().getAmount());
                       // double currentDeliveryCharge = Double.parseDouble(response.body().getAmountDetail().getAmount());
                        Log.e("FREE DELIVERY COST", freeDeliveryAmount + "");
                        if (sellingPrice > freeDeliveryAmount) {
                            totalAmount.setText("₹ " + sellingPrice);
                           // deliveryCharge.setText("Free Delivery");
                            Log.d("PRODUCT DELIVERY CHARGE", "Free Delivery");
                        } else {
                           deliveryCharge.setText("₹ " + deliverycharge);
                            totalAmount.setText("₹ " + (sellingPrice + deliverycharge));
                            Log.d("PRODUCT DELIVERY CHARGE", "₹ " + deliverycharge);
                        }
                    }
                } else {
                    Log.e("BODY", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<FreeDeliveryAmountRequest> call, Throwable t) {
                linearProgress.setVisibility(View.INVISIBLE);
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
        selectStateDialog = new BottomSheetDialog(CheckoutActivity.this);
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
                                , CheckoutActivity.this
                                , null
                                , null
                                , null
                                , response.body().getStates());
                        stateRecycler.setAdapter(stateListAdapter);
                    } else {

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
        selectDistrictDialog = new BottomSheetDialog(CheckoutActivity.this);
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
                                , CheckoutActivity.this
                                , null
                                , null
                                , null
                                , response.body().getDistricts());
                        districtRecycler.setAdapter(districtListAdapter);
                    } else {

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

    public boolean checkAllFields() {
        if(addressType==null){
            Snackbar.make(parentLayout, "Please Select Address Type...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtName.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Name...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtNumber.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtNumber.length() < 10) {
            Snackbar.make(parentLayout, "Please Enter Valid Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtEmailAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Email Address...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (!edtEmailAddress.getText().toString().contains("@")) {
            Snackbar.make(parentLayout, "Please Enter Valid Email Address...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (stateId == null) {
            Snackbar.make(parentLayout, "Please Select Your State...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (districtId == null) {
            Snackbar.make(parentLayout, "Please Select Your District...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPincode.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Area Pincode...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtPincode.length() < 6) {
            Snackbar.make(parentLayout, "Please Enter Valid Pincode...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Delivery Address...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }

        return true;

    }

    public void placeOrder(String token, String delivery_state, String delivery_district, String delivery_pincode,
                           String delivery_mobileno, String delivery_address, String payment_method, String shop_id, String selected_prods) {
        linearProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<PlaceOrderRequest> call = api.placeOrder(token, delivery_state, delivery_district,
                delivery_pincode, delivery_mobileno, delivery_address, payment_method, shop_id, selected_prods);
        call.enqueue(new Callback<PlaceOrderRequest>() {
            @Override
            public void onResponse(Call<PlaceOrderRequest> call, Response<PlaceOrderRequest> response) {
                linearProgress.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {

                        if(payment_method.contains("1")) {
                            Snackbar.make(parentLayout, "Your Order Placed Successfully\n Proceeding to payment", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();

//                            String url = response.body().getOrderId().getPayUrl();
//                            Intent intent = new Intent(CheckoutActivity.this, PaymentWebActivity.class);
//                            intent.putExtra("url", url + "");
//                            startActivity(intent);

                        }else{
                            Snackbar.make(parentLayout, "Your Order Placed Successfully", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(CheckoutActivity.this, DashboardActivity.class));
                                    finishAffinity();
                                }
                            }, 500);
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
            public void onFailure(Call<PlaceOrderRequest> call, Throwable t) {
                linearProgress.setVisibility(View.INVISIBLE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

    BottomSheetDialog paymentDialog;
    LinearLayout onlineLayout, cashLayout;
    RelativeLayout codNotAvailableLayout, onlineNotAvailableLayout;
    LinearProgressIndicator paymentProgress;
    RelativeLayout contantLayout;
    MaterialButton btnConfirm;

    public void getPaymentMethodDialog() {
        payment_code = null;
        paymentDialog = new BottomSheetDialog(CheckoutActivity.this);
        paymentDialog.setContentView(R.layout.custom_payment_method_layout);
        paymentDialog.getWindow().findViewById(R.id.contantLayout).setBackgroundColor(Color.TRANSPARENT);
        paymentDialog.getWindow().setTransitionBackgroundFadeDuration(10000);

        onlineLayout = paymentDialog.findViewById(R.id.onlineLayout);
        cashLayout = paymentDialog.findViewById(R.id.cashLayout);
        paymentProgress = paymentDialog.findViewById(R.id.linearProgress);
        codNotAvailableLayout = paymentDialog.findViewById(R.id.codNotAvailableLayout);
        onlineNotAvailableLayout = paymentDialog.findViewById(R.id.onlineNotAvailableLayout);
        btnConfirm = paymentDialog.findViewById(R.id.btnConfirm);
        contantLayout = paymentDialog.findViewById(R.id.contantLayout);

        paymentProgress.setVisibility(View.GONE);
        onlineNotAvailableLayout.setVisibility(View.GONE);
        codNotAvailableLayout.setVisibility(View.GONE);

        if (InternetValidation.validation(CheckoutActivity.this)) {
            getPaymentMethod();
        } else {
            InternetValidation.getNoConnectionDialog(CheckoutActivity.this);
        }

        onlineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlineLayout.setBackgroundResource(R.drawable.payment_method_bg);
                cashLayout.setBackgroundResource(R.drawable.payment_method_borderless);
                payment_code = "1";
            }
        });

        cashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashLayout.setBackgroundResource(R.drawable.payment_method_bg);
               onlineLayout.setBackgroundResource(R.drawable.payment_method_borderless);
                payment_code = "0";
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment_code != null) {
                    paymentDialog.dismiss();
                    MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                    Log.e("NAME", edtName.getText().toString());
                    Log.e("EMAIL", edtEmailAddress.getText().toString());
                    Log.e("NUMBER", edtNumber.getText().toString());
                    Log.e("TOKEN", preferences.getUserToken());
                    Log.e("STATE ID", stateId);
                    Log.e("DISTRICT ID", districtId);
                    Log.e("PINCODE", edtPincode.getText().toString());
                    Log.e("ADDRESS", edtAddress.getText().toString());
                //    Log.e("SHOP ID", preferences.getShopId());
                    Log.e("PAYMENT CODE", payment_code);
                    Log.e("PRODUCT ID LIST", productIdList);
                    if (InternetValidation.validation(CheckoutActivity.this)) {
                        placeOrder(preferences.getUserToken()
                                , stateId
                                , districtId
                                , edtPincode.getText().toString()
                                , edtNumber.getText().toString()
                                , edtAddress.getText().toString()
                                , payment_code
                                , ""
                                , productIdList);
                    } else {
                        InternetValidation.getNoConnectionDialog(CheckoutActivity.this);
                    }

                } else {
                  //  Toast.error(CheckoutActivity.this, "Please Select Payment Option...!!", Toast.LENGTH_SHORT, false).show();
                    Toast.makeText(CheckoutActivity.this, "Please Select Payment Option...!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        paymentDialog.show();
        paymentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void getPaymentMethod() {
        paymentProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<PaymentMethodRequest> call = api.getPaymentMethods();
        call.enqueue(new Callback<PaymentMethodRequest>() {
            @Override
            public void onResponse(Call<PaymentMethodRequest> call, Response<PaymentMethodRequest> response) {
                paymentProgress.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        List<PaymentMethod> paymentMethods = response.body().getPmethodRes();
                        if (paymentMethods.get(0).getActiveStatus().equals("0")) {
                            onlineLayout.setEnabled(false);
                            onlineNotAvailableLayout.setVisibility(View.VISIBLE);
                        }
                        if (paymentMethods.get(1).getActiveStatus().equals("0")) {
                            cashLayout.setEnabled(false);
                            codNotAvailableLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        paymentDialog.dismiss();
                        Snackbar.make(parentLayout, "Payment Not Available Right Now...!!", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();
                    }
                } else {
                    Log.e("BODY", "Body is nul");
                }
            }

            @Override
            public void onFailure(Call<PaymentMethodRequest> call, Throwable t) {
                paymentProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION", t.getLocalizedMessage());
            }
        });
    }

}