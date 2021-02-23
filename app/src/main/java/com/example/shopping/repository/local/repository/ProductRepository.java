package com.example.shopping.repository.local.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.DatabaseHandler;
import com.example.shopping.repository.local.dao.ProductDao;
import com.example.shopping.utility.AsyncResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(Application application) {
        DatabaseHandler handler = DatabaseHandler.getInstance(application);
        productDao = handler.productDao();
    }

    public LiveData<List<Product>> getAllInCart() {
        return productDao.getAllInCart();
    }

    public LiveData<List<Product>> getFavorites() {
        return productDao.getFavorites();
    }

    public void insert(Product product) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> productDao.insert(product));
    }

    public void delete(Product product) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> productDao.delete(product));
    }

    public void deleteAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(productDao::deleteAll);
    }

    public void get(int id, AsyncResponse asyncResponse) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> asyncResponse.onAsyncProcessFinish(productDao.get(id)));
    }

    public void runDynamicQuery(String queryString) {
//        new RunDynamicQuery(productDao).execute(queryString);
    }

    /*private static class RunDynamicQuery extends AsyncTask<String, Void, Void> {

        private final ProductDao productDao;

        public RunDynamicQuery(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            SimpleSQLiteQuery sqLiteQuery = new SimpleSQLiteQuery(strings[0]);
//            productDao.runDynamicQuery(sqLiteQuery);
            return null;
        }
    }*/
}
