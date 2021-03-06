package com.example.shopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.shopping.model.User;
import com.example.shopping.repository.local.repository.UserRepository;
import com.example.shopping.utility.AsyncResponse;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "LoginViewModel";

    private final UserRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void get(String username, AsyncResponse asyncResponse) {
        repository.get(username, asyncResponse);
    }

    public void insert(User user) {
        repository.insert(user);
    }

}
