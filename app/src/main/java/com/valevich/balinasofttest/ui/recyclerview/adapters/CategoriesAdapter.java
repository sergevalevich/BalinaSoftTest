package com.valevich.balinasofttest.ui.recyclerview.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.CategorySelectedEvent;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.ui.recyclerview.ViewWrapper;
import com.valevich.balinasofttest.ui.recyclerview.views.CategoryItemView;
import com.valevich.balinasofttest.ui.recyclerview.views.CategoryItemView_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class CategoriesAdapter extends RecyclerViewAdapterBase<Category,CategoryItemView> {

    @RootContext
    Context mContext;

    @Bean
    EventBus mEventBus;

    public void initAdapter() {
        mItems = Category.getAll();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<CategoryItemView> holder, int position) {
        CategoryItemView view = holder.getView();
        Category category = mItems.get(position);
        view.bindData(category);

        setItemClickNotification(view,category);
    }

    @Override
    protected CategoryItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CategoryItemView_.build(mContext);
    }

    private void setItemClickNotification(CategoryItemView view, final Category category){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyCategorySelected(category.getName());
            }
        });
    }

    private void notifyCategorySelected(String categoryName) {
        mEventBus.post(new CategorySelectedEvent(categoryName));
    }
}
