package com.example.shopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.repository.ProductRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final LiveData<List<Product>> allInCart;
    ProductRepository repository;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allInCart = repository.getAllInCart();
    }

    public LiveData<List<Product>> getAllInCart() {
        return allInCart;
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public double getTotal(List<Product> products) {
        double totalPrice = 0.0;
        for (Product product :
                products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

}
