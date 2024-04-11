package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.DashboardActivity;
import com.ecom.ganpati_agency.FranchiseProductActivity;
import com.ecom.ganpati_agency.Model.response.Shop;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    Context context;
    DashboardActivity dashboardActivity;
    List<Shop> shopList;

    public ShopListAdapter(Context context, DashboardActivity dashboardActivity, List<Shop> shopList) {
        this.context = context;
        this.dashboardActivity = dashboardActivity;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_shop_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Shop shop = shopList.get(holder.getAdapterPosition());
        /*if(position==0){
            Glide.with(context)
                    .load(R.drawable.imgone)
                    .into(holder.shopImage);
        }else if(position==1){
            Glide.with(context)
                    .load(R.drawable.imgthree)
                    .into(holder.shopImage);
        }else if(position==2){
            Glide.with(context)
                    .load(R.drawable.imgtwo)
                    .into(holder.shopImage);
        }else if(position==3){
            Glide.with(context)
                    .load(R.drawable.imgfour)
                    .into(holder.shopImage);
        }*/
        holder.txtShopName.setText(shop.getName());

        String Image_url = shop.getImg().
                replace("[", "")
                .replace("]", "")
                .replace("\\", "")
                .replace("\"", "");

        if (!shop.getImg().equals("")) {
            Glide.with(context)
                    .load(Constant.IMAGE_URL + Image_url)
                    .into(holder.shopImage);
        } else {
            Glide.with(context)
                    .load(R.drawable.onlineshop)
                    .into(holder.shopImage);
        }

        holder.shopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), FranchiseProductActivity.class);
                intent.putExtra("shop_name",shop.getName());
                intent.putExtra("shop_id",shop.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(InternetValidation.validation(dashboardActivity)){
                    context.startActivity(intent);
                }else{
                    InternetValidation.getNoConnectionDialog(dashboardActivity);
                }


                /*MyPreferences.getInstance(context)
                        .saveShopDetails(shop.getId(),shop.getName());
                dashboardActivity.selectShopDialog.dismiss();
                dashboardActivity.getProductsList(MyPreferences.getInstance(context).getShopId());
                dashboardActivity.getCartList(MyPreferences.getInstance(context).getShopId(),MyPreferences.getInstance(context).getUserToken());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView shopImage;
        TextView txtShopName;
        CardView shopLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtShopName = itemView.findViewById(R.id.txtShopName);
            shopLayout = itemView.findViewById(R.id.shopLayout);
            shopImage = itemView.findViewById(R.id.shopImage);
        }
    }
}
