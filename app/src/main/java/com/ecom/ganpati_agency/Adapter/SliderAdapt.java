package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.DashboardActivity;
import com.ecom.ganpati_agency.FranchiseProductActivity;
import com.ecom.ganpati_agency.Model.response.Banner;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;

import java.util.List;

public class SliderAdapt extends PagerAdapter {


        Context context;
        DashboardActivity dashboardActivity;
        FranchiseProductActivity franchiseProductActivity;
        List<Banner> headerImageList;

        public SliderAdapt(Context context, DashboardActivity dashboardActivity, FranchiseProductActivity franchiseProductActivity, List<Banner> headerImageList) {
            this.context = context;
            this.dashboardActivity = dashboardActivity;
            this.franchiseProductActivity = franchiseProductActivity;
            this.headerImageList = headerImageList;
        }

        @Override
        public int getCount() {
            return headerImageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imgHeader;
            Banner banner = headerImageList.get(position);

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.custom_slider_layout, container, false);
            imgHeader = view.findViewById(R.id.imgHeader);
            Glide.with(context)
                    .load(Constant.IMAGE_URL + banner.getImg())
                    .into(imgHeader);

        /*imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Image"+position,Snackbar.LENGTH_LONG).show();
            }
        });*/
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

}
