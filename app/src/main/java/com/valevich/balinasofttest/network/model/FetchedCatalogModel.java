package com.valevich.balinasofttest.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "yml_catalog", strict = false)
public class FetchedCatalogModel {

    @Element(name = "shop")
    private ShopApiModel mShop;

    public ShopApiModel getShop () {
        return mShop;
    }

}
