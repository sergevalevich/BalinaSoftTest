package com.valevich.balinasofttest.storage.data;

import com.valevich.balinasofttest.ui.recyclerview.utils.CategoriesFinder;
import com.valevich.balinasofttest.ui.recyclerview.utils.MealsFinder;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EBean
public class MealsEntry implements MealsFinder {

    @Override
    public List<MealsEntry> findAllByCategory(int categoryId) {
        return null;
    }

}
