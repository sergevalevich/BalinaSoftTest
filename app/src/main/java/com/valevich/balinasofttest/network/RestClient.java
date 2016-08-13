package com.valevich.balinasofttest.network;

import com.valevich.balinasofttest.network.requests.FetchCatalogApi;
import com.valevich.balinasofttest.utils.CustomRestErrorHandler;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

@EBean
public class RestClient {

    private static final String BASE_URL = "http://ufa.farfor.ru";

    private FetchCatalogApi mFetchCatalogApi;

    @Bean
    CustomRestErrorHandler mCustomRestErrorHandler;

    @AfterInject
    void setUpRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(new SimpleXMLConverter())
                .setErrorHandler(mCustomRestErrorHandler)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mFetchCatalogApi = restAdapter.create(FetchCatalogApi.class);

    }

    public FetchCatalogApi getFetchCatalogApi() {
        return mFetchCatalogApi;
    }

}
