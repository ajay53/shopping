package com.example.shopping.utility;

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
}
