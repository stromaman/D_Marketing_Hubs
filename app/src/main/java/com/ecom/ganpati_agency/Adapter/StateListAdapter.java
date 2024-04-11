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
import com.ecom.ganpati_agency.Model.response.State;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.seller.JoinSellerActivity;

import java.util.List;

public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.ViewHolder> {

    Context context;
    CheckoutActivity checkoutActivity;
    JoinSellerActivity joinSellerActivity;
    DirectBuyActivity directBuyActivity;
    SignUpActivity signUpActivity;
    List<State> stateList;

    public StateListAdapter(Context context, CheckoutActivity checkoutActivity, JoinSellerActivity joinSellerActivity
            , DirectBuyActivity directBuyActivity, SignUpActivity signUpActivity, List<State> stateList) {
        this.context = context;
        this.checkoutActivity = checkoutActivity;
        this.joinSellerActivity = joinSellerActivity;
        this.directBuyActivity = directBuyActivity;
        this.signUpActivity = signUpActivity;
        this.stateList = stateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_area_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        State state = stateList.get(holder.getAdapterPosition());
        holder.txtStateName.setText(state.getName());
        holder.stateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkoutActivity!=null){
                    checkoutActivity.stateId = state.getStateId();
                    checkoutActivity.txtState.setText(state.getName());
                    checkoutActivity.selectStateDialog.dismiss();
                    Log.e("STATE ID",checkoutActivity.stateId);
                }else if(joinSellerActivity!=null){
                    joinSellerActivity.state_id = state.getStateId();
                    joinSellerActivity.txtState.setText(state.getName());
                    joinSellerActivity.selectStateDialog.dismiss();
                    Log.e("STATE ID",joinSellerActivity.state_id);
                }else if(directBuyActivity!=null){
                    directBuyActivity.stateId = state.getStateId();
                    directBuyActivity.txtState.setText(state.getName());
                    directBuyActivity.selectStateDialog.dismiss();
                    Log.e("STATE ID",directBuyActivity.stateId);
                }else if(signUpActivity!=null){
                    signUpActivity.state_id = state.getStateId();
                    signUpActivity.txtState.setText(state.getName());
                    signUpActivity.selectStateDialog.dismiss();
                    Log.e("STATE ID",signUpActivity.state_id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtStateName;
        LinearLayout stateLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStateName = itemView.findViewById(R.id.txtAreaName);
            stateLayout = itemView.findViewById(R.id.areaLayout);

        }
    }

    public void filterList(List<State> filteredStateList) {
        stateList = filteredStateList;
        notifyDataSetChanged();
    }

}

