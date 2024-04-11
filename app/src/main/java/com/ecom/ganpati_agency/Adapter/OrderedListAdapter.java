package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Activity.OrderHistoryActivity;
import com.ecom.ganpati_agency.Activity.OrderTrackingActivity;
import com.ecom.ganpati_agency.Model.response.OrderedProduct;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;

import java.util.List;

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListAdapter.ViewHolder> {

    Context context;
    OrderHistoryActivity orderHistoryActivity;
    List<OrderedProduct> orderedProductList;

    public OrderedListAdapter(Context context, OrderHistoryActivity orderHistoryActivity, List<OrderedProduct> orderedProductList) {
        this.context = context;
        this.orderHistoryActivity = orderHistoryActivity;
        this.orderedProductList = orderedProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_order_history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderedProduct orderedProduct = orderedProductList.get(holder.getAdapterPosition());
        String[] timeArr = Constant.getFormattedTime(orderedProduct.getAddedOn());
        holder.txtOrderDate.setText(timeArr[0] + ", " + timeArr[1]);
        holder.txtOrderId.setText(orderedProduct.getOrderNo());
        holder.txtOrderAmount.setText("₹" + orderedProduct.getOrderTotal());
        holder.shopName.setText("On " + orderedProduct.getShopName());

        if (Double.parseDouble(orderedProduct.getDeliveryRate()) == 0) {
            holder.deliveryLayout.setVisibility(View.GONE);
        } else {
            holder.deliveryLayout.setVisibility(View.VISIBLE);
            holder.txtDeliveryCharge.setText("₹" + orderedProduct.getDeliveryRate());
        }
        if (orderedProduct.getDeliveryStatus().equals("4")) {
            holder.tickCard.setVisibility(View.VISIBLE);
        } else {
            holder.tickCard.setVisibility(View.GONE);
        }
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), OrderTrackingActivity.class);
                intent.putExtra("order_id", orderedProduct.getId());
                intent.putExtra("shop_name", orderedProduct.getShopName());
                intent.putExtra("order_date", timeArr[0] + ", " + timeArr[1]);
                intent.putExtra("order_no", orderedProduct.getOrderNo());
                intent.putExtra("status", orderedProduct.getDeliveryStatus());
                intent.putExtra("address", orderedProduct.getDeliveryAddress() + ", " + orderedProduct.getDistrictName() + ", " + orderedProduct.getStateName() + "-" + orderedProduct.getDeliveryPincode());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (InternetValidation.validation(orderHistoryActivity)) {
                    context.startActivity(intent);
                } else {
                    InternetValidation.getNoConnectionDialog(orderHistoryActivity);
                }
            }
        });

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderHistoryActivity.showActions(holder.imgMenu, orderedProduct.getId());
            }
        });

        if(orderedProduct.getPaymentMethod().equals("1")){
            holder.paymethod.setText("Online");
        }else{
            holder.paymethod.setText("Cash");
        }

        if(orderedProduct.getPaymentStatus().equals("1")){
            holder.paystatus.setText("Paid");
            holder.paystatus.setTextColor(Color.parseColor("#4CAF50"));
        }else{
            holder.paystatus.setText("Pending");
            holder.paystatus.setTextColor(Color.parseColor("#FA2111"));
        }

    }

    @Override
    public int getItemCount() {
        return orderedProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtOrderId, txtOrderDate, txtOrderAmount, shopName, txtDeliveryCharge, paystatus, paymethod;
        ImageView imgMenu;
        CardView tickCard;
        LinearLayout deliveryLayout;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtOrderAmount = itemView.findViewById(R.id.txtOrderAmount);
            tickCard = itemView.findViewById(R.id.tickCard);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            shopName = itemView.findViewById(R.id.shopName);
            txtDeliveryCharge = itemView.findViewById(R.id.txtDeliveryCharge);
            deliveryLayout = itemView.findViewById(R.id.deliveryLayout);
            imgMenu = itemView.findViewById(R.id.imgMenu);
            paystatus = itemView.findViewById(R.id.paystatus);
            paymethod = itemView.findViewById(R.id.paymethod);

            tickCard.setVisibility(View.GONE);
            deliveryLayout.setVisibility(View.GONE);

        }
    }

}

