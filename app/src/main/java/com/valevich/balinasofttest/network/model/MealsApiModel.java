package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "offers")
public class MealsApiModel {

    @ElementList(inline = true)
    private List<MealApiModel> mMeals;

    public List<MealApiModel> getMeals() {
        return mMeals;
    }

}
