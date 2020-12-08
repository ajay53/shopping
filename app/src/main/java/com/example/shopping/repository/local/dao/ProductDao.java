package com.example.shopping.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopping.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * from product WHERE id = :id")
    Product get(int id);

    @Query("Select * from product")
    LiveData<List<Product>> getAll();

    @Query("Delete from product")
    void deleteAll();
}
