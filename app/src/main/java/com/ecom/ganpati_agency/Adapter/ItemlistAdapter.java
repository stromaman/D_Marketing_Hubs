package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Model.ItemlistModel;
import com.ecom.ganpati_agency.R;

import java.util.List;

public class ItemlistAdapter extends RecyclerView.Adapter<ItemlistAdapter.ViewHolder> {
    Context context;
    List<ItemlistModel>list;

    public ItemlistAdapter(Context context, List<ItemlistModel>list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ItemlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_category_item,parent,false);
        return new ItemlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemlistAdapter.ViewHolder holder, int position) {
        ItemlistModel resp = list.get(position);
        holder.txtCategoryName.setText(resp.getName());
        holder.imgCategory.setImageResource(resp.getImage());
        holder.txtaboutCategory.setText(resp.getAbout());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView txtCategoryName,txtaboutCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory=itemView.findViewById(R.id.imgCategory);
            txtCategoryName=itemView.findViewById(R.id.txtCategoryName);
            txtaboutCategory=itemView.findViewById(R.id.txtaboutCategory);

        }
    }
}
