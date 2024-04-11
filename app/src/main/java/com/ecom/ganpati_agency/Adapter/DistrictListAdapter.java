package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Activity.DirectBuyActivity;
import com.ecom.ganpati_agency.Activity.SignUpActivity;
import com.ecom.ganpati_agency.Activity.CheckoutActivity;
import com.ecom.ganpati_agency.seller.JoinSellerActivity;
import com.ecom.ganpati_agency.Model.response.District;
import com.ecom.ganpati_agency.R;

import java.util.List;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.ViewHolder> {

    Context context;
    CheckoutActivity checkoutActivity;
    JoinSellerActivity joinSellerActivity;
    DirectBuyActivity directBuyActivity;
    SignUpActivity signUpActivity;
    List<District> districtList;

    public DistrictListAdapter(Context context, CheckoutActivity checkoutActivity, JoinSellerActivity joinSellerActivity
            , DirectBuyActivity directBuyActivity, SignUpActivity signUpActivity, List<District> districtList) {
        this.context = context;
        this.checkoutActivity = checkoutActivity;
        this.joinSellerActivity = joinSellerActivity;
        this.directBuyActivity = directBuyActivity;
        this.signUpActivity = signUpActivity;
        this.districtList = districtList;
    }

    /*public DistrictListAdapter(Context context, CheckoutActivity checkoutActivity, JoinSellerActivity joinSellerActivity, DirectBuyActivity directBuyActivity, List<District> districtList) {
        this.context = context;
        this.checkoutActivity = checkoutActivity;
        this.joinSellerActivity = joinSellerActivity;
        this.directBuyActivity = directBuyActivity;
        this.districtList = districtList;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_area_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        District district = districtList.get(holder.getAdapterPosition());
        holder.txtDistrictName.setText(district.getName());
        holder.districtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkoutActivity!=null){
                    checkoutActivity.districtId = district.getDistrictId();
                    checkoutActivity.txtDistrict.setText(district.getName());
                    checkoutActivity.selectDistrictDialog.dismiss();
                    Log.e("DISTRICT ID",checkoutActivity.districtId);
               }
                else if(joinSellerActivity!=null){
                    joinSellerActivity.district_id = district.getDistrictId();
                    joinSellerActivity.txtDistrict.setText(district.getName());
                    joinSellerActivity.selectDistrictDialog.dismiss();
                    Log.e("DISTRICT ID",joinSellerActivity.district_id);
                }
                else if(directBuyActivity!=null){
                    directBuyActivity.districtId = district.getDistrictId();
                    directBuyActivity.txtDistrict.setText(district.getName());
                    directBuyActivity.selectDistrictDialog.dismiss();
                    Log.e("DISTRICT ID",directBuyActivity.districtId);
                }else if(signUpActivity!=null){
                    signUpActivity.district_id = district.getDistrictId();
                    signUpActivity.txtDistrict.setText(district.getName());
                    signUpActivity.selectDistrictDialog.dismiss();
                    Log.e("DISTRICT ID",signUpActivity.district_id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return districtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtDistrictName;
        LinearLayout districtLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDistrictName = itemView.findViewById(R.id.txtAreaName);
            districtLayout = itemView.findViewById(R.id.areaLayout);

        }
    }

    public void filterList(List<District> districtFilteredList) {
        districtList = districtFilteredList;
        notifyDataSetChanged();
    }

}

