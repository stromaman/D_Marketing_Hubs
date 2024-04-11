package com.ecom.ganpati_agency.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ecom.ganpati_agency.R;
import com.google.android.material.button.MaterialButton;

public class InternetValidation {

    public static boolean validation(Context context) {
        Boolean isErrorOccurred = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            isErrorOccurred = true;
        }
        return isErrorOccurred;
    }

    public static Dialog connectionDialog;

    public static void getNoConnectionDialog(Context context){
        connectionDialog = new Dialog(context);
        connectionDialog.setContentView(R.layout.custom_internet_check_dialog);
        connectionDialog.setCancelable(true);

        MaterialButton btnOk = connectionDialog.findViewById(R.id.btnTry);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetValidation.validation(context)){
                    connectionDialog.dismiss();
                }else{
                    Toast.makeText(context, "Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        connectionDialog.show();
        connectionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
