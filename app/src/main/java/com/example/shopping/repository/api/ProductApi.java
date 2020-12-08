package com.example.shopping.repository.api;

import com.example.shopping.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/category/{value}")
    Call<List<Product>> getProductsByCategory(@Path("value") String category);
}
