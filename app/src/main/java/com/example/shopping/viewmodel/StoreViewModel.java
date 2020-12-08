package com.example.shopping.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.api.WebServiceClient;
import com.example.shopping.repository.local.repository.ProductRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreViewModel extends AndroidViewModel {

    private static final String TAG = "StoreViewModel";

    private final ProductRepository repository;
    private final MutableLiveData<List<Product>> mProducts;

    public StoreViewModel(Application application) {
        super(application);
        repository = new ProductRepository(application);
        mProducts = new MutableLiveData<>();
    }

    public LiveData<List<Product>> getProducts() {
        return mProducts;
    }

    public void getProductsApi() {
        Log.d(TAG, "getProductsApi: ");

        Call<List<Product>> productsCall = WebServiceClient.productApi.getProducts();

        productsCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Code" + response.code());
                    return;
                }
                mProducts.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public LiveData<List<Product>> getAll() {
        return repository.getAll();
    }
}
