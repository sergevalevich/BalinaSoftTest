package com.valevich.balinasofttest.eventbus.events;

import com.valevich.balinasofttest.storage.data.Category;

public class CategorySelectedEvent {

    private Category mCategory;

    public CategorySelectedEvent(Category category) {
        mCategory = category;
    }

    public Category getCategory() {
        return mCategory;
    }

}
