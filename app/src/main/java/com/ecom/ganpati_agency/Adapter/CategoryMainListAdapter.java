package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Activity.DashboardActivity;
import com.ecom.ganpati_agency.Activity.CategoryWiseProductActivity;
import com.ecom.ganpati_agency.Model.response.Dashboard;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.InternetValidation;

import java.util.List;

public class CategoryMainListAdapter extends RecyclerView.Adapter<CategoryMainListAdapter.ViewHolder>{
    Context context;
    DashboardActivity dashboardActivity;
    List<Dashboard> dashboardList;

    public CategoryMainListAdapter(Context context, DashboardActivity dashboardActivity, List<Dashboard> dashboardList) {
        this.context = context;
        this.dashboardActivity = dashboardActivity;
        this.dashboardList = dashboardList;
    }

    @NonNull
    @Override
    public CategoryMainListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_products_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMainListAdapter.ViewHolder holder, int position) {
        Dashboard dashboard = dashboardList.get(holder.getAdapterPosition());
        if(!dashboard.getProducts().isEmpty()){
            holder.parentLayout.setVisibility(View.VISIBLE);
            holder.txtCategoryName.setText(dashboard.getCatName());
            ProductListAdapter productListAdapter = new ProductListAdapter(context,dashboardActivity,dashboard.getProducts(),dashboard.getCatName());
            holder.productsRecycler.setAdapter(productListAdapter);
        }

        holder.seeMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), CategoryWiseProductActivity.class);
                intent.putExtra("cat_id",dashboard.getCatId());
                intent.putExtra("cat_name",dashboard.getCatName());
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
        return dashboardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView productsRecycler;
        TextView txtCategoryName;
        LinearLayout parentLayout, seeMoreLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            productsRecycler = itemView.findViewById(R.id.productsRecycler);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            seeMoreLayout = itemView.findViewById(R.id.seeMoreLayout);

            parentLayout.setVisibility(View.GONE);
            productsRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }
    }
}
