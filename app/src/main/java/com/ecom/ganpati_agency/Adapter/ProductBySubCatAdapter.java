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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.DirectBuyActivity;
import com.ecom.ganpati_agency.Activity.ProductDetailsActivity;
import com.ecom.ganpati_agency.Activity.CategoryWiseProductActivity;
import com.ecom.ganpati_agency.Model.response.Product;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;

import java.util.List;

public class ProductBySubCatAdapter extends RecyclerView.Adapter<ProductBySubCatAdapter.ViewHolder> {

    Context context;
    CategoryWiseProductActivity categoryWiseProductActivity;
    List<Product> productList;

    public ProductBySubCatAdapter(Context context, CategoryWiseProductActivity categoryWiseProductActivity, List<Product> productList) {
        this.context = context;
        this.categoryWiseProductActivity = categoryWiseProductActivity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_allproduct_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = productList.get(holder.getAdapterPosition());
        holder.txtDiscountAmount.setPaintFlags(holder.txtDiscountAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        String Image_url = product.getPath().
                replace("[","")
                .replace("]","")
                .replace("\\","")
                .replace("\"","");
        Log.e("URL",Image_url);
        if(!product.getPath().equals("")){
            Glide.with(context)
                    .load(Constant.IMAGE_URL+Image_url)
                    .into(holder.imgProduct);
        }else{
            Glide.with(context)
                    .load(R.drawable.noimg)
                    .into(holder.imgProduct);
        }
        holder.txtProductName.setText(product.getProductName());
        holder.txtAmount.setText("₹"+product.getOnlinePrice());
        holder.txtDiscountAmount.setText("₹"+product.getMrp());
        holder.txtQty.setText(""+product.getAttrValueNamefinal());

        if(!product.getAttrDiscount().equals("")){
            holder.offCard.setVisibility(View.VISIBLE);
            holder.txtOff.setText((int)Double.parseDouble(product.getAttrDiscount())+"% OFF");
        }

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product_id",product.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(categoryWiseProductActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(categoryWiseProductActivity);
                }
            }
        });

        holder.detailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product_id",product.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(categoryWiseProductActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(categoryWiseProductActivity);
                }
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context.getApplicationContext(), DirectBuyActivity.class);
                intent.putExtra("product_name",product.getProductName());
                intent.putExtra("product_image",product.getPath());
                intent.putExtra("product_discount",product.getAttrDiscount());
                intent.putExtra("attr_valuefinal",product.getAttrValuefinal());
                intent.putExtra("quantity",product.getAttrValueNamefinal());
                intent.putExtra("attr_idfinal",product.getAttrIdfinal());
                intent.putExtra("shop_id",product.getShopId());
                intent.putExtra("shop_name",product.getShopName());
                intent.putExtra("product_id",product.getId());
                intent.putExtra("mrp",product.getMrp());
                intent.putExtra("discount_amount",product.getOnlinePrice());
                intent.putExtra("delivery_charge", product.getDeliveryCharge());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(categoryWiseProductActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(categoryWiseProductActivity);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgProduct;
        CardView offCard;
        LinearLayout addToCart, detailsLayout;
        TextView txtQty, txtOff, txtProductName, txtAmount, txtDiscountAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtOff = itemView.findViewById(R.id.txtOff);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDiscountAmount = itemView.findViewById(R.id.txtDiscountAmount);
            offCard = itemView.findViewById(R.id.offCard);
            addToCart = itemView.findViewById(R.id.addToCart);
            detailsLayout = itemView.findViewById(R.id.detailsLayout);

            offCard.setVisibility(View.GONE);
        }
    }

}

