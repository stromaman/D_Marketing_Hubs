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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Adapter.DistrictListAdapter;
import com.ecom.ganpati_agency.Adapter.StateListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.BuyNowRequest;
import com.ecom.ganpati_agency.Model.request.DistrictRequest;
import com.ecom.ganpati_agency.Model.request.FreeDeliveryAmountRequest;
import com.ecom.ganpati_agency.Model.request.PaymentMethodRequest;
import com.ecom.ganpati_agency.Model.request.StateRequest;
import com.ecom.ganpati_agency.Model.response.AddToCart;
import com.ecom.ganpati_agency.Model.response.District;
import com.ecom.ganpati_agency.Model.response.PaymentMethod;
import com.ecom.ganpati_agency.Model.response.State;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
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

public class DirectBuyActivity extends AppCompatActivity {

    CardView cardBack, offCard;
    LinearLayout addToCartLayout, placeOrderLayout, oldAddressLayout, newAddressLayout, addressLayout;
    RelativeLayout parentLayout;
    TextView productName, weight, txtOff, productAmount, productDiscount, totalPrice, discountPrice, txtCount, itemCount,
            txtItemCount, totalAmount, txtUserNumber, txtUserName, txtUserEmailAddress, deliveryCharge;
    ImageView imgProduct;
    EditText edtPincode, edtAddress, edtNumber;
    ElasticLayout minusLayout, plusLayout;
    String product_name, product_image, product_discount, attr_valuefinal, quantity, attr_idfinal, shop_id, shop_name, product_id, mrp, discount_amount, payment_code, delivery_charge;
    int count = 1, payingCost, freeDeliveryAmount;
    public TextView txtState, txtDistrict;
    public String stateId, districtId, addressType;
    LinearProgressIndicator linearProgress;

    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_buy);
        getSupportActionBar().hide();


        cardBack = findViewById(R.id.cardBack);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        parentLayout = findViewById(R.id.parentLayout);
        productName = findViewById(R.id.productName);
        weight = findViewById(R.id.weight);
        txtOff = findViewById(R.id.txtOff);
        productAmount = findViewById(R.id.productAmount);
        productDiscount = findViewById(R.id.productDiscount);
        imgProduct = findViewById(R.id.imgProduct);
        offCard = findViewById(R.id.offCard);
        totalPrice = findViewById(R.id.totalPrice);
        discountPrice = findViewById(R.id.discountPrice);
        minusLayout = findViewById(R.id.minusLayout);
        plusLayout = findViewById(R.id.plusLayout);
        txtCount = findViewById(R.id.txtCount);
        itemCount = findViewById(R.id.itemCount);
        totalAmount = findViewById(R.id.totalAmount);
        placeOrderLayout = findViewById(R.id.placeOrderLayout);
        edtAddress = findViewById(R.id.edtAddress);
        edtPincode = findViewById(R.id.edtPincode);
        linearProgress = findViewById(R.id.linearProgress);
        edtNumber = findViewById(R.id.edtNumber);
        txtItemCount = findViewById(R.id.txtItemCount);
        txtUserNumber = findViewById(R.id.txtUserNumber);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmailAddress = findViewById(R.id.txtUserEmailAddress);
        addToCartLayout = findViewById(R.id.addToCartLayout);
        deliveryCharge = findViewById(R.id.deliveryCharge);
        oldAddressLayout = findViewById(R.id.oldAddressLayout);
        newAddressLayout = findViewById(R.id.newAddressLayout);
        addressLayout = findViewById(R.id.addressLayout);


        linearProgress.setVisibility(View.INVISIBLE);
        offCard.setVisibility(View.GONE);
        addressLayout.setVisibility(View.GONE);
        productDiscount.setPaintFlags(productDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        totalPrice.setPaintFlags(totalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product_name = bundle.getString("product_name");
            product_discount = bundle.getString("product_discount");
            product_image = bundle.getString("product_image");
            attr_valuefinal = bundle.getString("attr_valuefinal");
            quantity = bundle.getString("quantity");
            attr_idfinal = bundle.getString("attr_idfinal");
            shop_id = bundle.getString("shop_id");
            shop_name = bundle.getString("shop_name");
            product_id = bundle.getString("product_id");
            mrp = bundle.getString("mrp");
            discount_amount = bundle.getString("discount_amount");
            delivery_charge = bundle.getString("delivery_charge");
            String Image_url = product_image.
                    replace("[", "")
                    .replace("]", "")
                    .replace("\\", "")
                    .replace("\"", "");

            MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
//            Log.e("TOKEN", preferences.getUserToken());
            Log.e("SHOP ID", shop_id);
            Log.e("SHOP NAME", shop_name);
            Log.e("PRODUCT ID", product_id);
            Log.e("PRODUCT NAME", product_name);
            Log.e("PRODUCT IMAGE", product_image);
            Log.e("PRODUCT WT.", quantity);
            Log.e("PRODUCT DISCOUNT PERCENT", product_discount);
            Log.e("PRODUCT MRP", mrp);
            Log.e("PRODUCT DISCOUNT AMOUNT", discount_amount);
            Log.e("DELIVERY CHARGE", delivery_charge);
            Log.e("VALUE FINAL", attr_valuefinal);
            Log.e("ID FINAL", attr_idfinal);
            Log.e("URL", Image_url);

            productName.setText(product_name);
            productAmount.setText("₹" + discount_amount);
            productDiscount.setText("₹" + mrp);
            totalPrice.setText("₹" + mrp);
            discountPrice.setText("₹" + discount_amount);
            deliveryCharge.setText("₹" + delivery_charge);
            weight.setText(quantity);
            txtUserName.setText("Name: " + preferences.getUserName());
            txtUserNumber.setText("Number: +91 " + preferences.getUserNumber());
            txtUserEmailAddress.setText("Email Address: " + preferences.getUserEmail());
            edtNumber.setText(preferences.getUserNumber());
            txtState.setText(preferences.getStateName());
            txtDistrict.setText(preferences.getDistrictName());
            stateId = preferences.getStateId();
            districtId = preferences.getDistrictId();
            edtPincode.setText(preferences.getPincode());
            edtAddress.setText(preferences.getAddress());
            if (!product_image.equals("")) {
                Glide.with(getApplicationContext())
                        .load(Constant.IMAGE_URL + Image_url)
                        .into(imgProduct);
            } else {
                Glide.with(getApplicationContext())
                        .load(R.drawable.noimg)
                        .into(imgProduct);
            }
            if (!product_discount.equals("")) {
                offCard.setVisibility(View.VISIBLE);
                txtOff.setText((int) Double.parseDouble(product_discount) + "% OFF");
            }

        }

        if (InternetValidation.validation(DirectBuyActivity.this)) {
            getFreeAmount();
        } else {
            InternetValidation.getNoConnectionDialog(DirectBuyActivity.this);
        }

        addressLayout.setVisibility(View.GONE);

        addToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPreferences preferences = MyPreferences.getInstance(getApplicationContext());
                addToCart(preferences.getUserToken()
                        ,preferences.getUserDeviceId()
                        , attr_valuefinal
                        , quantity
                        , attr_idfinal
                        , shop_id
                        , product_id);
            }
        });

        txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetValidation.validation(DirectBuyActivity.this)) {
                    selectStatePopup();
                } else {
                    InternetValidation.getNoConnectionDialog(DirectBuyActivity.this);
                }
            }
        });


        txtDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateId != null) {
                    if (InternetValidation.validation(DirectBuyActivity.this)) {
                        selectDistrictPopup(stateId);
                    } else {
                        InternetValidation.getNoConnectionDialog(DirectBuyActivity.this);
                    }
                } else {
                    Snackbar.make(parentLayout, "Please Select State...!!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                            .setTextColor(Color.WHITE)
                            .show();
                }
            }
        });

        minusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    count = count - 1;
                    txtCount.setText(count + "");
                    itemCount.setText(count + " Item");
                    txtItemCount.setText("You have total of " + count + " Item");

                    int amount = ((int) (Double.parseDouble(discount_amount)) * count);
                    if (amount > freeDeliveryAmount) {
                        deliveryCharge.setText("Free Delivery");
                        payingCost = ((int) (Double.parseDouble(discount_amount)) * count);
                    } else {
                        deliveryCharge.setText("₹" + delivery_charge);
                        payingCost = ((int) (Double.parseDouble(discount_amount)) * count) + (int) (Double.parseDouble(delivery_charge));
                    }
                    totalAmount.setText("₹" + payingCost + ".00");
                } else {
                    DirectBuyActivity.super.onBackPressed();
                }
            }
        });

        plusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                txtCount.setText(count + "");
                itemCount.setText(count + " Item");
                txtItemCount.setText("You have total of " + count + " Item");

                int amount = ((int) (Double.parseDouble(discount_amount)) * count);
                if (amount > freeDeliveryAmount) {
                    deliveryCharge.setText("Free Delivery");
                    payingCost = ((int) (Double.parseDouble(discount_amount)) * count);
                } else {
                    deliveryCharge.setText("₹" + delivery_charge);
                    payingCost = ((int) (Double.parseDouble(discount_amount)) * count) + (int) (Double.parseDouble(delivery_charge));
                }
                totalAmount.setText("₹" + payingCost + ".00");
            }
        });

        placeOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                   // Log.e("TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                    Log.e("SHOP ID", shop_id);
                    Log.e("SHOP NAME", shop_name);
                    Log.e("PRODUCT ID", product_id);
                    Log.e("PRODUCT NAME", product_name);
                    Log.e("PRODUCT IMAGE", product_image);
                    Log.e("PRODUCT WT.", quantity);
                    Log.e("PRODUCT DISCOUNT PERCENT", product_discount);
                    Log.e("PRODUCT MRP", mrp);
                    Log.e("PRODUCT DISCOUNT AMOUNT", discount_amount);
                    Log.e("PRODUCT COUNT", count + "");
                    Log.e("VALUE FINAL", attr_valuefinal);
                    Log.e("ID FINAL", attr_idfinal);
                    Log.e("STATE ID", stateId);
                    Log.e("DISTRICT ID", districtId);
                    Log.e("PINCODE", edtPincode.getText().toString());
                    Log.e("ADDRESS", edtAddress.getText().toString());
                    Log.e("NUMBER", edtNumber.getText().toString());
                    Log.e("COUNT", count + "");
                    if (InternetValidation.validation(DirectBuyActivity.this)) {
                            getPaymentMethodDialog();

                    } else {
                        InternetValidation.getNoConnectionDialog(DirectBuyActivity.this);
                    }
                }
            }
        });


        newAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressType = "new";
                newAddressLayout.setBackgroundResource(R.drawable.payment_method_bg);
                oldAddressLayout.setBackgroundResource(R.drawable.payment_method_borderless);

                addressLayout.setVisibility(View.VISIBLE);
                edtNumber.setText("");
                txtState.setText("");
                txtDistrict.setText("");
                stateId = null;
                districtId = null;
                edtPincode.setText("");
                edtAddress.setText("");
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
                edtNumber.setText(preferences.getUserNumber());
                txtState.setText(preferences.getStateName());
                txtDistrict.setText(preferences.getDistrictName());
                stateId = preferences.getStateId();
                districtId = preferences.getDistrictId();
                edtPincode.setText(preferences.getPincode());
                edtAddress.setText(preferences.getAddress());
            }
        });

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DirectBuyActivity.super.onBackPressed();
            }
        });
    }

    public void getFreeAmount() {
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<FreeDeliveryAmountRequest> call = api.getFreeDeliveryAmount();
        call.enqueue(new Callback<FreeDeliveryAmountRequest>() {
            @Override
            public void onResponse(Call<FreeDeliveryAmountRequest> call, Response<FreeDeliveryAmountRequest> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        freeDeliveryAmount = (int) Double.parseDouble(response.body().getAmountDetail().getAmount());
                        Log.e("FREE DELIVERY COST", freeDeliveryAmount + "");
                        if (((int) (Double.parseDouble(discount_amount)) * count) > freeDeliveryAmount) {
                            payingCost = (int) ((int) Double.parseDouble(discount_amount));
                            deliveryCharge.setText("Free Delivery");
                        } else {
                            payingCost = (int) ((int) Double.parseDouble(discount_amount) + Double.parseDouble(delivery_charge));
                            deliveryCharge.setText("₹" + delivery_charge);
                        }
                        Log.e("COST", payingCost + "");
                        totalAmount.setText("₹" + payingCost + ".00");
                    } else {

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

    public boolean checkAllFields() {

        if (addressType == null) {
            Snackbar.make(parentLayout, "Please Select Address Type...!!", Snackbar.LENGTH_SHORT)
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
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        }
        if (edtNumber.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Number...!!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
                    .setTextColor(Color.WHITE)
                    .show();
            return false;
        } else if (edtNumber.length() < 10) {
            Snackbar.make(parentLayout, "Please Enter Valid Number...!!", Snackbar.LENGTH_SHORT)
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
        if (edtAddress.length() == 0) {
            Snackbar.make(parentLayout, "Please Enter Your Delivery Address", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#EA2525"))
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
        selectStateDialog = new BottomSheetDialog(DirectBuyActivity.this);
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
                                , DirectBuyActivity.this
                                ,null
                                , response.body().getStates());
                        stateRecycler.setAdapter(stateListAdapter);
                    } else {
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
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
        selectDistrictDialog = new BottomSheetDialog(DirectBuyActivity.this);
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
                                , null
                                , DirectBuyActivity.this
                                , null
                                , response.body().getDistricts());
                        districtRecycler.setAdapter(districtListAdapter);
                    } else {
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
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

    public void addToCart(String token,String deviceid, String attrValFinal, String attrValNameFinal, String attrIdFinal, String shopId, String productId) {
        linearProgress.setVisibility(View.VISIBLE);
         if(token == null){
             Intent intent = new Intent(DirectBuyActivity.this,LoginActivity.class);
             startActivity(intent);
         }
         else{
             ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
             Call<AddToCart> call = api. addToCart(token,deviceid, attrValFinal, attrValNameFinal, attrIdFinal, shopId, productId);
             call.enqueue(new Callback<AddToCart>() {
                 @Override
                 public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {
                     linearProgress.setVisibility(View.GONE);
                     if (response.body() != null) {
                         if (response.body().getStatus()) {
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
                 public void onFailure(Call<AddToCart> call, Throwable t) {
                     linearProgress.setVisibility(View.GONE);
                     Log.e("EXCEPTION", t.getLocalizedMessage());
                 }
             });
         }
    }




    public void buyNow(String token, String attr_valuefinal, String attr_value_namefinal, String attr_idfinal, String shop_id, String product_id
            , String stateId, String districtId, String pincode, String mobile, String address, String paymethod, String qty) {
        linearProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<BuyNowRequest> call = api.buyNow(token, attr_valuefinal, attr_value_namefinal, attr_idfinal, shop_id, product_id,
                stateId, districtId, pincode, mobile, address, paymethod, qty);
        call.enqueue(new Callback<BuyNowRequest>() {
            @Override
            public void onResponse(Call<BuyNowRequest> call, Response<BuyNowRequest> response) {
                linearProgress.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        if(paymethod.contains("1")) {
                            Snackbar.make(parentLayout, "Your Order Placed Successfully\n proceeding to pay", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(Color.parseColor("#12d06b"))
                                    .setTextColor(Color.WHITE)
                                    .show();
//                            String url = response.body().getOrderId().getPayUrl();
//                            Intent intent = new Intent(DirectBuyActivity.this, PaymentWebActivity.class);
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
                                    startActivity(new Intent(DirectBuyActivity.this, DashboardActivity.class));
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
            public void onFailure(Call<BuyNowRequest> call, Throwable t) {
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
        paymentDialog = new BottomSheetDialog(DirectBuyActivity.this);
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

        if (InternetValidation.validation(DirectBuyActivity.this)) {
            getPaymentMethod();
        } else {
            InternetValidation.getNoConnectionDialog(DirectBuyActivity.this);
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
                    Log.e("TOKEN", MyPreferences.getInstance(getApplicationContext()).getUserToken());
                    Log.e("SHOP ID", shop_id);
                    Log.e("SHOP NAME", shop_name);
                    Log.e("PRODUCT ID", product_id);
                    Log.e("PRODUCT NAME", product_name);
                    Log.e("PRODUCT IMAGE", product_image);
                    Log.e("PRODUCT WT.", quantity);
                    Log.e("PRODUCT DISCOUNT PERCENT", product_discount);
                    Log.e("PRODUCT MRP", mrp);
                    Log.e("PRODUCT DISCOUNT AMOUNT", discount_amount);
                    Log.e("PRODUCT COUNT", count + "");
                    Log.e("VALUE FINAL", attr_valuefinal);
                    Log.e("ID FINAL", attr_idfinal);
                    Log.e("STATE ID", stateId);
                    Log.e("DISTRICT ID", districtId);
                    Log.e("PINCODE", edtPincode.getText().toString());
                    Log.e("ADDRESS", edtAddress.getText().toString());
                    Log.e("NUMBER", edtNumber.getText().toString());
                    Log.e("PAYMENT CODE", payment_code);
                    Log.e("COUNT", count + "");

                    if (InternetValidation.validation(DirectBuyActivity.this)) {
                        buyNow(MyPreferences.getInstance(getApplicationContext()).getUserToken()
                                , attr_valuefinal
                                , quantity
                                , attr_idfinal
                                , shop_id
                                , product_id
                                , stateId
                                , districtId
                                , edtPincode.getText().toString()
                                , edtNumber.getText().toString()
                                , edtAddress.getText().toString()
                                , payment_code
                                , count + "");
                    } else {
                        InternetValidation.getNoConnectionDialog(DirectBuyActivity.this);
                    }
                } else {
                   // Toasty.error(DirectBuyActivity.this, "Please Select Payment Option...!!", Toasty.LENGTH_SHORT, false).show();
                    Toast.makeText(DirectBuyActivity.this, "Please Select Payment Option...!!", Toast.LENGTH_SHORT).show();
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