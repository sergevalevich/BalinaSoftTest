package com.valevich.balinasofttest.storage.data;

import com.valevich.balinasofttest.ui.recyclerview.utils.MealsFinder;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean
public class Meal implements MealsFinder {

    @Override
    public List<Meal> findAllByCategory(int categoryId) {
        return new ArrayList<>();
    }

}
