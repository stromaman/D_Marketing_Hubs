package com.ecom.ganpati_agency.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Model.response.BannerRe;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.smarteist.autoimageslider.SliderViewAdapter;


import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {


    List<BannerRe> mSliderItems;
    Context context;
    // Constructor
    public SliderAdapter(Context context, List<BannerRe> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
        this.context = context;
    }

    @Override
    public SliderAdapter.SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapter.SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapter.SliderAdapterViewHolder viewHolder, int position) {
        //viewHolder.imageViewBackground.setImageDrawable(context.getResources().getDrawable(mSliderItems.get(position).getBanner(), context.getTheme()));
        Glide.with(context).load(Constant.IMAGE_URL + mSliderItems.get(position).getImg()).into(viewHolder.imageViewBackground);
//        Picasso.get().load(Constant.IMAGE_URL + mSliderItems.get(position).getImg()).fit()
//                .into(viewHolder.imageViewBackground);
        Log.d("eeee", mSliderItems.get(position).getImg());
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public class SliderAdapterViewHolder extends ViewHolder {
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;
        }
    }
}


