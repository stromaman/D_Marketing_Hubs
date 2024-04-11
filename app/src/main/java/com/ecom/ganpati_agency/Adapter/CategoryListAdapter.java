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
import com.ecom.ganpati_agency.Activity.CategoryWiseProductActivity;
import com.ecom.ganpati_agency.Model.response.CatRe;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;


import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{
    Context context;
    List<CatRe> catRes;
    DashboardActivity dashboardActivity;

    public CategoryListAdapter(Context context, DashboardActivity dashboardActivity, List<CatRe> catRes) {
        this.context = context;
        this.catRes =catRes;
        this.dashboardActivity=dashboardActivity;
    }


    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_category_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        CatRe resp = catRes.get(position);
        holder.txtCategoryName.setText(resp.getName());
        if(!resp.getImagePath().equals("")){
            Glide.with(context)
                    .load(Constant.IMAGE_URL+resp.getImagePath())
                    .into(holder.imgCategory);
        }else{
            Glide.with(context)
                    .load(R.drawable.noimg)
                    .into(holder.imgCategory);
        }

        holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), CategoryWiseProductActivity.class);
                intent.putExtra("cat_id",resp.getId());
                intent.putExtra("cat_name",resp.getName());
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
        return catRes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView categoryLayout;
        ImageView imgCategory;
        TextView txtCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryLayout = itemView.findViewById(R.id.categoryLayout);
            imgCategory=itemView.findViewById(R.id.imgCategory);
            txtCategoryName=itemView.findViewById(R.id.txtCategoryName);

        }
    }
}
