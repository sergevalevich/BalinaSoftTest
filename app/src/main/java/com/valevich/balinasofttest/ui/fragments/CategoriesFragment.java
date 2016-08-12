package com.valevich.balinasofttest.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.ui.recyclerview.adapters.CategoriesAdapter;
import com.valevich.balinasofttest.utils.ConstantsManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_categories)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.categories_list)
    RecyclerView mCategoriesRecyclerView;

    @Bean
    CategoriesAdapter mCategoriesAdapter;

    @Override
    public void onResume() {
        super.onResume();
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
        getLoaderManager().restartLoader(ConstantsManager.CATEGORIES_LOADER_ID,
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
                    }

                    @Override
                    public void onLoaderReset(Loader loader) {

                    }
                });
    }
}
