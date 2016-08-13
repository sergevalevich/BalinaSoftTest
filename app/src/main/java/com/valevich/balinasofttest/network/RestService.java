package com.valevich.balinasofttest.network;

import com.valevich.balinasofttest.network.model.FetchedCatalogModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import retrofit.Callback;

@EBean
public class RestService {
    @Bean
    RestClient restClient;

    public void fetchCatalog(String key, Callback<FetchedCatalogModel> callback) {
        restClient.getFetchCatalogApi().fetchCatalog(key, callback);
    }
}
