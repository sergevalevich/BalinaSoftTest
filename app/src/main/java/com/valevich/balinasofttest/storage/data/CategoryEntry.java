package com.valevich.balinasofttest.storage.data;

import com.valevich.balinasofttest.ui.recyclerview.utils.CategoriesFinder;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class CategoryEntry implements CategoriesFinder {

    private int mId;

    @Override
    public List<CategoryEntry> findAll() {
        return null;
    }

    public int getId() {
        return mId;
    }
}
