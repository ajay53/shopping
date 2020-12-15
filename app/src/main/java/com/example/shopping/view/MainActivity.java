package com.example.shopping.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.model.User;
import com.example.shopping.utility.AsyncResponse;
import com.example.shopping.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Log.d(TAG, "init: ");

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getAll(this);
    }

    @Override
    public void onAsyncProcessFinish(Object output) {
        Log.d(TAG, "onAsyncProcessFinish: ");

        List<User> users = (List<User>) output;

        Intent intent;
        if (users.size() == 0) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, NavigationActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}