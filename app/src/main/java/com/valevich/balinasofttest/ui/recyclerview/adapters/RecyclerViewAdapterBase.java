package com.valevich.balinasofttest.ui.recyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.balinasofttest.ui.recyclerview.ViewWrapper;
import com.valevich.balinasofttest.ui.recyclerview.utils.ViewBinder;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View & ViewBinder<T>>
        extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> mItems = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

}
