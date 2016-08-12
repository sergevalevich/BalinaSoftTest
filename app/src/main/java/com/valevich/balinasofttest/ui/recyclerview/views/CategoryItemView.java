package com.valevich.balinasofttest.ui.recyclerview.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EViewGroup(R.layout.category_list_item)
public class CategoryItemView extends LinearLayout implements ViewBinder<Category> {

    @ViewById(R.id.name)
    TextView mCategoryNameLabel;

    @ViewById(R.id.icon)
    ImageView mCategoryIcon;

    public CategoryItemView(Context context) {
        super(context);
    }

    @Override
    public void bindData(Category item) {
        bindName("FOOD");
        bindIcon(item.getIconResourceId());
    }

    private void bindName(String name) {
        mCategoryNameLabel.setText(name);
    }

    private void bindIcon(int id) {
        // TODO: 11.08.2016  load image
        Glide.with(getContext())
                .load(id)
                .asBitmap()
                .placeholder(R.drawable.category_placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(mCategoryIcon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mCategoryIcon.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}
