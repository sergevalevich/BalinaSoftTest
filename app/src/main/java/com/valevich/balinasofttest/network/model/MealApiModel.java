package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "offer", strict = false)
public class MealApiModel {

    @Element(name = "name", required = false)
    private String mName;

    @Element(name = "price", required = false)
    private String mPrice;

    @Element(name = "description", required = false)
    private String mDescription;


    @Element(name = "picture", required = false)
    private String mPictureUrl;

    @ElementList(inline = true, required = false)
    private List<ParamApiModel> mParams;

    @Element(name = "categoryId")
    private int mCategoryId;

    public String getName() {
        return mName;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public String getParam(String name) {
        if(mParams != null) {
            for (ParamApiModel param: mParams) {
                if(param.getName().equals(name)) return param.getValue();
            }
        }
        return "";
    }

    public int getCategoryId() {
        return mCategoryId;
    }
}

