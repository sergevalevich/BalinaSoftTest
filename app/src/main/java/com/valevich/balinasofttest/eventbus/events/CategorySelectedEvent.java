package com.valevich.balinasofttest.eventbus.events;

public class CategorySelectedEvent {

    private String mCategoryName;

    public CategorySelectedEvent(String categoryName) {
        mCategoryName = categoryName;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

}
