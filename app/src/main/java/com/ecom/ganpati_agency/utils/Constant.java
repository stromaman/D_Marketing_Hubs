package com.ecom.ganpati_agency.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {
    public final static String BASE_URL= "https://demo.bssranchi.in/ganpatiagency/api/";
    public static String IMAGE_URL = "https://demo.bssranchi.in/ganpatiagency";


    public static String[] getFormattedTime(String dateTime) {
        String newDate = dateTime;
        String day = "";
        String[] arr = {};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date2 = formatter.parse(newDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy hh:mm a");
            day = dateFormat.format(date2);
            System.out.println(day.substring(0, 12) + " at " + day.substring(13));
            arr = new String[]{day.substring(0, 12), day.substring(13)};
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return arr;

    }
}
