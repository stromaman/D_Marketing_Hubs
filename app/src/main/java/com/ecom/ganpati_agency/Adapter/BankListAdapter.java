package com.ecom.ganpati_agency.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.ganpati_agency.Model.response.Bank;
import com.ecom.ganpati_agency.R;
import com.ecom.ganpati_agency.seller.BankActivity;

import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder> {

    Context context;
    BankActivity bankActivity;
    List<Bank> bankList;

    public BankListAdapter(Context context, BankActivity bankActivity, List<Bank> bankList) {
        this.context = context;
        this.bankActivity = bankActivity;
        this.bankList = bankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_bank_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtCount.setText((position+1)+". ");
        Bank bank = bankList.get(holder.getAdapterPosition());
        holder.txtBankName.setText(bank.getName());
        holder.bankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankActivity.bankId = bank.getId();
                bankActivity.txtBank.setText(bank.getName());
                bankActivity.bankDialog.dismiss();
                Log.e("BANK ID", bankActivity.bankId);
                Log.e("BANK NAME", bank.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtBankName, txtCount;
        LinearLayout bankLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBankName = itemView.findViewById(R.id.txtBankName);
            bankLayout = itemView.findViewById(R.id.bankLayout);
            txtCount = itemView.findViewById(R.id.txtCount);

        }
    }

}
