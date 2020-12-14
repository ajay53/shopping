package com.example.shopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.repository.ProductRepository;
import com.example.shopping.utility.AsyncResponse;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {
    private static final String TAG = "FavoritesViewModel";

    private final ProductRepository repository;
    private final LiveData<List<Product>> mAllFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        mAllFavorites=  repository.getFavorites();
    }

    public LiveData<List<Product>> getFavorites() {
        return mAllFavorites;
    }

    public void get(int id, AsyncResponse asyncResponse) {
        repository.get(id, asyncResponse);
    }

    public void insert(Product product) {
        repository.insert(product);
    }
}
