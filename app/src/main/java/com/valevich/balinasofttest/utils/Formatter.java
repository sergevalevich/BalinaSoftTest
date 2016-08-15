package com.valevich.balinasofttest.utils;

import com.valevich.balinasofttest.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

import java.text.DecimalFormat;

@EBean
public class Formatter {

    @StringRes(R.string.no_data_available_message)
    String mDataMissingMessage;

    @StringRes(R.string.description_unavailable_message)
    String mDescriptionMissingMessage;

    @StringRes(R.string.decimal_format)
    String mDecimalFormat;

    @StringRes(R.string.price_default_metrics)
    String mPriceMetrics;

    public String formatPrice(String priceString) {
        if (priceString == null || priceString.isEmpty()) {
            priceString = mDataMissingMessage;
        } else {
            priceString = formatDoubleString(priceString) + mPriceMetrics;
        }
        return priceString;
    }
    public String formatWeight(String weightString) {
        if(weightString == null || weightString.isEmpty()) {
            weightString = mDataMissingMessage;
        } else {
            weightString = formatWeightValue(weightString);
        }
        return weightString;
    }

    public String formatDescription(String description) {
        if(description == null || description.isEmpty()) description = mDescriptionMissingMessage;
        return description;
    }

    private String formatDoubleString(String s) {
        double d = Double.valueOf(s.replace(",", "."));
        if (d % 1 == 0) {
            s = new DecimalFormat(mDecimalFormat)
                    .format(d);
        }
        return s;
    }

    private String formatWeightValue(String weightString) {
        int valueMetricsBound = weightString.length() - 3;
        String metrics = weightString.substring(valueMetricsBound);
        String value = weightString.substring(0, valueMetricsBound);
        return formatDoubleString(value) + metrics;
    }

}
