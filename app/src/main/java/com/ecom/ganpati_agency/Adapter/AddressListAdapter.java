package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Activity.CheckoutActivity;
import com.ecom.ganpati_agency.Activity.AddAddressActivity;
import com.ecom.ganpati_agency.Model.response.Address;
import com.ecom.ganpati_agency.R;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

    Context context;
    AddAddressActivity addAddressActivity;
    List<Address> addressList;

    public AddressListAdapter(Context applicationContext, AddAddressActivity addAddressActivity, List<android.location.Address> custAddressResp) {
        this.context = context;
        this.addAddressActivity = addAddressActivity;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_address_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(holder.getAdapterPosition());
        holder.txtAreaName.setText(address.getDistrictName() + ", " + address.getStateName());
        holder.txtPincode.setText(address.getPinCode());
        holder.txtAddress.setText(address.getAddressDetail());
        holder.txtFullAddress.setText(address.getAddressDetail() + ", " + address.getDistrictName() + ", " + address.getStateName() + ", " + address.getPinCode());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addAddressActivity.flag==null) {
                    Intent intent = new Intent(context.getApplicationContext(), CheckoutActivity.class);
                    intent.putExtra("coming_from", "AddressActivity");
                    intent.putExtra("state_name", address.getStateName());
                    intent.putExtra("state_id", address.getStateId());
                    intent.putExtra("district_name", address.getDistrictName());
                    intent.putExtra("district_id", address.getDistrictId());
                    intent.putExtra("pincode", address.getPinCode());
                    intent.putExtra("address", address.getAddressDetail());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    /*addAddressActivity.finish();*/
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAreaName, txtPincode, txtAddress, txtFullAddress;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAreaName = itemView.findViewById(R.id.txtAreaName);
            txtPincode = itemView.findViewById(R.id.txtPincode);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtFullAddress = itemView.findViewById(R.id.txtFullAddress);
            parentLayout = itemView.findViewById(R.id.parentLayout);

        }
    }

}

