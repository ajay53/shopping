package com.example.shopping.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
    //    public static SharedPreferences sharedPref;
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
//        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        createNotificationChannel();
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constant.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth = null;
    }
}