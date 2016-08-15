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
import com.valevich.balinasofttest.eventbus.events.MealSelectedEvent;
import com.valevich.balinasofttest.storage.data.Meal;
import com.valevich.balinasofttest.ui.activities.DetailsActivity_;
import com.valevich.balinasofttest.ui.recyclerview.adapters.MealsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_meals)
public class MealsFragment extends Fragment {

    private static final int MEALS_LOADER_ID = 1;

    @ViewById(R.id.meals_list)
    RecyclerView mMealsRecyclerView;

    @ViewById(R.id.empty_view)
    TextView mEmptyView;

    @FragmentArg
    String mCategoryName;

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

    @Subscribe
    public void onMealSelected(MealSelectedEvent event) {
        Meal meal = event.getMeal();
        if (meal != null)
            DetailsActivity_.intent(getContext())
                    .name(meal.getName())
                    .description(meal.getDescription())
                    .price(meal.getPrice())
                    .weight(meal.getWeight())
                    .imageUrl(meal.getImageUrl())
                    .start();
    }

    private void setupRecyclerView() {
        mMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadMeals() {

        getLoaderManager().restartLoader(MEALS_LOADER_ID,
                null,
                new LoaderManager.LoaderCallbacks() {
                    @Override
                    public Loader onCreateLoader(int id, Bundle args) {
                        final AsyncTaskLoader loader = new AsyncTaskLoader(getActivity()) {
                            @Override
                            public Object loadInBackground() {
                                mMealsAdapter.initAdapter(mCategoryName);
                                return null;
                            }
                        };
                        loader.forceLoad();
                        return loader;
                    }

                    @Override
                    public void onLoadFinished(Loader loader, Object data) {
                        mMealsRecyclerView.setAdapter(mMealsAdapter);
                        showListIfNotEmpty();
                    }

                    @Override
                    public void onLoaderReset(Loader loader) {

                    }
                });
    }

    private void showListIfNotEmpty() {
        if (mMealsAdapter.getItemCount() == 0) {
            mMealsRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mMealsRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

}
