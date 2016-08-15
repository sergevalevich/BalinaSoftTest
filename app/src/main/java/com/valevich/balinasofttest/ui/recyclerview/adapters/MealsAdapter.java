package com.valevich.balinasofttest.ui.recyclerview.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.MealSelectedEvent;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.ui.recyclerview.ViewWrapper;
import com.valevich.balinasofttest.ui.recyclerview.views.MealsItemView;
import com.valevich.balinasofttest.ui.recyclerview.views.MealsItemView_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class MealsAdapter
        extends RecyclerViewAdapterBase<Meal,MealsItemView> {

    @RootContext
    Context mContext;

    @Bean
    EventBus mEventBus;

    public void initAdapter(String categoryName) {
        Category category = Category.get(categoryName);
        if(category != null) mItems = category.getMeals();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MealsItemView> holder, int position) {

        MealsItemView itemView = holder.getView();
        Meal meal = mItems.get(position);
        itemView.bindData(meal);

        setItemClickNotification(itemView,meal);

    }

    @Override
    protected MealsItemView onCreateItemView(ViewGroup parent, int viewType) {
        return MealsItemView_.build(mContext);
    }

    private void setItemClickNotification(MealsItemView view, final Meal meal){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyMealSelected(meal);
            }
        });
    }

    private void notifyMealSelected(Meal meal) {
        mEventBus.post(new MealSelectedEvent(meal));
    }

}
