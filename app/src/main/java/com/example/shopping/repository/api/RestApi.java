package com.example.shopping.repository.api;

import com.example.shopping.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {

    @GET("products")
    Call<List<Product>> getProducts();
}
