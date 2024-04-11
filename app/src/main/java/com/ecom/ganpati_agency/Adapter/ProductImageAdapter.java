package com.ecom.ganpati_agency.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.ProductDetailsActivity;
import com.ecom.ganpati_agency.Model.response.Image;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {

    List<Image> imageList;
    ProductDetailsActivity productDetailsActivity;

    public ProductImageAdapter(List<Image> imageList, ProductDetailsActivity productDetailsActivity) {
        this.imageList = imageList;
        this.productDetailsActivity = productDetailsActivity;
    }

    /*public ProductImageAdapter(List<SliderModel> headerImageList, ProductDetailsActivity productDetailsActivity) {
        this.headerImageList = headerImageList;
        this.productDetailsActivity = productDetailsActivity;
    }*/

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imgProduct;
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.custom_product_image_layout, container, false);
        imgProduct = view.findViewById(R.id.imgProduct);
        Glide.with(productDetailsActivity)
                .load(Constant.IMAGE_URL + imageList.get(position).getImg())
                .into(imgProduct);
        /*imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Image"+position,Snackbar.LENGTH_LONG).show();
            }
        });*/
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productDetailsActivity.imageDialog(imageList.get(position).getImg());
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
