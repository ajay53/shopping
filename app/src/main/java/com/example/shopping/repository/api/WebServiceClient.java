package com.example.shopping.repository.api;

import com.example.shopping.utility.Endpoints;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RestApi restApi = retrofit.create(RestApi.class);
}
