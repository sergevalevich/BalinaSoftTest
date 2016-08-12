package com.valevich.balinasofttest.ui.recyclerview.utils;


import com.valevich.balinasofttest.storage.data.MealsEntry;

import java.util.List;

public interface MealsFinder {
    List<MealsEntry> findAllByCategory(int categoryId);
}
