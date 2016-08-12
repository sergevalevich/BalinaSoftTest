package com.valevich.balinasofttest.storage.data;

import com.valevich.balinasofttest.ui.recyclerview.utils.ItemsFinder;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class CategoryEntry implements ItemsFinder<CategoryEntry> {
    @Override
    public List<CategoryEntry> findAll() {
        return null;
    }
}
