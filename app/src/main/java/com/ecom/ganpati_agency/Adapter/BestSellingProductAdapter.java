package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.DashboardActivity;
import com.ecom.ganpati_agency.Activity.DirectBuyActivity;
import com.ecom.ganpati_agency.Activity.ProductDetailsActivity;
import com.ecom.ganpati_agency.Model.response.BestsellprodRe;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;

import java.util.List;

public class BestSellingProductAdapter extends RecyclerView.Adapter<BestSellingProductAdapter.ViewHolder>{
    Context context;
    DashboardActivity dashboardActivity;
    List<BestsellprodRe> bestsellprodRes;

    public BestSellingProductAdapter(Context context, DashboardActivity dashboardActivity, List<BestsellprodRe> bestsellprodRes) {
        this.context = context;
        this.dashboardActivity = dashboardActivity;
        this.bestsellprodRes = bestsellprodRes;
    }
    @NonNull
    @Override
    public BestSellingProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_product_main_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellingProductAdapter.ViewHolder holder, int position) {
        BestsellprodRe  product = bestsellprodRes.get(holder.getAdapterPosition());
        holder.productDiscount.setPaintFlags(holder.productDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productName.setText(product.getProductName());
        holder.productWeight.setText(product.getAttrValueNamefinal());
        holder.shopName.setText("By " + product.getShopName());
        holder.amount.setText("₹" + product.getOnlinePrice());
        holder.productDiscount.setText("₹" + product.getMrp());
        String Image_url = product.getPath().
                replace("[", "")
                .replace("]", "")
                .replace("\\", "")
                .replace("\"", "");
        Log.e("URL", Image_url);
        if (!product.getAttrDiscount().equals("")) {
            holder.offLayout.setVisibility(View.VISIBLE);
            holder.txtOff.setText((int) Double.parseDouble(product.getAttrDiscount()) + "% OFF");
        }
        if (!product.getPath().equals("")) {
            Glide.with(context)
                    .load(Constant.IMAGE_URL + Image_url)
                    .into(holder.imgProduct);
        } else {
            Glide.with(context)
                    .load(R.drawable.noimg)
                    .into(holder.imgProduct);
        }

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product_id", product.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(dashboardActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(dashboardActivity);
                }
            }
        });

        holder.detailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product_id", product.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(dashboardActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(dashboardActivity);
                }
            }
        });

        holder.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DirectBuyActivity.class);
                intent.putExtra("product_name", product.getProductName());
                intent.putExtra("product_image", product.getPath());
                intent.putExtra("product_discount", product.getAttrDiscount());
                intent.putExtra("attr_valuefinal", product.getAttrValuefinal());
                intent.putExtra("quantity", product.getAttrValueNamefinal());
                intent.putExtra("attr_idfinal", product.getAttrIdfinal());
                intent.putExtra("shop_id", product.getShopId());
                intent.putExtra("shop_name", product.getShopName());
                intent.putExtra("product_id", product.getId());
                intent.putExtra("mrp", product.getMrp());
                intent.putExtra("discount_amount", product.getOnlinePrice());
                intent.putExtra("delivery_charge", product.getDeliveryCharge());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(dashboardActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(dashboardActivity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bestsellprodRes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productDiscount, txtCount, productName, productWeight, amount, txtOff, shopName;
        ImageView imgProduct;
        LinearLayout addLayout, offLayout, detailsLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addLayout = itemView.findViewById(R.id.addLayout);
            txtCount = itemView.findViewById(R.id.txtCount);
            productName = itemView.findViewById(R.id.productName);
            productWeight = itemView.findViewById(R.id.productWeight);
            amount = itemView.findViewById(R.id.amount);
            productDiscount = itemView.findViewById(R.id.productDiscount);
            txtOff = itemView.findViewById(R.id.txtOff);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            offLayout = itemView.findViewById(R.id.offLayout);
            shopName = itemView.findViewById(R.id.shopName);
            detailsLayout = itemView.findViewById(R.id.detailsLayout);

            addLayout.setVisibility(View.VISIBLE);
            offLayout.setVisibility(View.GONE);
        }
    }
}
