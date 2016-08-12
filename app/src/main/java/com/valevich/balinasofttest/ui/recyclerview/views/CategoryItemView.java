package com.valevich.balinasofttest.ui.recyclerview.views;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.storage.data.CategoryEntry;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.category_list_item)
public class CategoryItemView extends LinearLayout implements ViewBinder<CategoryEntry> {

    @ViewById(R.id.name)
    TextView mCategoryNameLabel;

    @ViewById(R.id.icon)
    ImageView mCategoryIcon;

    public CategoryItemView(Context context) {
        super(context);
    }

    @Override
    public void bindData(CategoryEntry item) {

    }

    private void bindName(String name) {
        mCategoryNameLabel.setText(name);
    }

    private void bindIcon(String icon) {
        // TODO: 11.08.2016  load image
    }
}
