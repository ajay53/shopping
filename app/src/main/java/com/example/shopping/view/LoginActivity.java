package com.example.shopping.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.databinding.ActivityLoginBinding;
import com.example.shopping.model.User;
import com.example.shopping.utility.Constant;
import com.example.shopping.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private LoginViewModel viewModel;
    ActivityLoginBinding binding;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {
        Log.d(TAG, "init: ");

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        user = new User("", "", "", "", "");
        binding.setUser(user);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        int id = v.getId();

        if (id == R.id.btnLogin) {
            if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
                //setting preferences
                SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
                editor.putString(Constant.USERNAME, user.getUsername());
                editor.putString(Constant.PASSWORD, user.getPassword());
                editor.apply();

                Intent intent = new Intent(this, NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        } else if (id == R.id.btnRegister) {
            Intent intent = new Intent(this, RegistrationActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
