package com.example.shopping.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.shopping.R;
import com.example.shopping.databinding.ActivityLoginBinding;
import com.example.shopping.databinding.ActivityRegistrationBinding;
import com.example.shopping.model.User;
import com.example.shopping.utility.Util;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegistrationActivity";

    private ActivityRegistrationBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        init();
    }

    private void init() {
        user = new User("", "", "", "", "");
        binding.setUser(user);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    private boolean validateFields() {
        return !user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getEmailId().isEmpty() && !user.getPhoneNo().isEmpty() && !user.getAddress().isEmpty();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRegister) {
            if (validateFields()) {
                //persist in firebase
                Util.showSnackBar(this, "Registration successful");
            } else {
                Util.showSnackBar(this, "Please fill all fields");
            }
        }
    }
}