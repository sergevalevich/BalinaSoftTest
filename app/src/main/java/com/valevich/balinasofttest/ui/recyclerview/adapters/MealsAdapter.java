package com.valevich.balinasofttest.ui.recyclerview.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.ui.recyclerview.ViewWrapper;
import com.valevich.balinasofttest.ui.recyclerview.views.MealsItemView;
import com.valevich.balinasofttest.ui.recyclerview.views.MealsItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class MealsAdapter
        extends RecyclerViewAdapterBase<Meal,MealsItemView> {

    @RootContext
    Context mContext;

    private int mExpandedItemPosition = -1;

    public void initAdapter(String categoryName) {
        Category category = Category.get(categoryName);
        if(category != null) mItems = category.getMeals();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MealsItemView> holder, int position) {

        MealsItemView itemView = holder.getView();
        Meal meal = mItems.get(position);
        itemView.bindData(meal);

        setUpExpandableArea(itemView,position);

    }

    @Override
    protected MealsItemView onCreateItemView(ViewGroup parent, int viewType) {
        return MealsItemView_.build(mContext);
    }

    private void setUpExpandableArea(MealsItemView view, final int position) {
        final boolean isExpanded = position == mExpandedItemPosition;
        view.getExpandableView().setVisibility(isExpanded?View.VISIBLE:View.GONE);

        final ImageView arrow = view.getDropDownArrow();
        arrow.setRotation(isExpanded ? 180 : 0);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExpandedItemPosition(position,isExpanded);
                arrow.startAnimation(getArrowAnimation());
            }
        });
    }

    private void setExpandedItemPosition(int position, boolean isExpanded) {
        mExpandedItemPosition = isExpanded ? -1 : position;
    }

    private Animation getArrowAnimation() {

        Animation arrowAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);

        arrowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return arrowAnimation;
    }

}
