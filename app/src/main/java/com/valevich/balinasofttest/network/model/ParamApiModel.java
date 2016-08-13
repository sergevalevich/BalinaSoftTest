package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "param")
public class ParamApiModel {

    @Attribute(name = "name")
    private String mName;

    @Text
    private String mValue;

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }
}
