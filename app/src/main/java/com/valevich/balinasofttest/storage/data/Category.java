package com.valevich.balinasofttest.storage.data;

import com.valevich.balinasofttest.ui.recyclerview.utils.CategoriesFinder;
import com.valevich.balinasofttest.utils.StubConstants;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean
public class Category implements CategoriesFinder {

    private int mId;

    private String mName;

    @Override
    public List<Category> findAll() {
        ArrayList<Category> categories = new ArrayList<>();
        for(int i = 0; i<10; i++) {
            Category category = new Category();
            categories.add(category);
        }
        return categories;
    }

    public int getId() {
        return mId;
    }


    //giving stub static icons
    public int getIconResourceId() {
        switch (mName) {
            case StubConstants.STUB_CATEGORY_PIZZA:
                return StubConstants.STUB_CATEGORY_PIZZA_ICON;
            case StubConstants.STUB_CATEGORY_BARBECUE:
                return StubConstants.STUB_CATEGORY_BARBECUE_ICON;
            case StubConstants.STUB_CATEGORY_CONSTRUCTOR:
                return StubConstants.STUB_CATEGORY_CONSTRUCTOR_ICON;
            case StubConstants.STUB_CATEGORY_NOODLES:
                return StubConstants.STUB_CATEGORY_NOODLES_ICON;
            case StubConstants.STUB_CATEGORY_SETS:
                return StubConstants.STUB_CATEGORY_SETS_ICON;
            case StubConstants.STUB_CATEGORY_ROLLS:
                return StubConstants.STUB_CATEGORY_ROLLS_ICON;
            case StubConstants.STUB_CATEGORY_SUSHI:
                return StubConstants.STUB_CATEGORY_SUSHI_ICON;
            case StubConstants.STUB_CATEGORY_SOUPS:
                return StubConstants.STUB_CATEGORY_SOUPS_ICON;
            case StubConstants.STUB_CATEGORY_SUPPLEMENTS:
                return StubConstants.STUB_CATEGORY_SUPPLEMENTS_ICON;
            case StubConstants.STUB_CATEGORY_SALADS:
                return StubConstants.STUB_CATEGORY_SALADS_ICON;
            case StubConstants.STUB_CATEGORY_WARM:
                return StubConstants.STUB_CATEGORY_WARM_ICON;
            case StubConstants.STUB_CATEGORY_SNACKS:
                return StubConstants.STUB_CATEGORY_SNACKS_ICON;
            case StubConstants.STUB_CATEGORY_DESSERTS:
                return StubConstants.STUB_CATEGORY_DESSERTS_ICON;
            case StubConstants.STUB_CATEGORY_DRINKS:
                return StubConstants.STUB_CATEGORY_DRINKS_ICON;
        }
        return StubConstants.CATEGORY_PLACEHOLDER_ICON;
    }
}
