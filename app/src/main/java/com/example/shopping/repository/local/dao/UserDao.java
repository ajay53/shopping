package com.example.shopping.repository.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopping.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from user where username = :username")
    User get(String username);

    @Insert
    void insert(User user);

    @Query("select * from user")
    List<User> getAll();
}
