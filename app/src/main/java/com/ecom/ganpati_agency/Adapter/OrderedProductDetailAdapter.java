package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.OrderTrackingActivity;
import com.ecom.ganpati_agency.Model.response.OrderedAllProduct;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;

import java.util.List;

public class OrderedProductDetailAdapter extends RecyclerView.Adapter<OrderedProductDetailAdapter.ViewHolder> {

    Context context;
    OrderTrackingActivity orderTrackingActivity;
    List<OrderedAllProduct> orderedAllProductList;

    public OrderedProductDetailAdapter(Context context, OrderTrackingActivity orderTrackingActivity, List<OrderedAllProduct> orderedAllProductList) {
        this.context = context;
        this.orderTrackingActivity = orderTrackingActivity;
        this.orderedAllProductList = orderedAllProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_order_tarcking_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderedAllProduct orderedAllProduct = orderedAllProductList.get(holder.getAdapterPosition());
        String Image_url = orderedAllProduct.getImage().
                replace("[","")
                .replace("]","")
                .replace("\\","")
                .replace("\"","");
        Log.e("URL",Image_url);
        if(!orderedAllProduct.getImage().equals("")){
            Glide.with(context)
                    .load(Constant.IMAGE_URL+Image_url)
                    .into(holder.imgProduct);
        }else{
            Glide.with(context)
                    .load(R.drawable.noimg)
                    .into(holder.imgProduct);
        }
        holder.txtProductName.setText(orderedAllProduct.getProductName());
        holder.txtWeight.setText(orderedAllProduct.getAttrValueName());
        holder.txtQty.setText(orderedAllProduct.getQuantity());
        holder.txtAmount.setText("₹"+orderedAllProduct.getPrice());
        holder.txtDeliveryCharge.setText("₹"+orderedAllProduct.getDeliveryCharge());
    }

    @Override
    public int getItemCount() {
        return orderedAllProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtProductName, txtWeight, txtQty, txtAmount, txtDeliveryCharge;
        ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtDeliveryCharge = itemView.findViewById(R.id.txtDeliveryCharge);

        }
    }

}

