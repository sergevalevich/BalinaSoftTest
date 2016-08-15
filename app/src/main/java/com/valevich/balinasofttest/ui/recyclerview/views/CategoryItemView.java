package com.valevich.balinasofttest.ui.recyclerview.views;


import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;
import com.valevich.balinasofttest.utils.ImageLoader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.category_list_item)
public class CategoryItemView extends LinearLayout implements ViewBinder<Category> {

    @ViewById(R.id.name)
    TextView mCategoryNameLabel;

    @ViewById(R.id.icon)
    ImageView mCategoryIcon;

    @Bean
    ImageLoader mImageLoader;

    public CategoryItemView(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bindData(Category item) {
        mCategoryNameLabel.setText(item.getName());
        mImageLoader.loadRoundedImageByResId(item.getIconResourceId(),mCategoryIcon);
    }

}
