package org.mphasis.accounts;

import java.text.DecimalFormat;

public class Utility {
    public static double roundToPenny(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(Math.round(value * 100) / 100.0));
    }
}
