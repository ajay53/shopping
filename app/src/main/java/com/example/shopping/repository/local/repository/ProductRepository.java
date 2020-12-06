package com.example.shopping.repository.local.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.DatabaseHandler;
import com.example.shopping.repository.local.dao.ProductDao;

import java.util.List;

public class ProductRepository {

    private ProductDao productDao;

    public ProductRepository(Application application) {
        DatabaseHandler handler = DatabaseHandler.getInstance(application);
        productDao = handler.productDao();
    }

    public void insert(Product product) {
        new InsertProductAsyncTask(productDao).execute(product);
    }

    public LiveData<List<Product>> getAll() {
        return productDao.getAll();
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao productDao;

        private InsertProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }
}
