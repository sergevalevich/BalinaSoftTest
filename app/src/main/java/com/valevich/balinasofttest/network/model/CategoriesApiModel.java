package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "categories")
public class CategoriesApiModel {

    @ElementList(inline = true)
    private List<CategoryApiModel> mCategories;

    public List<CategoryApiModel> getCategories () {
        return mCategories;
    }

}
