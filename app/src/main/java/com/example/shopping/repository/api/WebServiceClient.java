package com.example.shopping.repository.api;

import com.example.shopping.utility.Endpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Endpoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ProductApi productApi = retrofit.create(ProductApi.class);
}
