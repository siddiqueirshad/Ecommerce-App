package com.ecommerce.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {

    private static final NumberFormat INR_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    public static String format(BigDecimal amount) {
        if (amount == null) return format(BigDecimal.ZERO);
        return INR_FORMAT.format(amount);
    }

    public static String format(double amount) {
        return INR_FORMAT.format(amount);
    }
}
