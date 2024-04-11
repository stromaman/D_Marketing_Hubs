package com.ecom.ganpati_agency.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.ganpati_agency.Adapter.AddressListAdapter;
import com.ecom.ganpati_agency.Api.ApiClient;
import com.ecom.ganpati_agency.Api.ServiceApi;
import com.ecom.ganpati_agency.Model.request.AddressRequest;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    RecyclerView addressListRecycler;
    RelativeLayout parentLayout, noDataLayout;
    CardView cardBack;
    LinearLayout mainLayout;
    ProgressBar addressProgress;
    public String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cardBack = findViewById(R.id.cardBack);
        addressProgress = findViewById(R.id.addressProgress);
        addressListRecycler = findViewById(R.id.addressListRecycler);
        parentLayout = findViewById(R.id.parentLayout);
        noDataLayout = findViewById(R.id.noDataLayout);
        mainLayout = findViewById(R.id.mainLayout);

        mainLayout.setVisibility(View.GONE);
        addressProgress.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        addressListRecycler.setLayoutManager(new LinearLayoutManager(AddAddressActivity.this));

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            flag = bundle.getString("flag");
        }

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAddressActivity.super.onBackPressed();
            }
        });

        getAddressList(MyPreferences.getInstance(getApplicationContext()).getUserToken());

    }

    public void getAddressList(String token){
        addressProgress.setVisibility(View.VISIBLE);
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<AddressRequest> call = api.getAddressList(token);
        call.enqueue(new Callback<AddressRequest>() {
            @Override
            public void onResponse(Call<AddressRequest> call, Response<AddressRequest> response) {
                addressProgress.setVisibility(View.GONE);
                if(response.body()!=null){
                    if(response.body().getStatus()){
                        mainLayout.setVisibility(View.VISIBLE);
                        noDataLayout.setVisibility(View.GONE);
                        AddressListAdapter addressListAdapter = new AddressListAdapter(getApplicationContext()
                                ,AddAddressActivity.this, response.body().getCustAddressResp());
                        addressListRecycler.setAdapter(addressListAdapter);
                    }else{
                        mainLayout.setVisibility(View.GONE);
                        noDataLayout.setVisibility(View.VISIBLE);
                        /*Snackbar.make(parentLayout, "" + response.body().getMessage(), Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(Color.parseColor("#EA2525")) // 136afb
                                .setTextColor(Color.WHITE)
                                .show();*/
                    }
                }else{
                    Log.e("BODY","Body is null");
                }
            }

            @Override
            public void onFailure(Call<AddressRequest> call, Throwable t) {
                addressProgress.setVisibility(View.GONE);
                Log.e("EXCEPTION",t.getLocalizedMessage());
            }
        });
    }

    Dialog addAddressDialog;
    CardView addCardBack;
    EditText edtName, edtNumber, edtAddress;
    TextView txtName, txtNumber, txtAddress, txtFullAddress;

    public void getAddAddressDialog() {
        addAddressDialog = new Dialog(AddAddressActivity.this);
        addAddressDialog.setContentView(R.layout.custom_add_address_dialog_layout);
        addAddressDialog.setCancelable(true);

        addCardBack = addAddressDialog.findViewById(R.id.cardBack);
        edtName = addAddressDialog.findViewById(R.id.edtName);
        edtNumber = addAddressDialog.findViewById(R.id.edtNumber);
        edtAddress = addAddressDialog.findViewById(R.id.edtAddress);
        txtName = addAddressDialog.findViewById(R.id.txtName);
        txtNumber = addAddressDialog.findViewById(R.id.txtNumber);
        txtAddress = addAddressDialog.findViewById(R.id.txtAddress);
        txtFullAddress = addAddressDialog.findViewById(R.id.txtFullAddress);

        setTextValue(edtName,txtName,null);
        setTextValue(edtNumber,txtNumber,null);
        setTextValue(edtAddress,txtAddress,txtFullAddress);

        addCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddressDialog.dismiss();
            }
        });

        addAddressDialog.show();
        addAddressDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addAddressDialog.getWindow().setGravity(Gravity.TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addAddressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            addAddressDialog.getWindow().setStatusBarColor(getApplicationContext().getColor(R.color.white));
        }
    }

    public void setTextValue(EditText editText, TextView textView, TextView fullAddress){

        if(fullAddress!=null){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    textView.setText(editable.toString());
                    fullAddress.setText(editable.toString());
                }
            });
        }else{
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    textView.setText(editable.toString());
                }
            });
        }

    }

}
