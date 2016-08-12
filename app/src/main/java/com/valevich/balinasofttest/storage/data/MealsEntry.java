package com.valevich.balinasofttest.storage.data;

import com.valevich.balinasofttest.ui.recyclerview.utils.ItemsFinder;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EBean
public class MealsEntry implements ItemsFinder<MealsEntry> {
    @Override
    public List<MealsEntry> findAll() {
        return new ArrayList<MealsEntry>(Arrays.asList(new MealsEntry(), new MealsEntry()));
    }
}
