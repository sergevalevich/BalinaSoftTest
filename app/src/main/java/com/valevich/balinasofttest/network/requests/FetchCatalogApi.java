package com.valevich.balinasofttest.network.requests;

import com.valevich.balinasofttest.network.model.FetchedCatalogModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FetchCatalogApi {
    @GET("/getyml/")
    void fetchCatalog(
            @Query("key") String key,
            Callback<FetchedCatalogModel> callback);
}
