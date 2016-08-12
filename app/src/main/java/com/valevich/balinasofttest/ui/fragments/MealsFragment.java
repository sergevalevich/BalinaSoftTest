package com.valevich.balinasofttest.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.valevich.balinasofttest.R;
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
    int categoryId;

    @AfterViews
    void setupViews() {
        setupRecyclerView();
    }

    @Bean
    MealsAdapter mMealsAdapter;

    @Override
    public void onResume() {
        super.onResume();
        loadMeals(categoryId);
    }

    private void setupRecyclerView() {
        mMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadMeals(final int categoryId) {

        getLoaderManager().restartLoader(ConstantsManager.MEALS_LOADER_ID,
                null,
                new LoaderManager.LoaderCallbacks() {
                    @Override
                    public Loader onCreateLoader(int id, Bundle args) {
                        final AsyncTaskLoader loader = new AsyncTaskLoader(getActivity()) {
                            @Override
                            public Object loadInBackground() {
                                mMealsAdapter.initAdapter(categoryId);
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