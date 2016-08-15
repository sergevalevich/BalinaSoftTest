package com.valevich.balinasofttest.ui.recyclerview.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;
import com.valevich.balinasofttest.utils.Formatter;
import com.valevich.balinasofttest.utils.ImageLoader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.meals_list_item)
public class MealsItemView extends LinearLayout implements ViewBinder<Meal> {

    @ViewById(R.id.picture)
    ImageView mPicture;

    @ViewById(R.id.name)
    TextView mNameLabel;

    @ViewById(R.id.weight)
    TextView mWeightLabel;

    @ViewById(R.id.price)
    TextView mPriceLabel;

    @Bean
    ImageLoader mImageLoader;

    @Bean
    Formatter mFormatter;

    public MealsItemView(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bindData(Meal item) {
        mImageLoader.loadImageByUrl(item.getImageUrl(),mPicture);
        mNameLabel.setText(item.getName());
        mWeightLabel.setText(mFormatter.formatWeight(item.getWeight()));
        mPriceLabel.setText(mFormatter.formatPrice(item.getPrice()));
    }

}
