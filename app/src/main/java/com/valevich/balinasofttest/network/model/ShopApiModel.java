package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "shop")
public class ShopApiModel {

    @Element(name = "offers")
    private MealsApiModel mMealsApiModel;

    @Element(name = "categories")
    private CategoriesApiModel mCategoriesApiModel;

    public MealsApiModel getMealsApiModel() {
        return mMealsApiModel;
    }

    public CategoriesApiModel getCategoriesApiModel() {
        return mCategoriesApiModel;
    }

}
