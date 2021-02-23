package com.example.shopping.repository.local.repository;

import android.app.Application;

import com.example.shopping.model.User;
import com.example.shopping.repository.local.DatabaseHandler;
import com.example.shopping.repository.local.dao.UserDao;
import com.example.shopping.utility.AsyncResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static final String TAG = "UserRepository";

    private final UserDao userDao;

    public UserRepository(Application application) {
        DatabaseHandler handler = DatabaseHandler.getInstance(application);
        userDao = handler.userDao();
    }

    public void get(String username, AsyncResponse asyncResponse) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> asyncResponse.onAsyncProcessFinish(userDao.get(username)));
    }

    public void insert(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> userDao.insert(user));
    }

    public void getAll(AsyncResponse asyncResponse) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> asyncResponse.onAsyncProcessFinish(userDao.getAll()));
    }
}
