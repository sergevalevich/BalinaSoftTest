package com.valevich.balinasofttest.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.CatalogSavedEvent;
import com.valevich.balinasofttest.ui.recyclerview.adapters.CategoriesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

    private static final int CATEGORIES_LOADER_ID = 0;

    @ViewById(R.id.categories_list)
    RecyclerView mCategoriesRecyclerView;

    @ViewById(R.id.empty_view)
    TextView mEmptyView;

    @Bean
    CategoriesAdapter mCategoriesAdapter;

    @Bean
    EventBus mEventBus;

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategories();
    }

    @Subscribe
    public void onCatalogSaved(CatalogSavedEvent event) {
        loadCategories();
    }

    @AfterViews
    void setupViews() {
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadCategories() {
        getLoaderManager().restartLoader(CATEGORIES_LOADER_ID,
                null,
                new LoaderManager.LoaderCallbacks() {
                    @Override
                    public Loader onCreateLoader(int id, Bundle args) {
                        final AsyncTaskLoader loader = new AsyncTaskLoader(getActivity()) {
                            @Override
                            public Object loadInBackground() {
                                mCategoriesAdapter.initAdapter();
                                return null;
                            }
                        };
                        loader.forceLoad();
                        return loader;
                    }

                    @Override
                    public void onLoadFinished(Loader loader, Object data) {
                        mCategoriesRecyclerView.setAdapter(mCategoriesAdapter);
                        showListIfNotEmpty();
                    }

                    @Override
                    public void onLoaderReset(Loader loader) {

                    }
                });
    }

    private void showListIfNotEmpty() {
        if (mCategoriesAdapter.getItemCount() == 0) {
            mCategoriesRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mCategoriesRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }
}
