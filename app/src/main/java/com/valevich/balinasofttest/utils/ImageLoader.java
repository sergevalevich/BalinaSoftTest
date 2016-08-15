package com.valevich.balinasofttest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.valevich.balinasofttest.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ImageLoader {

    @RootContext
    Context mContext;

    public void loadRoundedImageByResId(int resId, final ImageView imageView) {
        Glide.with(mContext)
                .load(resId)
                .asBitmap()
                .thumbnail(0.1f)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public void loadImageByUrl(String imageUrl, ImageView imageView) {
        Glide.with(mContext)
                .load(imageUrl)
                .thumbnail(0.1f)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder_wide)
                .crossFade()
                .into(imageView);
    }
}
