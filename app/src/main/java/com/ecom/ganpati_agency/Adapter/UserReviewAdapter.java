package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Activity.ProductDetailsActivity;
import com.ecom.ganpati_agency.Model.response.UserReview;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;

import java.util.List;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.ViewHolder> {

    Context context;
    ProductDetailsActivity productDetailsActivity;
    List<UserReview> userReviewList;

    public UserReviewAdapter(Context context, ProductDetailsActivity productDetailsActivity, List<UserReview> userReviewList) {
        this.context = context;
        this.productDetailsActivity = productDetailsActivity;
        this.userReviewList = userReviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_users_review_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserReview review = userReviewList.get(holder.getAdapterPosition());
        String[] timeArr = Constant.getFormattedTime(review.getCreatedAt());
        holder.txtUser.setText(review.getName());
        holder.txtTime.setText(timeArr[0]+", "+timeArr[1]);
        holder.txtComment.setText("\""+review.getComment()+"\"");
        holder.ratingBar.setRating(Float.parseFloat(review.getRate()));

    }

    @Override
    public int getItemCount() {
        return userReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtUser, txtTime, txtComment;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUser = itemView.findViewById(R.id.txtUser);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtComment = itemView.findViewById(R.id.txtComment);
            ratingBar = itemView.findViewById(R.id.RatingBar);

        }
    }

}

