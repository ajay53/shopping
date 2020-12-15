package com.example.shopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.shopping.repository.local.repository.UserRepository;
import com.example.shopping.utility.AsyncResponse;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";

    private final UserRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void getAll(AsyncResponse asyncResponse) {
        repository.getAll(asyncResponse);
    }
}
