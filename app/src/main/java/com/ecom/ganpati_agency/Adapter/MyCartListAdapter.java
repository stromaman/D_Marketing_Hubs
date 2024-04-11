package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ganpati_agency.Activity.MyCartActivity;
import com.ecom.ganpati_agency.Model.response.Cart;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.utils.Constant;
import com.ecom.ganpati_agency.utils.InternetValidation;
import com.ecom.ganpati_agency.utils.MyPreferences;
import com.skydoves.elasticviews.ElasticCardView;
import com.skydoves.elasticviews.ElasticLayout;

import java.util.List;

public class MyCartListAdapter extends RecyclerView.Adapter<MyCartListAdapter.ViewHolder> {

    Context context;
    MyCartActivity myCartActivity;
    List<Cart> cartList;

    public MyCartListAdapter(Context context, MyCartActivity myCartActivity, List<Cart> cartList) {
        this.context = context;
        this.myCartActivity = myCartActivity;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Cart cart = cartList.get(holder.getAdapterPosition());
        MyPreferences preferences = MyPreferences.getInstance(context);
        holder.productDiscount.setPaintFlags(holder.productDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productName.setText(cart.getProductdata().getProductName());
        holder.weight.setText(cart.getProductdata().getAttrValueNamefinal());
        holder.productAmount.setText("₹" + cart.getProductdata().getOnlinePrice());
        holder.productDiscount.setText("₹" + cart.getProductdata().getMrp());
        holder.txtCount.setText(cart.getQty());
        String Image_url = cart.getProductdata().getPath().
                replace("[", "")
                .replace("]", "")
                .replace("\\", "")
                .replace("\"", "");
        Log.e("URL", Image_url);
        if (!cart.getProductdata().getPath().equals("")) {
            Glide.with(context)
                    .load(Constant.IMAGE_URL + Image_url)
                    .into(holder.imgProduct);
        } else {
            Glide.with(context)
                    .load(R.drawable.noimg)
                    .into(holder.imgProduct);
        }

        if (!cart.getProductdata().getAttrDiscount().equals("")) {
            holder.offCard.setVisibility(View.VISIBLE);
            holder.txtOff.setText((int) (Double.parseDouble(cart.getProductdata().getAttrDiscount())) + "% OFF");
        }

        holder.plusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(cart.getQty());
                int newQuantity = quantity + 1;
                Log.e("QUANTITY", String.valueOf(quantity));
                Log.e("NEW QUANTITY", String.valueOf(newQuantity));
                Log.e("CART ID", cart.getId());
                if (InternetValidation.validation(myCartActivity)) {
                    myCartActivity.updateCartQuantity(cart.getId()
                            , String.valueOf(newQuantity)
                            , preferences.getUserToken());
                } else {
                    InternetValidation.getNoConnectionDialog(myCartActivity);
                }
            }
        });

        holder.minusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(cart.getQty());
                int newQuantity = quantity - 1;
                Log.e("QUANTITY", String.valueOf(quantity));
                Log.e("NEW QUANTITY", String.valueOf(newQuantity));
                Log.e("CART ID", cart.getId());
                if (quantity > 1) {
                    if (InternetValidation.validation(myCartActivity)) {
                        myCartActivity.updateCartQuantity(cart.getId()
                                , String.valueOf(newQuantity)
                                , preferences.getUserToken());
                    } else {
                        InternetValidation.getNoConnectionDialog(myCartActivity);
                    }
                } else {
                    if (InternetValidation.validation(myCartActivity)) {
                        myCartActivity.deleteCartItem(cart.getId());
                    } else {
                        InternetValidation.getNoConnectionDialog(myCartActivity);
                    }
                }
            }
        });

        holder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetValidation.validation(myCartActivity)) {
                    myCartActivity.deleteCartItem(cart.getId());
                } else {
                    InternetValidation.getNoConnectionDialog(myCartActivity);
                }
            }
        });


        holder.cartCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.e("PRODUCT ID", "" + cart.getProductdata().getId());

                    if (InternetValidation.validation(myCartActivity)) {
                        myCartActivity.productIdList.add(cart.getId());
                    } else {
                        InternetValidation.getNoConnectionDialog(myCartActivity);
                    }

                    myCartActivity.sellingPrice += (Double.parseDouble(cart.getProductdata().getOnlinePrice()) * Integer.parseInt(cart.getQty()));
                    myCartActivity.mrp += (Double.parseDouble(cart.getProductdata().getMrp()) * Integer.parseInt(cart.getQty()));
                    myCartActivity.totalProduct += Integer.parseInt(cart.getQty());

                    updateDeliveryCharge(cart.getProductdata().getDeliveryCharge(), myCartActivity);
                } else {
                    Log.e("PRODUCT ID", "" + cart.getProductdata().getId());

                    if (InternetValidation.validation(myCartActivity)) {
                        myCartActivity.productIdList.remove(cart.getId());
                    } else {
                        InternetValidation.getNoConnectionDialog(myCartActivity);
                    }

                    myCartActivity.sellingPrice -= (Double.parseDouble(cart.getProductdata().getOnlinePrice()) * Integer.parseInt(cart.getQty()));
                    myCartActivity.mrp -= (Double.parseDouble(cart.getProductdata().getMrp()) * Integer.parseInt(cart.getQty()));
                    myCartActivity.totalProduct -= Integer.parseInt(cart.getQty());

                    // Recalculate delivery charge when removing items
                    updateDeliveryCharge(null, myCartActivity);
                }
            }
        });

    }

//    private void updateDeliveryCharge(String currentDeliveryCharge, MyCartActivity myCartActivity) {
//        if (currentDeliveryCharge != null) {
//            double productDeliveryCharge = Double.parseDouble(currentDeliveryCharge);
//            myCartActivity.deliveryCharge = productDeliveryCharge;
//        } else {
//            // Set deliveryCharge to a fixed value of 0.0 or leave it unchanged
//            // For example:
//            // myCartActivity.deliveryCharge = 0.0;
//            // OR
//            // Leave it unchanged:
//            // myCartActivity.deliveryCharge = myCartActivity.deliveryCharge;
//        }
//    }

    private void updateDeliveryCharge(String currentDeliveryCharge, MyCartActivity myCartActivity) {
        if (currentDeliveryCharge != null) {
            double productDeliveryCharge = Double.parseDouble(currentDeliveryCharge);

            // If only a single product is selected, set deliveryCharge to the delivery charge of that product
            if (myCartActivity.productIdList.size() == 1) {
                myCartActivity.deliveryCharge = productDeliveryCharge;
            } else {
                // For multiple products, set deliveryCharge to the maximum value
                myCartActivity.deliveryCharge = Math.max(productDeliveryCharge, myCartActivity.deliveryCharge);
            }
        } else {
            // Set deliveryCharge to a fixed value of 0.0 or leave it unchanged
            // For example:
            // myCartActivity.deliveryCharge = 0.0;
            // OR
            // Leave it unchanged:
            // myCartActivity.deliveryCharge = myCartActivity.deliveryCharge;
        }
    }







    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        CardView offCard;
        ElasticLayout minusLayout, plusLayout;
        RelativeLayout notiLayout;
        ElasticCardView deleteCartItem;
        TextView productName, weight, txtOff, productAmount, productDiscount;
        EditText txtCount;
        CheckBox cartCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            productName = itemView.findViewById(R.id.productName);
            weight = itemView.findViewById(R.id.weight);
            txtOff = itemView.findViewById(R.id.txtOff);
            productAmount = itemView.findViewById(R.id.productAmount);
            productDiscount = itemView.findViewById(R.id.productDiscount);
            txtCount = itemView.findViewById(R.id.txtCount);
            minusLayout = itemView.findViewById(R.id.minusLayout);
            plusLayout = itemView.findViewById(R.id.plusLayout);
            notiLayout = itemView.findViewById(R.id.notiLayout);
            deleteCartItem = itemView.findViewById(R.id.deleteCartItem);
            offCard = itemView.findViewById(R.id.offCard);
            cartCheckBox = itemView.findViewById(R.id.cartCheckBox);

            offCard.setVisibility(View.GONE);

        }
    }

}

