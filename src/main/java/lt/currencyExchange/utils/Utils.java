package lt.currencyExchange.utils;

import java.text.DecimalFormat;

public class Utils {

    public static final DecimalFormat df = new DecimalFormat("0.00");

    public static double convertStringToDoubleFromElement(String value) {
        return Double.parseDouble(value.trim().replaceAll("[()]", ""));
    }

}
