package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "category")
public class CategoryApiModel {

    @Text
    private String mName;

    @Attribute(name = "id")
    private int mId;

    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }

}
