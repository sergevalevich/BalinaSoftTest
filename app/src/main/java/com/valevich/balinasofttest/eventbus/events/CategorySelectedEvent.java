package com.valevich.balinasofttest.eventbus.events;

public class CategorySelectedEvent {

    private int mCategoryId;

    public CategorySelectedEvent(int categoryId) {
        mCategoryId = categoryId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

}
