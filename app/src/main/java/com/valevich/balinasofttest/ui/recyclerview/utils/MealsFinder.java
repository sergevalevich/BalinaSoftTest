package com.valevich.balinasofttest.ui.recyclerview.utils;


import com.valevich.balinasofttest.storage.data.Meal;

import java.util.List;

public interface MealsFinder {
    List<Meal> findAllByCategory(int categoryId);
}
