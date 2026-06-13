package org.technischools.shop.config;

import java.text.DecimalFormat;

public class PriceFormatter {
    private final DecimalFormat formatter = new DecimalFormat("#,##0.00");

    public String format(double price) {
        return formatter.format(price) + " PLN";
    }
}
