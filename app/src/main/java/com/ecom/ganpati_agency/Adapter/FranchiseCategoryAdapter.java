package com.ecom.ganpati_agency.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.ULocale;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.FranchiseProductActivity;
import com.ecom.ganpati_agency.Model.response.CatRe;
import com.ecom.ganpati_agency.R;

import java.util.List;
import java.util.Locale;

public class FranchiseCategoryAdapter extends RecyclerView.Adapter<FranchiseCategoryAdapter.ViewHolder> {

    Context context;
    FranchiseProductActivity franchiseProductActivity;
    List<CatRe> categoryList;
    int row_index = -1;

    public FranchiseCategoryAdapter(Context context, FranchiseProductActivity franchiseProductActivity, List<CatRe> categoryList) {
        this.context = context;
        this.franchiseProductActivity = franchiseProductActivity;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_franchise_category_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CatRe category = categoryList.get(holder.getAdapterPosition());
        holder.txtCategoryName.setText(category.getName());

        holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                franchiseProductActivity.getProductByCategory(category.getId(), franchiseProductActivity.shop_id);
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.categoryLayout.setBackgroundResource(R.drawable.start_shopping_bg);
        } else {
            holder.categoryLayout.setBackgroundResource(R.drawable.frachise_category_bg);
        }

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategoryName;
        RelativeLayout categoryLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            categoryLayout = itemView.findViewById(R.id.categoryLayout);

        }
    }

}

