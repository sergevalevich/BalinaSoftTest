package com.valevich.balinasofttest.ui.recyclerview.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Map;

@EViewGroup(R.layout.contact_list_item)
public class ContactItemView extends LinearLayout implements ViewBinder<Map.Entry<String,String>> {

    @ViewById(R.id.icon)
    ImageView mIcon;

    @ViewById(R.id.address)
    TextView mAddressLabel;

    @ViewById(R.id.phone_number)
    TextView mPhoneNumberLabel;

    public ContactItemView(Context context) {
        super(context);
    }

    @Override
    public void bindData(Map.Entry<String, String> item) {
        mAddressLabel.setText(item.getKey());
        mPhoneNumberLabel.setText(item.getValue());
    }

}
