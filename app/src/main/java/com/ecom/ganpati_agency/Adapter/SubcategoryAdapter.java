package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.CategoryWiseProductActivity;
import com.ecom.ganpati_agency.Model.response.SubcatRe;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;


import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    Context context;
    CategoryWiseProductActivity categoryWiseProductActivity;
    List<SubcatRe> subCategoryList;

    public SubcategoryAdapter(Context context, CategoryWiseProductActivity categoryWiseProductActivity, List<SubcatRe> subCategoryList) {
        this.context = context;
        this.categoryWiseProductActivity = categoryWiseProductActivity;
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public SubcategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_category_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryAdapter.ViewHolder holder, int position) {
        SubcatRe subCategory = subCategoryList.get(holder.getAdapterPosition());
        holder.txtCategoryName.setText(subCategory.getName());
        if (!subCategory.getImagePath().equals("")) {
            Glide.with(context)
                    .load(Constant.IMAGE_URL + subCategory.getImagePath())
                    .into(holder.imgCategory);
        } else {
            Glide.with(context)
                    .load(R.drawable.noimg)
                    .into(holder.imgCategory);
        }

//        holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                categoryWiseProductActivity.txtSubCategoryName.setText(subCategory.getName());
//                categoryWiseProductActivity.subcat_id = subCategory.getSubcatId();
//                Log.e("SUBCAT ID", categoryWiseProductActivity.subcat_id);
//                categoryWiseProductActivity.getCategoryRelatedProduct(categoryWiseProductActivity.cat_id
//                        , subCategory.getSubcatId()
//                        , categoryWiseProductActivity.shop_id);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView categoryLayout;
        ImageView imgCategory;
        TextView txtCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            categoryLayout = itemView.findViewById(R.id.categoryLayout);
        }
    }
}
