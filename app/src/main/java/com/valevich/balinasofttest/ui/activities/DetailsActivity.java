package com.valevich.balinasofttest.ui.activities;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.TextView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.utils.Formatter;
import com.valevich.balinasofttest.utils.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity {

    @Extra
    @InstanceState
    String name;

    @Extra
    @InstanceState
    String description;

    @Extra
    @InstanceState
    String weight;

    @Extra
    @InstanceState
    String price;

    @Extra
    @InstanceState
    String imageUrl;

    @ColorRes(android.R.color.transparent)
    int mExpandedTitleColor;

    @ViewById(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mToolbarLayout;

    @ViewById(R.id.image)
    ImageView mImageView;

    @ViewById(R.id.description)
    TextView mDescriptionLabel;

    @ViewById(R.id.price)
    TextView mPriceLabel;

    @ViewById(R.id.weight)
    TextView mWeightLabel;

    @ViewById(R.id.name)
    TextView mNameLabel;

    @Bean
    ImageLoader mImageLoader;

    @Bean
    Formatter mFormatter;

    @AfterViews
    void setUpViews() {
        initActivityTransitions();
        supportPostponeEnterTransition();

        setupActionBar();
        setUpImageView();
        setUpName();
        setUpDescription();
        setUpPrice();
        setUpWeight();
    }

    private void setUpImageView() {
        mImageLoader.loadImageByUrl(imageUrl,mImageView);
    }

    private void setUpName() {
        mNameLabel.setText(name);
    }

    private void setUpDescription() {
        mDescriptionLabel.setText(mFormatter.formatDescription(description));
    }

    private void setUpPrice() {
        mPriceLabel.setText(mFormatter.formatPrice(price));
    }

    private void setUpWeight() {
        mWeightLabel.setText(mFormatter.formatWeight(weight));
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            setTitle("");
        }
        mToolbarLayout.setExpandedTitleColor(mExpandedTitleColor);
    }

}
