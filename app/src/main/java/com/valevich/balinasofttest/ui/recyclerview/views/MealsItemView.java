package com.valevich.balinasofttest.ui.recyclerview.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.meals_list_item)
public class MealsItemView extends CardView implements ViewBinder<Meal> {

    @ViewById(R.id.picture)
    ImageView mPicture;

    @ViewById(R.id.name)
    TextView mNameLabel;

    @ViewById(R.id.weight)
    TextView mWeightLabel;

    @ViewById(R.id.price)
    TextView mPriceLabel;

    @ViewById(R.id.description)
    TextView mDescriptionLabel;

    @ViewById(R.id.arrow)
    ImageView mDropDownArrow;

    @ViewById(R.id.expandableView)
    ScrollView mExpandableView;

    public MealsItemView(Context context) {
        super(context);
    }

    @Override
    public void bindData(Meal item) {

    }

    public ScrollView getExpandableView() {
        return mExpandableView;
    }

    public ImageView getDropDownArrow() {
        return mDropDownArrow;
    }

}
