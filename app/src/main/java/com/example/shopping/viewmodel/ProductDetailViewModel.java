package com.example.shopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.repository.ProductRepository;
import com.example.shopping.utility.AsyncResponse;

import java.util.List;

public class ProductDetailViewModel extends AndroidViewModel {

    private final ProductRepository repository;

    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public void get(int id, AsyncResponse asyncResponse) {
        repository.get(id, asyncResponse);
    }
}
