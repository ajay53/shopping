package com.example.shopping.utility;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class Util {

    public static String getPriceFormattedString(double price) {
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        return "$ " + df.format(price);
    }

    public static String getTotalFormattedString(double price) {
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        return "Total: $ " + df.format(price);
    }

    public static void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}
