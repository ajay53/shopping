package com.example.shopping.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.model.User;
import com.example.shopping.utility.AsyncResponse;
import com.example.shopping.utility.Constant;
import com.example.shopping.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    public static SharedPreferences sharedPref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Log.d(TAG, "init: ");

        mAuth = FirebaseAuth.getInstance();
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //viewModel.getAll(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //firebase signed in status
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent intent;
        if (currentUser != null) {
            intent = new Intent(this, NavigationActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        //offline login
        /*
        String username = sharedPref.getString(Constant.USERNAME, "");

        Intent intent;
        if (username != null && !username.isEmpty()) {
            intent = new Intent(this, NavigationActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();*/
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