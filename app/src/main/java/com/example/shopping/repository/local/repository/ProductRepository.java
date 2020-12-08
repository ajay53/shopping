package com.example.shopping.repository.local.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.shopping.model.Product;
import com.example.shopping.repository.local.DatabaseHandler;
import com.example.shopping.repository.local.dao.ProductDao;
import com.example.shopping.utility.AsyncResponse;

import java.util.List;

public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(Application application) {
        DatabaseHandler handler = DatabaseHandler.getInstance(application);
        productDao = handler.productDao();
    }

    public LiveData<List<Product>> getAll() {
        return productDao.getAll();
    }

    public void insert(Product product) {
        new InsertProductAsyncTask(productDao).execute(product);
    }

    public void delete(Product product) {
        new DeleteProductAsyncTask(productDao).execute(product);
    }

    public void get(int id, AsyncResponse asyncResponse) {
        new GetProductAsyncTask(productDao, asyncResponse).execute(id);
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private final ProductDao productDao;

        private InsertProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {

        private final ProductDao productDao;

        private DeleteProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.delete(products[0]);
            return null;
        }
    }

    private static class GetProductAsyncTask extends AsyncTask<Integer, Void, Product> {

        public AsyncResponse asyncResponse = null;
        private final ProductDao productDao;

        private GetProductAsyncTask(ProductDao productDao, AsyncResponse asyncResponse) {
            this.productDao = productDao;
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected Product doInBackground(Integer... ids) {
            return productDao.get(ids[0]);
        }

        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);

            asyncResponse.onProcessFinish(product);
        }
    }
}
