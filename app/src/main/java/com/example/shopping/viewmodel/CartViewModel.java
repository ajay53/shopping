package com.example.shopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.repository.ProductRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final LiveData<List<Product>> allProducts;

    public CartViewModel(@NonNull Application application) {
        super(application);
        ProductRepository repository = new ProductRepository(application);
        allProducts = repository.getAll();
    }

    public LiveData<List<Product>> getAll() {
        return allProducts;
    }

    public double getTotal(List<Product> products){
        double totalPrice = 0.0;
        for (Product product :
                products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}
