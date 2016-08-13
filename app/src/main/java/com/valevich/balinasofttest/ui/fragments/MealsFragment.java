package com.valevich.balinasofttest.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.otto.Subscribe;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.CatalogSavedEvent;
import com.valevich.balinasofttest.storage.data.Category;
import com.valevich.balinasofttest.ui.recyclerview.adapters.MealsAdapter;
import com.valevich.balinasofttest.utils.ConstantsManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_meals)
public class MealsFragment extends Fragment {

    @ViewById(R.id.meals_list)
    RecyclerView mMealsRecyclerView;

    @FragmentArg
    Category mCategory;

    @AfterViews
    void setupViews() {
        setupRecyclerView();
    }

    @Bean
    MealsAdapter mMealsAdapter;

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
        loadMeals();
    }

    @Subscribe
    public void onCatalogSaved(CatalogSavedEvent event) {
        loadMeals();
    }

    private void setupRecyclerView() {
        mMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadMeals() {

        getLoaderManager().restartLoader(ConstantsManager.MEALS_LOADER_ID,
                null,
                new LoaderManager.LoaderCallbacks() {
                    @Override
                    public Loader onCreateLoader(int id, Bundle args) {
                        final AsyncTaskLoader loader = new AsyncTaskLoader(getActivity()) {
                            @Override
                            public Object loadInBackground() {
                                mMealsAdapter.initAdapter(mCategory);
                                return null;
                            }
                        };
                        loader.forceLoad();
                        return loader;
                    }

                    @Override
                    public void onLoadFinished(Loader loader, Object data) {
                        mMealsRecyclerView.setAdapter(mMealsAdapter);
                    }

                    @Override
                    public void onLoaderReset(Loader loader) {

                    }
                });
    }

}
