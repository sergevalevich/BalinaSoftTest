package com.valevich.balinasofttest.eventbus.events;


import com.valevich.balinasofttest.storage.data.Meal;

public class MealSelectedEvent {
    private Meal mMeal;

    public MealSelectedEvent(Meal meal) {
        mMeal = meal;
    }

    public Meal getMeal() {
        return mMeal;
    }
}
