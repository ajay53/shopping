package com.example.shopping.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.shopping.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        Button btnLogin = findViewById(R.id.btnLogin);

        Intent intent = new Intent(this, NavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        btnLogin.setOnClickListener(v -> {
            startActivity(intent);
            finish();
        });
    }
}
